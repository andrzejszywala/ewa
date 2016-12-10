package pl.ewa.business.copied.entity;

import java.math.BigDecimal;

public class Copied {
	private String name;
	private BigDecimal profit;
	private BigDecimal spread;
	private BigDecimal rolloverFeesAndDividends;
	
	public Copied() {
	}

	public Copied(String name, BigDecimal profit, BigDecimal spread, BigDecimal rolloverFeesAndDividends) {
		super();
		this.name = name;
		this.profit = profit;
		this.spread = spread;
		this.rolloverFeesAndDividends = rolloverFeesAndDividends;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public BigDecimal getSpread() {
		return spread;
	}

	public void setSpread(BigDecimal spread) {
		this.spread = spread;
	}

	public BigDecimal getRolloverFeesAndDividends() {
		return rolloverFeesAndDividends;
	}

	public void setRolloverFeesAndDividends(BigDecimal rolloverFeesAndDividends) {
		this.rolloverFeesAndDividends = rolloverFeesAndDividends;
	}
	
}
