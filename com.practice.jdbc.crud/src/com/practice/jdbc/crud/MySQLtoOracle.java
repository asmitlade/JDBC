 package com.practice.jdbc.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLtoOracle {
	private static final String SELECT_FROM_MYSQL = "SELECT ACCNO, HOLDERNAME, ADDRESS, BALANCE FROM ACC_NO_AUTO"; 
	private static final String INSERT_TO_ORACLE = "INSERT INTO ACC_DETAILS (ACCNO, HOLDERNAME, ADDRESS, BALANCE) VALUES(?,?,?,?)";
	public static void main(String[] args) {
		Connection oracleCon = null;
		Connection mysqlCon = null;
		Statement st =null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int accno = 0, count=0;
		String holdername = null, address = null;
		float balance = 0.0f;
		try {
			//register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			// establish the connection
			oracleCon = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			mysqlCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankaccount", "root", "root");
			if(oracleCon!=null)
				ps = oracleCon.prepareStatement(INSERT_TO_ORACLE);
			// create and prepare Statement object
			if(mysqlCon!=null)
				st = mysqlCon.createStatement();
			//execute SQL query
			if(st!=null)
				rs = st.executeQuery(SELECT_FROM_MYSQL);
			// read and process the ResultSet
			if(rs!=null && ps!=null) {
				while(rs.next()) {
					// get each record from ResultSet
					accno = rs.getInt(1);
					holdername = rs.getString(2);
					address = rs.getString(3);
					balance = rs.getFloat(4);
					// set each record values insert query param values
					ps.setInt(1, accno);
					ps.setString(2, holdername);
					ps.setString(3, address);
					ps.setFloat(4, balance);
					count = ps.executeUpdate();
				}//while
			}
				if(count == 0) {
					System.out.println("Record Not Transfer");
				}
				else {
					System.out.println("Record Transfered");
				}
		}
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
			//close ResultSet object
			try {
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			// close Statement object
			try {
				if(ps!=null)
					ps.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			// close PreparedStatement object
			try {
				if(ps!=null)
					ps.close();
				}
			catch(SQLException se) {
				se.printStackTrace();
			}
			//close oracleCon object
			try {
				if(oracleCon!=null)
					oracleCon.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			// close mysqlCon object
			try {
				if(mysqlCon!=null)
					mysqlCon.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
