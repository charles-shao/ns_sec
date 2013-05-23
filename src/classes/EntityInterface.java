package classes;

import java.io.File;
import java.util.Collection;

import encryption_module.AsymmetricKey;

public interface EntityInterface {

	public Boolean recieveTransaction(File signatureFile, AsymmetricKey pk,
			Collection<byte[]> encryptedTransaction, String md);

}
