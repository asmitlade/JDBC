package com.practice.jdbc.swingapp;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/*
 SQL> create table student_record(sno number(5) primary key,sname varchar2(10), saddrs varchar2(10), course varchar2(10));
 SQL> create sequence sno_seq start with 10001 increment by 1;
 */
public class StudentManagementGUIApp extends JFrame {
	private static final String INSERT_STUD_DETAILS = "INSERT INTO STUDENT_RECORD VALUES(SNO_SEQ.NEXTVAL,?,?,?)";
	private static final String DELETE_STUD_RECORD = "DELETE FROM STUDENT_RECORD WHERE SNO=?";
	private static final String UPDATE_STUD_DETAILS = "UPDATE STUDENT_RECORD SET SNAME=?, SADDRS=?, COURSE=? WHERE SNO=?";
	private static final String SELECT_STUD_DETAILS = "SELECT SNAME, SADDRS, COURSE FROM STUDENT_RECORD WHERE SNO=?";
	private Connection con;
	private ResultSet rs;
	private PreparedStatement ps;
	private PreparedStatement ps1;
	private PreparedStatement ps2;
	private PreparedStatement ps3;	
	private JPanel contentPane;
	private JTextField tsno;
	private JTextField tsname;
	private JTextField tsaddrs;
	private JTextField tscourse;
	private int no, result;
	private String sname;
	private String addrs;
	private String course;
	private JFrame frame1 = new JFrame();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentManagementGUIApp frame = new StudentManagementGUIApp();
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
	public StudentManagementGUIApp() {
		setTitle("Student Masnagement App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStudentManagementApp = new JLabel("Student Management App");
		lblStudentManagementApp.setForeground(new Color(0, 0, 204));
		lblStudentManagementApp.setFont(new Font("Calibri", Font.BOLD, 22));
		lblStudentManagementApp.setBounds(101, 28, 258, 45);
		contentPane.add(lblStudentManagementApp);
		
		JLabel lsno = new JLabel("Student Number ");
		lsno.setFont(new Font("Calibri", Font.PLAIN, 16));
		lsno.setBounds(79, 106, 117, 22);
		contentPane.add(lsno);
		
		JLabel lsname = new JLabel("Student Name ");
		lsname.setFont(new Font("Calibri", Font.PLAIN, 16));
		lsname.setBounds(79, 149, 117, 22);
		contentPane.add(lsname);
		
		JLabel laddrs = new JLabel("Student Address");
		laddrs.setFont(new Font("Calibri", Font.PLAIN, 16));
		laddrs.setBounds(79, 195, 117, 22);
		contentPane.add(laddrs);
		
		JLabel lcourse = new JLabel("Student Course");
		lcourse.setFont(new Font("Calibri", Font.PLAIN, 16));
		lcourse.setBounds(79, 243, 117, 22);
		contentPane.add(lcourse);
		
		tsname = new JTextField();
		tsname.setColumns(10);
		tsname.setBounds(206, 150, 153, 20);
		contentPane.add(tsname);
		
		tsaddrs = new JTextField();
		tsaddrs.setColumns(10);
		tsaddrs.setBounds(206, 196, 153, 20);
		contentPane.add(tsaddrs);
		
		tscourse = new JTextField();
		tscourse.setColumns(10);
		tscourse.setBounds(206, 244, 153, 20);
		contentPane.add(tscourse);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sname  = tsname.getText();
				addrs = tsaddrs.getText();
				course = tscourse.getText();
				try{
					// set values to Query params
					ps.setString(1, sname);
					ps.setString(2, addrs);
					ps.setString(3, course);
					//execute the SQL Query
					result = ps.executeUpdate();
					if(result == 0) {
						JOptionPane.showMessageDialog(frame1, "Record Insertion Failed","Failed",
									JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(frame1, "Record Insert Successfully","Success",
									JOptionPane.INFORMATION_MESSAGE);						 
					}
				}//try
				catch(SQLException se) {
					if(se.getErrorCode()==12899) {
						JOptionPane.showMessageDialog(frame1, "Too Much Large Value","Failed",
								JOptionPane.ERROR_MESSAGE);
					}
					se.printStackTrace();
				}
				catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnInsert.setBounds(107, 297, 89, 23);
		contentPane.add(btnInsert);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				no = Integer.parseInt(tsno.getText());
				sname  = tsname.getText();
				addrs = tsaddrs.getText();
				course = tscourse.getText();
				try {
					// set value to Query params
					ps2.setInt(4, no);
					ps2.setString(1, sname);
					ps2.setString(2, addrs);
					ps2.setString(3, course);
					// execute the SQL Query
					result = ps2.executeUpdate();
					if(result == 0) {
						JOptionPane.showMessageDialog(frame1, "Record Updation Failed","Failed",
									JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(frame1, "Record Update Successfully","Success",
									JOptionPane.INFORMATION_MESSAGE);						 
					}
				}//try
				catch(SQLException se) {
					if(se.getErrorCode()==12899) {
						JOptionPane.showMessageDialog(frame1, "Too Much Large Value","Failed",
								JOptionPane.ERROR_MESSAGE);
					}
					se.printStackTrace();
				}
				catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnUpdate.setBounds(243, 297, 89, 23);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				no = Integer.parseInt(tsno.getText());
				try {
					// set value to query param
					ps1.setInt(1, no);
					// execute the SQL Query
					result = ps1.executeUpdate();
					if(result == 0) {
						JOptionPane.showMessageDialog(frame1, "Record Deletion Failed","Failed",
									JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(frame1, "Record Deleted Successfully","Success",
									JOptionPane.INFORMATION_MESSAGE);						 
					}
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
				catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(107, 349, 89, 23);
		contentPane.add(btnDelete);
		
		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				no = Integer.parseInt(tsno.getText());
				try {
					// set values to Query params
					ps3.setInt(1, no);
					// execute the SQL Query
					rs = ps3.executeQuery();
					// gather the result
					if(rs.next()) {
						sname = rs.getString(1);
						addrs = rs.getString(2);
						course = rs.getString(3);
						tsname.setText(sname);
						tsaddrs.setText(addrs);
						tscourse.setText(course);
					}
				}//try
				catch(SQLException se) {
					se.printStackTrace();
				}
				catch(Exception e3) {
					e3.printStackTrace();
				}
			}
		});
		btnView.setBounds(243, 349, 89, 23);
		contentPane.add(btnView);
		
		tsno = new JTextField();
		tsno.setColumns(10);
		tsno.setBounds(206, 107, 153, 20);
		contentPane.add(tsno);
		intializeDB();
	}
	// database connection method
	public void intializeDB() {
		try {
			// register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// create PreparedStatement Object
			ps = con.prepareStatement(INSERT_STUD_DETAILS);
			ps1 = con.prepareStatement(DELETE_STUD_RECORD);
			ps2 = con.prepareStatement(UPDATE_STUD_DETAILS);
			ps3 = con.prepareStatement(SELECT_STUD_DETAILS);
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
	}//try
}//class