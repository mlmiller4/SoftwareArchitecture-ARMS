package arms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;

import java.sql.*;
import javax.swing.*;

public class ARMS_LogIn {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ARMS_LogIn window = new ARMS_LogIn();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	private JTextField textFieldUserName;
	private JPasswordField passwordField;

	/**
	 * Create the application.
	 */
	public ARMS_LogIn() {
		connection = sqliteConnection.dbConnector();
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 630, 396);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnLoginStudentButton = new JButton("Log In As Student");
		btnLoginStudentButton.setFont(new Font("Times New Roman", Font.PLAIN,
				18));
		btnLoginStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Check user name and password for student log in
				try {
					String query = "select * from Students where UserName=? and Password=? ";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldUserName.getText());
					pst.setString(2, passwordField.getText());
					// pst.setString(3, "False");

					ResultSet rs = pst.executeQuery();

					int count = 0;

					while (rs.next()) {
						count++;
					}

					if (count == 1) {
						// JOptionPane.showMessageDialog(null,
						// "Log In Successfull.");

						// Open Student Frame
						frame.dispose();
						StudentFrame sf = new StudentFrame(false);
						sf.setVisible(true);

					} else {
						JOptionPane.showMessageDialog(null, "Log In Failed.");
					}

					rs.close();
					pst.close();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnLoginStudentButton.setBounds(79, 257, 230, 49);
		frame.getContentPane().add(btnLoginStudentButton);

		JButton btnLoginAdminButton = new JButton("Log In As Administrator");
		btnLoginAdminButton
				.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnLoginAdminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					String query = "select * from Administrators where UserName=? and Password=? ";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldUserName.getText());
					pst.setString(2, passwordField.getText());

					ResultSet rs = pst.executeQuery();

					int count = 0;

					while (rs.next()) {
						count++;
					}

					if (count == 1) {
						// JOptionPane.showMessageDialog(null,
						// "Log In Successfull.");

						// Open Admin Frame
						frame.dispose();
						AdminFrame af = new AdminFrame();
						af.setVisible(true);

					} else {
						JOptionPane.showMessageDialog(null, "Log In Failed.");
					}

					rs.close();
					pst.close();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnLoginAdminButton.setBounds(331, 257, 230, 49);
		frame.getContentPane().add(btnLoginAdminButton);

		JLabel lblAcademicResourceManagement = new JLabel(
				"Academic Resource Management System");
		lblAcademicResourceManagement.setForeground(Color.RED);
		lblAcademicResourceManagement.setFont(new Font("Times New Roman",
				Font.BOLD, 25));
		lblAcademicResourceManagement
				.setHorizontalAlignment(SwingConstants.CENTER);
		lblAcademicResourceManagement.setBackground(Color.WHITE);
		lblAcademicResourceManagement.setBounds(10, 11, 621, 96);
		frame.getContentPane().add(lblAcademicResourceManagement);

		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setBounds(79, 98, 135, 57);
		frame.getContentPane().add(lblUserName);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPassword.setBounds(99, 178, 115, 37);
		frame.getContentPane().add(lblPassword);

		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(224, 111, 200, 37);
		frame.getContentPane().add(textFieldUserName);
		textFieldUserName.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(224, 178, 200, 37);
		frame.getContentPane().add(passwordField);
	}
}
