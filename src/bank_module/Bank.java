package bank_module;

import hash_module.Digestor;

import java.io.File;
import java.util.Collection;

import classes.Entity;
import encryption_module.AsymmetricKey;
import encryption_module.RSA;

public class Bank extends Entity {

	public Bank() {
		CERTIFICATE_PATH = "files/bank/pk_certificate.ds";
		_RSA = new RSA();
		PUBLIC_KEY = _RSA.getPublicKey();
	}

	@Override
	public Boolean recieveTransaction(File signatureFile, AsymmetricKey pk,
			Collection<byte[]> encryptedPI, String oimd) {
		String dualSignature = decryptSignature(read(signatureFile), pk);
		String digestedPI = digest(encryptedPI);
		
		String paymentOrder = Digestor.process(digestedPI.concat(oimd));
		if (paymentOrder.equals(dualSignature)) {
			return true;
		}
		return false;
	}
}
