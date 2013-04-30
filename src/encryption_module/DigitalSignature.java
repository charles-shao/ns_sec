package encryption_module;

import hash_module.Digestor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class DigitalSignature {
	private static final String FILE_PATH = "/home/charles/devel/ns_set/files/ca";
	private static final String BREAK = "\r\n";

	public DigitalSignature(BigInteger publicKey, BigInteger modulus) {
		try {
			writeDS(publicKey, modulus);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void writeDS(BigInteger publicKey, BigInteger modulus)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(FILE_PATH, "UTF-8");
		StringBuffer sb = new StringBuffer();
		sb.append("Certificate:" + BREAK);
		sb.append("\tVersion: 0" + BREAK);
		sb.append("\tSignature Algorithm: SHA1WithRSAEncryption" + BREAK);
		sb.append("\tIssuer: Charles Shao" + BREAK);
		sb.append("\tValidity:" + BREAK);
		sb.append("\t\tNot Before: Feb 7 12:00:00 2013 AEST" + BREAK);
		sb.append("\t\tNot After : Feb 7 12:00:00 2014 AEST" + BREAK);
		sb.append("\tName: " + BREAK);
		sb.append("\tPublic Key Algorithm: RSA: " + BREAK);
		sb.append("\tPublic Key: " + BREAK);
		sb.append("\t\tModulus: " + modulus + BREAK);
		sb.append("\t\tExponent: " + publicKey + BREAK);
		String hashSignature = Digestor.process(sb.toString());
		sb.append("Signature Algorithm: SHA1withRSAEncryption" + BREAK);
		sb.append("\t" + hashSignature);
		writer.write(sb.toString());
		writer.close();
	}
}
