package com.petertemplin;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class NetworkManager {
	
	public List<InputData> data;
	public static int CLASSES_TO_USE = 2;
	
	public NetworkManager() {
		data = LoadingUtils.loadChars74K(200, CLASSES_TO_USE);
		//data = ImageUtils.loadImages();
	}
	
	public void startSimpleSession() {
		Session session = new Session(data, data, CLASSES_TO_USE);
		session.run();
	}
	
	public void startRevisedSession() {
		// populate the training and testing data
		List<InputData> trainingData = new ArrayList<>();
		List<InputData> testingData = new ArrayList<>();
		// shrink the testing size to 10% there are more than 100 samples
		double trainingSize = 0.8;
		if (data.size() > 100) {
			trainingSize = 0.9;
		}
		for(int i = 0; i < data.size(); i++) {
			if (i < data.size()*trainingSize) {
				trainingData.add(data.get(i));
			} else {
				testingData.add(data.get(i));
			}
		}
				
		// create and run the session
		RevisedSession session = new RevisedSession(trainingData, testingData, CLASSES_TO_USE);
		session.run();
	}
	
	public void startTestingSession() {
		
		// populate the training and testing data
		List<InputData> trainingData = new ArrayList<>();
		List<InputData> testingData = new ArrayList<>();
		// shrink the testing size to 10% there are more than 100 samples
		double trainingSize = 0.8;
		if (data.size() > 100) {
			trainingSize = 0.9;
		}
		for(int i = 0; i < data.size(); i++) {
			if (i < data.size()*trainingSize) {
				trainingData.add(data.get(i));
			} else {
				testingData.add(data.get(i));
			}
		}
		
		// create and run the session
		Session session = new Session(trainingData, testingData, CLASSES_TO_USE);
		session.run();
	}
	
	public static void runVaryingParams(List<InputData> data) {
		// set testing parameter
		double[] weightInitFactors = {0.02};
		double[] learningRates = {0.00005};
		int[] numHiddenLayers = {1};
			
		for(int i = 0; i < numHiddenLayers.length; i++) {
			int numHLayers = numHiddenLayers[i];
			NeuralNetwork.NUM_HIDDEN_LAYERS = numHLayers;
			for(int j = 0; j < learningRates.length; j++) {
				double learningRate = learningRates[j];
				NetworkNode.LEARNING_RATE = learningRate;
				System.out.println("Starting with a new learning rate");
				for(int k = 0; k < weightInitFactors.length; k++) {
					double weightInitFactor = weightInitFactors[k];
					NetworkNode.WEIGHT_INIT_FACTOR = weightInitFactor;
					ReportingUtils.writeStringToLog("\n==================== New Test =====================" +
							"\nWeight init factor: " + weightInitFactor +
							"\nNumber of hidden layers: " + numHLayers +
							"\nLearning rate: " + learningRate);
					Session session = new Session(data, data, CLASSES_TO_USE);
					session.run();
				}
			}
		}
		ReportingUtils.closeWriter();
	}
}