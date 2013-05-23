package encryption_module;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * KeyPair
 * Key pairs e and d. Algorithm used: e.d = k.(p - 1).(q - 1) + 1 where k is any
 * integer > 0
 */
public class KeyPair {
	private final static BigInteger one = new BigInteger("1");
	private List<BigInteger[]> factors;
	private static BigInteger k;
	private AsymmetricKey publicKey;
	private AsymmetricKey privateKey;

	public KeyPair() {
		k = new BigInteger("1");
		factors = new ArrayList<BigInteger[]>();
		generateKeyPair();
	}
	
	/**
	 * TODO: Generate KeyPair with a given set of prime numbers
	 * @param primeOne
	 * @param primeTwo
	 */
	public KeyPair(BigInteger primeOne, BigInteger primeTwo) {
		
	}

	private void generateKeyPair() {
		Primes primes = new Primes();
		BigInteger p = primes.getLargePrime();
		BigInteger q = primes.getSmallPrime();

		BigInteger modulus = p.multiply(q);
		BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
		BigInteger keyPair;

		long kCount = k.intValue();
		do {
			keyPair = k.multiply(phi).add(one);
			k = BigInteger.valueOf(kCount++);
		} while (!getFactors(keyPair.intValue()));

		/*
		 * Get the first set as they are the set of numbers which are farthest
		 * apart
		 */
		BigInteger[] keys = factors.get(0);
		publicKey = new AsymmetricKey(keys[0], modulus);
		privateKey = new AsymmetricKey(keys[1], modulus);
	}

	/**
	 * Get Factors Does not add 1 and itself as part of the candidates for key
	 * pairs
	 * 
	 * @param value
	 * @return
	 */
	private boolean getFactors(int value) {
		BigInteger[] pair;
		Integer upperLimit = value;
		Integer currentNumber = value;
		for (int i = 2; i < upperLimit; i++) {
			if ((currentNumber % i) == 0) {
				upperLimit = currentNumber / i;
				/*
				 * Avoid its root number
				 */
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

	public AsymmetricKey getPublicKey() {
		return publicKey;
	}

	public AsymmetricKey getPrivateKey() {
		return privateKey;
	}
}
