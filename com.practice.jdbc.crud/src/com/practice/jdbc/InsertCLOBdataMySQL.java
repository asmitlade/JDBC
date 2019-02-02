package com.practice.jdbc;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertCLOBdataMySQL {

	private static final String INSERT_CLOB_DATA = "INSERT INTO IT_JOBPORTAL(NAME, QUALIFICATION, SPECIALIZATION, RESUME) VALUES(?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc = null;
		String name = null, qualification = null, specialization = null, resumeLocation = null;
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		long length = 0;
		Reader reader = null;
		File file = null;
		try {
			// read inputs
			sc = new Scanner(System.in);
			if(sc != null) {
				System.out.println("Enter Applicant Name ::");
				name = sc.nextLine();
				System.out.println("Enter Applicant Qualification ::");
				qualification = sc.nextLine();
				System.out.println("Enter Specialization ::");
				specialization = sc.nextLine();
				System.out.println("Enter Resume Location ::");
				resumeLocation = sc.nextLine();
			}
			// file related logic
			file = new File(resumeLocation);
			reader = new FileReader(file);
			length = file.length();
			// Register JDBC driver 
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Establish the Connection
			con = DriverManager.getConnection("jdbc:mysql:///JOBPORTAL", "root", "root");
			// Create PreparedStatement object
			if(con != null)
				ps = con.prepareStatement(INSERT_CLOB_DATA);
			// set values to SQL Query parameter
			if(ps != null)
				ps.setString(1, name);
				ps.setString(2, qualification);
				ps.setString(3, specialization);
				ps.setCharacterStream(4, reader, length);
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
			try {
				if(reader != null)
					reader.close();
			}
			catch(Exception e) {
				e.printStackTrace();
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
}
