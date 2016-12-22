package com.petertemplin.ocr.session;

import java.util.List;
import java.util.ArrayList;

import com.petertemplin.ocr.data.CharImage;
import com.petertemplin.ocr.data.ImageLoader;
import com.petertemplin.ocr.data.ImageLoader.ImageSet;
import com.petertemplin.ocr.model.Classifier;
import com.petertemplin.ocr.session.Session;

/**
 * Orchestrates the creation and running of sessions for common tasks.
 */
public class SessionManager {
	
	private Classifier classifier;
	
	public SessionManager(Classifier classifier) {
		this.classifier = classifier;
	}
	
	public void trainAndTestClassifier(ImageSet imageSet, int samplesPerClass) {
		
		ImageLoader loader = new ImageLoader(imageSet, samplesPerClass);
		List<CharImage> data = loader.loadSet();
		
		List<CharImage> trainingData = new ArrayList<>();
		List<CharImage> testingData = new ArrayList<>();
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
		
		Session trainingSession = SessionFactory.createSession(classifier, trainingData, SessionType.TRAINING);
		trainingSession.run();
		Session testingSession = SessionFactory.createSession(classifier, testingData, SessionType.TESTING);
		testingSession.run();
	}
	
	public void fullTrainAndTestClassifier(ImageSet imageSet) {
		trainAndTestClassifier(imageSet, Integer.MAX_VALUE);
	}
	
	public void testClassifier(ImageSet imageSet, int samplesPerClass) {
		ImageLoader loader = new ImageLoader(imageSet, samplesPerClass);
		List<CharImage> data = loader.loadSet();
		
		Session testingSession = SessionFactory.createSession(classifier, data, SessionType.TESTING);
		testingSession.run();
	}
	
	public void classifyUserInput() {
		Session userSession = SessionFactory.createSession(classifier, null, SessionType.USER_INPUT);
		userSession.run();
	}
}