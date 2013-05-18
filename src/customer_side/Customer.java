package customer_side;

import hash_module.Digestor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import javax.crypto.SecretKey;

import certificate_authority.CertificateAuthority;
import encryption_module.AsymmetricKey;
import encryption_module.RSA;
import encryption_module.TripleDES;

public class Customer {

	private static final String PAYMENT_INFORMATION_PATH = "/home/charles/devel/ns_set/files/payment_information.txt";
	private static final String ORDER_INFORMATION_PATH = "/home/charles/devel/ns_set/files/order_information.txt";
	private Collection<String> PAYMENT_HASH;
	private Collection<String> ORDER_HASH;

	private RSA RSA;
	private AsymmetricKey publicKey;
	private SecretKey secretKey;
	private CertificateAuthority certificateAuthority;

	public Customer() {
		RSA = new RSA();
		PAYMENT_HASH = new ArrayList<String>();
		ORDER_HASH = new ArrayList<String>();
		publicKey = RSA.getPublicKey();
	}

	/**
	 * Digest both PI and OI. Order of concatenation is MD(PI)||MD(OI)
	 */
	public void digestPaymentOrder() {
		digestFile(PAYMENT_INFORMATION_PATH, PAYMENT_HASH);
		digestFile(ORDER_INFORMATION_PATH, ORDER_HASH);

		StringBuffer messageDigest = new StringBuffer();
		for (String hash : PAYMENT_HASH) {
			messageDigest.append(hash);
		}
		for (String hash : ORDER_HASH) {
			messageDigest.append(hash);
		}

		// Rehash the message digest
		String paymentOrder = Digestor.process(messageDigest.toString());

		// Encrypt the hash with RSA
		String encryptedString = RSA.encrypt(paymentOrder).serialize();
		
		// TODO: finish dual certificates
		System.out.print(encryptedString);
	}

	public AsymmetricKey getPublicKey() {
		return publicKey;
	}

	/**
	 * Contact with CA and set up secret key
	 */
	public void establishSecret() {
		certificateAuthority = new CertificateAuthority();
		secretKey = certificateAuthority.getKey();
	}

	/**
	 * Get encrypted certificate from the CA by sending the encrypted public
	 * key. Decrypt the certificate with the secret.
	 */
	public void requestCertificate() {
		certificateAuthority.createCertificate(publicKey);
	}

	/**
	 * Encrypt public key using self secret key (TripleDES algorithm)
	 * NOTE: May not need this.
	 * @return
	 */
	public byte[] encryptPK() {
		return TripleDES.encryptKey(publicKey.serialize(), secretKey);
	}

	/**
	 * Encrypt public key given a secret key (TripleDES algorithm)
	 * 
	 * @param secretKey
	 * @return
	 */
	public byte[] encryptPK(SecretKey secretKey) {
		return TripleDES.encryptKey(publicKey.serialize(), secretKey);
	}

	/**
	 * Decrypt public key using self secret key (TripleDES algorithm)
	 * 
	 * @param cipherKey
	 * @return
	 */
	public AsymmetricKey decryptPK(byte[] cipherKey) {
		byte[] decryptedCipher = TripleDES.decryptKey(cipherKey, secretKey);
		return (AsymmetricKey) AsymmetricKey.deserialize(decryptedCipher);
	}

	/**
	 * Decrypt public key given a secret key (TripleDES algorithm)
	 * 
	 * @param cipherKey
	 * @param secretKey
	 * @return
	 */
	public AsymmetricKey decryptPK(byte[] cipherKey, SecretKey secretKey) {
		byte[] decryptedCipher = TripleDES.decryptKey(cipherKey, secretKey);
		return (AsymmetricKey) AsymmetricKey.deserialize(decryptedCipher);
	}

	private void digestFile(String filename, Collection<String> hash) {
		try (Scanner scanner = new Scanner(new File(filename))) {
			String line;
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				hash.add(Digestor.process(line));
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
