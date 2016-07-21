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

public class UpdateCourse extends JFrame {

	private JPanel contentPane;
	private JTextField classroomSize;
	private JTextField courseNameField;

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

	private DbActions dbactions = new DbActions();
	private List<CourseInstance> catalog = dbactions.getCatalog();
	private List<Semester> semesters = dbactions.getSemesters();
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

		JLabel lblCourseId = new JLabel("Course Name:");
		lblCourseId.setBounds(50, 83, 90, 15);
		contentPane.add(lblCourseId);

		courseNameField = new JTextField();
		courseNameField.setVisible(false);

		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setBounds(50, 128, 90, 15);
		contentPane.add(lblSemester);

		// TBD: Grab list of course Ids and store into an array of strings
		// This will be used to populate the courseId combo box
		String[] courseList = getCourseList();

		// AV: Temporary string to show combo boxes work
		// String[] courseList = { "SELECT", "CS6300", "CS6310", "CS6340",
		// "CS6350" };
		JComboBox<String[]> courseId = new JComboBox(courseList);
		courseId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox) e.getSource();
				String course = (String) cb.getSelectedItem();
				courseNameField.setText(course);
			}
		});
		courseId.setBounds(182, 78, 153, 24);
		contentPane.add(courseId);

		JLabel lblClassroomSize = new JLabel("Classroom Size:");
		lblClassroomSize.setBounds(50, 173, 114, 15);
		contentPane.add(lblClassroomSize);

		classroomSize = new JTextField();
		classroomSize.setColumns(10);
		classroomSize.setBounds(182, 171, 105, 19);
		contentPane.add(classroomSize);

		// TBD: Grab a list of semester Ids valid for a specific course and
		// store into an array of strings
		// This will be used to populate the semesterId combo box
		// AV: Uncomment this when dB access is ready
		String[] semesters = getSemesters();
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
				Integer classSize = 0;

				// Get specific course from CourseInstance list
				updateCourse = getCourse(courseNameField.getText(), semester);
				if ( updateCourse != null)
				{
					classSize = updateCourse.getClassSize();
				}
				classroomSize.setText(classSize.toString());
			}
		});
		semesterId.setBounds(182, 123, 153, 24);
		contentPane.add(semesterId);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				// calculate remaining seats based on new class size
				updateCourse.setClassSize(Integer.parseInt(classroomSize
						.getText()));
				if (updateCourse.getId() < 0) {
					dbactions.insertCourseOffering(updateCourse);
				} else {
					dbactions.updateCourseOffering(updateCourse);
				}
				// Show dialog that message was successfully updated

			}
		});
		btnUpdate.setBounds(50, 212, 117, 25);
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
		btnBack.setBounds(182, 212, 117, 25);
		contentPane.add(btnBack);
	}

	/**
	 * Returns course list as a string. This will be used as input for the
	 * Courses combo box
	 * 
	 * @return Returns a String array with the course names
	 */
	public String[] getCourseList() {
		Set<String> courseSet = new HashSet<String>();
		// catalog = dbactions.getCatalog();
		// First selection should be "Select"
		courseSet.add("-SELECT-");

		// Iterate through catalog and store course ID and course names into
		// hash set
		// HashSet will contain non-duplicate course Ids
		// We are trying to just capture the course list.
		if (catalog != null) {
			for (CourseInstance course : catalog) {
				courseSet.add(course.getCourseName());
			}
		}

		// Ensure list is in order
		TreeSet<String> sortedCourses = new TreeSet<String>();
		sortedCourses.addAll(courseSet);
		return (String[]) sortedCourses
				.toArray(new String[sortedCourses.size()]);
	}

	/**
	 * Returns a list of semesters a course is offered. This will be used as
	 * input for the Semesters combo box
	 * 
	 * @param courseName
	 *            Name of the course
	 * @return Returns a String array with the list of semester names the course
	 *         is offered
	 */
	public String[] getSemesters() {
		Set<String> semesterSet = new HashSet<String>();

		// First selection should be "Select"
		semesterSet.add("-SELECT-");

		// Iterate through catalog and store course ID and course names into
		// hash set
		// HashSet will contain non-duplicate course Ids
		// We are trying to just capture the course list.
		for (Semester semester : semesters) {
			semesterSet.add(semester.getSemesterName());
		}

		// Ensure list is in order
		TreeSet<String> sortedSemesters = new TreeSet<String>();
		sortedSemesters.addAll(semesterSet);
		return (String[]) sortedSemesters.toArray(new String[sortedSemesters
				.size()]);
	}

	public CourseInstance getCourse(String cname, String sname) {
		// Iterate through catalog and store course ID and course names into
		// hash set
		// HashSet will contain non-duplicate course Ids
		// We are trying to just capture the course list.
		if ( catalog != null)
		{
			for (CourseInstance course : catalog) {
				if (course.getCourseName().equals(cname)) {
					if (course.getSemester().equals(sname)) {
						return course;
					}
				}
			}
		}
		else {
			return null;
		}
		// If cannot find semester it's a new entry into the CourseOfferings
		// table
		CourseInstance newCourse = new CourseInstance(-1, cname, sname, 0, 0,
				null);
		return newCourse;
	}
}
