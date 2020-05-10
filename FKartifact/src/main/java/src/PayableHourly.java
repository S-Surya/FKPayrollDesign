package src;

import java.time.LocalDate;  
public interface PayableHourly
{
	public void postTimeCard(int empID, LocalDate date, int noOfHours);
	public void printTimeCards();
}