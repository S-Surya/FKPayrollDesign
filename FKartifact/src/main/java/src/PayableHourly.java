package src;

import java.time.LocalDate;  
public interface PayableHourly
{
	public HourlyEmp.TimeCard postTimeCard(int empID, LocalDate date, int noOfHours);
}