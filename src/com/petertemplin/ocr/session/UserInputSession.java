package com.petertemplin.ocr.session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import com.petertemplin.ocr.data.CharImage;
import com.petertemplin.ocr.data.ImageLoader;
import com.petertemplin.ocr.data.ImageLoader.ImageSet;
import com.petertemplin.ocr.model.Classifier;

/**
 * Starts a session which accepts input through stdin representing image files
 * to classify and performs the classification of the image.
 */
public class UserInputSession extends Session {
	
	public UserInputSession(Classifier classifier, List<CharImage> imageList) {
		super(classifier, Collections.emptyList());
	}
	
	@Override
	public void run() {
		acceptInput();
	}
	
	private void acceptInput() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		ImageLoader loader = new ImageLoader(ImageSet.CHARS74K_ALL);
		String input = "";
		while (true) {
			try {
				// get the folder and image num from the user
				System.out.println("Now accepting user input (Enter q to quit).\n" +
							"Which character class?: ");
				input = reader.readLine();
				if (input.equals("q")) {
					break;
				}
				int folderNum = Integer.parseInt(input) + 1;
				System.out.println("Which image?: ");
				input = reader.readLine();
				if (input.equals("q")) {
					break;
				}
				int imageNum = Integer.parseInt(input);
				
				// load the image
				try {
					CharImage digitImage = loader.loadImage(folderNum, imageNum);
					classifier.test(digitImage);
					
					// print the result
					System.out.println("The image was a " + (folderNum -1));
					System.out.println("The system classified as " + (classifier.getClassification() - 1));
				} catch (IOException e) {
					System.out.println("Unable to load specified image");
				}
				
			} catch (IOException e) {
				System.out.println("Not valid input");
			} catch (NumberFormatException e) {
				System.out.println("Not a usable folder/image");
			}
		}
	}
	
}
