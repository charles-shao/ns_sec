package classes;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/* Use this later */
public class DigitalSignature {
	
	public DigitalSignature() {
		
		try {
			// Generate the KeyPair
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
			
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(1024, random);
			
			// Create KeyPair
			KeyPair pair = keyGen.generateKeyPair();
			
			PrivateKey priv = pair.getPrivate();
			PublicKey pub = pair.getPublic();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}

	}
	
	public void sign(String[] args){
        if (args.length != 1) {
            System.out.println("Usage: GenSig nameOfFileToSign");
        }
        else try {

        // the rest of the code goes here

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }		
	}
}
