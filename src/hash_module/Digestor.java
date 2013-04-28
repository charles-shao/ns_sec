package hash_module;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA1 Hash function 
 * Output bits: 160 bits 
 * Internal state: 160 bits 
 * Block size: 512 bits
 * Maximum message: 2^64 - 1
 * Word size: 32 
 * Rounds: 80 
 * Operations: add, and, or, xor, rotate, mod
 * Collisions: theoretical attack of 2^51
 */
public class Digestor {
	
	private static final String SHA1 = "SHA1";
	
	public static String process(String message) {
		String sha1 = null;
		try {
			sha1 = sha1(message);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sha1;
	}

	private static String sha1(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = null;
		mDigest = MessageDigest.getInstance(SHA1);

		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < result.length; i++) {
			// Convert bytecode to String
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
