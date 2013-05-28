package customer_module;

import hash_module.Digestor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import merchant_module.Merchant;
import bank_module.Bank;
import certificate_authority.CertificateAuthority;
import classes.Logger;
import encryption_module.AsymmetricKey;
import encryption_module.DigtialSignatureVerifier;
import encryption_module.RSA;
import encryption_module.TripleDES;

public class Customer {

	private static final String PAYMENT_INFORMATION_PATH = "files/customer/payment_information.txt";
	private static final String ORDER_INFORMATION_PATH = "files/customer/order_information.txt";
	private static final String PUBLIC_KEY_CERTIFICATE_PATH = "files/customer/pk_certificate.ds";
	private static final String DUAL_SIGNATURE_PATH = "files/customer/dual_signature.sig";
	private Collection<String> PAYMENT_HASH;
	private Collection<String> ORDER_HASH;

	private RSA _RSA;
	private TripleDES TDES;
	private AsymmetricKey PUBLIC_KEY;
	private CertificateAuthority CA;

	public Customer() {
		Logger.write("Customer: Generating RSA key pairs...");
		_RSA = new RSA();
		Logger.write("Generating TripleDES secret key...");
		TDES = new TripleDES();
		PAYMENT_HASH = new ArrayList<String>();
		ORDER_HASH = new ArrayList<String>();
		PUBLIC_KEY = _RSA.getPublicKey();
		Logger.write("Key generation successful. Using RSA and TripleDES encryption.\n");
	}

	/**
	 * Create dual signature - does not return the file yet though
	 * 
	 * @return Customer object
	 */
	public Customer createDualSignature() {
		PrintWriter writer = null;
		Logger.write("Hashing payment and order information.");
		String dualSignature = digestPaymentOrder();
		
		Logger.write("Dual Signature RSAwithSHA1:\n" + dualSignature + "\n");
		try {
			writer = new PrintWriter(DUAL_SIGNATURE_PATH, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		writer.write(dualSignature);
		writer.close();
		return this;
	}

	public File getDualSignature() {
		return new File(DUAL_SIGNATURE_PATH);
	}

	public Collection<byte[]> getEncryptedOI() {
		File orderInformation = new File(ORDER_INFORMATION_PATH);
		return encryptFile(orderInformation);
	}

	public Collection<byte[]> getEncryptedPI() {
		File paymentInformation = new File(PAYMENT_INFORMATION_PATH);
		return encryptFile(paymentInformation);
	}

	public String getPIMD() {
		return messageDigest(PAYMENT_HASH);
	}

	public String getOIMD() {
		return messageDigest(ORDER_HASH);
	}

	/**
	 * Get encrypted certificate from the CA by sending the encrypted public
	 * key. Decrypt the certificate with the secret.
	 */
	public void requestCertificate() {
		Logger.write("Getting digital certificate from cerfiticate authority...");
		CA = new CertificateAuthority();
		Logger.write("Customer sending public key to certificate authority...");
		CA.createCertificate(PUBLIC_KEY, PUBLIC_KEY_CERTIFICATE_PATH);
	}

	public AsymmetricKey getPublicKey() {
		return PUBLIC_KEY;
	}

	/**
	 * Encrypt TripleDES secret with private key
	 * @return
	 */
	public byte[] getSecretKey() {
		return _RSA.encryptSecretKey(TDES.getKey());
	}
	
	/**
	 * Verify the merchant by checking their certificate and their PK
	 * 
	 * @param merchant
	 * @return
	 */
	public Boolean verifyMerchant(Merchant merchant) {
		AsymmetricKey merchantPublicKey = merchant.getPublicKey();
		AsymmetricKey caPublicKey = merchant.getCAPublicKey();
		File certificate = merchant.getCertificate();

		DigtialSignatureVerifier dsv = new DigtialSignatureVerifier(
				certificate, caPublicKey, merchantPublicKey);
		return dsv.verify();
	}

	public Boolean verifyBank(Bank bank) {
		AsymmetricKey bankPublicKey = bank.getPublicKey();
		AsymmetricKey caPublicKey = bank.getCAPublicKey();
		File certificate = bank.getCertificate();

		DigtialSignatureVerifier dsv = new DigtialSignatureVerifier(
				certificate, caPublicKey, bankPublicKey);
		return dsv.verify();
	}

	/**
	 * Digest both PI and OI. Order of concatenation is MD(PI)||MD(OI)
	 * 
	 * @return PIMD||OIMD
	 */
	private String digestPaymentOrder() {
		Logger.write("\nPayment information:");
		Logger.write("=======================================================");
		digestFile(PAYMENT_INFORMATION_PATH, PAYMENT_HASH);
		Logger.write("\nOrder information:");
		Logger.write("=======================================================");
		digestFile(ORDER_INFORMATION_PATH, ORDER_HASH);
		
		StringBuffer messageDigest = new StringBuffer();
		for (String hash : PAYMENT_HASH) {
			messageDigest.append(hash);
		}
		for (String hash : ORDER_HASH) {
			messageDigest.append(hash);
		}
		
		String paymentOrder = Digestor.process(messageDigest.toString());
		Logger.write("\nMessage digest of PI || OI: \n" + paymentOrder);
		String encryptedPaymentOrder = _RSA.encryptMessage(paymentOrder).serialize();
		Logger.write("\nRSA encryption of MD(PI) || MD(OI): \n" + encryptedPaymentOrder);
		return encryptedPaymentOrder;
	}

	private void digestFile(String filename, Collection<String> hash) {
		try (Scanner scanner = new Scanner(new File(filename))) {
			String line;
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				Logger.write(line);
				hash.add(Digestor.process(line));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Collection<byte[]> encryptFile(File filename) {
		Collection<byte[]> encryptedCollection = new ArrayList<byte[]>();
		try (Scanner scanner = new Scanner(filename)) {
			String line;
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				byte[] cipher = TDES.encrypt(line);
				encryptedCollection.add(cipher);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return encryptedCollection;
	}

	private String messageDigest(Collection<String> collection) {
		StringBuilder md = new StringBuilder();
		for (String hash : collection) {
			md.append(hash);
		}
		return md.toString();	
	}
}
