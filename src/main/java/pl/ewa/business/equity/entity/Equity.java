package pl.ewa.business.equity.entity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class Equity {
	private Date date;
	private BigDecimal value;

	public Equity() {
	}
	
	public Equity(Date date, BigDecimal value) {
		super();
		this.date = date;
		this.value = value;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getLabel() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		return (day < 10 ? "0" + day : "" + day) + "." + (month < 10 ? "0" + month : "" + month) + "." + year;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
