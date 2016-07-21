package arms.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import arms.api.CourseInstance;
import arms.api.ScheduleRequest;
import arms.api.Student;
import arms.api.Semester;

public class DbActions {


	public static boolean addStudent(Student student) {
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		try 
		{
			String query = "insert into Students (ID,FirstName,LastName,EarnedHours,GPA,Password) " +
					"values (?,?,?,?,?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, student.getId());
			pst.setString(2, student.getFirstName());
			pst.setString(3,  student.getLastName());
			pst.setFloat(4, student.getEarnedHours());
			pst.setFloat(5,  student.getGpa());
			pst.setString(6,  student.getPassword());
			pst.executeUpdate();
			pst.close();
			connection.commit();
			connection.close();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		} 
		return true;
	}

	/** Remove student using Student object **/
	public static boolean removeStudent(Student student) {
		return removeStudent(student.getId());
	}

	/** Remove student using student id **/
	public static boolean removeStudent(int studentID) {
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		try 
		{
			String query = "delete from Students where ID=? ";   
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, studentID);
			pst.executeUpdate();
			pst.close();
			connection.commit();
			connection.close();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		} 
		return true;
	}
	
	public static List<CourseInstance> getCatalog() {
		List<CourseInstance> catalog = new ArrayList<CourseInstance>();
		String courseTitle = "";
		String semester = "";
		
		try 
		{
			//String query = "select * from CourseOfferings "; 
			String query = "select CourseOfferings.Id AS Id, CourseOfferings.ClassSize AS ClassSize, CourseOfferings.CourseId AS CourseId," +
					"CourseOfferings.RemSeats AS RemSeats, Courses.Name AS CourseName, Semesters.SemesterName AS SemesterId " +
					"FROM CourseOfferings " +
					"LEFT JOIN Courses ON CourseOfferings.CourseId = Courses.CourseID " +
					"LEFT JOIN Semesters ON CourseOfferings.SemesterId = Semesters.SemesterID";
			Connection connection = arms.dataAccess.DbConnection.dbConnector();
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			int count = 0;
			//Get all rows in Courses table
			while (rs.next()) {
				int id = rs.getInt("Id");
				int courseId = rs.getInt("CourseId");
				courseTitle = rs.getString("CourseName");
				semester = rs.getString("SemesterId");
				int classSize = rs.getInt("ClassSize");
				int remSeats = rs.getInt("RemSeats");
				List<String> prerequisits = new ArrayList<String>();
				//Create new CourseInstance object with courseId and courseTitle, rest of the input variable will be set in second db connection
				CourseInstance newCourseInstance = new CourseInstance(id, courseId, courseTitle, semester, classSize, remSeats, prerequisits);
				catalog.add(count, newCourseInstance);
				count++;
			}
			if(count == 0) {
				return null;
			}
			rs.close();
			pst.close();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
//		//Update courses title
//		try 
//		{
//			String query = "select * from Courses where Name = ? "; 
//			Connection connection = arms.dataAccess.DbConnection.dbConnector();
//			PreparedStatement pst = connection.prepareStatement(query);
//			pst.setString(cu)
//			ResultSet rs = pst.executeQuery();
//			int count = 0;
//			//Get all rows in Courses table
//			while (rs.next()) {
//				courseTitle = rs.getString("Name");
//				int courseId = rs.getInt("CourseID");
//				//Go over all courses in catalog and update their title
//				for (CourseInstance currentCourse : catalog) {
//					if (currentCourse.getId() == courseId) {
//						currentCourse.setCourseName(courseTitle);
//					}
//				}
//				count++;
//			}
//			if(count == 0) {
//				return null;
//			}
//			rs.close();
//			pst.close();
//		}  
//		catch (Exception e)
//		{
//			JOptionPane.showMessageDialog(null, e);
//			return null;
//		}
		
		//Update courses prerequisites
		try 
		{
			String query = "select cp.PrerequisiteID AS PrerequisiteID, " +
					"c1.Name AS PrerequisiteTitle, " +
					"cp.CourseID AS CourseID, " +
					"c2.Name AS CourseTitle " +
					"FROM CoursePrerequisites cp " +
					"LEFT JOIN Courses c1 ON c1.CourseID = cp.PrerequisiteID " +
					"LEFT JOIN Courses c2 ON c2.CourseID = cp.CourseID";
			Connection connection = arms.dataAccess.DbConnection.dbConnector();
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			int count = 0;
			//String currentPrerequisiteTitle = "";
			//Get all rows in CoursePrerequisites table
			while (rs.next()) {
				String currentCourseName = rs.getString("CourseTitle");
				String prerequisiteName = rs.getString("PrerequisiteTitle");
				//Go over all courses in catalog and find course with courseId
				for (CourseInstance currentCourse : catalog) {
					if (currentCourse.getCourseName() == currentCourseName) {
						//Add corresponding prerequisite to the course in the catalog
						currentCourse.getPrerequisits().add(prerequisiteName);
					}
				}
				count++;
			}
			if(count == 0) {
				return null;
			}
			rs.close();
			pst.close();
			connection.close();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}

		return catalog;
	}
	
	/**
	 * If studentId = -1 and courseId = -1: returns all schedule requests in the system
	 * If studentId != -1: filter by student
	 * If courseId != -1: filter by course
	 * @return Returns a list of ScheduleRequests with filters if necessary
	 */
	private static List<ScheduleRequest> getScheduleRequests(int studentId, int courseId) {
		List<ScheduleRequest> scheduleRequests = new ArrayList<ScheduleRequest>(); 
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		//Get all schedule requests in the system
		String query = "select * from ScheduleRequests ";
		try {	
			Connection connection = arms.dataAccess.DbConnection.dbConnector();
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			int count = 0;
			while (rs.next()) {
				HashMap<Integer, Integer> requestedCourses = null;
				int SRId = rs.getInt("SRID");
				String submitTimeStr = rs.getString("SubmitTime");
				Date submitTime = df.parse(submitTimeStr);
				int studentIdFromDb = rs.getInt("StudentID");
				ScheduleRequest newScheduleRequest = new ScheduleRequest(studentIdFromDb, SRId, submitTime, requestedCourses);
				scheduleRequests.add(count, newScheduleRequest);
				count++;
			}
			connection.close();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		//Get requestedCourses from SRDetails table
		//Iterate over all requests
		for(ScheduleRequest currentRequest : scheduleRequests ) {
			int SRId = currentRequest.getSRID();
			//Create hashMap that will contain the courses of the currentRequest
			HashMap<Integer, Integer> coursesOfCurrentRequest = new HashMap<Integer, Integer>();
			//Get only the rows with SRID of the currentRequest
			query = "select * from SRDetails where SRID=? ";
			//HashMap<Integer, Integer> requestedCoursesPerStudent = new HashMap<Integer, Integer>();
			try 
			{	
				Connection connection = arms.dataAccess.DbConnection.dbConnector();
				PreparedStatement pst = connection.prepareStatement(query);
				pst.setInt(1, SRId);
				ResultSet rs = pst.executeQuery();
				int count = 0;

				//Iterate over rows of SRDetails table that matches SRId
				while (rs.next()) {
					coursesOfCurrentRequest.put(rs.getInt("CourseID"), rs.getInt("OfferingID"));
					count++;
				}
				currentRequest.setRequestedCourses(coursesOfCurrentRequest);
				

				if(count == 0) {
					return null;
				}
				rs.close();
				pst.close();
				connection.close();
			}  
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, e);
				return null;
			}
		}

		//Filter by student
		if(studentId != -1) {
			//Create a list of requests to be removed because they don't contain studentId
			List<ScheduleRequest> requestsToRemove = new ArrayList<ScheduleRequest>();
			//Go over schduleRequests list and add all entries where the student id != studentID to the rquestsToRemove list
			for(ScheduleRequest currenRequest : scheduleRequests) {
				if(currenRequest.getStudentId() != studentId) {
					requestsToRemove.add(currenRequest);
				}
			}
			scheduleRequests.removeAll(requestsToRemove);
		}
		//Filter by course
		if(courseId != -1) {
			//Create a list of requests to be removed because they don't contain a course with courseId
			List<ScheduleRequest> requestsToRemove = new ArrayList<ScheduleRequest>();
			//Go over schduleRequests list and add all entries that don't contain courseIs to the rquestsToRemove list
			for(ScheduleRequest currentRequest : scheduleRequests) {
				//Find out if the requested courses of the currentRequest contain courseId
				if(!currentRequest.getRequestedCourses().keySet().contains(courseId)) {
					requestsToRemove.add(currentRequest);	
				}
			}
			scheduleRequests.removeAll(requestsToRemove);
		}
		return scheduleRequests;
	}
	
	/**
	 * @return Returns a list of all schedule requests in the system
	 * Returns an empty list if there are no requests in the system
	 */
	public static List<ScheduleRequest> getAllScheduleRequests() {
		return getScheduleRequests(-1, -1);
	}
	
	/**
	 * @return Returns a list of ScheduleRequests placed by the student with studentId
	 * Returns an empty list if student did not place any requests
	 */
	public static List<ScheduleRequest> getStudentScheduleRequests(int studentId) {
		return getScheduleRequests(studentId, -1);
	}
	
	/**
	 * @return Returns a list of ScheduleRequests that contains courseId
	 * Returns an empty list if there are no requests with courseId
	 */
	public static List<ScheduleRequest> getCourseScheduleRequests(int courseId) {
		return getScheduleRequests(-1, courseId);
	}
	
	public static List<ScheduleRequest> getAllRecentScheduleRequests(){
	        return null;
	        //Group by student, order by submit time, return top 1
	}

	public static List<Student> getStudents(){
		List<Student> students = new ArrayList<Student>();
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		try 
		{
			String query = "select * from Students";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			int count = 0;

			//Iterate over rows of SRDetails table that matches SRId
			while (rs.next()) {
				int studentId = rs.getInt("ID");
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				float earnedHours = rs.getFloat("EarnedHours");
				float gpa = rs.getFloat("GPA");
				String password = rs.getString("Password");
				String userName = rs.getString("UserName");
				Student student = new Student(studentId, firstName, lastName, earnedHours, gpa, password, userName);
				students.add(student);
			}
			rs.close();
			pst.close();
			connection.close();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		} 
		return students;
	}
	
	public static boolean insertCourseOffering(CourseInstance courseInstance) {
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		
		// find corresponding courseId.s
		try 
		{
			String query = "insert into CourseOfferings (Id, CourseId, SemesterId, ClassSize, RemSeats) " +
					"values ((Select MAX(Id)+1 from CourseOfferings),(Select CourseID from Courses where Name = ?)" +
					",(Select SemesterID from Semesters where SemesterName = ?),?,?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, courseInstance.getCourseName());
			pst.setString(2, courseInstance.getSemester());
			pst.setInt(3, courseInstance.getClassSize());
			pst.setInt(4, courseInstance.getRemSeats());
			pst.executeUpdate();
			pst.close();
			connection.commit();
			connection.close();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		} 
		return true;				
	}
	
	public static boolean updateCourseOffering(CourseInstance courseInstance) {
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		try 
		{
			String query = "update CourseOfferings set ClassSize = ? where Id = ? ";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, courseInstance.getClassSize());
			pst.setInt(2, courseInstance.getId());
			pst.executeUpdate();
			pst.close();
			connection.commit();
			connection.close();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		} 
		return true;				
	}
	
	public static List<Semester> getSemesters() {
		List<Semester> semesters = new ArrayList<Semester>();
		
		try 
		{
			String query = "select * from Semesters "; 
			Connection connection = arms.dataAccess.DbConnection.dbConnector();
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			int count = 0;
			//Get all rows in Courses table
			while (rs.next()) {
				int semesterId = rs.getInt("SemesterID");
				String semesterName = rs.getString("SemesterName");
				//Create new Semester object with semesterId and semesterName
				Semester newSemester = new Semester(semesterId, semesterName);
				semesters.add(count, newSemester);
				count++;
			}
			if(count == 0) {
				return null;
			}
			rs.close();
			pst.close();
			connection.close();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}		
		return semesters;
	}
	public static void updateScheduleRequests(List<ScheduleRequest> recentStudentRequests) {
		//TODO: Implement.
		//If SRID exists, update SRDetails accordingly. Otherwise, add a new ScheduleRequest
		//row for the object with a missing id and add SRDetails accordingly.
	}

	public static void updateCourseOfferings(List<CourseInstance> offerings) {
		//TODO:Implement.
		//For each offering, update remaining seats in the row corresponding to the offering id in the db.
		//Only meant for existing course offerings.
	}

	public static int getScheduleRequestsCount(){
		//TODO:Implement
		return -1;
	}
}
