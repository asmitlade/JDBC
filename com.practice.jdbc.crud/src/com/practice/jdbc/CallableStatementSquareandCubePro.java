package com.practice.jdbc;
/*
 * SQL> create or replace procedure p_square_cabe(x in number, y out number, z out number)
    	is
    	begin
    	y:=x*x;
    	z:=x*x*x;
    	end;
    	/
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

public class CallableStatementSquareandCubePro {
	private static final String CALL_PROCEDURE = "{CALL P_SQUARE_CUBE(?,?,?)}";
	public static void main(String[] args) {
		Scanner sc = null; 
		int no = 0, square = 0, cube = 0;
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
			 cs.registerOutParameter(3, Types.INTEGER);
			 //set value to IN param`
			 cs.setInt(1, no);
			 // execute PL/SQL procedure
			 cs.execute();
			 // gather result from OUT param 
			 square = cs.getInt(2);
			 cube = cs.getInt(3);
			 System.out.println("SQUARE is ::"+square); 
			 System.out.println("CUBE is ::"+cube); 
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
