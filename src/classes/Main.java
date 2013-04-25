package classes; 

import java.math.BigInteger;


public class Main {
	
	public static void main(String[] args) {
		System.out.println("NS");	
//		String hash = Digestor.process("Nothing but penguins here");
//		System.out.println(hash);
		KeyPair keyPair = new KeyPair();
		System.out.println(keyPair.getPrivateKey());
		System.out.println(keyPair.getPublicKey());
		System.out.println(keyPair.getModulo());
	}
	
	// Encrypt algorithm c= m^e mod n
	private void encrypt(String message) {
		
		
	}
	
	private static int gcd(int a, int b) {
		BigInteger b1 = BigInteger.valueOf(a);
		BigInteger b2 = BigInteger.valueOf(b);
		BigInteger gcd = b1.gcd(b2);
	    return gcd.intValue();
	}
	

}
