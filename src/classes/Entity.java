package classes;

import hash_module.Digestor;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Scanner;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import certificate_authority.CertificateAuthority;
import encryption_module.AsymmetricKey;
import encryption_module.RSA;
import encryption_module.TripleDES;

/**
 * Entity object which contains methods for Merchant and Bank
 * 
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

	/**
	 * Decrypt the secret key with the public key. See RSA encrypt for reasons
	 * why this was not implemented.
	 * 
	 * @param bs
	 * @param customerPk
	 */
	public void establishSecret(byte[] bs, AsymmetricKey customerPk) {
		SECRET_KEY = new SecretKeySpec(bs, "DESede");
		Logger.write("Secret successfully established.");
	}

	public void requestCertificate() {
		CA = new CertificateAuthority();
		CA.createCertificate(PUBLIC_KEY, CERTIFICATE_PATH);
		CA_PUBLIC_KEY = CA.getPublicKey();
	}

	/**
	 * The order of hashes is PIMD||OIMD for verification purpose You need to
	 * implement this function
	 * 
	 * @param signatureFile
	 * @param pk
	 * @param encryptedTransaction
	 * @param dependantEntity
	 *            (either PIMD or OIMD)
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
		Logger.write("\tHash of file is: ");
		Logger.write("\t\t" + sb.toString());
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

	protected static BigInteger decrypt(BigInteger cipher,
			AsymmetricKey publicKey) {
		return cipher.modPow(publicKey.getExponent(), publicKey.getModulus());
	}
}
