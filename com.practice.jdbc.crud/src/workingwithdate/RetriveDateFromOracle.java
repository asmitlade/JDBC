package workingwithdate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class RetriveDateFromOracle {
	private static final String RETRIVE_DATE_ORACLE = "SELECT PID, NAME, DOB,DOJ,DOM FROM PERSON_DETAILS WHERE PID = ?";
	public static void main(String[] args) {
		Scanner sc = null;
		int pid = 0;
		Connection con =null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		SimpleDateFormat sdf = null;
		String name = null, dob = null, doj = null, dom = null;
		java.util.Date udob = null, udoj = null, udom = null;
		java.sql.Date sqdob = null, sqdoj = null, sqdom = null;
		try {
			// read input
			sc = new Scanner(System.in);
			System.out.println("Enter Person id ::");
			pid = sc.nextInt();
			//register JDBC driver 
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// create PreaparedStatement object
			if(con != null)
				ps = con.prepareStatement(RETRIVE_DATE_ORACLE);
			// set value in SQL Query param
			if(ps != null)
				ps.setInt(1, pid);
			// execute the SQL Query
			if(ps != null)
				rs = ps.executeQuery();
			if(rs != null) {
				if(rs.next()) {
					// process the result
					pid = rs.getInt(1);
					name = rs.getString(2);
					sqdob = rs.getDate(3);
					sqdoj = rs.getDate(4);
					sqdom = rs.getDate(5);
					//convert java.sql.Date object to java.util.Date object
					udob = sqdob;
					udoj = sqdoj;
					udom = sqdom;
					// convert java.util.Date object to String date object
					sdf = new SimpleDateFormat("dd-MM-yyyy");
					dob = sdf.format(udob);
					sdf = new SimpleDateFormat("yy-MMM-dd");
					doj = sdf.format(udoj);
					sdf = new SimpleDateFormat("MMM-yy-dd");
					dom = sdf.format(udom);
					System.out.println(name+" "+dob+" "+doj+" "+dom);
				}
				else
					System.out.println("Person Not Found");
			}
		} //try
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
			//close ResultSet object
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