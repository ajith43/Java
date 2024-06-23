package com.jspiders.bankApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class BankApp
{
	private static final String dburl="jdbc:mysql://localhost:3306/bank";
	private static final String user="root";
	private static final String password="root";
	public static void main(String[] args)
	{
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		try 
		{
			Connection connection=DriverManager.getConnection(dburl, user, password);
			Scanner scanner=new Scanner(System.in);
			User user=new User(connection,scanner);
			Accounts accounts= new Accounts(connection, scanner);
			AccountManager accountManager=new AccountManager(connection, scanner);
			String email;
			long account_number;
			while(true)
			{
				System.out.println("* WELCOME TO BANK SYSTEM *");
				System.out.println();
				System.out.println("1.Register\n2.Login\n3.Exit");
				System.out.println("Enter Your Choice");
				int choice=scanner.nextInt();
				switch (choice) {
				case 1:
					user.register();
					
					break;
				case 2:
					email=user.login();
					if(email!=null)
					{
						System.out.println("\nUser is Logged In !!!");
						if(!accounts.accountExist(email))
						{
							System.out.println("1.Open a New Bank Account\n2.Exit");
							int choice3=scanner.nextInt();
							if(choice3==1)
							{
								account_number=accounts.openAccount(email);
								System.out.println("Account Creatd SuccessFully");
								System.out.println("Account Number"+account_number);
								
							}
							else
							{
								break;
							}
							
						}
						account_number=accounts.getAccountNumber(email);
						int choice2=0;
						while(choice2!=5)
						{
							System.out.println("\n1.Debit Money\n2.Credit Money\n3.Transfer Money\n4.Check Balance\n5.Logout");
							System.out.println("Enter Your Choice");
							choice2=scanner.nextInt();
							switch (choice2)
							{
							case 1:
								accountManager.debitMoney(account_number);;
								
								break;
							case 2:
								accountManager.creditMoney(account_number);
								break;
							case 3:
								accountManager.transferMoney(account_number);
								break;
							case 4:
								accountManager.getBalance(account_number);
								break;
							case 5:
								
								break;
								
							default:
								System.out.println("Enter Valid Choice");
								break;
							}
						}
					
					}
			
					else
					{
						System.out.println("Incorrect Email Or Password");
					}
					break;
				case 3:
					System.out.println("THANK YOU FOR USING BANKING SYSTEM");
					System.out.println("Existing the System");
					return;

				default:
					System.out.println("Invalid Valid Choice");
					break;
				}
			}
		}
		catch (Exception e) 
		{
			
			e.printStackTrace();
			
		}
	}
}
