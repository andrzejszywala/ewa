package pl.ewa.business.position.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import pl.ewa.business.webtrader.entity.Account;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Position {
	@Id
	private Long id;
	private String action;
	private String copyTraderName;
	@Column(precision = 19, scale = 2)
	private BigDecimal amount;
	@Column(precision = 19, scale = 2)
	private BigDecimal units;
	@Column(precision = 19, scale = 4)
	private BigDecimal openRate;
	@Column(precision = 19, scale = 4)
	private BigDecimal closeRate;
	@Column(precision = 6, scale = 2)
	private BigDecimal spread;
	@Column(precision = 19, scale = 2)
	private BigDecimal profit;
	@Temporal(TemporalType.TIMESTAMP)
	private Date openDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date closeDate;
	@Column(precision = 19, scale = 4)
	private BigDecimal takeProfitRate;
	@Column(precision = 19, scale = 4)
	private BigDecimal stopLossRate;
	@Column(precision = 19, scale = 2)
	private BigDecimal rolloverFeesAndDividends;
	@ManyToOne(fetch = FetchType.LAZY)
	@XmlTransient
	private Account account;

	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCopyTraderName() {
		return copyTraderName;
	}

	public void setCopyTraderName(String copyTraderName) {
		this.copyTraderName = copyTraderName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getUnits() {
		return units;
	}

	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	public BigDecimal getOpenRate() {
		return openRate;
	}

	public void setOpenRate(BigDecimal openRate) {
		this.openRate = openRate;
	}

	public BigDecimal getCloseRate() {
		return closeRate;
	}

	public void setCloseRate(BigDecimal closeRate) {
		this.closeRate = closeRate;
	}

	public BigDecimal getSpread() {
		return spread;
	}

	public void setSpread(BigDecimal spread) {
		this.spread = spread;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public BigDecimal getTakeProfitRate() {
		return takeProfitRate;
	}

	public void setTakeProfitRate(BigDecimal takeProfitRate) {
		this.takeProfitRate = takeProfitRate;
	}

	public BigDecimal getStopLossRate() {
		return stopLossRate;
	}

	public void setStopLossRate(BigDecimal stopLossRate) {
		this.stopLossRate = stopLossRate;
	}

	public BigDecimal getRolloverFeesAndDividends() {
		return rolloverFeesAndDividends;
	}

	public void setRolloverFeesAndDividends(BigDecimal rolloverFeesAndDividends) {
		this.rolloverFeesAndDividends = rolloverFeesAndDividends;
	}

	@Override
	public String toString() {
		return "ClosedPosition [id=" + id + ", action=" + action + ", copyTraderName=" + copyTraderName + ", amount="
				+ amount + ", units=" + units + ", openRate=" + openRate + ", closeRate=" + closeRate + ", spread="
				+ spread + ", profit=" + profit + ", openDate=" + openDate + ", closeDate=" + closeDate
				+ ", takeProfitRate=" + takeProfitRate + ", stopLossRate=" + stopLossRate
				+ ", rolloverFeesAndDividends=" + rolloverFeesAndDividends + "]";
	}

}
