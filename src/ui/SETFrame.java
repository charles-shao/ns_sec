package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import encryption_module.Primes;

public class SETFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JFrame frmSecureElectronicTransaction;
	private JTextField prime1;
	private JTextField prime2;
	private JButton btnKeyPair;
	private JScrollPane consolePane;
	private JTextPane console;

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
		getFrmSecureElectronicTransaction().setTitle(
				"Secure Electronic Transaction");
		getFrmSecureElectronicTransaction().setBounds(100, 100, 734, 506);
		getFrmSecureElectronicTransaction().setDefaultCloseOperation(
				JFrame.EXIT_ON_CLOSE);
		getFrmSecureElectronicTransaction().getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { ColumnSpec.decode("100dlu"),
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						ColumnSpec.decode("50dlu:grow"),
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						ColumnSpec.decode("50dlu"),
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						ColumnSpec.decode("100dlu"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						RowSpec.decode("top:15dlu"), RowSpec.decode("15dlu"),
						RowSpec.decode("15dlu"), RowSpec.decode("15dlu"),
						RowSpec.decode("150dlu"), }));

		JLabel lblPrimeNumbersFor = new JLabel("Prime Numbers for Key Pair");
		getFrmSecureElectronicTransaction().getContentPane().add(
				lblPrimeNumbersFor, "1, 2, right, default");

		prime1 = new JTextField();
		getFrmSecureElectronicTransaction().getContentPane().add(prime1,
				"3, 2, fill, default");
		prime1.setColumns(10);

		prime2 = new JTextField();
		getFrmSecureElectronicTransaction().getContentPane().add(prime2,
				"5, 2, fill, default");
		prime2.setColumns(10);

		btnKeyPair = new JButton("Make Keys");
		getFrmSecureElectronicTransaction().getContentPane().add(btnKeyPair,
				"7, 2");
		btnKeyPair.addActionListener(this);

		consolePane = new JScrollPane();
		frmSecureElectronicTransaction.getContentPane().add(consolePane,
				"3, 5, 5, 1, fill, fill");

		console = new JTextPane();
		console.setText("Initializing...");
		consolePane.setViewportView(console);
	}

	public void console(String log) {
		console.setText(log);
	}

	public void actionPerformed(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == btnKeyPair) {
			Primes prime = new Primes();
			System.out.println(prime.getLargePrime());
		}
	}

	public JFrame getFrmSecureElectronicTransaction() {
		return frmSecureElectronicTransaction;
	}

	public void setFrmSecureElectronicTransaction(
			JFrame frmSecureElectronicTransaction) {
		this.frmSecureElectronicTransaction = frmSecureElectronicTransaction;
	}
}
