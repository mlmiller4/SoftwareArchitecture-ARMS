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

/**
 * commonFunctions library extracts select data (ex: course name) from helper objects
 * and adds them to strings to be used as input for the combo boxes
 * @author avalera
 *
 */
public class CommonFunctions {

	private static List<CourseInstance> catalog = DbActions.getCatalog();
	private static List<Semester> semesters = DbActions.getSemesters();
	private static List<Student> students = DbActions.getStudents(); 
		
	/**
	 * Returns course list as a string. This will be used as input for the
	 * Courses combo box
	 * 
	 * @return Returns a String array with the course names
	 */
	public static String[] getCourseList() {
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
				courseSet.add(new Integer (course.getCourseId()).toString());
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
	public static String[] getSemesters() {
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

	/**
	 * Gets a particular courseInstance
	 * @param cname course name
	 * @param sname semester name
	 * @return
	 */
	public static CourseInstance getCourseInstanceBySemester(String cname, String sname) {
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
		CourseInstance newCourse = new CourseInstance(-1, -1, cname, sname, 0, 0,
				null);
		return newCourse;
	}
	
	/**
	 * Gets any courseInstance with a name of course (th
	 * @param cname course name
	 * @return
	 */
	public static CourseInstance getCourse(String courseId) {
		if ( catalog != null)
		{
			for (CourseInstance course : catalog) {
				if (course.getCourseId() == Integer.parseInt(courseId)) {
						return course;
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets a particular courseInstance
	 * @param cname course name
	 * @param sname semester name
	 * @return
	 */
	public static CourseInstance getCourseInstanceById(int offerId) {
		// Iterate through catalog and store course ID and course names into
		// hash set
		// HashSet will contain non-duplicate course Ids
		// We are trying to just capture the course list.
		if ( catalog != null)
		{
			for (CourseInstance course : catalog) {
				if (course.getId() == offerId) {
					return course;
				}
			}
		}
		return null;
	}
	
	/**
	 * Returns student list as a string array. This will be used as input for the
	 * Students combo box
	 * 
	 * @return Returns a String array with the student ids
	 */
	public static String[] getStudentList() {
		Set<String> studentSet = new HashSet<String>();
		// catalog = dbactions.getCatalog();
		// First selection should be "Select"
		studentSet.add("-SELECT-");

		// Iterate through students and store student ID into
		// hash set
		// HashSet will contain non-duplicate course Ids
		// We are trying to just capture the course list.
		if (students != null) {
			for (Student s : students) {
				studentSet.add(new Integer(s.getId()).toString());
			}
		}

		// Ensure list is in order
		TreeSet<String> sortedStudents = new TreeSet<String>();
		sortedStudents.addAll(studentSet);
		return (String[]) sortedStudents
				.toArray(new String[sortedStudents.size()]);
	}
	
	/**
	 * Gets any courseInstance with a name of course (th
	 * @param cname course name
	 * @return
	 */
	public Student getStudentInstance(int studentId) {
		// Iterate through catalog and store course ID and course names into
		// hash set
		// HashSet will contain non-duplicate course Ids
		// We are trying to just capture the course list.
		if ( students != null)
		{
			for (Student student : students) {
				if (student.getId() == studentId) {
						return student;
				}
			}
		}
		return null;
	}
}
