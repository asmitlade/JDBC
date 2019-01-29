package com.practice.jdbc.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectMaxSalTest {

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		String query = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			//prepare the Statement
			if(con!=null)
				st = con.createStatement();
			// prepare SQL Query
			query = "SELECT EMPNO,ENAME,JOB,HIREDATE,SAL FROM EMP WHERE SAL=(SELECT MAX(SAL) FROM EMP)";
			// read and execute SQL query
			if(st!=null)
				rs = st.executeQuery(query);
			//process the result
			if(rs!=null)
				while(rs.next()) {
					flag = true;
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getDate(4)+" "+rs.getInt(5));					
				}//while
			if(flag)
				System.out.println("Record Found And Display");
			else
				System.out.println("Record Not Found");
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
