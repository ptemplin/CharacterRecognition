package com.petertemplin;

import java.io.*;

public class ReportingUtils {
	
	public static PrintWriter writer;
	
	public static final String PATH_TO_LOG = "C:\\Users\\Me\\Pictures\\Characters\\reporting.txt";

	public static void writeStringToLog(String data) {
		try {
			if(writer == null)
				writer = new PrintWriter(PATH_TO_LOG, "UTF-8");
			writer.print(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeWriter() {
		writer.close();
	}
	
}
