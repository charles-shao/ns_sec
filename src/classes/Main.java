package classes;

import java.awt.EventQueue;
import java.io.File;

import merchant_module.Merchant;
import ui.SETFrame;
import bank_module.Bank;
import customer_module.Customer;

public class Main {
	public static SETFrame WINDOW_FRAME;
	public static Logger LOG;
	
	public static void main(String[] args) {
		LOG = new Logger();
		run();
		
		Customer customer = setupCustomer();
		Merchant merchant = setupMerchant();
		Bank bank = setupBank();

		/*
		 * Customer creates dual signature with the order and payment
		 * information
		 */
		File dualSignature = customer.createDualSignature().getDualSignature();
		log("Dual Signature created at " + dualSignature.getAbsolutePath());
		/*
		 * Before sending the dual signature, verify the merchant
		 */
		if (customer.verifyMerchant(merchant)) {
			/*
			 * Establish secret with merchant for symmetric encryption
			 */
			merchant.establishSecret(customer.getSecretKey());

			/*
			 * Send the dual signature, public key of the customer and the OI
			 * encrypted with TripleDES. If it checks out then the order
			 * registration is successful
			 */
			if (merchant.recieveTransaction(dualSignature, customer.getPublicKey(),
					customer.getEncryptedOI(), customer.getPIMD())) {
				log("Merchant has confirmed the order.");
			}

		}

		/* Before sending dual signature, verify the bank */
		if (customer.verifyBank(bank)) {
			/*
			 * Establish secret with bank for symmetric encryption
			 */
			bank.establishSecret(customer.getSecretKey());

			/*
			 * Send the dual signature, public key of the customer and the PI
			 * encrypted with TripleDES. If it checks out then the payment
			 * registration is successful
			 */
			if (bank.recieveTransaction(dualSignature, customer.getPublicKey(),
					customer.getEncryptedPI(), customer.getOIMD())) {
				log("Bank has confirmed the payment.");
			}
		}

	}

	/**
	 * Customer sends CA its public key and then digitally signed with the CA's
	 * private key
	 * 
	 * @return
	 */
	private static Customer setupCustomer() {
		Customer customer = new Customer();
		customer.requestCertificate();
		return customer;
	}

	private static Merchant setupMerchant() {
		Merchant merchant = new Merchant();
		merchant.requestCertificate();
		return merchant;
	}

	private static Bank setupBank() {
		Bank bank = new Bank();
		bank.requestCertificate();
		return bank;
	}
	
	private static void log(String m) {
		LOG.log(m);
		WINDOW_FRAME.console(LOG.spit());	
	}
	
	/**
	 * UI interface link
	 */
	private static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WINDOW_FRAME = new SETFrame();
					WINDOW_FRAME.getFrmSecureElectronicTransaction().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
