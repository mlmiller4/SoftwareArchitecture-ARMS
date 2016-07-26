package arms.dataAccess;

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
		catalog = DbActions.getCatalog();
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
		semesters = DbActions.getSemesters();
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
		int courseId = 0;
		List<String> prereqs = null;
		int semesterId = 0;
		catalog = DbActions.getCatalog();
		if ( catalog != null)
		{
			for (CourseInstance course : catalog) {
				if (course.getCourseName().matches(cname)) {
					courseId = course.getCourseId();
					prereqs = course.getPrerequisits();
					if (course.getSemester().matches(sname)) {
						return course;
					}
					
				}
			}
			//Get SemesterID
			for (Semester semester: semesters)
			{
				if (semester.getSemesterName().matches(sname))
				{
					semesterId = semester.getId();
				}
			}
			// If cannot find semester it's a new entry into the CourseOfferings
			// table
			CourseInstance newCourse = new CourseInstance(-1, courseId, cname, semesterId, sname, 0, 0, prereqs);

			return newCourse;
		}
		return null;
	}
	
	/**
	 * Gets any courseInstance with a name of course
	 * @param cname course name
	 * @return
	 */
	public static CourseInstance getCourse(String courseId) {
		catalog = DbActions.getCatalog();
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
		catalog = DbActions.getCatalog();
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
		students = DbActions.getStudents();
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
	 * Gets the student info from the list of students based on studentid
	 * @param studentId student id
	 * @return Student object if it exists, null otherwise
	 */
	public static Student getStudentInstance(int studentId) {

		students = DbActions.getStudents(); 
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
	
	/**
	 * Gets the student info from the list of students based on username
	 * @param studentId student id
	 * @return Student object if it exists, null otherwise
	 */
	public static Student getStudentInstance(String userName) {
		students = DbActions.getStudents(); 
		if ( students != null)
		{
			for (Student student : students) {
				if (student.getUserName().matches(userName)) {
						return student;
				}
			}
		}
		return null;
	}
}
