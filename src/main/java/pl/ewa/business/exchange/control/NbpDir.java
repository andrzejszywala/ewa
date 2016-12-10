package pl.ewa.business.exchange.control;

import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

public class NbpDir implements Iterable<String> {
	private static final String URL_PATTERN = "http://www.nbp.pl/kursy/xml/dir{0}.txt";
	private static final String AVERAGE_EXCHANGE_RATE_PATTERN = "a";
	private int year;
	
	public NbpDir(int year) {
		super();
		this.year = year;
	}

	@Override
	public Iterator<String> iterator() {
		return fileNames().iterator();
	}

	private Collection<String> fileNames() {
		Collection<String> result = new ArrayList<>();
		try {
			try (Scanner in = new Scanner(new InputStreamReader(new URL(MessageFormat.format(URL_PATTERN, getYear())).openStream()))) {
				String line = null;
				while(in.hasNextLine()) {
					line = in.nextLine();
					if (line.startsWith(AVERAGE_EXCHANGE_RATE_PATTERN)) {
						result.add(line);
					}
				}
			}
		} catch (Exception e) {
			
		}
		return result;
	}

	private String getYear() {
		return year == Calendar.getInstance().get(Calendar.YEAR) ? "" : "" + year ;
	}
}
