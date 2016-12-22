package com.petertemplin.ocr.session;

import java.util.List;

import com.petertemplin.ocr.data.CharImage;
import com.petertemplin.ocr.model.Classifier;

/**
 * Abstract session uses an classifier and image list.
 */
public abstract class Session {
	
	protected final Classifier classifier;
	protected final List<CharImage> imageList;
	
	public Session(Classifier classifier, List<CharImage> imageList) {
		this.classifier = classifier;
		this.imageList = imageList;
	}
	
	public abstract void run();
	
}
