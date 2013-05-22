package ui;

import java.awt.EventQueue;

public class Frame {
	
	SETFrame window;
	public Frame() {
	}

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new SETFrame();
					window.frmSecureElectronicTransaction.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void console(String log) {
		window.console(log);
	}

}
