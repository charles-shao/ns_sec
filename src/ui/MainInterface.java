package ui;


import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MainInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtRandominput;

	/**
	 * Create the frame.
	 */
	public MainInterface() {
		setTitle("Network Security: SET Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		txtRandominput = new JTextField();
		txtRandominput.setBounds(41, 52, 114, 19);
		txtRandominput.setColumns(10);
		
		JButton btnGenerateSignature = new JButton("Generate Signature");
		btnGenerateSignature.setBounds(160, 49, 175, 25);
		contentPane.setLayout(null);
		contentPane.add(txtRandominput);
		contentPane.add(btnGenerateSignature);
	}
	
	/**
	 * Launch the application.
	 */
	public void run(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainInterface frame = new MainInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
