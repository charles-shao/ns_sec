package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import classes.Logger;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class SETFrame extends SETImpl implements ActionListener {

	private JFrame frmSecureElectronicTransaction;
	private JScrollPane consolePane;
	private JTextPane console;
	private JButton btnSetCustomerUp;
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
	private JButton btnSetMerchantUp;
	private JButton btnSetBankUp;

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
		getFrmSecureElectronicTransaction().setBounds(100, 100, 800, 700);
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
		
		lblConsoleLog = new JLabel("Console log");
		frmSecureElectronicTransaction.getContentPane().add(lblConsoleLog, "2, 3");
		
		btnClearLog = new JButton("Clear log");
		btnClearLog.addActionListener(this);
		
		frmSecureElectronicTransaction.getContentPane().add(btnClearLog, "8, 3");

		consolePane = new JScrollPane();
		frmSecureElectronicTransaction.getContentPane().add(consolePane,
				"2, 4, 7, 5, fill, fill");

		console = new JTextPane();
		consolePane.setViewportView(console);
		
		btnVerifyBank = new JButton("Verify bank");
		btnVerifyBank.addActionListener(this);
		
		btnVerifyMerchant = new JButton("Verify merchant");
		btnVerifyMerchant.addActionListener(this);
		
		btnSetCustomerUp = new JButton("Set customer up");
		btnSetCustomerUp.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnSetCustomerUp, "2, 9");
		
		btnCreateDualSignature = new JButton("Create dual signature");
		btnCreateDualSignature.addActionListener(this);
		
		btnSetMerchantUp = new JButton("Set merchant up");
		btnSetMerchantUp.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnSetMerchantUp, "4, 9, 3, 1");
		
		btnSetBankUp = new JButton("Set bank up");
		btnSetBankUp.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnSetBankUp, "8, 9");
		frmSecureElectronicTransaction.getContentPane().add(btnCreateDualSignature, "2, 11");
		
		lblMerchantActions = new JLabel("Merchant actions");
		frmSecureElectronicTransaction.getContentPane().add(lblMerchantActions, "2, 12");
		frmSecureElectronicTransaction.getContentPane().add(btnVerifyMerchant, "2, 13");
		
		btnSymmetricKeyMerchant = new JButton("Trade symmetric key secret");
		btnSymmetricKeyMerchant.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnSymmetricKeyMerchant, "4, 13, 3, 1");
		
		btnSendOrderDetails = new JButton("Send order details");
		btnSendOrderDetails.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnSendOrderDetails, "8, 13");
		
		lblBankActions = new JLabel("Bank actions");
		frmSecureElectronicTransaction.getContentPane().add(lblBankActions, "2, 14");
		frmSecureElectronicTransaction.getContentPane().add(btnVerifyBank, "2, 15");
		
		btnTradeSymmeticKeyBank = new JButton("Trade symmetric key secret");
		btnTradeSymmeticKeyBank.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnTradeSymmeticKeyBank, "4, 15, 3, 1");
		
		btnSendPaymentDetails = new JButton("Send payment details");
		btnSendPaymentDetails.addActionListener(this);
		frmSecureElectronicTransaction.getContentPane().add(btnSendPaymentDetails, "8, 15");
	}

	public void console(String log) {
		console.setText(log);
	}

	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		
		if (src == btnSetCustomerUp) {
			setImpl.setupCustomer();
		} else if (src == btnSetMerchantUp) {
			setImpl.setupMerchant();	
		} else if (src == btnSetBankUp) {
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
