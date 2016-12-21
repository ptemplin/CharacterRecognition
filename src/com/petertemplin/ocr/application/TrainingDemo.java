package com.petertemplin.ocr.application;

import com.petertemplin.ocr.data.ImageLoader.ImageSet;
import com.petertemplin.ocr.model.Classifier;
import com.petertemplin.ocr.model.FeedforwardNN;
import com.petertemplin.ocr.model.FeedforwardNNConfig;
import com.petertemplin.ocr.session.SessionManager;

public class TrainingDemo {
	
	public static String classifierFilePath = "C:/Users/Me/Pictures/Characters/32x32-digits-1.nn";

	/*
	 * 
	 */
	public static void main(String[] args) throws Exception {
		// use the default config
		FeedforwardNNConfig config = new FeedforwardNNConfig.Builder().outputSize(10).learningRate(0.00005).build();
		Classifier classifier = new FeedforwardNN(config);
		SessionManager manager = new SessionManager(classifier);
		manager.trainAndTestClassifier(ImageSet.CHARS74K_BINARY, 500);
	}
	
}
