package com.petertemplin.ocr.model;

import java.io.*;
import java.util.Scanner;

import com.petertemplin.ocr.model.FeedforwardNN;

public class ModelIOManager {
		
	public static void save(FeedforwardNN network, String filePath) {
		System.out.println("Saving network state...");
		// open the stream
		PrintWriter out = null;
		try {
			out = new PrintWriter(filePath, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// save the network weights
		for (int i = 0; i < network.config.inputSize; i++) {
			for (int j = 0; j < network.config.hiddenLayerSize; j++) {
				out.printf("%f\n", network.inputLayerWeights[i][j]);
			}
		}
		for (int i = 0; i < network.config.hiddenLayerSize; i++) {
			for (int j = 0; j < network.config.outputSize; j++) {
				out.printf("%f\n", network.hiddenLayerWeights[i][j]);
			}
		}
		
		if (out!= null) {
			out.close();
		}
		
	}
	
	public static FeedforwardNN loadFeedforwardNN(String filePath, FeedforwardNNConfig config) {
		System.out.println("Loading network state...");
		// open the stream
		Scanner reader = null;
		try {
			reader = new Scanner(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		// create a new network with the config
		FeedforwardNN network = new FeedforwardNN(config);
		System.out.println("Using config: ");
		System.out.println(config.weightInitFactor);
		System.out.println(config.learningRate);
		System.out.println(config.inputSize);
		System.out.println(config.hiddenLayerSize);
		System.out.println(config.outputSize);
		System.out.println(config.labelOffset);

		
		// load the network weights
		for (int i = 0; i < network.config.inputSize; i++) {
			for (int j = 0; j < network.config.hiddenLayerSize; j++) {
				network.inputLayerWeights[i][j] = reader.nextDouble();
			}
		}
		for (int i = 0; i < network.config.hiddenLayerSize; i++) {
			for (int j = 0; j < network.config.outputSize; j++) {
				network.hiddenLayerWeights[i][j] = reader.nextDouble();
			}
		}
		
		if (reader!= null) {
			reader.close();
		}
		return network;
	}

}
