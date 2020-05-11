package src;

import java.util.ArrayList;
import java.util.Scanner;

import java.time.LocalDate;  

public class Employee implements Union
{
	final static LocalDate startDate = LocalDate.of(2020,1,1);
	static Integer nextID=1, nextUnionID=1;
	public Integer unionDueRate=0;
	public static Integer getNextEmpID() {  return nextID++;  }
	public static Integer getNextUnionID() { return nextUnionID++; }


	Integer empID=0, unionID=0;
	float commishRate;
	String empName;
	
	public int getEmpID()
	{
		return empID;
	}
	
	public String getName() 
	{
		return empName;
	}
	
	protected enum PaymentMethod {MAIL,PAYMASTER,BANK}
	PaymentMethod paymentMethod;
	
	protected enum PayableType {HOURLY,MONTHLY}
	PayableType payableType;

	class HourlyPay implements PayableHourly
	{

		Integer hourlyRate=0;
		public class TimeCard
		{
			LocalDate date;
			Integer noOfHours;
		}
		ArrayList<TimeCard> timeCards = new ArrayList<>();
		
		@Override
		public void postTimeCard(int empID, LocalDate date, int noOfHours) 
		{
			TimeCard timeCard = new TimeCard();
			timeCard.date = date;
			timeCard.noOfHours = noOfHours;
			timeCards.add(timeCard);
		}

		@Override
		public void printTimeCards() {
			for(TimeCard tc: timeCards)
				System.out.println(tc.date+ ": " +tc.noOfHours+"hrs");
		}
		
		void calculatePayroll()
		{
			LocalDate curr = LocalDate.parse("2020-04-03");
			while(curr.isBefore(LocalDate.now()))
			{
				System.out.println("Dues on "+curr);
				LocalDate prev = curr.minusDays(7);
				int sal=0,salesTot=0,unionDueTot=0;
				//calculate and print salary payments
				for(HourlyPay.TimeCard tc : hp.timeCards)
				{
					if(tc.date.isBefore(curr) && tc.date.isAfter(prev))
					{
						if(tc.noOfHours>8)
						{
							sal+=8*hp.hourlyRate;
							sal+=(tc.noOfHours-8)*hp.hourlyRate*1.5;
						}
						
						else
						{
							sal+=(tc.noOfHours)*hp.hourlyRate;
						}
					}
				}
				System.out.println("Salary due : "+sal);
				
				//add sales
				for(Sale s: sales)
				{
					if(s.date.isBefore(curr) && s.date.isAfter(prev))
					{
						salesTot+=(s.amt)*commishRate;
					}
				}
				System.out.println("Sales due : "+salesTot);
				//subtract union rate and service charges
				
				if(unionID!=0)
				{
					unionDueTot+=unionDueRate;
					for(UnionCharge uc : unionCharges)
					{
						if(uc.date.isBefore(curr) && uc.date.isAfter(prev))
							unionDueTot+=uc.amount;
					}
				}
				System.out.println("Union due : "+unionDueTot);
				int net=sal+salesTot-unionDueTot;
				System.out.println("Final Payment : "+net);
				//print final payment
				curr=curr.plusDays(7);
			}
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
		
		void calculatePayroll()
		{
			LocalDate curr=LocalDate.parse("2020-03-31");
			
			while(curr.isBefore(LocalDate.now()))
			{
				System.out.println("Dues on "+curr);
				LocalDate prev = curr.withDayOfMonth(1);
				int sal=mp.flatSalary,salesTot=0,unionDueTot=0;
				
				System.out.println("Salary Due : " + sal);
				
				//calculate sales
				for(Sale s: sales)
				{
					if(s.date.isBefore(curr) && s.date.isAfter(prev))
					{
						salesTot+=(s.amt)*commishRate;
					}
				}
				System.out.println("Sales due : "+salesTot);
				
				//calculate union dues
				if(unionID!=0)
				{
					unionDueTot+=unionDueRate*4;
					for(UnionCharge uc : unionCharges)
					{
						if(uc.date.isBefore(curr) && uc.date.isAfter(prev))
							unionDueTot+=uc.amount;
					}
				}
				System.out.println("Union due : "+unionDueTot);
				int net=sal+salesTot-unionDueTot;
				System.out.println("Final Payment : "+net);
				curr = curr.plusDays(1);
				curr = curr.withDayOfMonth(curr.lengthOfMonth());
			}
		}
	}
	
	class UnionCharge
	{
		String service;
		LocalDate date;
		Integer amount;
	}
	ArrayList<UnionCharge> unionCharges = new ArrayList<>();
	
	@Override
	public void postCharge(String desc, int amt, LocalDate date) {
		UnionCharge uc = new UnionCharge();
		uc.service = desc;
		uc.date = date;
		uc.amount = amt;
		unionCharges.add(uc);
	}
	
	public void printCharges()
	{
		for(UnionCharge uc : unionCharges)
			System.out.println(uc.date+ " " +uc.service+" "+uc.amount);
	}
	//store sales if made by emp
	public class Sale 
	{
		Integer amt;
		LocalDate date;
	}
	ArrayList<Sale> sales = new ArrayList<>();
	
	public void postSalesReciept(int amt, LocalDate date) 
	{
		Sale sale = new Sale();
		sale.amt = amt;
		sale.date = date;
		sales.add(sale);
	}
	
	public void printSales()
	{
		for(Sale s : sales)
			System.out.println(s.date+ " " +s.amt);
	}
	HourlyPay hp = new HourlyPay();
	MonthlyPay mp = new MonthlyPay();
	
	//assume start date is 1st March 2020
	
	//assume start date is 1st April 2020
	
		
	static Employee getEmpInstance()
	{
		//empID
		Employee newEmp = new Employee();
		newEmp.empID = Employee.getNextEmpID();

		@SuppressWarnings("resource")
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
	            newEmp.hp.hourlyRate = in.nextInt();
	    		break;
	    	case 2:
	    		newEmp.payableType = PayableType.MONTHLY;
	    		System.out.println("Enter flat salary in Rupees: ");
	            newEmp.mp.flatSalary = in.nextInt();
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
		
	
	
	
	@Override
	public void postMembership(int empID) {
		
	}

	
	

		
	
}