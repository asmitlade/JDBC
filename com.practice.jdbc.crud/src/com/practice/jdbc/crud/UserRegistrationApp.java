package com.practice.jdbc.crud;
/*
 * SQL> create table user_register(name varchar2(20), uname varchar2(30), pwd varchar2(20));
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UserRegistrationApp {

	public static void main(String[] args) {
		Scanner sc = null;
		String name = null, uname = null, pwd = null;
		Connection con = null;
		Statement st = null;
		String query = null;
		int result = 0;
		try {
			// read inputs
			sc = new Scanner(System.in);
			System.out.println("Enter Name ::");
			name = sc.next();
			System.out.println("Enter UserName ::");
			uname = sc.next();
			System.out.println("Enter Password ::");
			pwd = sc.next();
			// Register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// create Statement object
			if(con != null)
				st = con.createStatement();
			name = "'"+name+"'";
			uname = "'"+uname+"'";
			pwd = "'"+pwd+"'";
			// Prepare SQL Query
			query = "INSERT INTO USER_REGISTER VALUES("+name+", "+uname+", "+pwd+")";
			// execute the SQL Query
			if(st != null)
				result = st.executeUpdate(query);
			if(result == 0)
				System.out.println("Registration Failed");
			else
				System.out.println("Registration Successfully");
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
				if(st != null)
					st.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			//close Connection object
			try {
				if(con != null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			//close Scanner object
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
