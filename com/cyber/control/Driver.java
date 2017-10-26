package com.cyber.control;

public class Driver extends Thread {
	
	public static void main(String[] args) {
		MasterController master = new MasterController();
		try {
			master.start();
			master.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
