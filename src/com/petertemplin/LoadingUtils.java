package com.petertemplin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class LoadingUtils {
	// options to be used
	public static final int ALL_IMAGES = -1;
	public static final int ALL_CLASSES = 100;
	public static final int NUMBERS_ONLY = 200;
	public static final int LETTERS_ONLY = 300;
	public static final int BINARY_ONLY = 400;
	public static final int LOWER_CASE = 500;
	public static final int UPPER_CASE = 600;

	// total number of available 74k classes
	public static int NUM_74K_CLASSES = 62;

	// chars74k data set info
	public static int IMAGE_SIZE_74K = 32;
	public static final String PATH_TO_74K_IMAGES = "C:\\Users\\Me\\Pictures\\Characters\\English\\Fnt\\";

	// personal data set info
	public static final String PATH_TO_IMAGES = "C:\\Users\\Me\\Pictures\\Characters\\";
	public static String[] FOLDER_NAMES = {"Zero\\", "One\\", "Two\\", "Three\\", "Four\\", "Five\\",
		"Six\\", "Seven\\", "Eight\\", "Nine\\"};

	public static List<InputData> loadChars74K(int numFromEachClass, int classesToUse) {

		ImprovedNeuralNetwork.INPUT_SIZE = IMAGE_SIZE_74K*IMAGE_SIZE_74K;
		ImprovedNeuralNetwork.LAYER_SIZE = IMAGE_SIZE_74K*IMAGE_SIZE_74K;
		ImprovedNeuralNetwork.OUTPUT_SIZE = 26;

		// Adjust range of classes to use
		int startOfRange = 1;
		int endOfRange = NUM_74K_CLASSES;

		switch(classesToUse) {
		case ALL_CLASSES:
			break;
		case NUMBERS_ONLY:
			endOfRange = 10;
			break;
		case LETTERS_ONLY:
			startOfRange = 11;
			break;
		case BINARY_ONLY:
			endOfRange = 2;
			break;
		case LOWER_CASE:
			startOfRange = 11;
			endOfRange = 36;
			break;
		case UPPER_CASE:
			startOfRange = 37;
			endOfRange = 62;
			break;
		default:
			endOfRange = classesToUse;
			break;
		}

		// progress message
		System.out.println("Reading " + numFromEachClass*(endOfRange - startOfRange + 1) + " images...");

		ArrayList<InputData> images = new ArrayList<>();
		String pathToImage;
		for(int i = startOfRange; i <= endOfRange; i++) {
			System.out.println("Loading class " + i + "...");
			String subFolder = "Sample" + getStringThatFillsDigits(i, 3) + "\\";
			if (numFromEachClass == ALL_IMAGES) {
				int j = 0;
				int label = i;
				while (true) {
					++j;
					if (j % 100 == 0) { System.out.println("Image number " + j); }
					pathToImage = PATH_TO_74K_IMAGES + subFolder + 
							"img" + getStringThatFillsDigits(i,3) + "-" + getStringThatFillsDigits(j, 5) + ".png";
					try {
						File file = new File(pathToImage);
						BufferedImage image = ImageIO.read(file);
						DigitImage digitImage = new DigitImage(IMAGE_SIZE_74K,
								IMAGE_SIZE_74K,
								ImageUtils.scalePixelsToHalf(ImageUtils.scalePixelsToHalf(ImageUtils.getPixels(pathToImage))),
								label,
								image);
						images.add(digitImage);
					} catch (IOException e) {
						break;
					}
				}
			} else { // loading specific number from each class
				int label = i;
				for (int j = 1; j <= numFromEachClass; j++) {
					pathToImage = PATH_TO_74K_IMAGES + subFolder + 
							"img" + getStringThatFillsDigits(i,3) + "-" + getStringThatFillsDigits(j, 5) + ".png";
					if (j % 100 == 0) { System.out.println("Image number " + j); }
					try {
						File file = new File(pathToImage);
						BufferedImage image = ImageIO.read(file);
						DigitImage digitImage = new DigitImage(IMAGE_SIZE_74K,
								IMAGE_SIZE_74K,
								ImageUtils.scalePixelsToHalf(ImageUtils.scalePixelsToHalf(ImageUtils.getPixels(pathToImage))),
								label,
								image);
						images.add(digitImage);
					} catch (IOException e) {
						break;
					}
				}
			}
		}
		System.out.println("Finished loading " + images.size() + " images. Randomizing...");
		ImageUtils.randomizeOrder(images);
		System.out.println("Done!");
		return images;
	}
	
	public static List<InputData> loadImages() {
		ArrayList<InputData> images = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			String subFolder = FOLDER_NAMES[i];
			int j = 1;
			while(true) {
				try {
					for (int k = 0; k < 50; k++) {
						images.add(new DigitImage(ImageUtils.getPixels(PATH_TO_IMAGES + subFolder + j + ".jpg"), i));
					}
					j++;
				} catch (IOException e) {
					break;
				}
			}
		}
		System.out.println("Finished loading " + images.size() + " images");
		ImageUtils.randomizeOrder(images);
		return images;
	}

	public static String getStringThatFillsDigits(int n, int spaces) {
		String toReturn = "" + n;
		while (toReturn.length() != spaces) {
			toReturn = "0" + toReturn;
		}
		return toReturn;
	}

}