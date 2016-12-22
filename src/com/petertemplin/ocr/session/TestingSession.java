package com.petertemplin.ocr.session;

import java.util.List;

import com.petertemplin.ocr.data.CharImage;
import com.petertemplin.ocr.model.Classifier;

/**
 * Tests the configured image set against the configured classifier.
 *
 */
public class TestingSession extends Session {
	
	private int correct, incorrect = 0;
	
	public TestingSession(Classifier classifier, List<CharImage> imageList) {
		super(classifier, imageList);
	}

	@Override
	public void run() {
		int i = 0;
		for (CharImage image : imageList) {
			if(i % 5 == 0) { System.out.println(i + "th testing iteration"); }
			classifier.test(image);
			// System.out.println("Image is: " + image.classLabel + " Classified as: " + (classifier.getClassification() - 1));
			System.out.println(classifier.getClassification());
			if (classifier.getClassification() == image.classLabel) {
				correct++;
			} else { incorrect++; }
			i++;
		}
		
		printSummary();
	}
	
	private void printSummary() {
		System.out.println("\n======================= Testing Summary =========================" +
						   "\n Test Set size: " + imageList.size() +
						   "\n Number of samples classified correctly: " + correct +
						   "\n Number of errors: " + incorrect +
						   "\n Percentage correct: " + 100*((double)correct)/(correct+incorrect) + " %\n" );
	}
	
}
