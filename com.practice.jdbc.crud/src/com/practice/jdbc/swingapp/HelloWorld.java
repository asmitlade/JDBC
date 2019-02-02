package com.practice.jdbc.swingapp;


import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class HelloWorld extends JFrame {

	public void m1() {
		JLabel l1 = null;
		l1 = new JLabel("Hello World..");
		add(l1);
		JFrame j = new JFrame("Welcome");
		j.setVisible(true);
		j.setDefaultCloseOperation(EXIT_ON_CLOSE);
		j.setSize(400, 300);
	}
	public static void main(String[] args) {
		HelloWorld  h1 = new HelloWorld();
		h1.m1();
	}

}
