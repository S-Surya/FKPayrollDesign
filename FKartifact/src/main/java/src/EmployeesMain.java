package src;

import java.util.*;
import java.io.*;	
import java.time.LocalDate;  

//jsonstuff
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EmployeesMain
{
	
	private static ArrayList<Employee> employees = new ArrayList<>();

	//Function to display menu and get userinput
	public static int menu(Scanner input) {

        int selection;
		Scanner inputm = input;

        /***************************************************/

        System.out.println("\n\nWelcome Menu. Please choose your option : ");
        System.out.println("-------------------------\n");
        System.out.println("1 - Display Short Employee Details");
        System.out.println("2 - Add a new Employee");
        System.out.println("3 - Delete an Employee");
        System.out.println("4 - Post Time card for a suitable Employee");
        System.out.println("5 - Post Union Membership for a suitable Employee");
        System.out.println("6 - Post Union service charge for a union member");;
        System.out.println("7 - Post Sales Receipt");
        System.out.println("0 - Quit");
        //System.out.println("3 - Decrypt a number");
        //System.out.println("4 - Quit");

        selection = inputm.nextInt();
        return selection;    
    }
	
	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		//take input from user
		int userChoice=0;
		Scanner input = new Scanner(System.in);
		while((userChoice=menu(input))!=0 )
		{
			switch(userChoice)
			{
				case 0:
					break;
					
				case 1:
					printRecords();
					break;
					
				case 2: 
					Employee newEmp = Employee.getEmpInstance();
					employees.add(newEmp);
					writeJson();
					readJson();
					//System.out.println("UPdated db");
					//System.out.println("Employee added.");
					break;

				case 3:
					System.out.println("Enter valid empID : ");
					Scanner input1 = new Scanner(System.in);
					int empID = input1.nextInt();
					deleteEmp(empID);
					break;
				case 4:
					System.out.println("Enter valid empID: ");
					Scanner input11 = new Scanner(System.in);
					int empID1 = input11.nextInt();
					Employee emp = searchByID(empID1);
					if(emp==null || emp.payableType!=Employee.PayableType.HOURLY)
						System.out.println("Not a valid EmpID");
					else
					{
						System.out.println("Enter Date in YYYY-MM-DD format: ");
						Scanner scanner = new Scanner(System.in);
						LocalDate date = LocalDate.parse(scanner.nextLine());
						System.out.println("Enter number of hours: ");
						Integer noOfHours = scanner.nextInt();
						emp.hp.postTimeCard(empID1, date, noOfHours);
						System.out.println("\nTime card posted.");
						System.out.println("All Time cards of "+emp.getName()+" are :");
						emp.hp.printTimeCards();
					}		
					break;
				case 5:
					//Assumption : Employees are already part of union as of startDate(jan01-2020)
					System.out.println("Enter valid empID: ");
					Scanner input111 = new Scanner(System.in);
					int empID11 = input111.nextInt();
					Employee emp1;
					if((emp1=searchByID(empID11)).unionID==0)
					{
						//assign union ID
						emp1.unionID=Employee.getNextUnionID();
						System.out.println("Union Membership Successful.");
						System.out.println("UnionID: "+emp1.unionID);
						System.out.println("Enter weekly due rate for "+emp1.getName()+":");
						emp1.unionDueRate=input111.nextInt();
						System.out.println("Due Rate set successfully");
					}
					break;
				case 6:
					System.out.println("Enter valid empID: ");
					Scanner input1111 = new Scanner(System.in);
					int empID6 = input1111.nextInt();
					Employee emp6;
					if((emp6=searchByID(empID6)).unionID!=0)
					{
						System.out.println("Enter service charge puropose:");
						String desc = input1111.next();
						System.out.println("Enter amount: ");
						Integer amt = input1111.nextInt();
						System.out.println("Enter Date in YYYY-MM-DD format: ");
						LocalDate date = LocalDate.parse(input1111.next());
						emp6.postCharge(desc, amt, date);
						System.out.println("\nAll Union Charges for "+emp6.getName()+" are :");
						emp6.printCharges();
					}
					break;
				case 7:
					System.out.println("Enter valid empID: ");
					Scanner input7 = new Scanner(System.in);
					int empID7 = input7.nextInt();
					Employee emp7=searchByID(empID7);
					if(emp7!=null)
					{
						System.out.println("Enter amount: ");
						Integer amt = input7.nextInt();
						System.out.println("Enter Date in YYYY-MM-DD format: ");
						LocalDate date = LocalDate.parse(input7.next());
						emp7.postSalesReciept(amt, date) ;
						System.out.println("\nAll sales made by "+emp7.getName()+" are :");
						emp7.printSales();
					}
					break;
			}
		}
	}
	
	private static Employee searchByID(int empID1) {
		for(Employee e: employees)
			if(e.getEmpID()==empID1)
				return e;
		return null;
	}

	@SuppressWarnings("unchecked")
	public static void writeJson()
	{
		JSONArray employeeList = new JSONArray();
		
		for(Employee emp : employees)
		{
			JSONObject employeeObject = new JSONObject();
			JSONObject employeeDetails = new JSONObject();
			employeeDetails.put("empID", emp.empID);
			employeeDetails.put("empName", emp.empName);
			employeeDetails.put("unionID", emp.unionID);
			employeeDetails.put("commishRate", emp.commishRate);
			employeeDetails.put("paymentMethod", emp.paymentMethod.name());
			employeeDetails.put("payableType", emp.payableType.name());
			
			if(emp.payableType == Employee.PayableType.HOURLY)
			{
				JSONObject hourlyPayObject = new JSONObject();
				hourlyPayObject.put("hourlyRate",emp.hp.hourlyRate);
				employeeDetails.put("hp",hourlyPayObject);
			}
			else if(emp.payableType == Employee.PayableType.MONTHLY)
			{
				JSONObject monthlyPayObject = new JSONObject();
				monthlyPayObject.put("flatSalary",emp.mp.flatSalary);
				employeeDetails.put("mp",monthlyPayObject);
			}
			
	        employeeObject.put("employee", employeeDetails);
	        employeeList.add(employeeObject);
		}
		
		try (FileWriter file = new FileWriter("employees.json")) {			 
            file.write(employeeList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
		
	@SuppressWarnings("unchecked")
    public static void readJson() 
    {
		//updatedEmpList = new ArrayList<>(); 
        //JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader("employees.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray employeeList = (JSONArray) obj;
            //System.out.println(employeeList);
             
            //Iterate over employee array
            employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
	
	private static void printRecords()
	{
		System.out.println("\n\nEmployee Records");
		System.out.println("-------------------------\n");
		for(Employee e: employees)
		{
			System.out.println("Employee ID: "+e.empID);
			System.out.println("Employee Name: "+e.empName);
			if(e.unionID!=0)
				System.out.println("Union ID: "+e.unionID);
			System.out.println("Payable type: "+e.payableType);
			System.out.println("Payment Method: " +e.paymentMethod);
		}
	}
	
	//parse emp object and add to updatedEmpList
	private static void parseEmployeeObject(JSONObject employee) 
	{
		//Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("employee");
         
        System.out.println("\n\n");
        Long empID = (Long) employeeObject.get("empID");    
        System.out.println("Employee ID: "+empID);
        String empName = (String) employeeObject.get("empName");  
        System.out.println("Employee Name: "+empName);
        //Long unionID = (Long) employeeObject.get("unionID");
        //System.out.println("Union ID: "+unionID);
        Double commishRate = (Double) employeeObject.get("commishRate");
        System.out.println("Commision Rate: "+commishRate);
        String paymentMethod = (String) employeeObject.get("paymentMethod");
        System.out.println("Payment Method: " +paymentMethod);
        String payableType = (String) employeeObject.get("payableType");
        System.out.println("Payable type: "+payableType);
        
        if(payableType=="HOURLY")
        {
        	JSONObject hpObj = (JSONObject) employeeObject.get("hp");
        	Long hourlyRate = (Long) hpObj.get("hourlyRate");
        	System.out.println(hourlyRate);
        }
        else if(payableType=="MONTHLY")
        {
        	JSONObject mpObj = (JSONObject) employeeObject.get("mp");
        	Long flatSalary = (Long) mpObj.get("flatSalary");
        	System.out.println(flatSalary);
        }
        
	}

	

	private static void deleteEmp(int empID) {
		//search for empID
		for(Employee e : employees)
		{
			if(e.getEmpID()==empID)
			{
				System.out.println("Deleting emp : " + e.empName);
				employees.remove(e);
				break;
			}
		}
		printRecords();
	}
}