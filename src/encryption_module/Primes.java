package encryption_module;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Simple prime number generator.
 */
public class Primes {
	// 2 - 65536 prime number range
	private static final int LARGE_BIT_LENGTH = 16;
	// 2 - 256 prime number range
	private static final int SMALL_BIT_LENGTH = 8;
	private final static SecureRandom RANDOM = new SecureRandom();
	private BigInteger largePrime, smallPrime;

	public Primes() {
		largePrime = BigInteger.probablePrime(LARGE_BIT_LENGTH, RANDOM);
		smallPrime = BigInteger.probablePrime(SMALL_BIT_LENGTH, RANDOM);
	}

	public BigInteger getLargePrime() {
		return largePrime;
	}

	public BigInteger getSmallPrime() {
		return smallPrime;
	}
}
