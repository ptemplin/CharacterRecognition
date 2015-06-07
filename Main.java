package com.petertemplin;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws Exception {
		NetworkManager manager = new NetworkManager();
		manager.startRevisedSession();
	}
	
}