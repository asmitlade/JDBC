package workingwithdate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/* SQL> create table person_details(pid number(5) primary key, name varchar2(10),
   													dob date, doj date, dom date);

Table created.

SQL> create sequence pid_auto_increment start with 1001 increment by 1;

Sequence created.*/

public class InsertDOBintoOracleDB {
	private static final String INSERT_DATE_ORACLE = "INSERT INTO PERSON_DETAILS VALUES(PID_AUTO_INCREMENT.NEXTVAL,?,?,?,?)";
	public static void main(String[] args) {
		Scanner sc = null;
		String name = null, dob = null, doj = null, dom = null;
		SimpleDateFormat sdob = null, sdoj = null, sdom = null;
		java.util.Date udob= null, udoj = null, udom = null;
		java.sql.Date sqdob = null, sqdoj = null, sqdom = null;
		long d1 = 0, d2 = 0, d3 = 0;
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			// read input	
			sc = new Scanner(System.in);
			System.out.println("Enter Person Name ::");
			name = sc.nextLine();
			System.out.println("Enter DOB (dd-MM-yyyy)::");
			dob = sc.next();
			System.out.println("Enter DOJ (MM-dd-yyyy)");
			doj = sc.next();
			System.out.println("Enter DOM (yyyy-dd-MM)");
			dom = sc.next();
			// convert String date to SQL format date object
			
			if(dob != null)
				sdob = new SimpleDateFormat("dd-MM-yyyy");
			if(sdob != null)
				udob = sdob.parse(dob);
			if(udob != null)
				d1 = udob.getTime();
				sqdob = new java.sql.Date(d1);
			if(doj != null)
				sdoj = new SimpleDateFormat("MM-dd-yyyy");
			if(sdoj != null)
				udoj = sdoj.parse(doj);
			if(udoj != null)
				d2 = udoj.getTime();
				sqdoj = new java.sql.Date(d2);
			if(dom != null)
				sdom = new SimpleDateFormat("yyyy-dd-MM");
			if(sdom != null)
				udom = sdom.parse(dom);
			if(sdom != null)
				d3 = udom.getTime();
				sqdom = new java.sql.Date(d3);
			// Register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// create PreparedStatement object
			if(con != null)
				ps = con.prepareStatement(INSERT_DATE_ORACLE);
			// set value query param
			ps.setString(1, name);
			ps.setDate(2, sqdob);
			ps.setDate(3, sqdoj);
			ps.setDate(4, sqdom);
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
