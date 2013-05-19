package certificate_authority;

import java.io.File;

import javax.crypto.SecretKey;

import encryption_module.AsymmetricKey;
import encryption_module.DigitalSignature;
import encryption_module.RSA;
import encryption_module.TripleDES;

public class CertificateAuthority {
	private static final String CA_name = "Penguin Securities";
	private String FILE_PATH = "files/certificate_authority/certificate.ds";
	
	private SecretKey secretKey;
	private RSA RSA;
	
	public CertificateAuthority() {
		// May need to refactor
		TripleDES tDES = new TripleDES();
		secretKey = tDES.getKey();
		
		RSA = new RSA();
	}
	
	public SecretKey getKey(){
		return secretKey;
	}
	
	/**
	 * Certificate contains the requesters PK information
	 * @param cipherPublicKey
	 */
	public void createCertificate(AsymmetricKey publicKey, String filepath) {
		FILE_PATH = filepath;
		
		DigitalSignature digitalSignature = new DigitalSignature(publicKey, RSA, CA_name);
		digitalSignature.createDigitalSignature(FILE_PATH);
	}
	
	/**
	 * Return the certificate file
	 * @return File:certificate
	 */
	public File issueCertificate() {
		return new File(FILE_PATH);
	}
}
