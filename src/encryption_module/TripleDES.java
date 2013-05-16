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
	
	public String decrypt(byte[] cipherText, SecretKey secretKey) {
		byte[] outputBytes = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			outputBytes = cipher.doFinal(cipherText);
			return new String(outputBytes, "UTF-8");
		} catch (IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getKeyAsHex(){
		String keyHex = Hex.encodeHexString(key.getEncoded());
		return keyHex;
	}

	private static SecretKey generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
		return keygen.generateKey();
	}
}