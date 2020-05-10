package src;

import java.util.ArrayList;
import java.util.Scanner;

import src.Employee.PaymentMethod;
import src.HourlyEmp.TimeCard;

import java.time.LocalDate;  

public class Employee implements Union
{
	final static LocalDate startDate = LocalDate.of(2020,1,1);
	static int nextID=1;
	static int getNextEmpID()
	{  return nextID++;  }


	Integer empID=0, unionID=0;
	float commishRate;
	String empName;
	
	int getEmpID()
	{
		return empID;
	}
	protected enum PaymentMethod {MAIL,PAYMASTER,BANK;}
	PaymentMethod paymentMethod;
	
	protected enum PayableType {HOURLY,MONTHLY}
	PayableType payableType;

	class HourlyPay implements PayableHourly
	{

		Integer hourlyRate=0;
		public class TimeCard
		{
			
		}
		ArrayList<TimeCard> timeCards = new ArrayList<>();
		
		@Override
		public void postTimeCard(int empID, LocalDate date, int noOfHours) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class MonthlyPay implements PayableFlat
	{
		
		Integer flatSalary=0; 
		@Override
		public int setSalary(int sal) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	HourlyPay hp = new HourlyPay();
	MonthlyPay mp = new MonthlyPay();
	
	static Employee getEmpInstance()
	{
		//empID
		Employee newEmp = new Employee();
		newEmp.empID = Employee.getNextEmpID();

		Scanner in = new Scanner(System.in);  
		//empName
        System.out.println("Enter name of Employee: ");  
        newEmp.empName = in.nextLine(); 

        //PayableType 
        int payable=1;
        System.out.println("Enter Payable type of Employee: ");
        System.out.println("1 - Payable Hourly");
        System.out.println("2 - Payable Monthly");
        payable = in.nextInt();
        switch(payable)
        {
	        case 1:
	    		newEmp.payableType = PayableType.HOURLY;
	    		System.out.println("Enter hourly Rate in Rupees: ");
	            newEmp.hp.hourlyRate = (Integer) in.nextInt();
	    		break;
	    	case 2:
	    		newEmp.payableType = PayableType.MONTHLY;
	    		System.out.println("Enter flat salary in Rupees: ");
	            newEmp.mp.flatSalary = (Integer)in.nextInt();
	    		break;
        }
        

        //paymentMethod
        int userChoice=1;
        System.out.println("Enter Payment method: ");
        System.out.println("1 - Paychecks mailed to the postal address");
        System.out.println("2 - Paychecks held for pickup by the paymaster");
        System.out.println("3 - Paychecks directly deposited into bank account");
        userChoice = in.nextInt();
        switch(userChoice)
        {
        	case 1:
        		newEmp.paymentMethod = PaymentMethod.MAIL;
        		break;
        	case 2:
        		newEmp.paymentMethod = PaymentMethod.PAYMASTER;
        		break;
        	case 3:
        		newEmp.paymentMethod = PaymentMethod.BANK;
        		break;
        }

        //commishRate
        System.out.println("Enter commission rate in case this Employee makes a sale: ");
        newEmp.commishRate = in.nextFloat();

        System.out.println("\n\nNew Employee created: \n");

        return newEmp;
	}
		
	//store sales if made by emp
	public class Sale 
	{
		int empID;
		int amt;
		LocalDate date;
	}
	ArrayList<Sale> sales = new ArrayList<>();
	
	@Override
	public int postMembership(int empID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void postCharge(int empID, String desc, int amt) {
		// TODO Auto-generated method stub
		
	}	
	
}