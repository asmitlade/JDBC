package com.practice.jdbc.crud;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PsLoginApp {
	private static final String FETCH_USER_DATA = "SELECT COUNT(*) FROM USERS WHERE UNAME=? AND PASS=?";
	public static void main(String[] args) {
		Scanner sc = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String uname = null;
		String pass = null;
		int count = 0;
		try {
			//getting inputs from user
			sc = new Scanner(System.in);
			System.out.println("Enter username ::");
			uname = sc.nextLine();
			System.out.println("Enter password ::");
			pass = sc.nextLine();
			// register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// prepared the statement and execute the query
			if(con!=null)
				ps = con.prepareStatement(FETCH_USER_DATA);
			ps.setString(1,uname);
			ps.setString(2,pass);
			// process the result
			if(ps!=null)
				rs = ps.executeQuery();
			if(rs!=null) {
				while(rs.next()) {                    
					count = rs.getInt(1);
					System.out.println("Record found ::"+count);
				}
			}
			if(count == 0)
				System.out.println("Invalid Credential");
			else
				System.out.println("Valid Credential");
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close rs object
			try {
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			// close st object
			try {
				if(ps!=null)
					ps.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			//close con object
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			//close sc object
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}//finally
	}
}
