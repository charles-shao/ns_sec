package bank_module;

import hash_module.Digestor;

import java.io.File;
import java.util.Collection;

import classes.Entity;
import classes.Logger;
import encryption_module.AsymmetricKey;
import encryption_module.RSA;

public class Bank extends Entity {

	public Bank() {
		CERTIFICATE_PATH = "files/bank/pk_certificate.ds";
		Logger.write("Bank: Generating RSA key pairs...");
		_RSA = new RSA();
		PUBLIC_KEY = _RSA.getPublicKey();
		Logger.write("Key generation successful. Using RSA encryption.\n");
	}

	@Override
	public Boolean recieveTransaction(File signatureFile, AsymmetricKey pk,
			Collection<byte[]> encryptedPI, String oimd) {
		Logger.write("\tDecrypting the signature with the customers public key...");
		String dualSignature = decryptSignature(read(signatureFile), pk);
		Logger.write("\tDecrypting the order information with the customers public key...");
		String digestedPI = digest(encryptedPI);
		Logger.write("\tChecking if the hashes match the one in the dual signature.");
		String paymentOrder = Digestor.process(digestedPI.concat(oimd));
		
		if (paymentOrder.equals(dualSignature)) {
			Logger.write("\tHashes match!");
			return true;
		}
		Logger.write("\tHashes do not match!");
		return false;
	}
}
