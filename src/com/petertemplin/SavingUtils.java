package com.petertemplin;

import java.io.*;

public class SavingUtils {
	
	public static String networkFilePath = "C:\\Users\\Me\\Pictures\\Character\\improvedNN.nn";
	
	public static void saveNetworkState(ImprovedNeuralNetwork network) {
		// open the stream
		PrintWriter out = null;
		try {
			out = new PrintWriter(networkFilePath, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// save the network weights
		double[] inputWeights = new double[ImprovedNeuralNetwork.INPUT_SIZE*ImprovedNeuralNetwork.LAYER_SIZE];
		double[] hiddenWeights = new double[ImprovedNeuralNetwork.LAYER_SIZE*ImprovedNeuralNetwork.OUTPUT_SIZE];
		for (int i = 0; i < network.INPUT_SIZE; i++) {
			for (int j = 0; j < network.LAYER_SIZE; j++) {
				out.println(network.inputLayerWeights[i][j]);
			}
		}
		for (int i = 0; i < network.LAYER_SIZE; i++) {
			for (int j = 0; j < network.OUTPUT_SIZE; j++) {
				out.println(network.hiddenLayerWeights[i][j]);
			}
		}
		
		if (out!= null) {
			out.close();
		}
		
	}

}
