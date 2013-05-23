package ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class SETFrame {

	private JFrame frmSecureElectronicTransaction;
	private JTextField prime1;
	private JTextField prime2;
	private JButton btnKeyPair;
	private JTextArea console;

	/**
	 * Create the application.
	 */
	public SETFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrmSecureElectronicTransaction(new JFrame());
		getFrmSecureElectronicTransaction()
				.setTitle("Secure Electronic Transaction");
		getFrmSecureElectronicTransaction().setBounds(100, 100, 734, 506);
		getFrmSecureElectronicTransaction()
				.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrmSecureElectronicTransaction().getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("100dlu"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("50dlu"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("50dlu"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("100dlu"),},
			new RowSpec[] {
				RowSpec.decode("top:15dlu"),
				RowSpec.decode("15dlu"),
				RowSpec.decode("15dlu"),
				RowSpec.decode("15dlu"),
				RowSpec.decode("100dlu"),}));

		JLabel lblPrimeNumbersFor = new JLabel("Prime Numbers for Key Pair");
		getFrmSecureElectronicTransaction().getContentPane().add(lblPrimeNumbersFor,
				"1, 2, right, default");

		prime1 = new JTextField();
		getFrmSecureElectronicTransaction().getContentPane().add(prime1,
				"3, 2, fill, default");
		prime1.setColumns(10);

		prime2 = new JTextField();
		getFrmSecureElectronicTransaction().getContentPane().add(prime2,
				"5, 2, fill, default");
		prime2.setColumns(10);

		btnKeyPair = new JButton("Make Keys");
		getFrmSecureElectronicTransaction().getContentPane().add(btnKeyPair, "7, 2");

		console = new JTextArea();
		console.setEditable(false);
		getFrmSecureElectronicTransaction().getContentPane().add(console,
				"3, 5, 5, 1, fill, fill");
	}
	
	public void console(String log) {
		console.setText(log);
	}

	public JFrame getFrmSecureElectronicTransaction() {
		return frmSecureElectronicTransaction;
	}

	public void setFrmSecureElectronicTransaction(
			JFrame frmSecureElectronicTransaction) {
		this.frmSecureElectronicTransaction = frmSecureElectronicTransaction;
	}
}
