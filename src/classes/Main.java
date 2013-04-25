package classes; 

import java.math.BigInteger;

public class Main {
	public static void main(String[] args) {
		System.out.println("NS");	
		RSA rsa = new RSA();
		BigInteger message = BigInteger.valueOf(10);
		BigInteger cipher = rsa.encrypt(message);
		System.out.println(cipher);
		System.out.println(rsa.decrypt(cipher));
	}
}
