package hash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * SHA1 Hash function Output bits: 160 bits Internal state: 160 bits Block size:
 * 512 bits Maximum message: 2^64 - 1 Word size: 32 Rounds: 80 Operations: add,
 * and, or, xor, rotate, mod Collisions: theoretical attack of 2^51
 */
public class SHA1 {

	private Collection<String> INITIAL_HASH_FUNCTION;

	public SHA1() {
		INITIAL_HASH_FUNCTION = new ArrayList<String>();
		generateHashFunction();
	}

	public String getHash() {
		StringBuilder sb = new StringBuilder();
		for (String s : INITIAL_HASH_FUNCTION) {
			sb.append(s + " ");
		}
		return sb.toString().trim();
	}

	private void generateHashFunction() {
		for (int i = 0; i < 5; i++) {
			INITIAL_HASH_FUNCTION.add(randomizer(8));
		}
	}

	private String randomizer(int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length) {
			sb.append(Integer.toHexString(random.nextInt()));
		}
		return sb.toString();
	}

}
