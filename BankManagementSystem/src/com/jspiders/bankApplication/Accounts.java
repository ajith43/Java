package com.jspiders.bankApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Accounts
{
	private Connection con;
	private Scanner sc;
	
	public Accounts(Connection con, Scanner sc) 
	{
		this.con = con;
		this.sc = sc;
	}
	//method for creating account
	public long openAccount(String email)
	{
		if(!accountExist(email))
		{
			String query="insert into accounts values(?,?,?,?,?)";
			sc.nextLine();
			System.out.println("Enter Full name");
			String full_name=sc.nextLine();
			System.out.println("Enter Initial Balance");
			double balance=sc.nextDouble();
			sc.nextLine();
			System.out.println("Enter Pin");
			String pin=sc.nextLine();
			try
			{
				long account_number = generateAccountNumber();
				PreparedStatement ps= con.prepareStatement(query);
				ps.setLong(1, account_number);
				ps.setString(2, full_name);
				ps.setString(3, email);
				ps.setDouble(4, balance);
				ps.setString(5, pin);
				int count=ps.executeUpdate();
				if(count>0)
				{
					return account_number;
				}
				else
				{
					throw new RuntimeException("Account Creation failed");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			throw new RuntimeException("Account Already exist");
		}
		return 0;
	}
	//method for getting account number
	public long getAccountNumber(String email)
	{
		String query="select account_number from accounts where email=?";
		try
		{
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				return rs.getLong("account_number"); 
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		throw new RuntimeException("Account Number Doesn't exist");
	}
	//method for generating account number
	public long generateAccountNumber()
	{
		try
		{
			String query="select account_number from accounts order by account_number desc limit 1";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(query);
			if(rs.next())
			{
				long last_acc_number=rs.getLong("account_number");
				return last_acc_number;
			}
			else
			{
				return 10000100;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 10000100;
	}
	//method for checking account is exist or not
	public boolean accountExist(String email)
	{
		String query="select account_number from accounts where email=?";
		try 
		{
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
