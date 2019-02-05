package com.practice.jdbc.swingapp;

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
/*
 * SQL> create table emp_users(id number(5)primary key, uname varchar2(20), pwd varchar2(20));
 * SQL> create sequence emp_id_seq start with 100 increment by 1;
 */
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class LoginApp extends JFrame {
 private static final String LOGIN_QUERY = "SELECT ID FROM EMP_USERS WHERE UNAME = ? AND PWD = ?";
	private JPanel contentPane;
	private JTextField tuname;
	private JPasswordField tpass;
	private Connection con;
	private PreparedStatement ps;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginApp frame = new LoginApp();
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
	public LoginApp() {
		setResizable(false);
		setFont(new Font("Dialog", Font.BOLD, 14));
		setTitle("Login Application");
		setBackground(Color.CYAN);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginApp.class.getResource("/com/practice/jdbc/swingapp/favicon.ico")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLoginPanel = new JLabel("Login Panel");
		lblLoginPanel.setForeground(Color.BLUE);
		lblLoginPanel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblLoginPanel.setBounds(154, 27, 124, 42);
		contentPane.add(lblLoginPanel);
		
		JLabel luname = new JLabel("Username");
		luname.setFont(new Font("Tahoma", Font.BOLD, 14));
		luname.setBounds(53, 96, 102, 14);
		contentPane.add(luname);
		
		JLabel lpass = new JLabel("Password");
		lpass.setFont(new Font("Tahoma", Font.BOLD, 14));
		lpass.setBounds(53, 143, 102, 14);
		contentPane.add(lpass);
		
		tuname = new JTextField(10);
		tuname.setForeground(new Color(0, 0, 255));
		tuname.setBackground(UIManager.getColor("TextField.light"));
		tuname.setBounds(170, 95, 193, 20);
		contentPane.add(tuname);
		tuname.setColumns(10);
		
		tpass = new JPasswordField(10);
		tpass.setForeground(new Color(0, 0, 255));
		tpass.setBackground(UIManager.getColor("TextField.light"));
		tpass.setBounds(170, 142, 193, 20);
		contentPane.add(tpass);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBackground(UIManager.getColor("Button.darkShadow"));
		btnLogin.setForeground(UIManager.getColor("Button.light"));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uname = null, pass = null;
				int count = 0;
				try {
					uname = tuname.getText();
					pass = new String(tpass.getPassword());
					JFrame frame1 = new JFrame();
					// set values to Query parameters
					ps.setString(1, uname);
					ps.setString(2, pass);
					
					// execute the Query
					count = ps.executeUpdate();
					if(count == 0) {
						 //JOptionPane.showMessageDialog(frame, "Registration Failed");
						 JOptionPane.showMessageDialog(frame1, "Login Failed","Failed",
								 										JOptionPane.ERROR_MESSAGE);
					 }
					 else {
						 JOptionPane.showMessageDialog(frame1, "Login Succeded","Success",
								 										JOptionPane.INFORMATION_MESSAGE);						 
					 }
				}
				catch(SQLException se) {
					se.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(170, 204, 89, 23);
		contentPane.add(btnLogin);
		try {
			// register JDBC driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott", "tiger");
			// create PreparedStatement object
			ps = con.prepareStatement(LOGIN_QUERY);
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
	}
}