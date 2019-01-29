package com.practice.jdbc.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertBankAccAutoIncrement {
	private static final String INSERT_AUTO_QUERY = "INSERT INTO BANKACCOUNT VALUES(ACC_NO_AUTO.NEXTVAL,?,?,?)";
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement ps = null;
		String holderName = null, address = null;
		float balance = 0.0f;
		int count = 0;
		Scanner sc = null;
		try {
			sc = new Scanner(System.in);
			System.out.println("Enter Applicant's Name ::");
			holderName = sc.nextLine();
			System.out.println("Enter Address ::");
			address = sc.nextLine();
			System.out.println("Enter Initial Balanace ::");
			balance = sc.nextFloat();
			// register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// create PreparedStatement Object
			if(con!=null)
				ps = con.prepareStatement(INSERT_AUTO_QUERY);
			
			ps.setString(1, holderName);
			ps.setString(2, address);
			ps.setFloat(3, balance);
			if(ps!=null)
				count = ps.executeUpdate();
			// process the result
			if(count == 0)
				System.out.println("Problem in account opening");
			else
				System.out.println("Account open Successfully");
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(ps!=null)
					ps.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}//finally
	}//main
}//class