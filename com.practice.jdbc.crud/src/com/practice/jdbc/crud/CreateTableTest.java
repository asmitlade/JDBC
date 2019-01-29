package com.practice.jdbc.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableTest {

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		int count = 0;
		try {
			// register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// prepare statement
			if(con!=null)
				st = con.createStatement();
			// send and execute Query
			if(st!=null)
				count = st.executeUpdate("CREATE TABLE STUDENT (SNO NUMBER(10) PRIMARY KEY, SNAME VARCHAR2(10), SADD VARCHAR2(10))");
			//process the result
			if(count == 0)
				System.out.println("Table Created");
			else
				System.out.println("Table Not Created");
		}//try
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(SQLException se) {
			if(se.getErrorCode()==955)
				System.out.println("Table Already Exists");
			else
				se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			// close Statement object
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
