package com.petertemplin.ocr.application;

import com.petertemplin.ocr.data.ImageLoader.ImageSet;
import com.petertemplin.ocr.model.Classifier;
import com.petertemplin.ocr.model.FeedforwardNN;
import com.petertemplin.ocr.model.FeedforwardNNConfig;
import com.petertemplin.ocr.session.SessionManager;

/**
 * Configures a feedforward NN with the specified parameters. Tests and trains
 * the network on the specified image set with the specified images per class.
 */
public class TrainingDemo {
	
	/**
	 * The filepath for where to output the trained network state.
	 */
	public static final String outputFilePath = "C:/Users/Me/Pictures/Characters/32x32-digits-1.nn";
	
	private static final int OUTPUT_SIZE = 10;
	private static final double LEARNING_RATE = 0.00005;
	private static final ImageSet IMAGE_SET = ImageSet.CHARS74K_BINARY;
	private static final int IMAGES_PER_CLASS = 500;

	public static void main(String[] args) throws Exception {
		// use the default config
		FeedforwardNNConfig config = new FeedforwardNNConfig.Builder()
										.outputSize(OUTPUT_SIZE)
										.learningRate(LEARNING_RATE)
										.build();
		Classifier classifier = new FeedforwardNN(config);
		SessionManager manager = new SessionManager(classifier);
		manager.trainAndTestClassifier(IMAGE_SET, IMAGES_PER_CLASS);
	}
	
}
