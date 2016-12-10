package pl.ewa.business.importer.boundary;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pl.ewa.business.webtrader.entity.Statement;

@Stateless
@Path("/account")
public class AccountResource {

	@PersistenceContext(name = "ewa-persistence-context")
	EntityManager em;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Statement summary(@QueryParam("year") Integer year, @QueryParam("month") Integer month) {
		return (Statement) em.createQuery("SELECT s FROM Statement s WHERE s.startDate <= :end AND s.endDate >= :begin ORDER BY s.startDate")
				.setParameter("begin", beginDate(year, month))
				.setParameter("end", endDate(year, month))
				.getResultList().get(0);
	}

	private Date endDate(Integer year, Integer month) {
		if (year == null) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		if (month == null) {
			month = Calendar.DECEMBER;
		} else {
			month--;
		}
		
		Calendar calendar = new GregorianCalendar(year, month, 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		
		return calendar.getTime();
	}

	private Date beginDate(Integer year, Integer month) {
		if (year == null) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		if (month == null) {
			month = Calendar.JANUARY;
		} else {
			month--;
		}
		
		return new GregorianCalendar(year, month, 1).getTime();
	}
}
