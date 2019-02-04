package com.practice.jdbc.swingapp;

/*SQL> create table employee(empid number(5) primary key, ename varchar2(10), designation varchar2(15), salary number(10));*/
/*SQL> create sequence empid_seq start with 1000 increment by 1;*/

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIEmployeeRegistrationAppwithOracle extends JFrame {
	private static final String INSERT_EMP_QUERY = "INSERT INTO EMPLOYEE VALUES(EMPID_SEQ.NEXTVAL,?,?,?)";
	private JPanel contentPane;
	private JTextField tname;
	private JTextField tdesignation;
	private JTextField tsalary;
	private Connection con;
	private PreparedStatement ps;
	private String ename,designation;
	private int salary, count = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIEmployeeRegistrationAppwithOracle frame = new GUIEmployeeRegistrationAppwithOracle();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIEmployeeRegistrationAppwithOracle() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("/favicon.ico"));
		setTitle("Employee Registration Application");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 341);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try {
			// register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// create PreparedStatement object
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
		JLabel lblWelcomeToEmployee = new JLabel("Welcome To Employee Registration");
		lblWelcomeToEmployee.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWelcomeToEmployee.setBounds(68, 31, 324, 38);
		contentPane.add(lblWelcomeToEmployee);
		
		JLabel lname = new JLabel("Employee Name");
		lname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lname.setBounds(42, 109, 134, 17);
		contentPane.add(lname);
		
		JLabel ldesignation = new JLabel("Employee Designation");
		ldesignation.setFont(new Font("Tahoma", Font.BOLD, 14));
		ldesignation.setBounds(42, 147, 165, 17);
		contentPane.add(ldesignation);
		
		JLabel lsalary = new JLabel("Employee Salary");
		lsalary.setFont(new Font("Tahoma", Font.BOLD, 14));
		lsalary.setBounds(42, 198, 165, 17);
		contentPane.add(lsalary);
		
		tname = new JTextField(10);
		tname.setBounds(223, 109, 169, 20);
		contentPane.add(tname);
		tname.setColumns(10);
		
		tdesignation = new JTextField(15);
		tdesignation.setColumns(10);
		tdesignation.setBounds(223, 147, 169, 20);
		contentPane.add(tdesignation);
		
		tsalary = new JTextField(10);
		tsalary.setColumns(10);
		tsalary.setBounds(223, 198, 169, 20);
		contentPane.add(tsalary);
		
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
					 ename = tname.getText();
					 designation = tdesignation.getText();
					 salary = Integer.parseInt(tsalary.getText());
					 JFrame frame1 = new JFrame();
					 // set value to Query parameter
					 ps.setString(1, ename);
					 ps.setString(2, designation);
					 ps.setInt(3, salary);
					 
					 // execute the Query
					 count = ps.executeUpdate();
					 if(count == 0) {
						 //JOptionPane.showMessageDialog(frame, "Registration Failed");
						 JOptionPane.showMessageDialog(frame1, "Registration Failed","Alert",
								 										JOptionPane.ERROR_MESSAGE);
					 }
					 else {
						 JOptionPane.showMessageDialog(frame1, "Registration Succeded","Success",
								 										JOptionPane.INFORMATION_MESSAGE);						 
					 }
				 }
				 catch(SQLException se) {
					 if(se.getErrorCode()==12899) {
						 JFrame frame2 = new JFrame();
						 JOptionPane.showMessageDialog(frame2,"String Value Too Large", "Alert", 
								 										JOptionPane.WARNING_MESSAGE);
							  //  System.exit(0);
					 }
				 }
				 catch(Exception e1) {
					 e1.printStackTrace();
				 }
			}
		});
		btnRegister.setBounds(166, 258, 126, 23);
		contentPane.add(btnRegister);
		
	}
}