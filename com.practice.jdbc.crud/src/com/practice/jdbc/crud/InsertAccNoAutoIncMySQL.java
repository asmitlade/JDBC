package com.practice.jdbc.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertAccNoAutoIncMySQL {
	private static final String INSERT_ACCNO_AUTO = "INSERT INTO ACC_NO_AUTO (HOLDERNAME, ADDRESS, BALANCE) VALUES(?,?,?)";
	public static void main(String[] args) {
		Scanner sc = null;
		String name = null, addrs = null;
		float bal = 0.0f;
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0; 
		try {
			// read input
			sc = new Scanner(System.in);
			System.out.println("Enter Applicant's Name ::");
			name = sc.nextLine();
			System.out.println("Enter Address ::");
			addrs = sc.nextLine();
			System.out.println("Enter Initial Amount ::");
			bal = sc.nextFloat();
			// Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:mysql:///bankaccount", "root", "root");
			// create PreparedStatement object
			if(con != null)
				ps = con.prepareStatement(INSERT_ACCNO_AUTO);
			// execute the SQL Query
			ps.setString(1, name);
			ps.setString(2, addrs);
			ps.setFloat(3, bal);
			if(ps != null)
				result = ps.executeUpdate();
			if(result == 0)
				System.out.println("Problem in Account Opening");
			else
				System.out.println("Account Open Successfully");
		}//try
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
			// close PreparedStatement object
			try {
				if(ps != null)
					ps.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			// close Connection object
			try {
				if(con != null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			// close Scanner object
			try {
				if(sc != null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}//finally
	}//main
}//class
