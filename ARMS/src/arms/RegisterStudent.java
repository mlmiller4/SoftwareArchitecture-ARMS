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

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import arms.api.Student;

public class RegisterStudent extends JFrame {

	private JPanel contentPane;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField earnedHours;
	private JTextField gpa;
	private JTextField password;
	private JTextField userId;

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
		setBounds(100, 100, 845, 591);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("Register Student");
		label.setBounds(5, 5, 833, 24);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		contentPane.add(label);

		JLabel lblEarnedHours = new JLabel("Earned Hours:");
		lblEarnedHours.setBounds(114, 166, 93, 24);
		contentPane.add(lblEarnedHours);

		JLabel lblGpa = new JLabel("GPA:");
		lblGpa.setBounds(114, 206, 93, 24);
		contentPane.add(lblGpa);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(114, 130, 93, 24);
		contentPane.add(lblLastName);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(114, 97, 93, 24);
		contentPane.add(lblFirstName);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(114, 242, 93, 24);
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
		gpa.setBounds(260, 209, 114, 19);
		contentPane.add(gpa);
		gpa.setColumns(10);

		password = new JTextField();
		password.setToolTipText("Enter password for student's account");
		password.setBounds(260, 245, 114, 19);
		contentPane.add(password);
		password.setColumns(10);

		userId = new JTextField();
		userId.setBounds(260, 64, 114, 19);
		contentPane.add(userId);
		userId.setColumns(10);

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
				arms.api.Student student = new arms.api.Student(userIdInt,
						firstName.getText(), lastName.getText(),
						earnedHoursFloat, gpaFloat, password.getText());

				// Call Database Action to insert student into Students table
			}
		});
		btnRegister.setBounds(114, 293, 117, 25);
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
		btnBack.setBounds(257, 293, 117, 25);
		contentPane.add(btnBack);

	}
}
