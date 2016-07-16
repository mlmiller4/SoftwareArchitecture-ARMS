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

public class SignUpFrame {

	private JFrame frame;
	private JTextField textFieldUserId;
	private JTextField textFieldUsername;
	private JTextField textFieldPassword;
	private JTextField textFieldRepeatPassword;
	private int passwordLenght = 6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpFrame window = new SignUpFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;


	/**
	 * Create the application.
	 */
	public SignUpFrame() {
		initialize();
		connection = sqliteConnection.dbConnector();		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 630, 396);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblSignUpFrame = new JLabel("Sign Up Screen");
		lblSignUpFrame.setForeground(Color.RED);
		lblSignUpFrame.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblSignUpFrame.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUpFrame.setBackground(Color.WHITE);
		lblSignUpFrame.setBounds(10, 11, 621, 96);
		frame.getContentPane().add(lblSignUpFrame);

		JLabel lblUserId = new JLabel("Student ID:");
		lblUserId.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblUserId.setBounds(64, 100, 135, 57);
		frame.getContentPane().add(lblUserId);

		textFieldUserId = new JTextField();
		textFieldUserId.setBounds(215, 100, 200, 37);
		frame.getContentPane().add(textFieldUserId);
		textFieldUserId.setColumns(10);
		
		JLabel lblUserName = new JLabel("Username:");
		lblUserName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblUserName.setBounds(64, 150, 135, 57);
		frame.getContentPane().add(lblUserName);

		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(215, 150, 200, 37);
		frame.getContentPane().add(textFieldUsername);
		textFieldUsername.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPassword.setBounds(64, 200, 150, 37);
		frame.getContentPane().add(lblPassword);

		textFieldPassword = new JPasswordField();
		textFieldPassword.setBounds(215, 200, 200, 37);
		frame.getContentPane().add(textFieldPassword);
		
		JLabel lblPasswordRequirement = new JLabel("*Must be 6 characters or more");
		lblPasswordRequirement.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblPasswordRequirement.setForeground(Color.RED);
		lblPasswordRequirement.setBounds(420, 200, 200, 37);
		frame.getContentPane().add(lblPasswordRequirement);

		JLabel lblRepeatPassword = new JLabel("Repeat Password:");
		lblRepeatPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblRepeatPassword.setBounds(64, 250, 150, 37);
		frame.getContentPane().add(lblRepeatPassword);

		textFieldRepeatPassword = new JPasswordField();
		textFieldRepeatPassword.setBounds(215, 250, 200, 37);
		frame.getContentPane().add(textFieldRepeatPassword);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Validate text fields
				if(textFieldUserId.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Pleas enter a student id");
				}
				else if(textFieldUsername.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Pleas enter a username");
				}
				else if(textFieldPassword.getText() == null || textFieldPassword.getText().length() < passwordLenght){
					JOptionPane.showMessageDialog(null, "Password must be more then 6 characters long");
				}
				else if(!textFieldPassword.getText().matches(textFieldRepeatPassword.getText())){
					JOptionPane.showMessageDialog(null, "Passwords do not match");
				}
				// Check if user exists
				try{
					String query = "select * from UserInfo where gtId=? ";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldUserId.getText());


					ResultSet rs = pst.executeQuery();						

					int count = 0;

					while(rs.next()){
						count++;
					}

					if (count == 1){
						// Add username and password to db
						query= "INSERT INTO UserInfo where gtId=? (userName,password) VALUES(?,?)";
						pst = connection.prepareStatement(query);
						pst.setString(1, textFieldUserId.getText());
						pst.setString(2, textFieldUsername.getText());					
						pst.setString(3, textFieldPassword.getText());

						// Open Student Frame
						frame.dispose();
						StudentFrame sf = new StudentFrame();
						sf.setVisible(true);

					} else {
						JOptionPane.showMessageDialog(null, "Sign Up Failed, No Such Student ID");
					}

					rs.close();
					pst.close();					

				} catch (Exception e){
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
		btnSignUp.setBounds(215, 300, 200, 37);
		frame.getContentPane().add(btnSignUp);

	}
}


