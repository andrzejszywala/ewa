package pl.ewa;

import java.util.Date;

public class Cal {
	private Cal() {		
	}
	
	public static java.util.Calendar endar(Date date) {
		java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        return cal;	
	}
}
