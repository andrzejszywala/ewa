package pl.ewa.business.equity.boundary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pl.ewa.business.equity.entity.Equity;

@Stateless
@Path("/equity")
public class EquityResource {

	@PersistenceContext(unitName = "ewa-persistence-unit")
	private EntityManager em;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Equity> realizedEquity() {
		List<Equity> equities = em.createQuery(
				" SELECT new pl.ewa.business.equity.entity.Equity(e.date, e.realizedEquity) "
                + " FROM Transaction e "
				+ "ORDER BY e.date ").getResultList();
		
		List<Equity> result = new ArrayList<>();
		Equity last = null;
		for (Equity current : equities) {
			if (last == null) {
				last = current;
			} else if (!sameDay(last, current)) {
				result.add(last);
			}
			last = current;
		}
		result.add(last);
		return result;
	}

	private boolean sameDay(Equity prev, Equity current) {
		return prev.getDate().getDate() == current.getDate().getDate() && prev.getDate().getMonth() == current.getDate().getMonth() && prev.getDate().getYear() == current.getDate().getYear();
	}
}
