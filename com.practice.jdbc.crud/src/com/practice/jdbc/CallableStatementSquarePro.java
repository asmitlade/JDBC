package com.practice.jdbc;
/*
 * SQL> create or replace procedure p_square(x in number, y out number)
    	is
    	begin
    	y:=x*x;
    	end;
    	/
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

public class CallableStatementSquarePro {
	private static final String CALL_PROCEDURE = "{CALL P_SQUARE(?,?)}";
	public static void main(String[] args) {
		Scanner sc = null; 
		int no = 0, result = 0;
		Connection con = null;
		CallableStatement cs = null;
		try {
			 // read inputs
			 sc = new Scanner(System.in);
			 if(sc != null) {
				System.out.println("Enter number ::");
			 	no = sc.nextInt();
			 }
			 // register JDBC driver
			 Class.forName("oracle.jdbc.driver.OracleDriver");
			 // establish the connection
			 con = DriverManager.getConnection("jdbc:oracle:thin:localhost:1521:orcl","scott", "tiger");
			 // create CallableStatement object
			 if(con != null)
				 cs = con.prepareCall(CALL_PROCEDURE);
			 // register OUT params with JDBC type
			 cs.registerOutParameter(2, Types.INTEGER);
			 //set value to IN param`
			 cs.setInt(1, no);
			 // execute PL/SQL procedure
			 cs.execute();
			 // gather result from OUT param 
			 result = cs.getInt(2);
			 System.out.println("SQUARE is ::"+result); 
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
