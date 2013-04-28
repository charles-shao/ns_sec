package ca_module;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class DigitalSignature {
	private static final String FILE_PATH = "/home/charles/devel/ns_set/certificates/ca";
	
	public DigitalSignature() {
		try {
			writeDS();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void writeDS() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(FILE_PATH, "UTF-8");
		writer.println("Certificate:");
		writer.println("\tName: ");
		writer.println("\tPublic Key: ");
		writer.println("\tPublic Key: ");
		writer.close();
	}
	
	private void writePk() {
		
	}
		
}
