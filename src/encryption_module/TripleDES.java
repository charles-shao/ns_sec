package encryption_module;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * TripleDES Specifications
 * 
 */
public class TripleDES {
	private static final String ALGORITHM = "DESede";

	public TripleDES() {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			SecretKey key = generateKey();

			System.out.println(key.getAlgorithm());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	public void encrypt(String message) {

	}

	public void decrypt() {

	}

	private static SecretKey generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
		return keygen.generateKey();
	}

}