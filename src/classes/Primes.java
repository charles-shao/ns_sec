package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Primes {
	private static final int PRIME_THRESHOLD = 300;
	private int largePrime, smallPrime;
	
	public Primes() {
		generatePrimePair();	
	}
	
	private void generatePrimePair() {
		List<Integer> primes = generatePrimes(PRIME_THRESHOLD);
		Random randomizer = new Random();
		int midPoint = primes.size() / 2;
		
		int _largePrime = primes.get(randomizer.nextInt(midPoint) + midPoint);
		int _smallPrime = primes.get(randomizer.nextInt(midPoint));
		setLargePrime(_largePrime);
		setSmallPrime(_smallPrime);
	}
	
	/** 
	 * Simple prime number generator. Not efficient for large numbers.
	 * @param threshold
	 * @return
	 */
	private List<Integer> generatePrimes(int threshold) {
	    List<Integer> primes = new ArrayList<Integer>();

	    OUTERLOOP:
	    for (int i = 2; i <= threshold; i++) {
	        for (Integer p : primes) {
	            if (i % p == 0) {
	            	continue OUTERLOOP;	
	            }
	        }
	        primes.add(i);
	    }
	    return primes;
	}

	public int getLargePrime() {
		return largePrime;
	}

	public void setLargePrime(int largePrime) {
		this.largePrime = largePrime;
	}

	public int getSmallPrime() {
		return smallPrime;
	}

	public void setSmallPrime(int smallPrime) {
		this.smallPrime = smallPrime;
	}
	
}
