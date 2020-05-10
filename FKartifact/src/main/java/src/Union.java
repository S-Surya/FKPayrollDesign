package src;

public interface Union
{
	//returns union id
	public int postMembership(int empID);
	public void postCharge(int empID, String desc, int amt); //first check if emp is a union member
}