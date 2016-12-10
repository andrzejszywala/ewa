package pl.ewa.business.position.boundary;

import static javax.xml.bind.DatatypeConverter.printDateTime;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import pl.ewa.business.position.entity.Position;
import pl.ewa.business.webtrader.entity.Transaction;

@Stateless
@Path("/position")
public class PositionResource {

	@PersistenceContext(unitName = "ewa-persistence-unit")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonArray positions() {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (Position position : (Collection<Position>) em.createQuery("SELECT p FROM Position p ORDER BY p.closeDate DESC").getResultList()) {
			builder.add(buildJsonPosition(position));
		}
		return builder.build();
	}

	@GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response position(@PathParam("id") Long id) {
		Position position = em.find(Position.class, id);
        if (position == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(buildJsonPositionDetails(position)).build();
    }
	
	private JsonObjectBuilder buildJsonPosition(Position position) {
        return Json.createObjectBuilder().add("id", position.getId())
        		.add("closeDate", javax.xml.bind.DatatypeConverter.printDateTime(asCalendar(position.getCloseDate())))
        		.add("action", position.getAction())
        		.add("amount", position.getAmount())
        		.add("copyTraderName", position.getCopyTraderName())
        		.add("units", position.getUnits())
        		.add("openRate", position.getOpenRate())
        		.add("closeRate", position.getCloseRate())
        		.add("spread", position.getSpread())
        		.add("profit", position.getProfit())
        		.add("rolloverFeesAndDividends", position.getRolloverFeesAndDividends());        		
    }
	
	
	
	private JsonObject buildJsonPositionDetails(Position position) {
        return Json.createObjectBuilder().add("id", position.getId())
        		.add("closeDate", printDateTime(asCalendar(position.getCloseDate())))
        		.add("action", position.getAction())
        		.add("copyTraderName", position.getCopyTraderName())
        		.add("units", position.getUnits())
        		.add("openRate", position.getOpenRate())
        		.add("closeRate", position.getCloseRate())
        		.add("spread", position.getSpread())
        		.add("profit", position.getProfit())
        		.add("rolloverFeesAndDividends", position.getRolloverFeesAndDividends())
        		.add("amount", position.getAmount())
        		.add("openDate", printDateTime(asCalendar(position.getOpenDate())))
        		.add("takeProfitRate", position.getTakeProfitRate())
        		.add("stopLossRate", position.getStopLossRate())
        		.add("transactions", buildJsonTransactions(position))        		
        		.build();
    }
	
	private JsonValue buildJsonTransactions(Position position) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (Transaction transaction : (Collection<Transaction>) em.createQuery("SELECT t FROM Transaction t WHERE t.position = :positionId ORDER BY t.date DESC").setParameter("positionId", position.getId()).getResultList()) {
			builder.add(buildJsonTransaction(transaction));
		}
		return builder.build();
	}

	private JsonObjectBuilder buildJsonTransaction(Transaction transaction) {
		return Json.createObjectBuilder()
				.add("date", printDateTime(asCalendar(transaction.getDate())))
				.add("type", transaction.getType())
				.add("realizedEquityChange", transaction.getRealizedEquityChange())
				.add("realizedEquity", transaction.getRealizedEquity());
	}

	private Calendar asCalendar(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        return cal;
    }

	
}
