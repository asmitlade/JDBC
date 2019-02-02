package com.practice.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class InsertDateIntoMySQL {
	private static final String INSERT_DATE_MYSQL = "INSERT INTO PERSON_DETAILS(NAME,DOB,DOJ,DOM) VALUES(?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc = null;
		String pname = null, dob = null, doj = null, dom = null;
		SimpleDateFormat sdf = null;
		Connection con = null;
		PreparedStatement ps = null;
		java.util.Date udob = null, udoj = null, udom = null;
		java.sql.Date sqdob = null, sqdoj = null, sqdom = null;
		long m = 0;
		int result = 0;
		try {
			//read input
			sc = new Scanner(System.in);
			System.out.println("Enter Person Name ::");
			pname = sc.nextLine();
			System.out.println("Enter DOB(MM-yyyy-dd)");
			dob = sc.nextLine();
			System.out.println("Enter DOJ(dd-yyyy-MM)");
			doj = sc.nextLine();
			System.out.println("Enter DOM(yy-dd-MM)");
			dom = sc.nextLine();
			// Register JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Establish the connection
			con = DriverManager.getConnection("jdbc:mysql:///bankaccount", "root", "root");
			// create PreaparedStatement object
			if(con != null)
				ps = con.prepareStatement(INSERT_DATE_MYSQL);
			// convert String date object to java.util.Date object
			if(ps !=null)
				sdf = new SimpleDateFormat("MM-yyyy-dd");
			if(sdf != null)
				udob = sdf.parse(dob);
			if(udob != null)
				m = udob.getTime();
				sqdob = new java.sql.Date(m);
			sdf = new SimpleDateFormat("dd-yyyy-MM");
			if(sdf != null)
				udoj = sdf.parse(doj);
			if(udoj != null)
				m = udoj.getTime();
				sqdoj = new java.sql.Date(m);
			sdf = new SimpleDateFormat("yy-dd-MM");
			if(sdf != null)
				udom = sdf.parse(dom);
			if(udom != null)
				m = udom.getTime();
				sqdom = new java.sql.Date(m);
			// set value Query param
			ps.setString(1, pname);
			ps.setDate(2, sqdob);
			ps.setDate(3, sqdoj);
			ps.setDate(4, sqdom);
			if(ps != null)
				result = ps.executeUpdate();
			if(result == 0)
				System.out.println("Record Not Inserted");
			else
				System.out.println("Record Successfully Inserted");
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
			//close PreparedStatement object
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
