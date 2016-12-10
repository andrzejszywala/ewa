package pl.ewa.business.transactin.boundary;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pl.ewa.Cal;
import pl.ewa.business.exchange.entity.Exchange;
import pl.ewa.business.webtrader.entity.Transaction;

@Stateless
@Path("/transaction")
public class TransactionResource {
	
	@PersistenceContext(name = "ewa-persistence-context")
	EntityManager em;

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response transactions() {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		Map<Date, Exchange> exchanges = exchanges();
		closedTransactions(builder, exchanges);
		availableBonus(builder, exchanges);
		
		return Response.ok().entity(builder.build()).build();
	}

	private void availableBonus(JsonArrayBuilder builder, Map<Date, Exchange> exchanges) {
		BigDecimal prevNwa = BigDecimal.ZERO;
		BigDecimal suma = BigDecimal.ZERO;

		for (Transaction transaction : (Collection<Transaction>)em.createQuery(
				  "SELECT t "
				+ "  FROM Transaction t "
				+ " ORDER BY t.date ASC, t.nwa DESC").getResultList()) {

			if (prevNwa.compareTo(transaction.getNwa()) > 0) {
				builder.add(buildAvailableBonus(prevNwa, transaction, exchanges));
				suma = suma.add(prevNwa.subtract(transaction.getNwa()));
				System.out.println(transaction.getDate() + " nwa " + transaction.getNwa() + " " + suma);
			}
			prevNwa = transaction.getNwa();
		}		
	}

	private void closedTransactions(JsonArrayBuilder builder, Map<Date, Exchange> exchanges) {
		for (Transaction transaction : (Collection<Transaction>)em.createQuery(
				  "SELECT t "
				+ "  FROM Transaction t "
				+ " WHERE t.realizedEquityChange <> 0 "
				+ "   AND t.type IN ('Profit/Loss of Trade', 'Rollover Fee') "
				+ " ORDER BY t.date").getResultList()) {
			builder.add(buildTransactionJson(transaction, exchanges));
	
		}
	}

	private JsonObjectBuilder buildAvailableBonus(BigDecimal prevNwa, Transaction transaction, Map<Date, Exchange> exchanges) {
		BigDecimal availableAmount = prevNwa.subtract(transaction.getNwa());
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("date",	javax.xml.bind.DatatypeConverter.printDateTime(Cal.endar(transaction.getDate())));
		builder.add("type", "NWA");
		builder.add("details", "Available bonus");
		builder.add("usd", availableAmount);
		Exchange previous = previousDay(exchanges, transaction.getDate());
		builder.add("plnExchange", previous.getAmount());
		builder.add("pln", availableAmount.multiply(previous.getAmount()).setScale(2, RoundingMode.HALF_UP));
		return builder;
	}

	private Map<Date, Exchange> exchanges() {
		Map<Date, Exchange> exchanges = new HashMap<>();
		for (Exchange exchange : (Collection<Exchange>) em.createQuery("SELECT e FROM Exchange e").getResultList()) {
			exchanges.put(exchange.getDate(), exchange);
		}
		return exchanges;
	}

	private JsonObjectBuilder buildTransactionJson(Transaction transaction, Map<Date, Exchange> exchanges) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("date",	javax.xml.bind.DatatypeConverter.printDateTime(Cal.endar(transaction.getDate())));
		builder.add("type", transaction.getType());
		builder.add("details", transaction.getDetails());
		builder.add("usd", transaction.getRealizedEquityChange());
		Exchange previous = previousDay(exchanges, transaction.getDate());
		builder.add("plnExchange", previous.getAmount());
		builder.add("pln", transaction.getRealizedEquityChange().multiply(previous.getAmount()).setScale(2, RoundingMode.HALF_UP));
		return builder;
	}

	private Exchange previousDay(Map<Date, Exchange> exchanges, Date date) {
		Exchange result = null;
		Calendar day = Cal.endar(date);
		day.add(Calendar.DAY_OF_MONTH, -1);
		day.set(Calendar.HOUR_OF_DAY, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MILLISECOND, 0);
		result = exchanges.get(day.getTime());
		while (result == null) {
			result = previousDay(exchanges, day.getTime());
		}
		return result;
	}
}
