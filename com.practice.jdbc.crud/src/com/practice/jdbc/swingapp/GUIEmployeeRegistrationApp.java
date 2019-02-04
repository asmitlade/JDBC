package com.practice.jdbc.swingapp;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIEmployeeRegistrationApp extends JFrame implements ActionListener, WindowListener {
	private static final String INSERT_EMP_QUERY = "INSERT INTO EMPLOYEE (EMPID, ENAME, DESIGNATION, SALARY) VALUES(EMPID_SEQ.NEXTVAL,?,?,?)";
	private JLabel lname, ldesignation, lsalary, lresult;
	private JTextField tname, tdesignation, tsalary;
	private JButton btn;
	private Connection con;
	private PreparedStatement ps;
	private Container c;
	public GUIEmployeeRegistrationApp() {
		System.out.println("GUIEmployeeRegistrationApp.GUIEmployeeRegistrationApp()");
		//super("Registration App");
		setTitle("Employee Registration App");
		c = getContentPane();
		c.setLayout(new FlowLayout());
		lname = new JLabel("Enter Employee Name ::");
		add(lname);
		
		tname = new JTextField(10);
		add(tname);
		
		ldesignation = new JLabel("Enter Designation ::");
		add(ldesignation);
		
		tdesignation = new JTextField(10);
		add(tdesignation);
		
		lsalary = new JLabel("Enter Salary ::");
		add(lsalary);
		
		tsalary = new JTextField(10);
		add(tsalary);
		
		btn = new JButton("Register");
		btn.addActionListener(this);
		add(btn);
		
		lresult = new JLabel("");
		add(lresult);
		
		setSize(300, 400);
		setBackground(Color.BLUE);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//call initialiseBD method
		initialiseDB();
		
		// call windowClosing method
		this.addWindowListener(this);
	}
	// database connection initialization
	private void initialiseDB() {
		System.out.println("GUIEmployeeRegistrationApp.initialiseDB()");
		try {
			// register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracl:thin:@localhost:1521:orcl", "scott", "tiger");
			//   create PreparedStatement object
			ps = con.prepareStatement(INSERT_EMP_QUERY);
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
	}//initialiseDB
	
	public static void main(String[] args) {
		System.out.println("GUIEmployeeRegistrationApp.main()");
		new GUIEmployeeRegistrationApp();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("GUIEmployeeRegistrationApp.actionPerformed()");
		String ename = null, designation = null;
		int salary = 0, count = 0;
		try {
			ename = tname.getText();
			designation = tdesignation.getText();
			salary = Integer.parseInt(tsalary.getText());
			
			// set value to Query params
			ps.setString(1, ename);
			ps.setString(2, designation);
			ps.setInt(3, salary);
			
			// execute the Query
			count = ps.executeUpdate();
			
			// process the result
			if(count == 0) {
				lresult.setForeground(Color.RED);
				lresult.setText("Registration Failed");
			}
			else {
				lresult.setForeground(Color.GREEN);
				lresult.setText("Registration Succeded");
			}
			
		}
		catch(SQLException se) {
			if(se.getErrorCode()==1) {
				lresult.setForeground(Color.RED);
				lresult.setText("Employee Already Registered");
			}
			else if(se.getErrorCode()==12899) {
				lresult.setForeground(Color.RED);
				lresult.setText("String value is Too Large");
			}
		}
		catch(Exception e1) {
			e1.printStackTrace();
		}
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("GUIEmployeeRegistrationApp.windowClosing()");
		// close PreparedStatement object
		try {
			if(ps != null)
				System.out.println("Close PreparedStatement object");
				ps.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		// close Connection object
		try {
			if(con != null)
				System.out.println("Close Connection object");
				con.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
