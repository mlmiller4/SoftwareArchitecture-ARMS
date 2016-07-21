package arms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import arms.dataAccess.*;
import arms.api.*;
import javax.swing.JTextArea;

public class UpdateCourse extends JFrame {

	private JPanel contentPane;
	private JTextField classroomSize;
	private JTextField courseIdField;
	private JTextField courseNameField;
	private Integer initialClassSize = new Integer(0);
	private Integer initialRemSeats = new Integer(0);
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateCourse frame = new UpdateCourse();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private CourseInstance updateCourse = null;

	/**
	 * Create the frame.
	 */
	public UpdateCourse() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 461, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblCourseCatalog = new JLabel("Update Course");
		lblCourseCatalog.setForeground(Color.BLACK);
		lblCourseCatalog.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblCourseCatalog.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseCatalog.setBounds(10, 0, 425, 49);
		contentPane.add(lblCourseCatalog);

		JLabel lblCourseTitleLabel = new JLabel("Course Title");
		lblCourseTitleLabel.setBounds(50, 109, 90, 15);
		contentPane.add(lblCourseTitleLabel);

		// Hidden Fields to store data
		courseIdField = new JTextField();
		courseIdField.setVisible(false);
		
		courseNameField = new JTextField();
		courseNameField.setVisible(false);

		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setBounds(50, 152, 90, 15);
		contentPane.add(lblSemester);
		
		JLabel label = new JLabel("Course Name:");
		label.setBounds(50, 65, 90, 15);
		contentPane.add(label);
		
		JLabel lblCourseTitle = new JLabel("");
		lblCourseTitle.setBounds(182, 109, 153, 14);
		contentPane.add(lblCourseTitle);

		// TBD: Grab list of course Ids and store into an array of strings
		// This will be used to populate the courseId combo box
		String[] courseList = CommonFunctions.getCourseList();

		// AV: Temporary string to show combo boxes work
		// String[] courseList = { "SELECT", "CS6300", "CS6310", "CS6340",
		// "CS6350" };
		JComboBox<String[]> courseId = new JComboBox(courseList);
		courseId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox) e.getSource();
				String course = (String) cb.getSelectedItem();
				courseIdField.setText(course);
				CourseInstance selectedCourse = CommonFunctions.getCourse(course);
				if ( selectedCourse != null )
				{
					courseNameField.setText(selectedCourse.getCourseName());
				}
			}
		});
		courseId.setBounds(182, 60, 153, 24);
		contentPane.add(courseId);
		
		lblCourseTitle.setText(courseNameField.getText());

		JLabel lblClassroomSize = new JLabel("Classroom Size:");
		lblClassroomSize.setBounds(50, 197, 114, 15);
		contentPane.add(lblClassroomSize);

		classroomSize = new JTextField();
		classroomSize.setColumns(10);
		classroomSize.setBounds(182, 195, 105, 19);
		contentPane.add(classroomSize);

		// TBD: Grab a list of semester Ids valid for a specific course and
		// store into an array of strings
		// This will be used to populate the semesterId combo box
		// AV: Uncomment this when dB access is ready
		String[] semesters = CommonFunctions.getSemesters();
		//
		// AV: temporary variable for testing
		// String[] offering = {"SELECT", "Fall 2016", "Spring 2017",
		// "Summer 2017"};
		JComboBox<String[]> semesterId = new JComboBox(semesters);
		semesterId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Once semester is selected, populate the classroom size text
				// field
				// with the existing data
				JComboBox<String> cb = (JComboBox) e.getSource();
				String semester = (String) cb.getSelectedItem();
				

				// Get specific course from CourseInstance list
				updateCourse = CommonFunctions.getCourseInstanceBySemester(courseIdField.getText(), semester);
				if ( updateCourse != null)
				{
					initialClassSize = updateCourse.getClassSize();
					initialRemSeats = updateCourse.getRemSeats();
				}
				classroomSize.setText(initialClassSize.toString());
			}
		});
		semesterId.setBounds(182, 147, 153, 24);
		contentPane.add(semesterId);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				// calculate remaining seats based on new class size
				Integer newRemSeats = new Integer(0);
				Integer newClassSize = Integer.parseInt(classroomSize.getText());
				newRemSeats = newClassSize - initialClassSize + initialRemSeats;
				if ( newRemSeats < 0)
				{
					newRemSeats = 0;
				}
				updateCourse.setClassSize(newClassSize);
				updateCourse.setRemSeats(newRemSeats);

				if (updateCourse.getId() < 0) {
					DbActions.insertCourseOffering(updateCourse);
				} else {
					DbActions.updateCourseOffering(updateCourse);
				}
				// Show dialog that message was successfully updated

			}
		});
		btnUpdate.setBounds(50, 236, 117, 25);
		contentPane.add(btnUpdate);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				contentPane.setVisible(false);
				dispose();
				AdminFrame af = new AdminFrame();
				af.setVisible(true);
			}
		});
		btnBack.setBounds(182, 236, 117, 25);
		contentPane.add(btnBack);
		

	}
}
