package customer_side;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Customer {
	
	private static final String PAYMENT_INFORMATION_PATH = "/home/charles/devel/ns_set/files/payment_information.txt";
	private static final String ORDER_INFORMATION_PATH = "/home/charles/devel/ns_set/files/order_information.txt";
	
	public Customer() {
		// hash message
		read(PAYMENT_INFORMATION_PATH);
		read(ORDER_INFORMATION_PATH);
	}
	
	private void read(String filename) {
		try (Scanner scanner = new Scanner(new File(filename))) {
			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			};
			scanner.close();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
