package classes;

import java.awt.EventQueue;

import ui.SETFrame;

public class Main extends Thread {
	public static void main(String[] args) throws InterruptedException {
		runInterface();
		
		/*
		 * Wait for the UI thread to finish drawing before working
		 */
		Thread.sleep(500);

	}
	
	/**
	 * UI interface link
	 */
	private static void runInterface() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SETFrame frame = new SETFrame();
					frame.getFrmSecureElectronicTransaction().setVisible(true);

					Logger.setFrame(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
