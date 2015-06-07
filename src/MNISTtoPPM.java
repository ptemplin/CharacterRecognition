package com.petertemplin;
//------------------------------------------------------------
//   File: MNISTtoPPM.java
//   Written for JDK 1.1 API 
//   Author: Douglas Eck  deck@unm.edu
//   Description:
//   This is a simple program which takes an MNIST image file, 
//   an MNIST label file, and a desired image number and 
//   prints that image to stdout as a ppm file. It also prints
//   the number that the file is supposed to represent to 
//   stderr.
//------------------------------------------------------------

import java.io.*;


public class MNISTtoPPM extends Object {
  public static void main(String [] args ) {
    MNISTImageFile imgF = null;
    MNISTLabelFile lblF = null;
    if (args.length!=3) {
      System.err.println("MNISTtoPPM syntax: MNISTtoPPM <MNIST image file name> <MNIST label file name> <img offset>");
      System.exit(0);
    } else {
      try {
	imgF = new MNISTImageFile(args[0],"r");
	lblF = new MNISTLabelFile(args[1],"r");
      } catch (FileNotFoundException e) { 
	System.err.println("File not found: " + e);
	System.exit(0);
      } catch (IOException e) {
	System.err.println("IO Exception: " + e);
	System.exit(0);
      }
      imgF.setCurr(Integer.parseInt(args[2]));
      lblF.setCurr(Integer.parseInt(args[2]));
      printPPMToStdOut(imgF, lblF);
      printLabelToStdErr(lblF);
    }
  }

  public static void printLabelToStdErr(MNISTLabelFile lblF) {
    //Prints to stderr the number that the image is supposed to be 
    System.err.println("Printing a " + lblF.data());
  } 

  public static void printPPMToStdOut(MNISTImageFile imgF, MNISTLabelFile lblF) {
    //Prints to stdout the ppm file of selected image (per ppm format in man ppm) 
    String s = null;
    int [][] dat = imgF.data();
    System.out.println("P3");
    System.out.println(""+ imgF.rows() + " " + imgF.cols() + " 255");
    System.out.println("# autogenerated by MNISTtoPPM");
    System.out.println("# this was drawn as a " + lblF.data());
    System.out.println("# " + imgF.name());
    System.out.println("# " + imgF.status());
    for (int i=0;i<imgF.rows();i++) {
      s="";
      for(int j=0;j<imgF.cols();j++)
	s = s + dat[i][j] + " " + dat[i][j] + " " + dat[i][j] + "  ";
      System.out.println(s);
    }
  }
}





