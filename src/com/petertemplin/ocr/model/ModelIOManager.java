package com.petertemplin.ocr.model;

import java.io.*;
import java.util.Scanner;

import com.petertemplin.ocr.model.FeedforwardNN;

/**
 * Provides methods for loading and saving state of classification models.
 */
public class ModelIOManager {
	
	private static final String ENCODING = "UTF-8";
		
	/**
	 * Saves the state of network into the file specified by filepath.
	 * 
	 * File will be created if does not exist and overwrite any previous data.
	 * @param network to save
	 * @param filePath to save into
	 */
	public static void save(FeedforwardNN network, String filePath) {
		System.out.println("Saving network state...");
		PrintWriter out = null;
		try {
			out = new PrintWriter(filePath, ENCODING);
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
	
	/**
	 * Loads a FeedForwardNN using the specified state file and config.
	 *
	 * @param filePath specifies the state file to load the NN from
	 * @param config parameter configurations to build the network with
	 * @return a FeedforwardNN representing the state in the given file and specified config
	 */
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
