package com.practice.jdbc.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LoginTest {

	public static void main(String[] args) {
		Scanner sc = null;
		Connection con = null;
		String user = null;
		String pass = null;
		Statement st = null;
		String query = null;
		ResultSet rs = null;
		int count = 0;
		try {
			// get input from user
			sc = new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter Username ::");
				user = sc.nextLine(); // get raja
				System.out.println("Enter Password ::");
				pass = sc.nextLine(); // get rani
			}
			// convert input values as required for the sql query
			user = "'"+user+"'";
			pass = "'"+pass+"'";
			// register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// create statement object
			if(con!=null)
				st = con.createStatement();
			
			// prepare query
			// SELECT COUNT(*) FROM USERS WHERE UNAME='RAJA'AND PASS='RANI';
			query = "SELECT COUNT(*) FROM USERS WHERE UNAME="+user+" AND PASS="+pass;
			//execute the query
			if(st!=null)
				rs = st.executeQuery(query);
			// process the ResultSet
			if(rs!=null) {
				if(rs.next())
				count = rs.getInt(1);
				System.out.println(count);
			}	
			if(count==0)
				System.out.println("Invalid Credential");	
			else
				System.out.println("Valid Credential");
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
				if(st!=null)
					st.close();
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
	}//main
}//class
