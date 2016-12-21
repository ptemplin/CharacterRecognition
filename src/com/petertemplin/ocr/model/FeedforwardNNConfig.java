package com.petertemplin.ocr.model;

public class FeedforwardNNConfig {
	
	public static final double DEFAULT_WEIGHT_INIT_FACTOR = 0.005;
	public static final double DEFAULT_LEARNING_RATE = 0.00005;

	public static final int DEFAULT_INPUT_SIZE = 1024;
	public static final int DEFAULT_HIDDEN_LAYER_SIZE = 1024;
	public static final int DEFAULT_OUTPUT_SIZE = 10;
		
	public static final int DEFAULT_LABEL_OFFSET = 0;
	
	/**
	 * Used as basis for random weight initialization.
	 */
	public final double weightInitFactor;
	
	/**
	 * The learning rate for gradient descent.
	 */
	public final double learningRate;
	
	public final int inputSize;
	public final int hiddenLayerSize;
	public final int outputSize;
	
	/**
	 * Used in the event that we are dealing with data where the labels
	 * don't start at zero (eg. letters start at 11)
	 */
	public final int labelOffset;
	
	private FeedforwardNNConfig(
			double weightInitFactor, 
			double learningRate, 
			int inputSize,
			int hiddenLayerSize, 
			int outputSize,
			int labelOffset) {
		this.weightInitFactor = weightInitFactor;
		this.learningRate = learningRate;
		this.inputSize = inputSize;
		this.hiddenLayerSize = hiddenLayerSize;
		this.outputSize = outputSize;
		this.labelOffset = labelOffset;
	}
	
	public static final class Builder {
		
		private double weightInitFactor = DEFAULT_WEIGHT_INIT_FACTOR;
		private double learningRate = DEFAULT_LEARNING_RATE;
		private int inputSize = DEFAULT_INPUT_SIZE;
		private int hiddenLayerSize = DEFAULT_HIDDEN_LAYER_SIZE;
		private int outputSize = DEFAULT_OUTPUT_SIZE;
		private int labelOffset = DEFAULT_LABEL_OFFSET;
		
		public Builder weightInitFactor(double wif) {
			this.weightInitFactor = wif;
			return this;
		}
		
		public Builder learningRate(double lr) {
			this.learningRate = lr;
			return this;
		}
		
		public Builder inputSize(int is) {
			this.inputSize = is;
			return this;
		}
		
		public Builder hiddenLayerSize(int hls) {
			this.hiddenLayerSize = hls;
			return this;
		}
		
		public Builder outputSize(int os) {
			this.outputSize = os;
			return this;
		}
		
		public Builder labelOffset(int lo) {
			this.labelOffset = lo;
			return this;
		}
		
		public FeedforwardNNConfig build() {
			return new FeedforwardNNConfig(
					weightInitFactor, 
					learningRate,
					inputSize,
					hiddenLayerSize,
					outputSize,
					labelOffset
					);
		}
	}
	
}
