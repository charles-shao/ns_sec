package classes; 

import hash_module.Digestor;

import java.util.Collection;

import customer_side.Customer;
import encryption_module.DigitalSignature;
import encryption_module.RSA;

public class Main {
	public static void main(String[] args) {
		System.out.println("NS");	
		RSA rsa = new RSA();
		
		String message = "The quick brown fox jumped over the lazy dog. 123ABC!@#$.";
		System.out.println(message);
		String hashed_message = Digestor.process(message);
		System.out.println("Hashed message: " + hashed_message);
		
		Collection<String> cipherBlock = rsa.encrypt(hashed_message);
		System.out.println("Cipher hash block: " + rsa.stringifyCipherMessage());
		String decry = rsa.decryptMessage(cipherBlock, rsa.getPublicKey(), rsa.getModulus());
		System.out.println(decry);

		System.out.println("\r\nCustomer dual signature");
		Customer customer = new Customer();
		customer.createDualSignature();
		System.out.println();

		DigitalSignature ds = new DigitalSignature(customer.getPublicKey(), customer.getModulus());

	}
}
