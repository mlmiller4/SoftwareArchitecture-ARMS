package arms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import javax.swing.*;
import javax.swing.border.LineBorder;

import arms.dataAccess.DbActions;

public class StudentFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldCourse1;
	private JTextField textFieldCourse2;
	private JTextField textFieldCourse3;
	private JTextField textFieldCourse4;
	private JTextField textFieldCourse5;
	private boolean shadowMode = false;
	private JTextField textFieldUserName;
	private JTextField textFieldStdID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentFrame frame = new StudentFrame(false, "");
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
	public StudentFrame(boolean shadow, String userName) {
		shadowMode = shadow;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 721, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStudentFrame = new JLabel("Student Menu");
		lblStudentFrame.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentFrame.setForeground(Color.RED);
		lblStudentFrame.setFont(new Font("Times New Roman", Font.BOLD, 26));
		lblStudentFrame.setBounds(244, 3, 209, 46);
		contentPane.add(lblStudentFrame);
		
		// Display Student's User Name and Student ID
		// --------------------------------------------------------------------------------
		textFieldUserName = new JTextField();
		textFieldUserName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldUserName.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldUserName.setBackground(new Color(220, 220, 220));
		textFieldUserName.setEditable(false);
		textFieldUserName.setBounds(348, 43, 86, 20);
		contentPane.add(textFieldUserName);
		textFieldUserName.setColumns(10);
		
		JLabel lblCurrentUser = new JLabel("Current User:");
		lblCurrentUser.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCurrentUser.setBounds(274, 46, 76, 14);
		contentPane.add(lblCurrentUser);
		
		textFieldStdID = new JTextField();
		textFieldStdID.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldStdID.setEditable(false);
		textFieldStdID.setBounds(348, 74, 86, 20);
		contentPane.add(textFieldStdID);
		textFieldStdID.setColumns(10);
		
		JLabel lblStudentId = new JLabel("Student ID:");
		lblStudentId.setBounds(274, 77, 76, 14);
		contentPane.add(lblStudentId);
		
		if (shadowMode){
			textFieldStdID.setText(userName);			
			String usr = DbActions.getUserName(userName);
			textFieldUserName.setText(usr);		
		} else {
			textFieldUserName.setText(userName);
			String stdID = DbActions.getStudentID(userName);
			textFieldStdID.setText(stdID);
		}	
		// ---------------------------------------------------------------------------------------------------
		
		
		
		
		
		JButton btnLogOut = new JButton("");
		if (shadowMode)
		{
			btnLogOut.setText("Back");
		} else {
			btnLogOut.setText("Log Out");
		}
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (shadowMode)
				{
					contentPane.setVisible(false);
					dispose();
					AdminFrame.main(null);
				} else {
					contentPane.setVisible(false);
					dispose();
					ARMS_LogIn.main(null);		
				}			
			}
		});
		btnLogOut.setBounds(606, 392, 89, 23);
		contentPane.add(btnLogOut);
		
		JButton btnViewCourseCatalog = new JButton("View Course Catalog");
		btnViewCourseCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//CourseCatalog catalog = new CourseCatalog();
					CourseCatalog.NewScreen();
			}
		});
		btnViewCourseCatalog.setBounds(65, 115, 200, 25);
		contentPane.add(btnViewCourseCatalog);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(33, 187, 637, 174);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblEnterCourseIds = new JLabel("Enter Course IDs You Would Like to Take:");
		lblEnterCourseIds.setBounds(33, 11, 369, 30);
		panel.add(lblEnterCourseIds);
		
		textFieldCourse1 = new JTextField();
		textFieldCourse1.setBounds(33, 79, 85, 25);
		panel.add(textFieldCourse1);
		textFieldCourse1.setColumns(10);
		
		JLabel lblCourse = new JLabel("Course 1:");
		lblCourse.setBounds(32, 54, 87, 14);
		panel.add(lblCourse);
		
		JLabel lblCourse_1 = new JLabel("Course 2:");
		lblCourse_1.setBounds(146, 54, 87, 14);
		panel.add(lblCourse_1);
		
		textFieldCourse2 = new JTextField();
		textFieldCourse2.setBounds(147, 79, 85, 25);
		panel.add(textFieldCourse2);
		textFieldCourse2.setColumns(10);
		
		JLabel lblCourse_2 = new JLabel("Course 3:");
		lblCourse_2.setBounds(264, 54, 87, 14);
		panel.add(lblCourse_2);
		
		textFieldCourse3 = new JTextField();
		textFieldCourse3.setBounds(266, 79, 85, 25);
		panel.add(textFieldCourse3);
		textFieldCourse3.setColumns(10);
		
		JLabel lblCourse_3 = new JLabel("Course 4:");
		lblCourse_3.setBounds(388, 54, 87, 14);
		panel.add(lblCourse_3);
		
		textFieldCourse4 = new JTextField();
		textFieldCourse4.setBounds(388, 79, 85, 25);
		panel.add(textFieldCourse4);
		textFieldCourse4.setColumns(10);
		
		textFieldCourse5 = new JTextField();
		textFieldCourse5.setBounds(521, 79, 85, 25);
		panel.add(textFieldCourse5);
		textFieldCourse5.setColumns(10);
		
		JLabel lblCourse_4 = new JLabel("Course 5:");
		lblCourse_4.setBounds(519, 54, 87, 14);
		panel.add(lblCourse_4);
		
		JButton btnSubmitCourseRequests = new JButton("Submit Course Requests");
		btnSubmitCourseRequests.setBounds(33, 129, 200, 25);
		panel.add(btnSubmitCourseRequests);
		btnSubmitCourseRequests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String courses = null;
				ArrayList<Integer> courseIDs = new ArrayList<Integer>();				
				
				if (!textFieldCourse1.getText().isEmpty()){
					courses = textFieldCourse1.getText();
					courseIDs.add(Integer.parseInt(textFieldCourse1.getText()));
					
					if (!textFieldCourse2.getText().isEmpty()){
						courses += ", " + textFieldCourse2.getText();
						courseIDs.add(Integer.parseInt(textFieldCourse2.getText()));
						
						if (!textFieldCourse3.getText().isEmpty()){
							courses += ", " + textFieldCourse3.getText();
							courseIDs.add(Integer.parseInt(textFieldCourse3.getText()));
							
							if (!textFieldCourse4.getText().isEmpty()){
								courses += ", " + textFieldCourse4.getText();
								courseIDs.add(Integer.parseInt(textFieldCourse4.getText()));
								
								if (!textFieldCourse5.getText().isEmpty()){
									courses += ", " + textFieldCourse5.getText();
									courseIDs.add(Integer.parseInt(textFieldCourse5.getText()));
								}
							}
						}
					}
					
					// Insert course selections into the ScheduleRequests table
					// What parameters are needed here??  The 'ScheduleRequests' table only has fields: StudentID, SRId, SubmitTime
					
					//dbActions.InsertSchedule(gtId, CourseId, Semester, schedType)
					
					JOptionPane.showMessageDialog(null, "You have selected the following courses: " + courses);
					
					
				} else {
					JOptionPane.showMessageDialog(null, "You must enter at least one course ID, starting with Course 1.");
				}						

			}
		});
		
		JButton btnViewPastCourse = new JButton("View Past Course Requests");
		btnViewPastCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(null, "Accessing Past Course Requests...");
				ViewPastRequests.NewScreen();
			}
		});
		btnViewPastCourse.setBounds(65, 151, 200, 25);
		contentPane.add(btnViewPastCourse);
		

		

	}
	
	public void setShadowMode(boolean shadowMode)
	{
		this.shadowMode = shadowMode;
	}
}
