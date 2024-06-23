package com.jspiders.bankApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager 
{
	private Connection con;
	private Scanner sc;
	public AccountManager(Connection con, Scanner sc) 
	{
		this.con = con;
		this.sc = sc;
	}
	public void creditMoney(long account_number) throws SQLException
	{
		sc.nextLine();
		System.out.println("Enter Amount");
		double amount=sc.nextDouble();
		sc.nextLine();
		System.out.println("Enter Pin");
		String pin=sc.nextLine();
		try {
			con.setAutoCommit(false);
			if(account_number!=0)
			{
				String query = "select * from accounts where account_number=? and security_pin=?";
				PreparedStatement ps=con.prepareStatement(query);
				ps.setLong(1, account_number);
				ps.setString(2, pin);
				ResultSet rs=ps.executeQuery();
				if(rs.next())
				{
					String credit="update accounts set balance=balance+? where account_number=?";
					PreparedStatement ps1=con.prepareStatement(credit);
					ps1.setDouble(1, amount);
					ps1.setLong(2, account_number);
					int count =ps1.executeUpdate();
					if(count>0)
					{
						System.out.println("RS. "+amount+"Credited successfully");
						con.commit();
						con.setAutoCommit(true);
						return;
					}
					else {
						System.out.println("Transaction failed");
						con.rollback();
						con.setAutoCommit(true);
					}
				}
				else
				{
					System.out.println("Invalid Pin");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.setAutoCommit(true);
	}
	
	public void debitMoney(long account_number) throws SQLException
	{
		sc.nextLine();
		System.out.println("Enter Amount");
		double amount=sc.nextDouble();
		System.out.println("Enter Pin");
		String pin=sc.next();
		try
		{
			con.setAutoCommit(false);
			if(account_number!=0)
			{
				String query="select * from accounts where account_number=? and security_pin=?";
				PreparedStatement ps=con.prepareStatement(query);
				ps.setLong(1, account_number);
				ps.setString(2, pin);
				ResultSet rs=ps.executeQuery();
				if(rs.next())
				{
					double current_balance=rs.getDouble("balance");
					if(amount<=current_balance)
					{
						String depit="update accounts set balance=balance-? where account_number=?";
						PreparedStatement ps1=con.prepareStatement(depit);
						ps1.setDouble(1, amount);
						ps1.setLong(2, account_number);
						int count=ps1.executeUpdate();
						if(count>0)
						{
							System.out.println("RS. "+amount+"Debited Successfully");
							con.commit();
							con.setAutoCommit(true);
						}
						else
						{
							System.out.println("Transaction failed");
							con.rollback();
							con.setAutoCommit(true);
						}
					}
					else
					{
						System.out.println("Insufficient balance");
					}
				}
				else
				{
					System.out.println("Invalid Pin");
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		con.setAutoCommit(true);
	}
	
	public void transferMoney(long account_number) throws SQLException
	{
		sc.nextLine();
		System.out.println("Enter Acconut Number");
		long acc_number=sc.nextLong();
		System.out.println("Enter Amount ");
		double amount=sc.nextDouble();
		sc.nextLine();
		System.out.println("Enter PIN");
		String pin=sc.nextLine();
		try
		{
			con.setAutoCommit(false);
			if(account_number!=0 && acc_number!=0)
			{
				String query ="select * from accounts where account_number=? and security_pin=?";
				PreparedStatement ps=con.prepareStatement(query);
				ps.setLong(1, acc_number);
				ps.setString(2, pin);
				ResultSet rs=ps.executeQuery();
				if(rs.next())
				{
					double current_balance=rs.getDouble("balance");
					if(amount<=current_balance)
					{
						String depit="update accounts set balance=balance-? where account_number=?";
						String credit="update accounts set balance=balance+? where account_number=?";
						PreparedStatement ps1=con.prepareStatement(credit);
						PreparedStatement ps2=con.prepareStatement(depit);
						ps1.setDouble(1, amount);
						ps1.setLong(2, acc_number);
						ps2.setDouble(1, amount);
						ps2.setLong(2, account_number);
						int count1=ps1.executeUpdate();
						int count2=ps2.executeUpdate();
						if(count1>0 && count2>0)
						{
							System.out.println("Transaction successfully");
							System.out.println("RS. "+amount+"Transferred successfully");
							con.commit();
							con.setAutoCommit(true);
							return;
						}
						else
						{
							System.out.println("Transaction failed");
							con.rollback();
							con.setAutoCommit(true);
						}
					}
					else
					{
						System.out.println("Insufficient balance");
					}
				}
				else
				{
					System.out.println("Invalid Pin");
				}
			}
			else
			{
				System.out.println("Invalid acconunt number");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		con.setAutoCommit(true);
	}
	
	
	public void getBalance(long account_number)
	{
		sc.nextLine();
		System.out.println("Enter Pin ");
		String pin=sc.nextLine();
		try 
		{
			String query="select balance from accounts where account_number=? and security_pin=?";
			PreparedStatement ps=con.prepareStatement(query);
			ps.setLong(1, account_number);
			ps.setString(2, pin);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				double balance=rs.getDouble("balance");
				System.out.println("Account balance : "+balance);
			}
			else
			{
				System.out.println("Invalid Pin");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
}
