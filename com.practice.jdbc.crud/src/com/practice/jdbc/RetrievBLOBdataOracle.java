package com.practice.jdbc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RetrievBLOBdataOracle {
	private static final String Retrive_BLOB_Oracle = "SELECT ID, NAME, AGE, GENDER, PHOTO FROM BHARATMATRIMONY WHERE ID = ?";
	public static void main(String[] args) {
		Scanner sc = null;
		int id = 0, no = 0, age = 0;
		String name=null,gender=null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] buffer = null;
		int byteReader = 0;
		try {
			// read inputs
			sc = new Scanner(System.in);
			System.out.println("Enter Person Id ::");
			id = sc.nextInt();
			// Register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// create PreparedStatement object
			if(con != null)
				ps = con.prepareStatement(Retrive_BLOB_Oracle);
			// set value to SQL Query param
			ps.setInt(1, id);
			// execute SQL Query
			if(ps != null)
				rs = ps.executeQuery();
			// process the ResultSet
			if(rs != null) {
				while(rs.next()) {
					no = rs.getInt(1);
					name = rs.getString(2);
					age = rs.getInt(3);
					gender = rs.getString(4);
					System.out.println(no+" "+name+" "+age+" "+" "+gender);
					is = rs.getBinaryStream(5);
					// fetch photo from database to destination
					os = new FileOutputStream("pict.png");
					buffer = new byte[4096];
					while((byteReader = is.read(buffer))!=-1) {
						os.write(buffer, 0, byteReader);
					}
				}
				System.out.println("Photo Retrieved Succussefully On Destination Location");
			}//if
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(IOException xe) {
			xe.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(is != null)
					is.close();
			}
			catch(IOException xe) {
				xe.printStackTrace();
			}
			try {
				if(os != null)
					os.close();
			}
			catch(IOException xe) {
				xe.printStackTrace();
			}
			try {
				if(rs != null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(ps != null)
					ps.close();
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
