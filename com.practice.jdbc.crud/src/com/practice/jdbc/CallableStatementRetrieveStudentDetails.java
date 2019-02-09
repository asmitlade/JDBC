package com.practice.jdbc;
/*
 * create or replace procedure p_student_details(no in number, name out varchar2, addrs out varchar2)
	is	
	begin
	select sname, saddrs into name, addrs from students where sno=no;
	end;
	/
 */
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

public class CallableStatementRetrieveStudentDetails {
	private static final String STUDENT_RETRIEVE_DETAILS = "{CALL P_STUDENT_DETAILS(?,?,?)}";
	public static void main(String[] args) {
		Scanner sc = null;
		String name = null, addrs = null;
		int no = 0;
		Connection con = null;
		CallableStatement cs = null;
		try {
			// read inputs
			sc = new Scanner(System.in);
			if(sc != null) {
				System.out.println("Enter Student Number :::");
				no = sc.nextInt();
			}
			// register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// create CallableStatement object
			if(con != null)
				cs = con.prepareCall(STUDENT_RETRIEVE_DETAILS);
			// register OUT param 
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			// set value to IN param
			cs.setInt(1, no);
			// execute the PL/SQL procedure
			cs.execute();
			// process the result
			name = cs.getString(2);
			addrs = cs.getString(3);
			System.out.println("Student Name :::"+name);
			System.out.println("Student Address :::"+addrs);
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