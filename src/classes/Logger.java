package classes;

import java.util.ArrayList;
import java.util.Collection;

import ui.SETFrame;

public class Logger {
	private static final String BREAK = "\r\n";
	private static Collection<String> LOG = new ArrayList<String>();
	private static SETFrame FRAME;
	public static void write(String m) {
		LOG.add(m);
		print();
	}
	
	public static void setFrame(SETFrame windowFrame) {
		FRAME = windowFrame;
	}
	
	private static void print() {
		StringBuilder sb = new StringBuilder();
		for (String s : LOG) {
			sb.append(s + BREAK);
		}
		FRAME.console(sb.toString());	
	}
}
