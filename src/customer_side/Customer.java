package customer_side;

import hash_module.Digestor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import javax.crypto.SecretKey;

import merchant_side.Merchant;
import certificate_authority.CertificateAuthority;
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

	private RSA rsa;
	private AsymmetricKey publicKey;
	private AsymmetricKey CA_publicKey;
	private CertificateAuthority CA;

	public Customer() {
		rsa = new RSA();
		PAYMENT_HASH = new ArrayList<String>();
		ORDER_HASH = new ArrayList<String>();
		publicKey = rsa.getPublicKey();
	}
	
	public void createDualSignature(){
		PrintWriter writer = null;
		String dualSignature = digestPaymentOrder();
		
		try {
			writer = new PrintWriter(DUAL_SIGNATURE_PATH, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		
		writer.write(dualSignature);
		writer.close();
	}

	/**
	 * Digest both PI and OI. Order of concatenation is MD(PI)||MD(OI)
	 * @return PIMD||OIMD
	 */
	private String digestPaymentOrder() {
		digestFile(PAYMENT_INFORMATION_PATH, PAYMENT_HASH);
		digestFile(ORDER_INFORMATION_PATH, ORDER_HASH);

		StringBuffer messageDigest = new StringBuffer();
		for (String hash : PAYMENT_HASH) {
			messageDigest.append(hash);
		}
		for (String hash : ORDER_HASH) {
			messageDigest.append(hash);
		}

		String paymentOrder = Digestor.process(messageDigest.toString());
		String encryptedPaymentOrder = rsa.encrypt(paymentOrder).serialize();
		
		return encryptedPaymentOrder;
	}

	/**
	 * Get encrypted certificate from the CA by sending the encrypted public
	 * key. Decrypt the certificate with the secret.
	 */
	public void requestCertificate() {
		CA = new CertificateAuthority();
		CA.createCertificate(publicKey, PUBLIC_KEY_CERTIFICATE_PATH);
		CA_publicKey = CA.getPublicKey();
	}
	
	public AsymmetricKey getPublicKey() {
		return publicKey;
	}

	/**
	 * Encrypt public key given a secret key (TripleDES algorithm)
	 * @param secretKey
	 * @return
	 */
	public byte[] encryptPK(SecretKey secretKey) {
		return TripleDES.encryptKey(publicKey.serialize(), secretKey);
	}

	/**
	 * Decrypt public key given a secret key (TripleDES algorithm)
	 * @param cipherKey
	 * @param secretKey
	 * @return
	 */
	public AsymmetricKey decryptPK(byte[] cipherKey, SecretKey secretKey) {
		byte[] decryptedCipher = TripleDES.decryptKey(cipherKey, secretKey);
		return (AsymmetricKey) AsymmetricKey.deserialize(decryptedCipher);
	}
	
	public Boolean verifyMerchant(Merchant merchant) {
		AsymmetricKey merchantPublicKey = merchant.getPublicKey();
		AsymmetricKey caPublicKey = merchant.getCAPublicKey();
		File certificate = merchant.getCertificate();
		
		DigtialSignatureVerifier dsv = new DigtialSignatureVerifier(certificate, caPublicKey, merchantPublicKey);
		return dsv.verify();
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
