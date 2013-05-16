package certificate_authority;

import encryption_module.DigitalSignature;
import encryption_module.RSA;

public class CertificateAuthority {
	private static final String CA_name = "Lock and Run";
	// Certificate contains their PK information
	public static void issueCertificate(RSA rsa) {
		DigitalSignature digitalSignature = new DigitalSignature(rsa.getPublicKey(), rsa.getModulus());
		digitalSignature.setIssuer(CA_name);
		digitalSignature.setPKA(rsa.getAlgorithm());
		digitalSignature.createDS();
	}
}
