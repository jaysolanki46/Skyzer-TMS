package Import;

import java.time.LocalDate;

public class DateRange {

	
	public String getDateRangeStr() {
		
		String dateRangeStr = "";
		LocalDate currentMonthLocalDate = LocalDate.now();
		int currentDay = currentMonthLocalDate.getDayOfMonth();
		int currentMonth = currentMonthLocalDate.getMonthValue();
		int currentYear = currentMonthLocalDate.getYear();
		String day = "";
		String month = "";
		String currentDateStr = "";
		
		if(String.valueOf(currentDay).length() < 2) {
			day = "0" + currentDay;
		} else {
			day = String.valueOf(currentDay);
		}
		
		if(String.valueOf(currentMonth).length() < 2) {
			month = "0" + currentMonth;
		} else {
			month = String.valueOf(currentMonth);
		}
		
		currentDateStr = day + month + currentYear;
		
		LocalDate lastMonthLocalDate = currentMonthLocalDate.minusMonths(1);
		int lastDay = lastMonthLocalDate.getDayOfMonth();
		int lastMonth = lastMonthLocalDate.getMonthValue();
		int lastYear = lastMonthLocalDate.getYear();
		String lastDateStr = "";
		
		if(String.valueOf(lastDay).length() < 2) {
			day = "0" + lastDay;
		} else {
			day = String.valueOf(lastDay);
		}
		
		if(String.valueOf(lastMonth).length() < 2) {
			month = "0" + lastMonth;
		} else {
			month = String.valueOf(lastMonth);
		}
		
		lastDateStr = day + month + lastYear;
		
		dateRangeStr = lastDateStr + " to " + currentDateStr;
		
		return dateRangeStr;
	}
}
