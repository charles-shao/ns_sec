package certificate_authority;

import javax.crypto.SecretKey;

import encryption_module.AsymmetricKey;
import encryption_module.DigitalSignature;
import encryption_module.TripleDES;

public class CertificateAuthority {
	private static final String CA_name = "Lock and Run";
	private SecretKey secretKey;

	public CertificateAuthority() {
		TripleDES tDES = new TripleDES();
		secretKey = tDES.getKey();
	}
	
	public SecretKey getKey(){
		return secretKey;
	}
	
	// Certificate contains their PK information
	public void issueCertificate(byte[] cipherPublicKey) {
		byte[] decryptedKey = TripleDES.decryptKey(cipherPublicKey, secretKey);
		AsymmetricKey publicKey = (AsymmetricKey)AsymmetricKey.deserialize(decryptedKey);

		DigitalSignature digitalSignature = new DigitalSignature(publicKey);
		digitalSignature.setIssuer(CA_name);
		digitalSignature.setPKA(publicKey.getAlgorithm());
		digitalSignature.createDS();
	}
}
