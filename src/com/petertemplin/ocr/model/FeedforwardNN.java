package com.petertemplin.ocr.model;

import java.util.List;

import com.petertemplin.ocr.data.CharImage;

/**
 * This is currently implemented as a modified feedforward NN. A true feedforward NN
 * optimizes the cost function to convergence for each instance of training data. This
 * modified version takes only a single step towards optimization for each training
 * instance. This was found to reduce overfitting, vastly increase speed, and have similar
 * performance in experiments.
 */
public class FeedforwardNN implements Classifier {
	
	public FeedforwardNNConfig config;
	
	// activations
	public double[] hiddenLayerActivations;
	public double[] outputLayerActivations;
	// weights
	public double[][] inputLayerWeights;
	public double[][] hiddenLayerWeights;
	// deltas
	public double[] hiddenLayerDeltas;
	public double[] outputLayerDeltas;
	
	public FeedforwardNN(FeedforwardNNConfig config) {
		this.config = config;
		initialize();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void train(List<CharImage> trainingData) {
		int i = 0;
		for (CharImage image : trainingData) {
			i++;
			if(i % 5 == 0) { System.out.println(i + "th training iteration");	}
			forwardPropagateData(image);
			backPropagate(image);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void test(CharImage testData) {
		forwardPropagateData(testData);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getClassification() {
		return findMaxOutput() + config.labelOffset;
	}
	
	/**
	 * Initialize all of the parameters of the network.
	 */
	private void initialize() {
		System.out.println("Initializing network...");
		
		// initialize all activations to zero
		System.out.println("Initializing activations...");
		hiddenLayerActivations = new double[config.hiddenLayerSize];
		for(int i=0;i<config.hiddenLayerSize;i++){hiddenLayerActivations[i]=0;}
		outputLayerActivations = new double[config.outputSize];
		for(int i=0;i<config.outputSize;i++){outputLayerActivations[i]=0;}
		
		// randomly initialize all weights
		System.out.println("Randomizing weights...");
		inputLayerWeights = new double[config.inputSize][config.hiddenLayerSize];
		for (int i = 0; i < config.inputSize; i++) {
			for (int j = 0; j < config.hiddenLayerSize; j++) {
				inputLayerWeights[i][j] = generateRandomWeight();
			}
		}
		hiddenLayerWeights = new double[config.hiddenLayerSize][config.outputSize];
		for (int i = 0; i < config.hiddenLayerSize; i++) {
			for (int j = 0; j < config.outputSize; j++) {
				hiddenLayerWeights[i][j] = generateRandomWeight();
			}
		}
		
		// set all deltas to zero
		System.out.println("Initializing deltas...");
		hiddenLayerDeltas = new double[config.hiddenLayerSize];
		for(int i=0;i<config.hiddenLayerSize;i++){hiddenLayerDeltas[i]=0;}
		outputLayerDeltas = new double[config.outputSize];
		for(int i=0;i<config.outputSize;i++){outputLayerDeltas[i]=0;}
	}
	
	/**
	 * Propagate the image data through the hidden layer and output layer.
	 * @param image data to propagate
	 */
	private void forwardPropagateData(CharImage image) {
		calculateActivationsForHiddenLayer(image);
		calculateOutputs();
		// printSummaryOfOutput();
	}
	
	/**
	 * Update the network parameters using the results of a forward propagation.
	 * @param image previously forward propagated
	 */
	private void backPropagate(CharImage image) {
		calculateOutputDeltas(image);
		updateHiddenLayer();
		updateInputLayer(image);
	}
	
	/**
	 * Propagate the image through the first layer of the network.
	 * @param image data to use
	 */
	private void calculateActivationsForHiddenLayer(CharImage image) {
		//System.out.println("Propagating data to hidden layer");
		for (int i = 0; i < config.hiddenLayerSize; i++) {
			double activation = 0;
			for (int j = 0; j < config.inputSize; j++) {
				activation += image.data[j]*inputLayerWeights[j][i];
			}
			activation = applySigmoidFunction(activation);
			hiddenLayerActivations[i] = activation;
		}
	}
	
	/**
	 * Calculate the activiations in the output layer based on hidden layer
	 * weights and activations.
	 */
	private void calculateOutputs() {
		for (int i = 0; i < config.outputSize; i++) {
			double activation = 0;
			for (int j = 0; j < config.hiddenLayerSize; j++) {
				activation += hiddenLayerActivations[j]*hiddenLayerWeights[j][i];
			}
			activation = applySigmoidFunction(activation);
			outputLayerActivations[i] = activation;
		}
	}
	
	/**
	 * Calculate the delta (error) in the classification of the specified image.
	 * @param image previously classified
	 */
	private void calculateOutputDeltas(CharImage image) {
		for(int i = 0; i < config.outputSize; i++) {
			if(i == image.classLabel - 1) {
				outputLayerDeltas[i] = 1 - outputLayerActivations[i];
			} else {
				outputLayerDeltas[i] = - outputLayerActivations[i];
			}
		}
	}
	
	/**
	 * Calculate hidden layer delats and update the weights using output layer deltas and 
	 * the configured learning rate.
	 */
	private void updateHiddenLayer() {
		
		//System.out.println("Updating hidden layer...");
		for (int i = 0; i < config.hiddenLayerSize; i++) {
			double delta = 0;
			// for each child...
			for (int j = 0; j < config.outputSize; j++) {
				// increase the delta count
				delta += outputLayerDeltas[j]*hiddenLayerWeights[i][j];
				// change the weight of the connection to reflect the past 
				// forward propagation
				hiddenLayerWeights[i][j] += config.learningRate*hiddenLayerActivations[i]*outputLayerDeltas[j];
			}
			// set this nodes delta
			hiddenLayerDeltas[i] = delta * hiddenLayerActivations[i] * (1 - hiddenLayerActivations[i]);
		}
	}
	
	/**
	 * Update the input layer of the network using the hidden layer deltas, image data, and configured
	 * learning rate.
	 * @param image previously classified
	 */
	private void updateInputLayer(CharImage image) {
		//System.out.println("Updating input layer...");
		for (int i = 0; i < config.inputSize; i++) {
			// for each child...
			for (int j = 0; j < config.hiddenLayerSize; j++) {
				// change the weight of the connection to reflect the past 
				// forward propagation
				inputLayerWeights[i][j] += config.learningRate*image.data[i]*hiddenLayerDeltas[j];
			}
		}
	}
	
	/**
	 * Finds the maximum output activation in the output layer.
	 * @return index of max output (0 to number of outputs - 1)
	 */
	private int findMaxOutput() {
		double max = 0;
		int indexOfMax = 0;
		for(int i = 0; i < config.outputSize; i++) {
			if(outputLayerActivations[i] > max) {
				max = outputLayerActivations[i];
				indexOfMax = i;
			}
		}
		return indexOfMax + 1;
	}
	
	/**
	 * Prints a debugging summary of the output layer to stdout.
	 */
	public void printSummaryOfOutput() {
		for(int i = 0; i < config.outputSize; i++) {
			System.out.println("Activation of " + i + ": " + outputLayerActivations[i]);
		}
	}
	
	/**
	 * @return a random weight between +/- weightInitFactor
	 */
	private double generateRandomWeight() {
		return Math.random()*(2*config.weightInitFactor) - config.weightInitFactor;
	}
	
	/**
	 * Applies the sigmoid function to the given input.
	 * @return sigmoid(input)
	 */
	private double applySigmoidFunction(double input) {
		return (1 / (1 + Math.exp(-input)));
	}
}
