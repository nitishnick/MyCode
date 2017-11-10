package com.sapient.test;

import com.sapient.controller.Controller;

public class Application {

	public static void main(String[] args) {
		try {
			boolean savedSuccess = new Controller().start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

//	private static boolean saveDataFromFiles() throws SAPException {}


}
