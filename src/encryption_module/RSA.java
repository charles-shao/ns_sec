package encryption_module;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

public class RSA {
	private BigInteger privateKey;
	private BigInteger publicKey;
	private BigInteger modulus;
	
	private Collection<String> cipherText;
	
	public RSA() {
		KeyPair keyPair = new KeyPair();
		modulus = keyPair.getModulus();
		publicKey = keyPair.getPublicKey();
		privateKey = keyPair.getPrivateKey();
	}
	
	// c = cipher^privateKey mod n
	private BigInteger encrypt(BigInteger message) {
		return message.modPow(privateKey, modulus);
	}
	
	// m = cipher^publicKey mod n
	private BigInteger decrypt(BigInteger encrypted) {
		return encrypted.modPow(publicKey, modulus);
	}
	
	private BigInteger decrypt(BigInteger cipher, BigInteger publicKey, BigInteger modulus) {
		return cipher.modPow(publicKey, modulus);
	}
	
	public Collection<String> encrypt(String message) {
		cipherText = new ArrayList<String>();
		for (int i = 0, n = message.length(); i < n; i++) {
			Integer codePoint = message.codePointAt(i);
		    BigInteger cipherPoint = encrypt(BigInteger.valueOf(codePoint));		    
		    String hexString = Long.toHexString(cipherPoint.intValue());
		    cipherText.add(hexString);
		}
		return cipherText;
	}
	
	public String decryptMessage(Collection<String> cipherBlock, BigInteger publicKey, BigInteger modulus) {
		StringBuffer sb = new StringBuffer();
		for (String hexPoint : cipherBlock) {
			BigInteger cipherPoint = BigInteger.valueOf(Long.parseLong(hexPoint, 16));
			Integer codePoint = decrypt(cipherPoint, publicKey, modulus).intValue();
			sb.append(Character.toChars(codePoint));
		}
		return sb.toString();
	}
	
	public String stringifyCipherMessage() {
		StringBuffer sb = new StringBuffer();
		for (String cipherPoint : cipherText) {
			sb.append(cipherPoint);
		}
		return sb.toString();
	}

	public void printDetails() {
		System.out.println("Private key: " + privateKey);
		System.out.println("Public key: " + publicKey);
		System.out.println("Mod: " + modulus);
	}
	
	public BigInteger getPublicKey() {
		return publicKey;
	}
	
	public BigInteger getModulus() {
		return modulus;
	}
}
