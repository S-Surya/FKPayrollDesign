package src;

import java.util.*;
import java.io.*;
import java.time.LocalDate;  

public class MonthlyEmp extends Employee implements PayableFlat,Union
{
	float flatSalary;
	//factory method which returns new instace of Monthly Emp
	static MonthlyEmp getEmpInstance()
	{
		//empID
		MonthlyEmp newEmp = new MonthlyEmp();
		newEmp.empID = Employee.getNextEmpID();

		Scanner in = new Scanner(System.in);  
		//empName
        System.out.println("Enter name of Employee: ");  
        newEmp.empName = in.nextLine(); 

        //hourlyRate 
        System.out.println("Enter flat salary in Rupees: ");
        newEmp.flatSalary = in.nextFloat();

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

        System.out.println("\n\nNew Employee created: \n" + newEmp);

        return newEmp;
	}

	public int postMembership(int empID)
	{
		return 0;
	}

	public void postCharge(int empID, String desc, int amt)
	{}

	@Override
	public int setSalary(int sal) {
		// TODO Auto-generated method stub
		return 0;
	} 
	
	@Override
    public String toString() { 
        return String.format("Name: " + empName + "\nEmpID: "+empID); 
    }
}