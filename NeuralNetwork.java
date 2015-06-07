package com.petertemplin;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {

	public static int INPUT_SIZE = 784;
	public static int LAYER_SIZE = 784;
	public static int OUTPUT_SIZE = 2;
	public static int NUM_HIDDEN_LAYERS = 1;
	
	public static NeuralNetwork instance = null;
	
	List<NetworkNode> inputLayer;
	List<List<NetworkNode>> hiddenLayers;
	List<NetworkNode> outputLayer;
	
	InputData currentData;
	
	public NeuralNetwork() {
		currentData = null;
		inputLayer = new ArrayList<NetworkNode>(INPUT_SIZE);
		hiddenLayers = new ArrayList<List<NetworkNode>>();
		for (int i = 1; i <= NUM_HIDDEN_LAYERS; i++) {
			hiddenLayers.add(new ArrayList<NetworkNode>(LAYER_SIZE));
		}
		outputLayer = new ArrayList<NetworkNode>(OUTPUT_SIZE);
		initializeNetwork();
	}
	
	public static NeuralNetwork getInstance() {
		if (instance == null) {
			instance = new NeuralNetwork();
		}
		return instance;
	}
	
	public void initializeNetwork() {
		
		// allocate the nodes first
		for (int i = 1; i <= INPUT_SIZE; i++) {
			inputLayer.add(new NetworkNode(1, i, LAYER_SIZE, 0));
		}
		for (int i = 0; i < NUM_HIDDEN_LAYERS; i++) {
			List<NetworkNode> hiddenLayer = hiddenLayers.get(i);
			for (int j = 1; j <= LAYER_SIZE; j++) {
				if (NUM_HIDDEN_LAYERS == 1) {
					hiddenLayer.add(new NetworkNode(2 + i, j, OUTPUT_SIZE, INPUT_SIZE));
				} else if (i == 0) {
					hiddenLayer.add(new NetworkNode(2 + i, j, LAYER_SIZE, INPUT_SIZE));
				} else if (i == NUM_HIDDEN_LAYERS - 1) {
					hiddenLayer.add(new NetworkNode(2 + i, j, OUTPUT_SIZE, LAYER_SIZE));
				} else {
					hiddenLayer.add(new NetworkNode(2 + i, j, LAYER_SIZE, LAYER_SIZE));
				}
			}
		}
		for (int i = 1; i <= OUTPUT_SIZE; i++) {
			outputLayer.add(new NetworkNode(2 + NUM_HIDDEN_LAYERS, i, 0, LAYER_SIZE));
		}
		
		NetworkNode currentNode = null;
		NetworkNode childToAdd = null;
		// create the connections for each node in the input layer
		for (int i = 0; i < INPUT_SIZE; i++) {
			currentNode = inputLayer.get(i);
			List<NetworkNode> firstHiddenLayer = hiddenLayers.get(0);
			for (int j = 0; j < LAYER_SIZE; j++) {
				childToAdd = firstHiddenLayer.get(j);
				currentNode.addChild(childToAdd);
			}
		}
		// the hidden layer
		for (int i = 0; i < NUM_HIDDEN_LAYERS; i++) {
			List<NetworkNode> currentLayer = hiddenLayers.get(i);
			List<NetworkNode> nextLayer;
			if (i == NUM_HIDDEN_LAYERS - 1) {
				nextLayer = outputLayer;
			} else {
				nextLayer = hiddenLayers.get(i);
			}
			for (int j = 0; j < LAYER_SIZE; j++) {
				currentNode = currentLayer.get(j);
				for (int k = 0; k < nextLayer.size(); k++) {
					childToAdd = nextLayer.get(k);
					currentNode.addChild(childToAdd);
				}
			}
		}
	}
	
	public void setCurrentData(InputData data) {
		this.currentData = data;
	}
	
	public void forwardPropagateData() {
		setActivationsOfInputLayer();
		calculateActivationsForHiddenLayers();
		calculateOutputs();
		printSummaryOfOutput();
	}
	
	private void setActivationsOfInputLayer() {
		for(int i = 0; i < INPUT_SIZE; i++) {
			inputLayer.get(i).setActivation(currentData.data[i]);
		}
	}
	
	private void calculateActivationsForHiddenLayers() {
		for(List<NetworkNode> hiddenLayer : hiddenLayers) {
			for(NetworkNode node : hiddenLayer) {
				node.calculateActivation();
			}
		}
	}
	
	private void calculateOutputs() {
		for(NetworkNode node : outputLayer) {
			node.calculateActivation();
		}
	}
	
	public void backPropagate() {
		calculateOutputDeltas();
		updateHiddenLayers();
		updateInputLayer();
	}
	
	private void calculateOutputDeltas() {
		for(NetworkNode node : outputLayer) {
			if(node.nodeNumber - 1 == currentData.label) {
				node.currentDelta = 1 - node.currentActivation;
			} else {
				node.currentDelta = -node.currentActivation;
			}
		}
	}
	
	private void updateHiddenLayers() {
		for(int i = NUM_HIDDEN_LAYERS; i > 0; i--) {
			List<NetworkNode> hiddenLayer = hiddenLayers.get(i-1);
			for(NetworkNode node : hiddenLayer) {
				node.learn();
			}
		}
	}
	
	private void updateInputLayer() {
		for(NetworkNode node : inputLayer) {
			node.learn();
		}
	}
	
	public int getClassification() {
		return findMaxOutput();
	}
	
	private int findMaxOutput() {
		double max = 0;
		NetworkNode maxOutput = null;
		for(NetworkNode node : outputLayer) {
			if(node.currentActivation > max) {
				max = node.currentActivation;
				maxOutput = node;
			}
		}
		if (maxOutput != null) {
			return maxOutput.nodeNumber;
		} else {
			return 0;
		}
	}
	
	public boolean printSummaryOfOutput() {
		
		for(int i = 0; i < OUTPUT_SIZE; i++) {
			System.out.println("Activation of " + i + ": " + outputLayer.get(i).currentActivation);
		}
		
		int classification = getClassification() - 1;
		return classification == currentData.label;
	}
	
	public boolean isClassificationCorrect() {
		int classification = getClassification() - 1;
		return classification == currentData.label;
	}
	
}
