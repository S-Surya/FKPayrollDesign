package src;

import java.util.ArrayList;
import java.time.LocalDate;  

public class Employee
{
	final static LocalDate startDate = LocalDate.of(2020,1,1);
	static int nextID=1;

	static int getNextEmpID()
	{  return nextID++;  }

	//store sales if made by emp
	public class Sale
	{
		int empID;
		int amt;
		LocalDate date;
	}

	int empID, unionID=0;
	float commishRate;
	String empName;

	protected enum PaymentMethod {MAIL,PAYMASTER,BANK;}
	PaymentMethod paymentMethod;


	

	ArrayList<Sale> sales = new ArrayList<>();	
	
}