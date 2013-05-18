package encryption_module;

import hash_module.Digestor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class DigitalSignature {
	private static final String FILE_PATH = "/home/charles/devel/ns_set/files/ca";
	private static final String BREAK = "\r\n";

	private String issuer = "Charles Shao";
	private String algorithm = "RSA";
	
	private AsymmetricKey publicKey;
	
	public DigitalSignature(AsymmetricKey publicKey) {
		this.publicKey = publicKey;
	}
	
	public void setIssuer(String issuer){
		this.issuer = issuer;	
	}
	
	public void setPKA(String algorithm){
		this.algorithm = algorithm;
	}

	public void createDS(){
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(FILE_PATH, "UTF-8");
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
		sb.append("\tPublic Key Algorithm: " + algorithm + ": " + BREAK);
		sb.append("\tPublic Key: " + BREAK);
		sb.append("\t\tModulus: " + publicKey.getModulus() + BREAK);
		sb.append("\t\tExponent: " + publicKey.getExponent() + BREAK);

		String hashSignature = Digestor.process(sb.toString());
		sb.append("Thumbprint Algorithm: SHA1withRSAEncryption" + BREAK);
		sb.append("\tThumbprint:" + BREAK);
		sb.append("\t" + hashSignature);
		writer.write(sb.toString());
		writer.close();
	}
}
