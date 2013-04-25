package classes;

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

	BigInteger encrypt(BigInteger message) {
		return message.modPow(publicKey, modulus);
	}

	BigInteger decrypt(BigInteger encrypted) {
		return encrypted.modPow(privateKey, modulus);
	}

	public void printDetails() {
		System.out.println("Private key: " + privateKey);
		System.out.println("Public key: " + publicKey);
		System.out.println("Mod: " + modulus);
	}
}
