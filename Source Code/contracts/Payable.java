import java.time.LocalDate;  

public interface Payable
{
	public Employee addNewEmployee(); //factory method. returns an instance of a new Hourly/Monthly Emp 
	public Employee deleteEmployee(int empID); //pretty straightfwd
	public Employee.Sale postSalesReceipt(int empID, int amt, LocalDate date); 
	public void calculatePayroll();
}