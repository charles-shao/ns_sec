package bank_side;

import java.io.File;

import encryption_module.AsymmetricKey;
import encryption_module.RSA;

public class Bank {
	
	private static final String PUBLIC_KEY_CERTIFICATE_PATH = "files/bank/pk_certificate.ds";
	
	private RSA rsa;
	private AsymmetricKey publicKey;
	
	public Bank() {
		rsa = new RSA();
		publicKey = rsa.getPublicKey();
	}
	
	public AsymmetricKey getPublicKey(){
		return publicKey;
	}
	
	public File getCertificate() {
		return new File(PUBLIC_KEY_CERTIFICATE_PATH);
	}
}
