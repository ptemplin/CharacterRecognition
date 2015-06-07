package com.petertemplin;

public class MockDigitImage extends DigitImage {

	public MockDigitImage() {
		super();
		rows = DEFAULT_ROWS;
		columns = DEFAULT_COLUMNS;
		data = new double[DEFAULT_ROWS*DEFAULT_COLUMNS];
		fillDataAtRandom();
	}
	
	public void fillDataAtRandom() {
		for (int i = 0; i < data.length; i++) {
			data[i] = (int) (Math.random() * 255);
		}
	}
	
}
