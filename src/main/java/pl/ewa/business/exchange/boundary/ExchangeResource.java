package pl.ewa.business.exchange.boundary;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pl.ewa.business.exchange.control.NbpUsdExchange;
import pl.ewa.business.exchange.entity.Exchange;
import pl.ewa.business.exchange.entity.ExchangePK;

@Stateless
@Path("/exchange")
public class ExchangeResource {

	private static final String YEAR_DIR = "dir{0}.txt";
	
	@PersistenceContext(unitName = "ewa-persistence-unit")
	private EntityManager em;
	
	@PUT
	public void importNBP(@QueryParam("yearFrom") Integer yearFrom, @QueryParam("yearTo") Integer yearTo) {
		for (Exchange exchange : new NbpUsdExchange(yearFrom, yearTo)) {
			if (em.find(Exchange.class, new ExchangePK(exchange.getCurrency(), exchange.getDate())) == null) {
				em.persist(exchange);
			}
		}
	}
	
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Exchange> exchanges() {
		return em.createQuery("SELECT e FROM Exchange e").getResultList();
	}
	
	
}
