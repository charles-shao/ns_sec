package classes; 

import hash.Digestor;


public class Main {
	public static void main(String[] args) {
		System.out.println("NS");
		
		String hash = Digestor.process("Nothing but penguins here");
		System.out.println(hash);
		
		
	}
	

}
