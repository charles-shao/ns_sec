package encryption_module;

import hash_module.Digestor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class DigitalSignature {
	private static final String BREAK = "\r\n";

	private String issuer;
	private RSA rsa;

	private AsymmetricKey publicKey;

	public DigitalSignature(AsymmetricKey publicKey, RSA rsa, String issuer) {
		this.publicKey = publicKey;
		this.rsa = rsa;
		this.issuer = issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public void createDigitalSignature(String filepath) {
		PrintWriter writer = null;

		try {
			writer = new PrintWriter(filepath, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		StringBuffer sb = new StringBuffer();
		sb.append("Certificate:" + BREAK);
		sb.append("\tVersion: 0" + BREAK);
		sb.append("\tSignature Algorithm: SHA1WithRSAEncryption" + BREAK);
		sb.append("\tIssuer: " + issuer + BREAK);
		sb.append("\tValidity:" + BREAK);
		sb.append("\t\tNot Before: Feb 7 12:00:00 2013 AEST" + BREAK);
		sb.append("\t\tNot After : Feb 7 12:00:00 2014 AEST" + BREAK);
		sb.append("\tName: " + BREAK);
		sb.append("\tSubject Public Key Algorithm: " + rsa.getAlgorithm()
				+ ": " + BREAK);
		sb.append("\tSubject Public Key: " + BREAK);
		sb.append("\t\tModulus: " + publicKey.getModulus() + BREAK);
		sb.append("\t\tExponent: " + publicKey.getExponent() + BREAK);
		
		String hashSignature = Digestor.process(sb.toString());
		String encryptedSignature = rsa.encrypt(hashSignature).serialize();

		sb.append("Signature Algorithm: SHA1withRSAEncryption" + BREAK);
		sb.append("\tSignature:" + BREAK);
		sb.append("\t\t" + encryptedSignature);
		writer.write(sb.toString());
		writer.close();
	}
}