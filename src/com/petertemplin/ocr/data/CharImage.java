package com.petertemplin.ocr.data;

/**
 * Represents a classifiable character image with the human-tagged classification.
 */
public class CharImage {

	public final double[] data;
	public final int classLabel;
	public final int imageNum;
		
	public CharImage(int[][] pixels, int label, int imageNum) {
		classLabel = label;
		this.imageNum = imageNum;
		data = new double[pixels.length*pixels[0].length];
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				data[i*pixels.length + j] = pixels[i][j];
			}
		}
	}
}
