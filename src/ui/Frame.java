package ui;
import javax.swing.*;  

public class Frame {
	
	public Frame() {
	}
	
    public void run() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("NS SET");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        JLabel label = new JLabel("Secure Electronic Transaction");
        frame.getContentPane().add(label);

        frame.pack();
        frame.setVisible(true);
    }
 
}
