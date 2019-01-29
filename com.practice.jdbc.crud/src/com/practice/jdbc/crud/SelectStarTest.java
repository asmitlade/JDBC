package com.practice.jdbc.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class SelectStarTest {
	public static void main(String[] args) {
		Scanner sc = null;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String query = null;
		int id = 0;
		try {
			// read inputs
			sc = new Scanner(System.in);
			if(sc!=null)
				System.out.println("Enter Employee id ::");
				id = sc.nextInt();
			// register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// create statement object
			if(con!=null)
				st = con.createStatement();
			// prepare sql query
			query = "SELECT * FROM EMP WHERE EMPNO =" + id;
			// select and execute sql query
			if(st!=null)
				rs = st.executeQuery(query);
			// process the result
			if(rs!=null)
				if(rs.next())
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getDate(5)+
							" "+rs.getInt(6)+" "+rs.getInt(7)+" "+rs.getInt(8));
				else
					System.out.println("Record Not Found");
		}//try
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
			//close jdbc obj
			try {
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			// close st obj
			try {
				if(st!=null)
					st.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			//close con obj
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			// close sc obj
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

