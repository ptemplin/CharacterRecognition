package com.petertemplin.ocr.data;

public abstract class InputData {

	public double[] data;
	public int label;
	
	public InputData(int label) {
		this.label = label;
	}
	
	public void setData(double[] data) {
		this.data = data;
	}
	
}
