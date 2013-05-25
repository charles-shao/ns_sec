package certificate_authority;

import java.io.File;

import classes.Logger;

import encryption_module.AsymmetricKey;
import encryption_module.DigitalSignature;
import encryption_module.RSA;

public class CertificateAuthority {
	private static final String CA_NAME = "Penguin Securities";
	private String FILE_PATH = "files/certificate_authority/certificate.ds";
	
	private RSA RSA;
	
	public CertificateAuthority() {
		Logger.write("CA: Generating RSA key pairs...");
		RSA = new RSA();
	}
	
	/**
	 * Certificate contains the requesters PK information
	 * @param cipherPublicKey
	 */
	public void createCertificate(AsymmetricKey publicKey, String filepath) {
		FILE_PATH = filepath;
		DigitalSignature digitalSignature = new DigitalSignature(publicKey, RSA, CA_NAME);
		digitalSignature.createDigitalSignature(FILE_PATH);
	}
	
	/**
	 * Return the certificate file
	 * @return File:certificate
	 */
	public File issueCertificate() {
		return new File(FILE_PATH);
	}

	public AsymmetricKey getPublicKey() {
		return RSA.getPublicKey();
	}
}
