package classes;

import java.util.ArrayList;
import java.util.Collection;

public class Logger {
	private static final String BREAK = "\r\n";
	private Collection<String> LOG;

	public Logger() {
		LOG = new ArrayList<String>();
	}
	
	public void log(String m) {
		LOG.add(m);
	}
	
	public String spit() {
		StringBuilder sb = new StringBuilder();
		for (String s : LOG) {
			sb.append(s + BREAK);
		}
		return sb.toString();
	}
}
