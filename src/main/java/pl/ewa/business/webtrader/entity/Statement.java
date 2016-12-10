package pl.ewa.business.webtrader.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Statement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	private BigDecimal beginningRealizedEquity;
	private BigDecimal deposits;
	private BigDecimal refunds;
	private BigDecimal bonuses;
	private BigDecimal adjustments;
	private BigDecimal tradeProfitOrLoss;
	private BigDecimal rolloverFees;
	private BigDecimal withdrawals;
	private BigDecimal withdrawalFees;
	private BigDecimal endingRealizedEquity;
	@ManyToOne(fetch = FetchType.LAZY)
	private Account account;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getBeginningRealizedEquity() {
		return beginningRealizedEquity;
	}

	public void setBeginningRealizedEquity(BigDecimal beginningRealizedEquity) {
		this.beginningRealizedEquity = beginningRealizedEquity;
	}

	public BigDecimal getDeposits() {
		return deposits;
	}

	public void setDeposits(BigDecimal deposits) {
		this.deposits = deposits;
	}

	public BigDecimal getRefunds() {
		return refunds;
	}

	public void setRefunds(BigDecimal refunds) {
		this.refunds = refunds;
	}

	public BigDecimal getBonuses() {
		return bonuses;
	}

	public void setBonuses(BigDecimal bonuses) {
		this.bonuses = bonuses;
	}

	public BigDecimal getAdjustments() {
		return adjustments;
	}

	public void setAdjustments(BigDecimal adjustments) {
		this.adjustments = adjustments;
	}

	public BigDecimal getTradeProfitOrLoss() {
		return tradeProfitOrLoss;
	}

	public void setTradeProfitOrLoss(BigDecimal tradeProfitOrLoss) {
		this.tradeProfitOrLoss = tradeProfitOrLoss;
	}

	public BigDecimal getRolloverFees() {
		return rolloverFees;
	}

	public void setRolloverFees(BigDecimal rolloverFees) {
		this.rolloverFees = rolloverFees;
	}

	public BigDecimal getWithdrawals() {
		return withdrawals;
	}

	public void setWithdrawals(BigDecimal withdrawals) {
		this.withdrawals = withdrawals;
	}

	public BigDecimal getWithdrawalFees() {
		return withdrawalFees;
	}

	public void setWithdrawalFees(BigDecimal withdrawalFees) {
		this.withdrawalFees = withdrawalFees;
	}

	public BigDecimal getEndingRealizedEquity() {
		return endingRealizedEquity;
	}

	public void setEndingRealizedEquity(BigDecimal endingRealizedEquity) {
		this.endingRealizedEquity = endingRealizedEquity;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
