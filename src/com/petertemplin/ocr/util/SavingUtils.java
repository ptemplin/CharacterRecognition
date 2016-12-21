package com.petertemplin.ocr.util;

import java.io.*;
import java.util.Scanner;

import com.petertemplin.ocr.model.FeedforwardNN;

public class SavingUtils {
	
	public static String networkFilePath = "C:/Users/Me/Pictures/Characters/improvedNN.nn";
	
	public static void saveNetworkState(FeedforwardNN network) {
		System.out.println("Saving network state...");
		// open the stream
		PrintWriter out = null;
		try {
			out = new PrintWriter(networkFilePath, "UTF-8");
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
	
	public static void loadNetworkState(FeedforwardNN network) {
		System.out.println("Loading network state...");
		// open the stream
		Scanner reader = null;
		try {
			reader = new Scanner(new File(networkFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		
	}

}
