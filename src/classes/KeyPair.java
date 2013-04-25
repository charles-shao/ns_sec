package classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Key pairs e and d.
 * Algorithm used: e.d = k.(p - 1).(q - 1) + 1
 * where k is any integer > 0
 */
public class KeyPair {
	private List<int[]> factors;
	private int n;
	private int publicKey;
	private int privateKey;
	
	public KeyPair() {
		factors = new ArrayList<int[]>();
		generateKeyPair();
	}
	
	public int getModulo() {
		return n;
	}
	
	private void generateKeyPair() {
		Primes primes = new Primes();
		int p = primes.getLargePrime();
		int q = primes.getSmallPrime();
		int k = 1;
		int kp;
		
		n = p * q;
		
		do {
			kp = k*(p-1)*(q-1)+1;
			k++;
		} while (!getFactors(kp));
		
		int[] keyPairs = factors.get(factors.size() -1);
		publicKey = keyPairs[0];
		privateKey = keyPairs[1];
	}

	// Do not add 1 and itself as part of the candidates for key pairs
	private boolean getFactors(int value) {
		int[] pair;
		int upperLimit = value;
		int currentNumber = value;

		for (int i = 2; i < upperLimit; i++) {
			if ((currentNumber % i) == 0) {
				upperLimit = currentNumber / i;
				// Avoid its root number
				if (upperLimit != i) {
					pair = new int[2];
					pair[0] = i;
					pair[1] = upperLimit;
					factors.add(pair);					
				}
			}
		}
		
		if (factors.size() > 1) {
			return true;
		} else {
			return false;
		}
	}

	public int getPublicKey() {
		return publicKey;
	}

	public int getPrivateKey() {
		return privateKey;
	}
}
