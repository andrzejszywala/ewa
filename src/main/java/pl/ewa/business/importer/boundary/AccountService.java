package pl.ewa.business.importer.boundary;

import java.io.IOException;
import java.io.InputStream;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;

import pl.ewa.business.importer.control.ExcelImporter;
import pl.ewa.business.webtrader.entity.Account;
import pl.ewa.business.webtrader.entity.Transaction;
import pl.ewa.business.webtrader.entity.TransactionPK;

@Stateless
public class AccountService {
	
	@PersistenceContext(unitName = "ewa-persistence-unit")
	private EntityManager em;

	public Response importPositions(InputStream is) throws IOException {
		Account account = new ExcelImporter().execute(is);
		saveOrUpdate(account);
		return Response.noContent().build();
	}

	private void saveOrUpdate(Account account) {
		Account userAccount = em.find(Account.class, account.getUserName());
		if (userAccount == null) {
			em.persist(account);
		} else {
			// TODO implement merging
			account.getTransactions().forEach(e -> saveTranaction(e));
		}
		
	}

	private void saveTranaction(Transaction transaction) {
		if (em.find(Transaction.class, new TransactionPK(transaction.getDate(), transaction.getType(), transaction.getPosition())) == null) {
			em.persist(transaction);
		}
	}

}
