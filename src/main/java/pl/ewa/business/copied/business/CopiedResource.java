package pl.ewa.business.copied.business;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pl.ewa.business.copied.entity.Copied;

@Stateless
@Path("/copied")
public class CopiedResource {

	@PersistenceContext(name = "ewa-persistent-context")
	private EntityManager em;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Copied> copiedTraders() {
		return em.createQuery("SELECT new pl.ewa.business.copied.entity.Copied(p.copyTraderName, SUM(p.profit), SUM(p.spread), SUM(p.rolloverFeesAndDividends)) "
				+ "              FROM Position p "
				+ "             GROUP BY p.copyTraderName").getResultList();				
	}
}
