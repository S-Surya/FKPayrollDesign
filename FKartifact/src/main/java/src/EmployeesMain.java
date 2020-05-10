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
	//remve all tis laterz
	private static ArrayList<HourlyEmp> hourlyEmployees;
	private static ArrayList<MonthlyEmp> monthlyEmployees; 

	//Function to display menu and get userinput
	public static int menu() {

        int selection;
        @SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

        /***************************************************/

        System.out.println("\n\nWelcome Menu. Please choose your option : ");
        System.out.println("-------------------------\n");
        System.out.println("1 - Display Short Employee Details");
        System.out.println("2 - Add a new Employee");
        System.out.println("0 - Quit");
        //System.out.println("3 - Decrypt a number");
        //System.out.println("4 - Quit");

        selection = input.nextInt();
        return selection;    
    }
	
	@SuppressWarnings("unchecked")
	public static void writeJson()
	{
		JSONArray employeeList = new JSONArray();
		for(HourlyEmp he : hourlyEmployees)
		{
			//add deets to obj
			JSONObject employeeDetails = new JSONObject();
			employeeDetails.put("empID", ""+he.empID);
	        employeeDetails.put("empName", he.empName);
	        employeeDetails.put("hourlyRate", he.hourlyRate);
	        
	        //add obj to list
	        JSONObject employeeObject = new JSONObject(); 
	        employeeObject.put("employee", employeeDetails);
	        employeeList.add(employeeObject);
		}
		for(MonthlyEmp me : monthlyEmployees)
		{
			//add deets to obj
			JSONObject employeeDetails = new JSONObject();
			employeeDetails.put("empID", ""+me.empID);
	        employeeDetails.put("empName", me.empName);
	        employeeDetails.put("flatSalary", me.flatSalary);
	        
	        //add obj to list
	        JSONObject employeeObject = new JSONObject(); 
	        employeeObject.put("employee", employeeDetails);
	        employeeList.add(employeeObject);
		}
		try (FileWriter file = new FileWriter("Employees.json")) {
			 
            file.write(employeeList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("unchecked")
    public static void readJson() 
    {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader("Employees.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray employeeList = (JSONArray) obj;
            //System.out.println(employeeList);
             
            //Iterate over employee array
            employeeList.forEach( emp -> System.out.println(emp) );
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args)
	{
		hourlyEmployees = new ArrayList<>();
		monthlyEmployees = new ArrayList<>();
		
		//new code to work with persistent data
		//HourlyEmp he; 

		//take input from user
		int userChoice=0;
		while( (userChoice=menu())!=0 )
		{
			switch(userChoice)
			{
				case 0:
					break;
				case 2: 
					Scanner input = new Scanner(System.in);
					System.out.println("Input 0 if this emp is paid by the hour. Else 1 for flat salary : ");
					int empChoice = input.nextInt();
					if(empChoice==0)
					{
						hourlyEmployees.add(HourlyEmp.getEmpInstance());
						writeJson();
						System.out.println("Done.");
					}
					else if(empChoice==1)
					{
						monthlyEmployees.add(MonthlyEmp.getEmpInstance());
						writeJson();
						System.out.println("Done.");
					}

					System.out.println("Employee added.");
					break;

				case 1:
					readJson();
					break;
			}
		}
	}
}