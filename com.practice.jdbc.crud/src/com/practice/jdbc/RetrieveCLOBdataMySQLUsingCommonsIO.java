package com.practice.jdbc;

import java.io.FileWriter;

import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

public class RetrieveCLOBdataMySQLUsingCommonsIO {

	private static final String RETRIEVE_CLOB_DATA = "SELECT JSID, NAME, QUALIFICATION, SPECIALIZATION, RESUME FROM IT_JOBPORTAL WHERE JSID = ?";
	public static void main(String[] args) {
		Scanner sc = null;
		String name = null, qualification = null, specialization = null;
		Connection con = null;
		PreparedStatement ps = null;
		int jsid = 0;
		Reader reader = null;
		Writer writer = null;
		ResultSet rs = null;
		try {
			// read inputs
			sc = new Scanner(System.in);
			if(sc != null) {
				System.out.println("Enter Job Seeker id ::");
				jsid = sc.nextInt();
			}
			// Register JDBC driver 
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Establish the Connection
			con = DriverManager.getConnection("jdbc:mysql:///JOBPORTAL", "root", "root");
			// Create PreparedStatement object
			if(con != null)
				ps = con.prepareStatement(RETRIEVE_CLOB_DATA);
			// set values to SQL Query parameter
			if(ps != null)
				ps.setInt(1, jsid);
			if(ps != null)
				rs = ps.executeQuery();
			if(rs != null) {
				while(rs.next()) {
					jsid = rs.getInt(1);
					name = rs.getString(2);
					qualification = rs.getString(3);
					specialization = rs.getString(4);
					System.out.println(jsid+" "+name+" "+qualification+" "+specialization);
					reader = rs.getCharacterStream(5);
					writer = new FileWriter("resume.txt");
					IOUtils.copy(reader, writer);
				}//while
			}//if
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
}//class
