package merchant_side;

import java.io.File;

import certificate_authority.CertificateAuthority;
import encryption_module.AsymmetricKey;
import encryption_module.RSA;

public class Merchant {

	private static final String PUBLIC_KEY_CERTIFICATE_PATH = "files/merchant/pk_certificate.ds";

	private RSA RSA;
	private AsymmetricKey publicKey;
	private AsymmetricKey CA_publicKey;
	private CertificateAuthority CA;

	public Merchant() {
		RSA = new RSA();
		publicKey = RSA.getPublicKey();
	}

	public AsymmetricKey getPublicKey() {
		return publicKey;
	}

	public AsymmetricKey getCAPublicKey() {
		return CA_publicKey;
	}
	
	public File getCertificate() {
		return new File(PUBLIC_KEY_CERTIFICATE_PATH);
	}

	public void requestCertificate() {
		CA = new CertificateAuthority();
		CA.createCertificate(publicKey, PUBLIC_KEY_CERTIFICATE_PATH);
		CA_publicKey = CA.getPublicKey();
	}
}
