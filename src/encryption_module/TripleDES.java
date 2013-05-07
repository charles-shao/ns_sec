package encryption_module;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * TripleDES Specifications
 * 
 */
public class TripleDES {
	private static final String FILE_PATH = "/home/charles/devel/ns_set/files/key";
	private static final String ALGORITHM = "DESede";

	private static File keyFile;
	private static Cipher cipher;

	public TripleDES() {
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			SecretKey key = generateKey();

			keyFile = new File(FILE_PATH);

			System.out.println("Writing key to file...");
			writeKey(key, keyFile);
			System.out.println("The key is stored. At this point keys are generated for each time this is run.");
			System.out.println("The key is unique to the instantiated object. Previous TripleDES objects will be rendered useless.");
			System.out.println("This behaviour is expected to change!");

		} catch (NoSuchAlgorithmException | InvalidKeySpecException
				| IOException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	public void encrypt(String message) {
		try {
			SecretKey key = readKey(keyFile);
			System.out.println(key.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//TODO: Read key and encrypt message
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	public void decrypt() {

	}

	private static SecretKey generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
		return keygen.generateKey();
	}

	private static void writeKey(SecretKey key, File f) throws IOException,
			NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(ALGORITHM);
		DESedeKeySpec keyspec = (DESedeKeySpec) keyfactory.getKeySpec(key,
				DESedeKeySpec.class);
		byte[] raw = keyspec.getKey();

		// Write key to file
		FileOutputStream out = new FileOutputStream(f);
		out.write(raw);
		out.close();
	}

	private static SecretKey readKey(File f) {
		SecretKey key = null;
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(f));
			byte[] rawkey = new byte[(int) f.length()];
			in.readFully(rawkey);
			in.close();

			// Convert raw to DESedeKeySpec
			DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
			SecretKeyFactory keyfactory = SecretKeyFactory
					.getInstance("DESede");
			key = keyfactory.generateSecret(keyspec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}
}