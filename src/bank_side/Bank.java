package bank_side;

import hash_module.Digestor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;

import javax.crypto.SecretKey;

import certificate_authority.CertificateAuthority;

import encryption_module.AsymmetricKey;
import encryption_module.RSA;
import encryption_module.TripleDES;

public class Bank {
	
	private static final String PUBLIC_KEY_CERTIFICATE_PATH = "files/bank/pk_certificate.ds";
	
	private RSA rsa;
	private AsymmetricKey publicKey;
	private AsymmetricKey CA_publicKey;
	private CertificateAuthority CA;
	private SecretKey customerSecretKey;
	
	public Bank() {
		rsa = new RSA();
		publicKey = rsa.getPublicKey();
	}
	
	public AsymmetricKey getPublicKey(){
		return publicKey;
	}
	
	public AsymmetricKey getCAPublicKey() {
		return CA_publicKey;
	}
	
	public File getCertificate() {
		return new File(PUBLIC_KEY_CERTIFICATE_PATH);
	}
	
	public void establishSecret(SecretKey secret) {
		customerSecretKey = secret;
	}
	
	public void requestCertificate() {
		CA = new CertificateAuthority();
		CA.createCertificate(publicKey, PUBLIC_KEY_CERTIFICATE_PATH);
		CA_publicKey = CA.getPublicKey();
	}

	/**
	 * The order of hashes is PIMD||OIMD for verification purpose
	 * 
	 * @param signatureFile
	 * @param pk
	 * @param encryptedOI
	 * @param pimd
	 * @return If order is successful
	 */
	public boolean recievePayment(File signatureFile, AsymmetricKey pk,
			Collection<byte[]> encryptedPI, String oimd) {
		String dualSignature = decryptSignature(read(signatureFile), pk);
		String digestedPI = digestPI(encryptedPI);
		
		String paymentOrder = Digestor.process(digestedPI.concat(oimd));
		if (paymentOrder.equals(dualSignature)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Decrypt it first and then hash it with RSA
	 * 
	 * @param cipherCollection
	 * @return
	 */
	private String digestPI(Collection<byte[]> cipherCollection) {
		StringBuffer sb = new StringBuffer();
		for (byte[] cipherByte : cipherCollection) {
			String message = TripleDES.decrypt(cipherByte, customerSecretKey);
			String hash = Digestor.process(message);
			sb.append(hash);
		}
		return sb.toString();
	}

	private String read(File dualSignature) {
		String line = null;
		try (Scanner scanner = new Scanner(dualSignature)) {
			line = scanner.nextLine();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return line;
	}

	private String decryptSignature(String line, AsymmetricKey pk) {
		return RSA.decryptMessage(line, pk);
	}
}
