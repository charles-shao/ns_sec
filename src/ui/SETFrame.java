package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import classes.Logger;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import encryption_module.Primes;

public class SETFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JFrame frmSecureElectronicTransaction;
	private JTextField prime1;
	private JTextField prime2;
	private JButton btnKeyPair;
	private JScrollPane consolePane;
	private JTextPane console;
	private JButton btnSetEntitiesUp;
	private JButton btnCreateDualSignature;
	private JButton btnVerifyMerchant;
	private JButton btnVerifyBank;
	private JButton btnEstablishSecret;

	/**
	 * Create the application.
	 */
	public SETFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrmSecureElectronicTransaction(new JFrame());
		getFrmSecureElectronicTransaction().setTitle(
				"Secure Electronic Transaction");
		getFrmSecureElectronicTransaction().setBounds(100, 100, 800, 600);
		getFrmSecureElectronicTransaction().setDefaultCloseOperation(
				JFrame.EXIT_ON_CLOSE);
		getFrmSecureElectronicTransaction().getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("left:15dlu"),
				ColumnSpec.decode("100dlu"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("50dlu"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("50dlu"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("100dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("15dlu"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("top:15dlu"),
				RowSpec.decode("15dlu"),
				RowSpec.decode("15dlu"),
				RowSpec.decode("15dlu"),
				RowSpec.decode("150dlu"),
				RowSpec.decode("15dlu"),
				RowSpec.decode("15dlu"),
				RowSpec.decode("15dlu"),
				RowSpec.decode("15dlu"),
				RowSpec.decode("15dlu"),
				RowSpec.decode("15dlu"),
				RowSpec.decode("15dlu"),}));
		
				JLabel lblPrimeNumbersFor = new JLabel("Prime Numbers for Key Pair");
				getFrmSecureElectronicTransaction().getContentPane().add(
						lblPrimeNumbersFor, "2, 3, right, default");
						
								prime1 = new JTextField();
								getFrmSecureElectronicTransaction().getContentPane().add(prime1,
										"4, 3, fill, default");
								prime1.setColumns(10);
				
						prime2 = new JTextField();
						getFrmSecureElectronicTransaction().getContentPane().add(prime2,
								"6, 3, fill, default");
						prime2.setColumns(10);
		
				btnKeyPair = new JButton("Make Keys");
				getFrmSecureElectronicTransaction().getContentPane().add(btnKeyPair,
						"8, 3");
				btnKeyPair.addActionListener(this);

		consolePane = new JScrollPane();
		frmSecureElectronicTransaction.getContentPane().add(consolePane,
				"2, 5, 7, 1, fill, fill");

		console = new JTextPane();
		console.setText("Initializing...");
		consolePane.setViewportView(console);
		
		btnSetEntitiesUp = new JButton("Set entities up");
		btnSetEntitiesUp.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnSetEntitiesUp, "2, 7");
		
		btnCreateDualSignature = new JButton("Create dual signature");
		btnCreateDualSignature.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnCreateDualSignature, "2, 8");
		
		btnVerifyMerchant = new JButton("Verify merchant");
		btnVerifyMerchant.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnVerifyMerchant, "2, 9");
		
		btnEstablishSecret = new JButton("Establish secret");
		btnEstablishSecret.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnEstablishSecret, "4, 9, 3, 1");
		
		btnVerifyBank = new JButton("Verify bank");
		btnVerifyBank.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnVerifyBank, "2, 10");
	}

	public void console(String log) {
		console.setText(log);
	}

	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == btnKeyPair) {
			Primes prime = new Primes();
			System.out.println(prime.getLargePrime());
		}
		
		if (src == btnSetEntitiesUp) {
			Logger.write("Setting the entities up...");
			System.out.println("works?");
		}
		
		if (src == btnCreateDualSignature) {
			Logger.write("Creating dual signature...");
		}
		
		if (src == btnVerifyMerchant) {
			Logger.write("Verifying merchant via certificate authority...");
		}
		
		if (src == btnVerifyBank) {
			Logger.write("Verifying bank via certificate authority...");
		}
	}

	public JFrame getFrmSecureElectronicTransaction() {
		return frmSecureElectronicTransaction;
	}

	public void setFrmSecureElectronicTransaction(
			JFrame frmSecureElectronicTransaction) {
		this.frmSecureElectronicTransaction = frmSecureElectronicTransaction;
	}
}
