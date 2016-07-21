package arms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import arms.api.Student;
import arms.dataAccess.DbActions;

public class RegisterStudent extends JFrame {

	private JPanel contentPane;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField earnedHours;
	private JTextField gpa;
	private JTextField password;
	private JTextField userId;
	private JTextField userName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterStudent frame = new RegisterStudent();
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
	public RegisterStudent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("Register Student");
		label.setBounds(5, 5, 480, 24);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		contentPane.add(label);

		JLabel lblEarnedHours = new JLabel("Earned Hours:");
		lblEarnedHours.setBounds(114, 166, 93, 24);
		contentPane.add(lblEarnedHours);

		JLabel lblGpa = new JLabel("GPA:");
		lblGpa.setBounds(114, 195, 93, 24);
		contentPane.add(lblGpa);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(114, 130, 93, 24);
		contentPane.add(lblLastName);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(114, 97, 93, 24);
		contentPane.add(lblFirstName);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(114, 257, 93, 24);
		contentPane.add(lblPassword);

		firstName = new JTextField();
		firstName.setToolTipText("Enter student's first name");
		firstName.setBounds(260, 100, 114, 19);
		contentPane.add(firstName);
		firstName.setColumns(10);

		lastName = new JTextField();
		lastName.setToolTipText("Enter student's last name");
		lastName.setBounds(260, 133, 114, 19);
		contentPane.add(lastName);
		lastName.setColumns(10);

		earnedHours = new JTextField();
		earnedHours.setToolTipText("Enter student's earned hours");
		earnedHours.setBounds(260, 166, 114, 19);
		contentPane.add(earnedHours);
		earnedHours.setColumns(10);

		gpa = new JTextField();
		gpa.setToolTipText("Enter student's GPA as a decimal number");
		gpa.setBounds(260, 197, 114, 19);
		contentPane.add(gpa);
		gpa.setColumns(10);

		password = new JTextField();
		password.setToolTipText("Enter password for student's account");
		password.setBounds(260, 260, 114, 19);
		contentPane.add(password);
		password.setColumns(10);

		userId = new JTextField();
		userId.setBounds(260, 64, 114, 19);
		contentPane.add(userId);
		userId.setColumns(10);
		
		userName = new JTextField();
		userName.setToolTipText("Enter username for student's account");
		userName.setColumns(10);
		userName.setBounds(260, 232, 114, 19);
		contentPane.add(userName);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(114, 229, 93, 24);
		contentPane.add(lblUsername);

		JLabel lblUserId = new JLabel("User Id:");
		lblUserId.setBounds(114, 61, 93, 24);
		contentPane.add(lblUserId);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Convert string to float
				float earnedHoursFloat = Float.parseFloat(earnedHours.getText());
				float gpaFloat = Float.parseFloat(gpa.getText());
				int userIdInt = Integer.parseInt(userId.getText());

				// Create Student object first
				Student student = new arms.api.Student(userIdInt,
						firstName.getText(), lastName.getText(),
						earnedHoursFloat, gpaFloat, password.getText(), userName.getText());

				if (DbActions.addStudent(student))
				{
					JOptionPane.showMessageDialog(null, "Student successfully registered");
					// Clear all text fields
					userId.setText("");
					firstName.setText("");
					lastName.setText("");
					earnedHours.setText("");
					gpa.setText("");
					password.setText("");
					userName.setText("");
				}
				else {
					JOptionPane.showMessageDialog(null, "Error registering student. Please try again");
				}
			}
		});
		btnRegister.setBounds(114, 320, 117, 25);
		contentPane.add(btnRegister);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				contentPane.setVisible(false);
				dispose();
				AdminFrame af = new AdminFrame();
				af.setVisible(true);
			}
		});
		btnBack.setBounds(257, 320, 117, 25);
		contentPane.add(btnBack);
		

	}
}
