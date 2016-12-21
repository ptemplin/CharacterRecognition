package com.petertemplin.ocr.application;

import com.petertemplin.ocr.model.Classifier;
import com.petertemplin.ocr.model.FeedforwardNNConfig;
import com.petertemplin.ocr.model.ModelIOManager;
import com.petertemplin.ocr.session.SessionManager;

public class UserInputDemo {
	
	public static String classifierFilePath = "C:/Users/Me/Pictures/Characters/improvedNN.nn";

	/*
	 * 
	 */
	public static void main(String[] args) throws Exception {
		// use the default config
		FeedforwardNNConfig config = new FeedforwardNNConfig.Builder().build();
		Classifier classifier = ModelIOManager.loadFeedforwardNN(classifierFilePath, config);
		SessionManager manager = new SessionManager(classifier);
		manager.classifyUserInput();
	}
	
}