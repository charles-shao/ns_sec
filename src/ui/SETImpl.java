package ui;

import java.io.File;

import merchant_module.Merchant;
import bank_module.Bank;
import classes.Logger;
import customer_module.Customer;

public class SETImpl {

	// Shared variables
	protected Customer customer;
	protected Merchant merchant;
	protected Bank bank;
	protected File dualSignature;

	protected Boolean merchantVerified;
	protected Boolean bankVerified;

	public SETImpl() {
		merchantVerified = false;
		bankVerified = false;
	}

	/**
	 * Customer sends CA its public key and then digitally signed with the CA's
	 * private key
	 * 
	 * @return
	 */
	public Customer setupCustomer() {
		Logger.write("Creating customer entity...");
		customer = new Customer();
		customer.requestCertificate();
		return customer;
	}

	public Merchant setupMerchant() {
		Logger.write("\nCreating merchant entity...");
		merchant = new Merchant();
		merchant.requestCertificate();
		return merchant;
	}

	public Bank setupBank() {
		Logger.write("\nCreating bank entity...");
		bank = new Bank();
		bank.requestCertificate();
		return bank;
	}

	/**
	 * Customer creates dual signature with the order and payment information
	 */
	public void createDualSignature() {
		Logger.write("Creating dual signature...");
		dualSignature = customer.createDualSignature().getDualSignature();
		Logger.write("Dual signature sucessfully created.");
	}

	/**
	 * Before sending the dual signature, verify the merchant
	 */
	public void verifyMerchant() {
		merchantVerified = customer.verifyMerchant(merchant);
		if (merchantVerified) {
			Logger.write("Merchant successfully verified via CA");
		} else {
			Logger.write("ERROR: Merchant verification failed - mismatch signature");
		}
	}

	/**
	 * Establish secret with merchant for symmetric encryption
	 */
	public void establishMerchantSecret() {
		if (merchantVerified) {
			Logger.write("Establishing symmetric key secret...");
			merchant.establishSecret(customer.getSecretKey(),
					customer.getPublicKey());
		} else {
			Logger.write("ERROR: Merchant verification failed - mismatch signature");
		}
	}

	/**
	 * Send the dual signature, public key of the customer and the OI encrypted
	 * with TripleDES. If it checks out then the order registration is
	 * successful
	 */
	public void sendOrder() {
		if (merchantVerified) {
			Logger.write("Sending dual signature, the customers public key, the encrpted order information and the hashed payment information...");
			if (merchant.recieveTransaction(dualSignature,
					customer.getPublicKey(), customer.getEncryptedOI(),
					customer.getPIMD())) {
				Logger.write("Merchant has confirmed the order.");
			}
		}
	}

	/**
	 * Before sending dual signature, verify the bank
	 * */
	public void verifyBank() {
		bankVerified = customer.verifyBank(bank);
		if (bankVerified) {
			Logger.write("Bank successfully verified via CA");
		} else {
			Logger.write("ERROR: Bank verification failed - mismatch signature");
		}
	}

	/**
	 * Establish secret with bank for symmetric encryption
	 */
	public void establishBankSecret() {
		if (bankVerified) {
			Logger.write("Establishing symmetric key secret...");
			bank.establishSecret(customer.getSecretKey(),
					customer.getPublicKey());
		} else {
			Logger.write("ERROR: Bank verification failed - mismatch signature");
		}
	}

	/**
	 * Send the dual signature, public key of the customer and the PI encrypted
	 * with TripleDES. If it checks out then the payment registration is
	 * successful
	 */
	public void sendPayment() {
		if (bankVerified) {
			if (bank.recieveTransaction(dualSignature, customer.getPublicKey(),
					customer.getEncryptedPI(), customer.getOIMD())) {
				Logger.write("Bank has confirmed the payment.");
			}
		}
	}

}
