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
import java.util.Date;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import javax.swing.*;
import javax.swing.border.LineBorder;

import arms.api.API;
import arms.api.ScheduleRequest;
import arms.api.Student;
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
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		textFieldStdID = new JTextField();
		textFieldStdID.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldStdID.setEditable(false);
		textFieldStdID.setBounds(348, 74, 86, 20);
		contentPane.add(textFieldStdID);
		textFieldStdID.setColumns(10);

		JLabel lblStudentId = new JLabel("Student ID:");
		lblStudentId.setBounds(242, 76, 98, 14);
		contentPane.add(lblStudentId);

		if (shadowMode) {
			textFieldStdID.setText(userName);
			Integer stdid = Integer.parseInt(userName);
			Student usr = DbActions.getStudentInstance(stdid);
			textFieldUserName.setText(usr.getUserName());
		} else {
			textFieldUserName.setText(userName);
			Student usr = DbActions.getStudentInstance(userName);
			if (usr != null) {
				Integer stdid = new Integer(0);
				stdid = usr.getId();
				textFieldStdID.setText(stdid.toString());
			}
		}
		// ---------------------------------------------------------------------------------------------------

		JButton btnLogOut = new JButton("");
		if (shadowMode) {
			btnLogOut.setText("Back");
		} else {
			btnLogOut.setText("Log Out");
		}
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (shadowMode) {
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
		btnLogOut.setBounds(528, 392, 139, 23);
		contentPane.add(btnLogOut);

		JButton btnViewCourseCatalog = new JButton("View Course Catalog");
		btnViewCourseCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// CourseCatalog catalog = new CourseCatalog();
				CourseCatalog.NewScreen();
			}
		});
		btnViewCourseCatalog.setBounds(65, 115, 247, 25);
		contentPane.add(btnViewCourseCatalog);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(33, 187, 637, 174);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblEnterCourseIds = new JLabel(
				"Enter Course IDs You Would Like to Take:");
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
		btnSubmitCourseRequests.setBounds(33, 129, 289, 25);
		panel.add(btnSubmitCourseRequests);

		final HashMap<Integer, Integer> hashCourses = new HashMap<Integer, Integer>();

		btnSubmitCourseRequests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String courses = null;
				int course = 0;
				ArrayList<Integer> offeringIDs = new ArrayList<Integer>();

				if (!textFieldCourse1.getText().isEmpty()) {
					course = Integer.parseInt(textFieldCourse1.getText());
					offeringIDs = DbActions.getCourseOfferings(course);

					for (int i = 0; i < offeringIDs.size(); i++) {
						hashCourses.put(course, offeringIDs.get(i));
					}

					if (!textFieldCourse2.getText().isEmpty()) {
						course = Integer.parseInt(textFieldCourse2.getText());
						offeringIDs = DbActions.getCourseOfferings(course);

						for (int i = 0; i < offeringIDs.size(); i++) {
							hashCourses.put(course, offeringIDs.get(i));
						}

						if (!textFieldCourse3.getText().isEmpty()) {
							course = Integer.parseInt(textFieldCourse3
									.getText());
							offeringIDs = DbActions.getCourseOfferings(course);

							for (int i = 0; i < offeringIDs.size(); i++) {
								hashCourses.put(course, offeringIDs.get(i));
							}

							if (!textFieldCourse4.getText().isEmpty()) {
								course = Integer.parseInt(textFieldCourse4
										.getText());
								offeringIDs = DbActions
										.getCourseOfferings(course);

								for (int i = 0; i < offeringIDs.size(); i++) {
									hashCourses.put(course, offeringIDs.get(i));
								}

								if (!textFieldCourse5.getText().isEmpty()) {
									course = Integer.parseInt(textFieldCourse5
											.getText());
									offeringIDs = DbActions
											.getCourseOfferings(course);

									for (int i = 0; i < offeringIDs.size(); i++) {
										hashCourses.put(course,
												offeringIDs.get(i));
									}
								}
							}
						}
					}

					// Create ScheduleRequest object and send to
					// API.submitRequest()
					int studentID = Integer.parseInt(textFieldStdID.getText());
					Date date = new Date();
					ScheduleRequest request = new ScheduleRequest(studentID,
							date, hashCourses);

					API myAPI = new API();
					// ---------------------------------------------------------------
					// Added so that student list is updated before running
					// engine
					// ---------------------------------------------------------------
					myAPI.updateStudentList();
					myAPI.submitRequest(request);

					// JOptionPane.showMessageDialog(null,
					// "You have selected the following courses: "
					// + courses);

					CourseOffering.NewScreen(studentID);

				} else {
					JOptionPane
							.showMessageDialog(null,
									"You must enter at least one course ID, starting with Course 1.");
				}

			}
		});

		JButton btnViewPastCourse = new JButton("View Past Course Requests");
		btnViewPastCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// JOptionPane.showMessageDialog(null,
				// "Accessing Past Course Requests...");
				ViewPastRequests.NewScreen(textFieldStdID.getText());
			}
		});
		btnViewPastCourse.setBounds(65, 151, 247, 25);
		contentPane.add(btnViewPastCourse);

		JLabel lblCurrentUser = new JLabel("Current User:");
		lblCurrentUser.setBounds(242, 45, 98, 14);
		contentPane.add(lblCurrentUser);

	}

	public void setShadowMode(boolean shadowMode) {
		this.shadowMode = shadowMode;
	}
}
