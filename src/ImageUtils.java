package com.petertemplin;

import java.io.*;

import javax.imageio.*;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.List;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageUtils {
	
	// conversion factors
	public static final double RED_TO_GRAY_FACTOR = 0.2989;
	public static final double GREEN_TO_GRAY_FACTOR = 0.5878;
	public static final double BLUE_TO_GRAY_FACTOR = 0.1140;
	
	public static int[][] getPixels(BufferedImage image) throws IOException{
		
		int imageHeight = image.getHeight();
		int imageWidth = image.getWidth();
		int[][] pixels = new int[imageHeight][imageWidth];
		
		//Loop through putting all pixel values into the array
		ColorModel cm = image.getColorModel();
		for (int i = 0; i < imageHeight; i++) {
			for (int j = 0; j < imageWidth; j++) {
				int color = image.getRGB(j, i);
				int grayPixel = rgbToGrayScale(cm, color);
				pixels[i][j] = grayPixel;
			}
		}
			
		return pixels;
	}

	public static int[][] getPixels(String filePath) throws IOException{

		File file = new File(filePath);
		BufferedImage image = ImageIO.read(file);
		
		int imageHeight = image.getHeight();
		int imageWidth = image.getWidth();
		int[][] pixels = new int[imageHeight][imageWidth];
		
		//Loop through putting all pixel values into the array
		ColorModel cm = image.getColorModel();
		for (int i = 0; i < imageHeight; i++) {
			for (int j = 0; j < imageWidth; j++) {
				int color = image.getRGB(j, i);
				int grayPixel = rgbToGrayScale(cm, color);
				pixels[i][j] = grayPixel;
			}
		}
			
		return pixels;
	}
	
	public static int rgbToGrayScale(ColorModel cm, int color) {
		double red = ((color & 0x00ff0000) >> 16) * RED_TO_GRAY_FACTOR;
		double green = ((color & 0x0000ff00) >> 8) * GREEN_TO_GRAY_FACTOR;
		double blue = (color & 0x000000ff) * BLUE_TO_GRAY_FACTOR;
		return (int) (red + green + blue); 
	}
	
	public static List<InputData> randomizeOrder(List<InputData> list) {
		InputData temp = null;
		for(int i = 0; i < list.size(); i++) {
			temp = list.get(i);
			int randomIndex = (int) (Math.random() * list.size());
			list.set(i, list.get(randomIndex));
			list.set(randomIndex, temp);
		}
		
		return list;
	}
	
	public static int[][] scalePixelsToHalf(int[][] pixels) {
		int[][] newPixels = new int[pixels.length][pixels.length];
		for (int i = 0; i < pixels.length; i += 2) {
			for (int j = 0; j < pixels.length; j += 2) {
				newPixels[i/2][j/2] = (pixels[i][j] + pixels[i+1][j]
									 + pixels[i][j+1] + pixels[i+1][j+1]) / 4;
			}
		}
		return newPixels;
	}
	
	public static void displayImage(BufferedImage image, int label, int classification) {
        ImageIcon icon=new ImageIcon(image);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(150,250);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        JLabel lbl2=new JLabel();
        lbl2.setText("Image is labeled as: " + label);
        frame.add(lbl2);
        JLabel lbl3=new JLabel();
        lbl3.setText("Image is classified as: " + classification);
        frame.add(lbl3);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
