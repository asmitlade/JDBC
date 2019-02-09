package com.practice.jdbc;

/*
 * SQL> create or replace procedure p_emp_details(eno in number, name out varchar2, job out varchar2, sal out number)
2  is
3  begin
4  select ename, job, sal into name, job, sal from emp where empno = eno;
5  end;
6  /
*/

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

public class CallableStatementRetriveEmpDetail {
	private static final String CALL_RETRIEVE_EMP = "{CALL P_EMP_DETAILS(?,?,?,?)}";
	public static void main(String[] args) {
		Scanner sc = null;
		int eno = 0, sal = 0;
		String name = null, job = null;
		Connection con = null;
		CallableStatement cs = null;
		try {
			// read inputs
			sc = new Scanner(System.in);
			if(sc != null) {
				System.out.println("Enter Employee No :::");
				eno = sc.nextInt();
			}
			// register JDBC Driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// create CallableStatement object
			if(con != null)
				cs = con.prepareCall(CALL_RETRIEVE_EMP);
			// register OUT params with JDBC Type
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.INTEGER);
			//set values In params
			cs.setInt(1, eno);
			// execute PL/SQL Procedure
			cs.execute();
			// gather result from OUT params
			name = cs.getString(2);
			job = cs.getString(3);
			sal = cs.getInt(4);
			System.out.println("Employee Name ::"+name);
			System.out.println("Employee Designation ::"+job);
			System.out.println("Employee Salary ::"+sal);
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
