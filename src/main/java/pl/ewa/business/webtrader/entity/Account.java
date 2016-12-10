package pl.ewa.business.webtrader.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import pl.ewa.business.position.entity.Position;

@Entity
public class Account implements Serializable {

	private static final long serialVersionUID = 5672801809200373720L;

	@Id
	private String name;
	private String userName;
	private String currency;
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST},mappedBy = "account")
	private Collection<Position> closedPositions;
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST},mappedBy = "account")
	private Collection<Transaction> transactions;
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST},mappedBy = "account")
	private Collection<Statement> statements;

	public Collection<Statement> getStatements() {
		return statements;
	}

	public void setStatements(Collection<Statement> statements) {
		this.statements = statements;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Collection<Position> getClosedPositions() {
		return closedPositions;
	}

	public void setClosedPositions(Collection<Position> closedPositions) {
		this.closedPositions = closedPositions;
	}

	public Collection<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Collection<Transaction> transactions) {
		this.transactions = transactions;
	}

}
