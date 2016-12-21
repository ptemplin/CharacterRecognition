package com.petertemplin.ocr.model;

import java.util.List;

import com.petertemplin.ocr.data.CharImage;

public interface Classifier {

	/**
	 * Train the classifier on the given input data.
	 */
	void train(List<CharImage> trainingData);
	
	/**
	 * Test the classifier on the given input data.
	 */
	void test(CharImage testingData);
	
	/**
	 * Retrieves the most recent classification output of the classifier.
	 * -1 if no classifications have been made.
	 */
	int getClassification();
	
}
