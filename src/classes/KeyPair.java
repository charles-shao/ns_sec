package classes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Key pairs e and d.
 * Algorithm used: e.d = k.(p - 1).(q - 1) + 1
 * where k is any integer > 0
 */
public class KeyPair {
	private final static BigInteger one = new BigInteger("1");
	private List<BigInteger[]> factors;
	private static BigInteger k;
	private BigInteger modulus;
	private BigInteger publicKey;
	private BigInteger privateKey;
	
	public KeyPair() {
		k = new BigInteger("1");
		factors = new ArrayList<BigInteger[]>();
		generateKeyPair();
	}
	
	public BigInteger getModulus() {
		return modulus;
	}
	
	private void generateKeyPair() {
		Primes primes = new Primes();
		BigInteger p = primes.getLargePrime();
		BigInteger q = primes.getSmallPrime();
		
		modulus = p.multiply(q);
		BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
		BigInteger keyPair;
		
		long kCount = k.intValue();
		do {
			keyPair = k.multiply(phi).add(one);
			k = BigInteger.valueOf(kCount++);	
		} while (!getFactors(keyPair.intValue()));
		
		int lastElement = factors.size() - 1;
		BigInteger[] keys = factors.get(lastElement);
		publicKey = keys[0];
		privateKey = keys[1];
	}
	
	// Do not add 1 and itself as part of the candidates for key pairs
	private boolean getFactors(int value) {
		BigInteger[] pair;
		int upperLimit = value;
		int currentNumber = value;
		for (int i = 2; i < upperLimit; i++) {
			if ((currentNumber % i) == 0) {
				upperLimit = currentNumber / i;
				// Avoid its root number
				if (upperLimit != i) {
					pair = new BigInteger[2];
					pair[0] = BigInteger.valueOf(i);
					pair[1] = BigInteger.valueOf(upperLimit);
					factors.add(pair);					
				}
			}
		}

		if (factors.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public BigInteger getPublicKey() {
		return publicKey;
	}

	public BigInteger getPrivateKey() {
		return privateKey;
	}
}
