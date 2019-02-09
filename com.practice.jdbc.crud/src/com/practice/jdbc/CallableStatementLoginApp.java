package com.practice.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

public class CallableStatementLoginApp {
	private static final String LOGIN_VALIDATION = "{CALL P_AUTH(?,?,?)}";
	public static void main(String[] args) {
		Scanner sc = null;
		String user = null, pwd = null, result= null;
		Connection con = null;
		CallableStatement cs = null;
		try {
			// read inputs
			sc= new Scanner(System.in);
			if(sc != null) {
				System.out.println("Enter Username :::");
				user = sc.next();
				System.out.println("Enter Password :::");
				pwd = sc.next();
			}
			// register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:localhost:1521:orcl", "scott", "tiger");
			// create CallableStatement object
			if( con != null)
				cs = con.prepareCall(LOGIN_VALIDATION);
			// set OUT param SQL type
			cs.registerOutParameter(3, Types.VARCHAR);
			// set value to IN param
			cs.setString(1, user);
			cs.setString(2, pwd);
			// execute the PL/SQL procedure
			cs.execute();
			// process the result
			result = cs.getString(3);
			System.out.println(result);
		}
		catch(SQLException se) {
			if(se.getErrorCode()==1403) {
				System.out.println("No Record Found");
			}
			else {
				se.printStackTrace();
			}
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			// close JDBC objects
			try {
				if(cs != null)
					cs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(con != null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
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