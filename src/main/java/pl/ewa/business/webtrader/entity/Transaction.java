package pl.ewa.business.webtrader.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(TransactionPK.class)
public class Transaction implements Serializable {

	private static final long serialVersionUID = 7551341633529585697L;

	@Id
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Id
	private String type;
	@Id
	private Long position;

	private BigDecimal accountBalance;
	private String details;
	private BigDecimal amount;
	private BigDecimal realizedEquityChange;
	private BigDecimal realizedEquity;
	private BigDecimal nwa;
	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRealizedEquityChange() {
		return realizedEquityChange;
	}

	public void setRealizedEquityChange(BigDecimal realizedEquityChange) {
		this.realizedEquityChange = realizedEquityChange;
	}

	public BigDecimal getRealizedEquity() {
		return realizedEquity;
	}

	public void setRealizedEquity(BigDecimal realizedEquity) {
		this.realizedEquity = realizedEquity;
	}

	public BigDecimal getNwa() {
		return nwa;
	}

	public void setNwa(BigDecimal nwa) {
		this.nwa = nwa;
	}

	@Override
	public String toString() {
		return "Transaction [date=" + date + ", accountBalance=" + accountBalance + ", type=" + type + ", details="
				+ details + ", position=" + position + ", amount=" + amount + ", realizedEquityChange="
				+ realizedEquityChange + ", realizedEquity=" + realizedEquity + ", nwa=" + nwa + "]";
	}

}
