package pl.ewa.business.exchange.control;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import pl.ewa.business.exchange.entity.Exchange;
import pl.ewa.business.exchange.entity.TabelaKursow;

public class NbpUsdExchange implements Iterable<Exchange> {

	private static final String FILES_URL = "http://www.nbp.pl/kursy/xml/";
	private static final String CURRENCY_CODE = "USD";
	
	private int yearFrom;
	private int yearTo;
	private Collection<Exchange> exchanges = new ArrayList<>();
	
	public NbpUsdExchange(int yearFrom, int yearTo) {
		super();
		this.yearFrom = yearFrom;
		this.yearTo = yearTo;
		collectExchanges();
	}

	@Override
	public Iterator<Exchange> iterator() {
		return exchanges.iterator();
	}

	private void collectExchanges() {
		for (int year = yearFrom; year <= yearTo; year++) {
			for (String filename : new NbpDir(year)) {
				addExchangeRate(filename);
			}
		}
	}

	private void addExchangeRate(String file) {
		TabelaKursow exchangesTable = (TabelaKursow)JAXBUtils.xmlToObject(fileContent(file));
		for (TabelaKursow.Pozycja position : exchangesTable.getPozycja()) {
			if (CURRENCY_CODE.equals(position.getKodWaluty())) {
				exchanges.add(new Exchange(CURRENCY_CODE, exchangesTable.getDataPublikacji().toGregorianCalendar().getTime(), new BigDecimal(position.getKursSredni().replace(",", "."))));
			}
		}
	}
	
	private String fileContent(String file) {
		StringBuilder result = new StringBuilder();
		try {
			try (Scanner in = new Scanner(new InputStreamReader(new URL(FILES_URL + file + ".xml").openStream()))) {
				while (in.hasNextLine()) {
					result.append(in.nextLine());
				}
			}
		} catch (Exception e) {

		}
		return result.toString();
	}

}
