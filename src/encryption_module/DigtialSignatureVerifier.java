package encryption_module;

import hash_module.Digestor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class DigtialSignatureVerifier {

	private String SIGNATURE;
	private String HASHED_CERTIFICATE;
	private String MODULUS;
	private String EXPONENT;

	private AsymmetricKey REQUESTER_PUBLIC_KEY;
	private AsymmetricKey CA_PUBLIC_KEY;

	private static final Integer SIGNATURE_LINE = 15;
	private static final Integer EXPONENT_LINE = 12;
	private static final Integer MODULUS_LINE = 11;

	public DigtialSignatureVerifier(File certificate,
			AsymmetricKey caPublicKey, AsymmetricKey requesterPublicKey) {
		CA_PUBLIC_KEY = caPublicKey;
		REQUESTER_PUBLIC_KEY = requesterPublicKey;
		readSignature(certificate);
	}

	public Boolean verify() {
		String signature = RSA.decryptMessage(SIGNATURE, CA_PUBLIC_KEY);
		if (signature.equals(HASHED_CERTIFICATE) && validPublicKey()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean validPublicKey() {
		if (REQUESTER_PUBLIC_KEY.getExponent().toString().equals(EXPONENT)
				&& REQUESTER_PUBLIC_KEY.getModulus().toString().equals(MODULUS)) {
			return true;
		}
		return false;
	}

	private void readSignature(File certificate) {
		Integer lineCount = 0;
		Collection<String> contents = new ArrayList<String>();
		;
		try (Scanner scanner = new Scanner(certificate)) {
			String line;
			while (scanner.hasNextLine()) {
				lineCount++;
				line = scanner.nextLine();
				if (lineCount <= 12)
					contents.add(line);
				if (lineCount.equals(MODULUS_LINE))
					MODULUS = line.split(":")[1].trim();
				if (lineCount.equals(EXPONENT_LINE))
					EXPONENT = line.split(":")[1].trim();
				if (lineCount.equals(SIGNATURE_LINE))
					SIGNATURE = line.trim();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		hashContents(contents);
	}

	private void hashContents(Collection<String> contents) {
		StringBuffer sb = new StringBuffer();
		for (String line : contents) {
			// TODO: refactor so that printed data is different from encrypted
			// data
			sb.append(line + "\r\n");
		}
		HASHED_CERTIFICATE = Digestor.process(sb.toString());
	}

}
