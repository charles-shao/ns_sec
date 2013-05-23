package classes;

import hash_module.Digestor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;

import javax.crypto.SecretKey;

import certificate_authority.CertificateAuthority;
import encryption_module.AsymmetricKey;
import encryption_module.RSA;
import encryption_module.TripleDES;

/**
 * Entity object which contains methods for Merchant and Bank
 * @author charles
 *
 */
public class Entity implements EntityInterface {
	protected String CERTIFICATE_PATH;
	
	protected RSA _RSA;
	protected AsymmetricKey PUBLIC_KEY;
	protected SecretKey SECRET_KEY;
	
	protected AsymmetricKey CA_PUBLIC_KEY;
	protected CertificateAuthority CA;
	
	public AsymmetricKey getPublicKey() {
		return PUBLIC_KEY;
	}

	public AsymmetricKey getCAPublicKey() {
		return CA_PUBLIC_KEY;
	}

	public File getCertificate() {
		return new File(CERTIFICATE_PATH);
	}

	public void establishSecret(SecretKey secret) {
		SECRET_KEY = secret;
	}
	
	public void requestCertificate() {
		CA = new CertificateAuthority();
		CA.createCertificate(PUBLIC_KEY, CERTIFICATE_PATH);
		CA_PUBLIC_KEY = CA.getPublicKey();
	}
	
	/**
	 * The order of hashes is PIMD||OIMD for verification purpose
	 * You need to implement this function
	 * @param signatureFile
	 * @param pk
	 * @param encryptedTransaction
	 * @param dependantEntity (either PIMD or OIMD)
	 * @return If order is successful
	 */
	public Boolean recieveTransaction(File signatureFile, AsymmetricKey pk,
			Collection<byte[]> encryptedTransaction, String mdEntity) {
		return false;
	}

	/**
	 * Decrypt it first and then hash it with RSA
	 * 
	 * @param cipherCollection
	 * @return
	 */
	protected String digest(Collection<byte[]> cipherCollection) {
		StringBuffer sb = new StringBuffer();
		for (byte[] cipherByte : cipherCollection) {
			String message = TripleDES.decrypt(cipherByte, SECRET_KEY);
			String hash = Digestor.process(message);
			sb.append(hash);
		}
		return sb.toString();
	}

	protected String read(File dualSignature) {
		String line = null;
		try (Scanner scanner = new Scanner(dualSignature)) {
			line = scanner.nextLine();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return line;
	}

	protected String decryptSignature(String line, AsymmetricKey pk) {
		return RSA.decryptMessage(line, pk);
	}
}
