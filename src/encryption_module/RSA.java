package encryption_module;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

public class RSA {
	private AsymmetricKey privateKey;
	private AsymmetricKey publicKey;
	
	private Collection<String> cipherText;
	
	public RSA() {
		KeyPair keyPair = new KeyPair();
		publicKey = keyPair.getPublicKey();
		privateKey = keyPair.getPrivateKey();
	}
	
	/**
	 * Encrypt when object is known
	 * c = cipher^privateKey mod n
	 * @param message
	 * @return
	 */
	private BigInteger encrypt(BigInteger message) {
		BigInteger exponent = privateKey.getExponent();
		BigInteger modulus = privateKey.getModulus();
		return message.modPow(exponent, modulus);
	}
	
	/**
	 * Decrypt when object is known
	 * m = cipher^publicKey mod n
	 * @param encrypted
	 * @return
	 */
	public BigInteger decrypt(BigInteger encrypted) {
		BigInteger exponent = publicKey.getExponent();
		BigInteger modulus = publicKey.getModulus();
		return encrypted.modPow(exponent, modulus);
	}
	
	public RSA encrypt(String message) {
		cipherText = new ArrayList<String>();
		for (int i = 0, n = message.length(); i < n; i++) {
			Integer codePoint = message.codePointAt(i);
		    BigInteger cipherPoint = encrypt(BigInteger.valueOf(codePoint));	
		    String hexString = Long.toHexString(cipherPoint.intValue());
		    cipherText.add(hexString);
		}
		return this;
	}
	
	public static String decryptMessage(String message, AsymmetricKey publicKey) {
		String[] cipherBlock = message.split(":");
		StringBuffer sb = new StringBuffer();
		for (String hexPoint : cipherBlock) {
			BigInteger cipherPoint = BigInteger.valueOf(Long.parseLong(hexPoint, 16));
			Integer codePoint = decrypt(cipherPoint, publicKey).intValue();
			sb.append(Character.toChars(codePoint));
		}
		return sb.toString();
	}
	
	public String serialize() {
		StringBuffer sb = new StringBuffer();
		for (String cipherPoint : cipherText) {
			sb.append(cipherPoint);
			sb.append(":");
		}
		sb.deleteCharAt(sb.length() - 1); // small hack - delete last delimiter
		return sb.toString();
	}

	public void printDetails() {
		System.out.println("Private key: " + privateKey);
		System.out.println("Public key: " + publicKey);
	}
	
	public AsymmetricKey getPublicKey() {
		return publicKey;
	}
	
	public String getAlgorithm(){
		return "RSA";
	}
	
	private static BigInteger decrypt(BigInteger cipher, AsymmetricKey publicKey) {
		return cipher.modPow(publicKey.getExponent(), publicKey.getModulus());
	}
}
