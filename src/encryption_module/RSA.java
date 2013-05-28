package encryption_module;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import javax.crypto.SecretKey;

import classes.Logger;

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
	 * Supposedly meant to encode the secret with the private key but had
	 * difficulties reconstructing the decrypted secret key
	 * 
	 * @param key
	 * @return
	 */
	public byte[] encryptSecretKey(SecretKey key) {
		byte[] encodedKey = key.getEncoded();
		return encodedKey;
	}

	/**
	 * Decrypt when object is known m = cipher^publicKey mod n
	 * 
	 * @param encrypted
	 * @return
	 */
	public BigInteger decrypt(BigInteger encrypted) {
		BigInteger exponent = publicKey.getExponent();
		BigInteger modulus = publicKey.getModulus();
		return encrypted.modPow(exponent, modulus);
	}

	/* Chain block method, leads to serialize() */
	public RSA encryptMessage(String message) {
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
		try {
			for (String hexPoint : cipherBlock) {
				BigInteger cipherPoint = BigInteger.valueOf(Long.parseLong(
						hexPoint, 16));
				Integer codePoint = decrypt(cipherPoint, publicKey).intValue();
				sb.append(Character.toChars(codePoint));
			}	
		} catch (Exception e) {
			Logger.write("Mismatch detected. Aborting.");
		}
		return sb.toString();
	}

	public String serialize() {
		StringBuffer sb = new StringBuffer();
		for (String cipherPoint : cipherText) {
			sb.append(cipherPoint);
			sb.append(":");
		}
		/*
		 * small hack - delete last delimiter. if only java came with a join()
		 * method...
		 */
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public AsymmetricKey getPublicKey() {
		return publicKey;
	}

	public String getAlgorithm() {
		return "RSA";
	}

	/**
	 * Encrypt when object is known. This is not revealed but internally
	 * processed. c = message^privateKey mod n
	 * 
	 * @param message
	 * @return
	 */
	private BigInteger encrypt(BigInteger message) {
		BigInteger exponent = privateKey.getExponent();
		BigInteger modulus = privateKey.getModulus();
		return message.modPow(exponent, modulus);
	}

	private static BigInteger decrypt(BigInteger cipher, AsymmetricKey publicKey) {
		return cipher.modPow(publicKey.getExponent(), publicKey.getModulus());
	}
}
