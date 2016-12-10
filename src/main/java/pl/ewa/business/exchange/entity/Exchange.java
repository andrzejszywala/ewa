package pl.ewa.business.exchange.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(ExchangePK.class)
public class Exchange {

	@Id
	private String currency;
	@Id
	@Temporal(TemporalType.DATE)
	private Date date;
	@Column(precision = 19, scale = 2)
	private BigDecimal amount;
	
	public Exchange() {
	}
	
	public Exchange(String currency, Date date, BigDecimal amount) {
		super();
		this.currency = currency;
		this.date = date;
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
