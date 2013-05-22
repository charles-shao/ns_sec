package classes;

import java.io.File;

import merchant_side.Merchant;
import ui.Frame;
import bank_side.Bank;
import customer_side.Customer;

public class Main {
	public static Frame frame;
	public static Logger logger;
	public static void main(String[] args) {
		logger = new Logger();
		frame = new Frame();
		frame.run();
		
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
			if (merchant.recieveOrder(dualSignature, customer.getPublicKey(),
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
			if (bank.recievePayment(dualSignature, customer.getPublicKey(),
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
		logger.log(m);
		frame.console(logger.spit());	
	}
}
