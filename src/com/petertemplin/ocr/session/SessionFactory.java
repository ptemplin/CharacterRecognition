package com.petertemplin.ocr.session;

import java.util.List;

import com.petertemplin.ocr.data.CharImage;
import com.petertemplin.ocr.model.Classifier;

public class SessionFactory {

	public static Session createSession(Classifier classifier, List<CharImage> imageData, SessionType type) {
		switch (type) {
		case TRAINING:
			return new TrainingSession(classifier, imageData);
		case TESTING:
			return new TestingSession(classifier, imageData);
		case USER_INPUT:
			return new UserInputSession(classifier, imageData);
		default:
			return new UserInputSession(classifier, imageData);
		}
	}
	
}
