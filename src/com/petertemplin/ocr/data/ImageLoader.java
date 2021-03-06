package com.petertemplin.ocr.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.petertemplin.ocr.data.CharImage;
import com.petertemplin.ocr.util.ImageUtils;

/**
 * Provides methods for loading single and sets of image files into CharImage objects.
 */
public class ImageLoader {

	// chars74k data set info
	public static int UNSCALED_IMAGE_SIZE_74K = 128;
	public static int SCALED_IMAGE_SIZE_74K = 32;
	public static int NUM_74K_CLASSES = 62;
	public static final String PATH_TO_74K_IMAGES = "C:/Users/Me/Pictures/Characters/English/Fnt/";
	public static final String IMAGE_FILETYPE = ".png";
	
	private final ImageSet imageSet;
	private final int samplesPerClass;
	
	public ImageLoader(ImageSet imageSet) {
		this.imageSet = imageSet;
		this.samplesPerClass = Integer.MAX_VALUE;
	}
	
	public ImageLoader(ImageSet imageSet, int samplesPerClass) {
		this.imageSet = imageSet;
		this.samplesPerClass = samplesPerClass;
	}
	
	/**
	 * Load the configured set of images and randomize the order.
	 * @return list of classifiable CharImage objects
	 */
	public List<CharImage> loadSet() {
		
		List<CharImage> images = new ArrayList<>();
		System.out.println("Reading " + samplesPerClass*(imageSet.end - imageSet.start + 1) + " images...");

		for(int classNum = imageSet.start; classNum <= imageSet.end; classNum++) {
			System.out.println("Loading class " + classNum + "...");
			for (int j = 1; j <= samplesPerClass; j++) {
				if (j % 100 == 0) { System.out.println("Image number " + j); }
				try {
					images.add(loadImage(classNum, j));
				} catch (IOException e) {
					break;
				}
			}
		}
		System.out.println("Finished loading " + images.size() + " images. Randomizing...");
		return randomizeOrder(images);
	}
	
	/**
	 * Load a single image using the given image set.
	 * 
	 * @param classNum the index of the image class to load from
	 * @param imageNum the image number within the class
	 * @return CharImage representing this image file
	 * @throws IOException if image file cannot be loaded
	 */
	public CharImage loadImage(int classNum, int imageNum) throws IOException {
		String imagePath = getChars74KPath(classNum, imageNum);
		int[][] unscaledPixels = loadPixels(imagePath);
		int[][] scaledPixels = ImageUtils.scalePixelsToQuarter(unscaledPixels);
			
		return new CharImage(scaledPixels, classNum, imageNum);
	}
	
	/**
	 * Gets the filepath to the specified Chars74K image.
	 *
	 * @param classNum the image class
	 * @param imageNum the image number within the class
	 * @return the filepath of the image
	 */
	private String getChars74KPath(int classNum, int imageNum) {
		String parentFolder = String.format("Sample%03d/", classNum);
		String imageFile = String.format("img%03d-%05d", classNum, imageNum) + IMAGE_FILETYPE;
		return PATH_TO_74K_IMAGES + parentFolder + imageFile;
	}
	
	/**
	 * Loads the unscaled pixels of the given image file.
	 *
	 * @param filePath the file to load from
	 * @return matrix of pixels from the unscaled image
	 * @throws IOException if image file cannot be loaded or is of unsupported size
	 */
	private static int[][] loadPixels(String filePath) throws IOException {

		File file = new File(filePath);
		BufferedImage image = ImageIO.read(file);
		
		int imageHeight = image.getHeight();
		int imageWidth = image.getWidth();
		if (imageHeight != UNSCALED_IMAGE_SIZE_74K || imageWidth != UNSCALED_IMAGE_SIZE_74K) {
			String error = String.format(
					"Attempting to load image with unsupported dimenions %d x %d", imageWidth, imageHeight);
			throw new IOException(error);
		}
		return ImageUtils.convertToGrayscale(image);
	}
	
	/**
	 * Randomize the order of the given list of images.
	 * @param list the images to randomize
	 * @return a randomized list of images
	 */
	private static List<CharImage> randomizeOrder(List<CharImage> list) {
		CharImage temp = null;
		for(int i = 0; i < list.size(); i++) {
			temp = list.get(i);
			int randomIndex = (int) (Math.random() * list.size());
			list.set(i, list.get(randomIndex));
			list.set(randomIndex, temp);
		}
		
		return list;
	}
	
	/**
	 * Supported subsets of the CHARS74K dataset.
	 */
	public static enum ImageSet {
		NONE(0, 0),
		CHARS74K_BINARY(1, 2),
		CHARS74K_NUMBERS(1, 10),
		CHARS74K_LETTERS(11, 62),
		CHARS74K_LOWERCASE(11, 36),
		CHARS74K_UPPERCASE(37, 62),
		CHARS74K_ALL(1, 62),
		CHARS74K_CUSTOM(0, 0);
		
		public int start;
		public int end;
		
		private ImageSet(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

}