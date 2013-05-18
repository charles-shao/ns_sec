package encryption_module;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Hex;

/**
 * TripleDES Specifications
 * 
 */
public class TripleDES {
	private static final String ALGORITHM = "DESede";

	private static Cipher cipher;
	private static SecretKey key;

	public TripleDES() {
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			key = generateKey();
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Encrypt with the same object - probably the safest way
	 * @param message
	 * @return byte[]
	 */
	public byte[] encrypt(String message) {
		byte[] inputBytes = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			inputBytes = cipher.doFinal(message.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException e) {
			e.printStackTrace();
		}
		return inputBytes;
	}

	/**
	 * Encrypt statically - messages only
	 * @param message
	 * @param secretKey
	 * @return byte[]
	 */
	public static byte[] encrypt(String message, SecretKey secretKey) {
		byte[] inputBytes = null;
		try {
			Cipher encryptCipher = Cipher.getInstance(ALGORITHM);
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			inputBytes = encryptCipher.doFinal(message.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return inputBytes;
	}

	/**
	 * Decrypt text
	 * @param cipherText
	 * @return String:message
	 */
	public String decrypt(byte[] cipherText) {
		byte[] outputBytes = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			outputBytes = cipher.doFinal(cipherText);
			return new String(outputBytes, "UTF-8");
		} catch (IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Message decryption
	 * @param cipherText
	 * @param secretKey
	 * @return String:message
	 */
	public static String decrypt(byte[] cipherText, SecretKey secretKey) {
		byte[] outputBytes = null;
		try {
			Cipher decryptCipher = Cipher.getInstance(ALGORITHM);
			decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
			outputBytes = decryptCipher.doFinal(cipherText);
			return new String(outputBytes, "UTF-8");
		} catch (IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Key encryption
	 * Used for encrypting key objects into byte array. Static encryption.
	 * @param message
	 * @param secretKey
	 * @return byte[]
	 */
	public static byte[] encryptKey(byte[] publicKey, SecretKey secretKey) {
		byte[] inputBytes = null;
		try {
			Cipher encryptCipher = Cipher.getInstance(ALGORITHM);
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			inputBytes = encryptCipher.doFinal(publicKey);
		} catch (IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return inputBytes;
	}
	
	/**
	 * Key decryption
	 * Used for decrypting byte array into object. Static decryption.
	 * @param cipherText
	 * @param secretKey
	 * @return byte[]
	 */
	public static byte[] decryptKey(byte[] cipherText, SecretKey secretKey) {
		byte[] outputBytes = null;
		try {
			Cipher decryptCipher = Cipher.getInstance(ALGORITHM);
			decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
			outputBytes = decryptCipher.doFinal(cipherText);
			return outputBytes;
		} catch (IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getKeyAsHex() {
		String keyHex = Hex.encodeHexString(key.getEncoded());
		return keyHex;
	}

	public SecretKey getKey() {
		return key;
	}

	private static SecretKey generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
		return keygen.generateKey();
	}
	
}