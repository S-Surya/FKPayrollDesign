package src;

import java.time.LocalDate;

public interface Union
{
	public void postMembership(int empID);
	public void postCharge(String desc, int amt, LocalDate date); //first check if emp is a union member
}