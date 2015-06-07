package com.petertemplin;

import java.util.List;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class RevisedSession {
	
	public List<InputData> testingData;
	public List<InputData> trainingData;
	public RevisedNeuralNetwork network;
	public int numCorrect, numWrong;
	
	public RevisedSession(List<InputData> train, List<InputData> test, int outputSize) {
		this.testingData = test;
		this.trainingData = train;
		this.network = new RevisedNeuralNetwork(trainingData.size());
		numCorrect = numWrong = 0;
	}
	
	public void run() {
		trainNetwork();
		testNetwork();
		printSummary();
	}
	
	private void trainNetwork() {
		while (Math.abs(network.currentCostFunction - network.previousCostFunction) > RevisedNeuralNetwork.GRADIENT_THRESHOLD) {
			network.previousCostFunction = network.currentCostFunction;
			network.currentCostFunction = 0;
			int i = 0;
			for (InputData currentData : trainingData) {
				if(i % 5 == 0) { System.out.println(i + "th training iteration");	}
				network.setCurrentData(currentData);
				network.forwardPropagateData();
				network.backPropagate();
				i++;
			}
			network.optimize();
			System.out.println("Previous cost function: " + network.previousCostFunction);
			System.out.println("Current cost function: " + network.currentCostFunction);
		}
	}
	
	private void testNetwork() {
		int i = 0;
		for (InputData currentData : testingData) {
			if(i % 5 == 0) { System.out.println(i + "th testing iteration"); }
			network.setCurrentData(currentData);
			network.forwardPropagateData();
			//displayImageWithLabel(((DigitImage)currentData).image, currentData.label,
			//		network.getClassification());
			if (network.isClassificationCorrect()) {
				numCorrect++;
			} else { numWrong++; }
			i++;
		}
	}
	
	private void printSummary() {
		System.out.println("\n======================= Summary =========================" +
						   "\n Training size: " + trainingData.size() +
						   "\n Testing size: " + testingData.size() +
						   "\n Number of samples classified correctly: " + numCorrect +
						   "\n Number of errors: " + numWrong +
						   "\n Percentage correct: " + 100*((double)numCorrect)/(numCorrect+numWrong) + " %\n" );
		ReportingUtils.writeStringToLog("\nPercentage correct: " + ((double)numCorrect)/(numCorrect+numWrong) +"\n");
	}
	
	public void displayImageWithLabel(BufferedImage image, int label, int classification) {
		ImageUtils.displayImage(image, label - 1, classification - 1);
		try { wait(500); } catch (Exception e) {}
	}
	
}
