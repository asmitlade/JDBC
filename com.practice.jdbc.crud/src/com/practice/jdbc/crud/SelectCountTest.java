package com.practice.jdbc.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectCountTest {
	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		String query = null;
		ResultSet rs = null;
		try {
			// register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// prepare statement
			if(con!=null)
				st = con.createStatement();
			// prepare SQL query
			query = "SELECT COUNT(*) FROM EMP";
			// read and execute the query
			if(st!=null)
				rs = st.executeQuery(query);
			//process the ResultSet object
			if(rs!=null)
				rs.next();
					System.out.println("Employee Count ::"+ rs.getInt(1));
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close ResultSet object
			try {
				if(rs!=null)
					rs.close();
				
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			//close Statement object
			try {
				if(st!=null)
					st.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			//close Connection object
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
