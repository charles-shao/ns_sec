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

public class SETFrame extends SETImpl implements ActionListener {

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
	private JButton btnSymmetricKeyMerchant;
	
	private SETImpl setImpl;
	private JButton btnClearLog;
	private JButton btnTradeSymmeticKeyBank;
	private JLabel lblBankActions;
	private JLabel lblMerchantActions;
	private JLabel lblConsoleLog;
	private JButton btnSendOrderDetails;
	private JButton btnSendPaymentDetails;

	/**
	 * Create the application.
	 */
	public SETFrame() {
		setImpl = new SETImpl();
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
				ColumnSpec.decode("100dlu:grow"),
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
		
		lblConsoleLog = new JLabel("Console log");
		frmSecureElectronicTransaction.getContentPane().add(lblConsoleLog, "2, 4");

		consolePane = new JScrollPane();
		frmSecureElectronicTransaction.getContentPane().add(consolePane,
				"2, 5, 7, 1, fill, fill");

		console = new JTextPane();
		consolePane.setViewportView(console);
		
		btnSetEntitiesUp = new JButton("Set entities up");
		btnSetEntitiesUp.addActionListener(this);
		
		btnClearLog = new JButton("Clear log");
		btnClearLog.addActionListener(this);
		
		frmSecureElectronicTransaction.getContentPane().add(btnClearLog, "8, 6");
		frmSecureElectronicTransaction.getContentPane().add(btnSetEntitiesUp, "2, 7");
		
		btnCreateDualSignature = new JButton("Create dual signature");
		btnCreateDualSignature.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnCreateDualSignature, "2, 8");
		
		btnVerifyBank = new JButton("Verify bank");
		btnVerifyBank.addActionListener(this);
		
		btnVerifyMerchant = new JButton("Verify merchant");
		btnVerifyMerchant.addActionListener(this);
		
		lblMerchantActions = new JLabel("Merchant actions");
		frmSecureElectronicTransaction.getContentPane().add(lblMerchantActions, "2, 10");
		frmSecureElectronicTransaction.getContentPane().add(btnVerifyMerchant, "2, 11");
		
		btnSymmetricKeyMerchant = new JButton("Trade symmetric key secret");
		btnSymmetricKeyMerchant.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnSymmetricKeyMerchant, "4, 11, 3, 1");
		
		btnSendOrderDetails = new JButton("Send order details");
		btnSendOrderDetails.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnSendOrderDetails, "8, 11");
		
		lblBankActions = new JLabel("Bank actions");
		frmSecureElectronicTransaction.getContentPane().add(lblBankActions, "2, 12");
		frmSecureElectronicTransaction.getContentPane().add(btnVerifyBank, "2, 13");
		
		btnTradeSymmeticKeyBank = new JButton("Trade symmetric key secret");
		btnTradeSymmeticKeyBank.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnTradeSymmeticKeyBank, "4, 13, 3, 1");
		
		btnSendPaymentDetails = new JButton("Send payment details");
		btnSendPaymentDetails.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnSendPaymentDetails, "8, 13");
	}

	public void console(String log) {
		console.setText(log);
	}

	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		
		if (src == btnKeyPair) {
			Primes prime = new Primes();
			System.out.println(prime.getLargePrime());
		} else if (src == btnSetEntitiesUp) {
			setImpl.setupCustomer();
			setImpl.setupMerchant();
			setImpl.setupBank();
		} else if (src == btnCreateDualSignature) {
			try {
				setImpl.createDualSignature();	
			} catch (Exception e) {
				Logger.write("Cannot create dual signature without the customer entity!");
			}
		} else if (src == btnVerifyMerchant) {
			Logger.write("Verifying merchant via certificate authority...");
			try {
				setImpl.verifyMerchant();	
			} catch (Exception e) {
				Logger.write("Cannot verify merchant without the merchant entity!");
			}
			
		} else if (src == btnVerifyBank) {
			Logger.write("Verifying bank via certificate authority...");
			try {
				setImpl.verifyBank();	
			} catch (Exception e) {
				Logger.write("Cannot verify bank without the bank entity!");
			}
		} else if (src == btnClearLog) {
			Logger.clear();
		} else if (src == btnSymmetricKeyMerchant) {
			setImpl.establishMerchantSecret();
		} else if (src == btnTradeSymmeticKeyBank) {
			setImpl.establishBankSecret();
		} else if (src == btnSendOrderDetails) {
			setImpl.sendOrder();
		} else if (src == btnSendPaymentDetails) {
			setImpl.sendPayment();
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
