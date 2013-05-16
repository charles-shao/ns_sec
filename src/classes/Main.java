package classes; 

import hash_module.Digestor;

import java.util.Collection;

import certificate_authority.CertificateAuthority;
import customer_side.Customer;
import encryption_module.RSA;
import encryption_module.TripleDES;

public class Main {
	public static void main(String[] args) {
		RSA rsa = new RSA();
		
		String message = "The quick brown fox jumped over the lazy dog. 123ABC!@#$.";
		System.out.println(message);
		String hashed_message = Digestor.process(message);
		System.out.println("Hashed message: " + hashed_message);
		
		Collection<String> cipherBlock = rsa.encrypt(hashed_message);
		System.out.println("Cipher hash block: " + rsa.stringifyCipherMessage());
		String decry = rsa.decryptMessage(cipherBlock, rsa.getPublicKey(), rsa.getModulus());
		System.out.println("Decrypted message: " + decry);

		// rough implementation
		System.out.println("\r\nMessage digest of OI/PI");
		Customer customer = new Customer();
		customer.digestPaymentOrder();
		
		System.out.println("\n");
		TripleDES tDES = new TripleDES();
		System.out.println("tDES key: " + tDES.getKeyAsHex());
		byte[] tdesMessage = tDES.encrypt(message);
		System.out.println(tDES.decrypt(tdesMessage));
		
		System.out.println("\n");
		CertificateAuthority.issueCertificate(rsa);
	}
	
	private void steps(){
		// 1. Customer, merchant and bank have CA certificates (contains each of their RSA public keys + encrypted with TripleDES)
		// 2. Customer establishes handshakes with the bank and merchant (Use RSA, TripleDES and SHA1). Create Signature and send secret.
		// 3. Merchant has two signatures. One to sign messages and another for key exchange.
		// 4. Bank has two signatures. One to sign messages and another for key exchange.
		// 5. Merchant receives the following information - PIMD, OI and Dual Signature (signed by CA)
		// 6. Merchant decrypts Dual Signature with Customer PK to verify that MD(OI)||PIMD is the same
		// 7. Bank receives the following information - OIMD, PI and Dual Signature (signed by CA)
		// 8. Bank decrypts Dual Signature with Customer PK to verify that OIMD||MD(PI) is the same
		// 9. Bank and Merchant send TripleDES secret
		// 9. Merchant sends PIMD, its public key encrypted with TripleDES and its Digital Signature signed by its CA
		// 10. Bank decrypts the public key with the shared secret (TripleDES), decrypts CA with the public key then...
	}
}
