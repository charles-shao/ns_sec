package customer_side;

import hash_module.Digestor;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import encryption_module.RSA;

public class Customer {
	
	private static final String PAYMENT_INFORMATION_PATH = "/home/charles/devel/ns_set/files/payment_information.txt";
	private static final String ORDER_INFORMATION_PATH = "/home/charles/devel/ns_set/files/order_information.txt";
	private Collection<String> PAYMENT_HASH;
	private Collection<String> ORDER_HASH;
	private RSA RSA;
	
	public Customer() {
		RSA = new RSA();
		PAYMENT_HASH =new ArrayList<String>();
		ORDER_HASH = new ArrayList<String>();
	}

	public void createDualSignature() {
		digestFile(PAYMENT_INFORMATION_PATH, PAYMENT_HASH);
		digestFile(ORDER_INFORMATION_PATH, ORDER_HASH);
		
		StringBuffer messageDigest = new StringBuffer();
		// Concatenate both hashes to message digest
		for (String hash : PAYMENT_HASH) {
			messageDigest.append(hash);
		}
		for (String hash : ORDER_HASH) {
			messageDigest.append(hash);
		}	
		
		// Rehash the message digest
		String paymentOrder = Digestor.process(messageDigest.toString());
		// Encrypt the hash with RSA
		Collection<String> pomd = RSA.encrypt(paymentOrder);
		for (String s : pomd) {
			System.out.print(s);	
		}	
	}
	
	public BigInteger getPublicKey() {
		return RSA.getPublicKey();
	}
	
	public BigInteger getModulus() {
		return RSA.getModulus();
	}
	
	private void digestFile(String filename, Collection<String> hash) {
		try (Scanner scanner = new Scanner(new File(filename))) {
			String line;
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				hash.add(Digestor.process(line));
			};
			scanner.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
