package com.jspiders.bankApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class User 
{
	private Connection con;
	private Scanner sc;
	
	public User(Connection con, Scanner sc) 
	{
		this.con = con;
		this.sc = sc;
	}
	//method for user registration 
	public void register()
	{
		sc.nextLine();
		System.out.println("Enter Full name");
		String full_name=sc.nextLine();
		System.out.println("Enter Email");
		String email=sc.nextLine();
		System.out.println("Enter Password");
		String password=sc.nextLine();
		if(userExist(email))
		{
			System.out.println("User already exist");
			return;
		}
		String query="insert into user values(?,?,?)";
		try
		{
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1, full_name);
			ps.setString(2, email);
			ps.setString(3, password);
			int count=ps.executeUpdate();
			if(count>0)
			{
				System.out.println("Registration Done");
			}
			else
			{
				System.out.println("Registration failed");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//method for login
	 public String login()
	  {
		  sc.nextLine();
		  System.out.println("enter email:");
		  String email=sc.nextLine();
		  System.out.println("enter password:");
		  String password=sc.nextLine();
		  String query="SELECT * FROM USER WHERE EMAIL=? AND PASSWORD=?";
		  try
		  {
			  PreparedStatement psmt = con.prepareStatement(query);
			  psmt.setString(1,email);
			  psmt.setString(2,password);
			  ResultSet rs=psmt.executeQuery();
			  if(rs.next())
			  {
				  return email;
			  }
			  else
			  {
				  return null;
			  }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		  return null;
	  }
	  // method checking weather user is exist or not
	  public boolean userExist(String email)
	  {
		  String query="SELECT * FROM USER WHERE EMAIL=?";
		  try
		  {
			  PreparedStatement psmt = con.prepareStatement(query);
			  psmt.setString(1, email);
			  ResultSet rs=psmt.executeQuery();
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
