package com.petertemplin.ocr.session;

import java.util.List;

import com.petertemplin.ocr.data.CharImage;
import com.petertemplin.ocr.model.Classifier;

public class TrainingSession extends Session {
	
	public TrainingSession(Classifier classifier, List<CharImage> imageList) {
		super(classifier, imageList);
	}

	@Override
	public void run() {
		classifier.train(imageList);
	}
}
