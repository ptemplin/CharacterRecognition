package com.petertemplin.ocr.util;

import com.petertemplin.ocr.data.CharImage;

/**
 * Utilities for apply image filters.
 */
public class Filters {
	
	public static final int BW_THRESHOLD = 100;

	public enum Filter {
		ENHANCED_BW,
		NONE
	};
	
	public static void applyFilter(CharImage image, Filter f) {
		if (f == Filter.ENHANCED_BW) {
			applyBWFilter(image);
		}
	}
	
	private static void applyBWFilter(CharImage image) {
		for (int i = 0; i < image.data.length;i++) {
			if (image.data[i] > BW_THRESHOLD) {
				image.data[i] = 255;
			} else {
				image.data[i] = 0;
			}
		}
	}
}
