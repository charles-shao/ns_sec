package encryption_module;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * General asymmetric key object
 * 
 * @author charles
 * 
 */
public class AsymmetricKey implements Serializable {

	private static final long serialVersionUID = 1L;
	private String algorithm;
	private BigInteger exponent;
	private BigInteger modulus;

	public AsymmetricKey(BigInteger exponent, BigInteger modulus) {
		this.exponent = exponent;
		this.modulus = modulus;
		//This needs to be refactored to take RSA argument
		this.algorithm = "RSA"; 
	}

	public BigInteger getExponent() {
		return exponent;
	}

	public BigInteger getModulus() {
		return modulus;
	}
	
	public String getAlgorithm(){
		return algorithm;
	}

	public byte[] serialize() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
			objectOutputStream.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return out.toByteArray();
	}

	public static Object deserialize(byte[] data) {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		ObjectInputStream objectInputStream = null;
		Object obj = null;
		
		try {
			objectInputStream = new ObjectInputStream(in);
			obj = objectInputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
}
