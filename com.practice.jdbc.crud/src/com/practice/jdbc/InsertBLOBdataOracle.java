package com.practice.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertBLOBdataOracle {
	private static final String INSERT_BLOB_DATA = "INSERT INTO BHARATMATRIMONY VALUES(ID_SEQ.NEXTVAL,?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc = null;
		String name = null, gender = null, photoLocation = null;
		FileInputStream fis = null;
		File file = null;
		int age = 0;
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		long length = 0;
		try {
			// read input
			sc = new Scanner(System.in);
			System.out.println("Enter Applicant Name ::");
			name = sc.nextLine();
			System.out.println("Enter Applicant age ::");
			age = sc.nextInt();
			System.out.println("Enter Applicant Gender ::");
			gender = sc.next();
			System.out.println("Enter Photo Location ::");
			sc.nextLine();
			photoLocation = sc.nextLine();
			// file related logic
			file = new File(photoLocation);
			length = file.length();
			fis = new FileInputStream(file);
			
			// Register JDBC driver 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Establish the Connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// Create PreparedStatement object
			if(con != null)
				ps = con.prepareStatement(INSERT_BLOB_DATA);
			// set values to SQL Query parameter
			if(ps != null)
				ps.setString(1, name);
				ps.setInt(2, age);
				ps.setString(3, gender);
				ps.setBinaryStream(4, fis, length);
			if(ps != null)
				result = ps.executeUpdate();
			if(result == 0)
				System.out.println("Record Not Inserted");
			else
				System.out.println("Record Inserted");
		}
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
			try {
				if(ps != null)
					ps.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			//close Connection object
			try {
				if(con != null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			//close Scanner object
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
