package ca_module;

import java.math.BigInteger;

public class RSA {
	private BigInteger privateKey;
	private BigInteger publicKey;
	private BigInteger modulus;

	public RSA() {
		KeyPair keyPair = new KeyPair();
		modulus = keyPair.getModulus();
		publicKey = keyPair.getPublicKey();
		privateKey = keyPair.getPrivateKey();
	}
	
	// c = cipher^privateKey mod n
	public BigInteger encrypt(BigInteger message) {
		return message.modPow(privateKey, modulus);
	}
	
	// m = cipher^publicKey mod n
	public BigInteger decrypt(BigInteger encrypted) {
		return encrypted.modPow(publicKey, modulus);
	}

	public void printDetails() {
		System.out.println("Private key: " + privateKey);
		System.out.println("Public key: " + publicKey);
		System.out.println("Mod: " + modulus);
	}
}
