package arms.dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import net.proteanit.sql.DbUtils;

import arms.api.CourseInstance;
import arms.api.ScheduleRequest;
import arms.api.Student;
import arms.api.Semester;
import arms.api.SystemReport;

public class DbActions {


	public static boolean addStudent(Student student) {
		Connection connection = null;
		PreparedStatement pst = null;
		try 
		{
			 connection = arms.dataAccess.DbConnection.dbConnector();
			String query = "insert into Students (ID,FirstName,LastName,EarnedHours,GPA,Password,UserName) " +
					"values (?,?,?,?,?,?,?)";
			 pst = connection.prepareStatement(query);
			pst.setInt(1, student.getId());
			pst.setString(2, student.getFirstName());
			pst.setString(3,  student.getLastName());
			pst.setFloat(4, student.getEarnedHours());
			pst.setFloat(5,  student.getGpa());
			pst.setString(6,  student.getPassword());
			pst.setString(7, student.getUserName());
			pst.executeUpdate();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		} finally {
			try {
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
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
		PreparedStatement pst = null;
		try 
		{
			String query = "delete from Students where ID=? ";   
		    pst = connection.prepareStatement(query);
			pst.setInt(1, studentID);
			pst.executeUpdate();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		} finally {
			try {
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		return true;
	}
	
	public static List<CourseInstance> getCatalog() {
		List<CourseInstance> catalog = new ArrayList<CourseInstance>();
		String courseTitle = "";
		String semester = "";
		
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try 
		{
			String query = "select CourseOfferings.Id AS Id, CourseOfferings.ClassSize AS ClassSize, CourseOfferings.CourseId AS CourseId," +
					"CourseOfferings.RemSeats AS RemSeats, Courses.Name AS CourseName, Semesters.SemesterID AS SemesterId, " +
					"Semesters.SemesterName AS SemesterName " +
					"FROM CourseOfferings " +
					"LEFT JOIN Courses ON CourseOfferings.CourseId = Courses.CourseID " +
					"LEFT JOIN Semesters ON CourseOfferings.SemesterId = Semesters.SemesterId";
			 connection = arms.dataAccess.DbConnection.dbConnector();
			 pst = connection.prepareStatement(query);
			 rs = pst.executeQuery();
			int count = 0;
			//Get all rows in Courses table
			while (rs.next()) {
				int id = rs.getInt("Id");
				int courseId = rs.getInt("CourseId");
				courseTitle = rs.getString("CourseName");
				int semesterId = rs.getInt("SemesterId");
				semester = rs.getString("SemesterName");
				int classSize = rs.getInt("ClassSize");
				int remSeats = rs.getInt("RemSeats");
				List<String> prerequisits = new ArrayList<String>();
				//Create new CourseInstance object with courseId and courseTitle, rest of the input variable will be set in second db connection
				CourseInstance newCourseInstance = new CourseInstance(id, courseId, courseTitle, semesterId, semester, classSize, remSeats, prerequisits );
				catalog.add(count, newCourseInstance);
				count++;
				
			}
			if(count == 0) {
				return null;
			}
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}

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
			 connection = arms.dataAccess.DbConnection.dbConnector();
			 pst = connection.prepareStatement(query);
			 rs = pst.executeQuery();
			int count = 0;
			//Get all rows in CoursePrerequisites table
			while (rs.next()) {
				String currentCourseName = rs.getString("CourseTitle");
				String prerequisiteName = rs.getString("PrerequisiteTitle");
				//Go over all courses in catalog and find course with courseId
				for (CourseInstance currentCourse : catalog) {
					if (currentCourse.getCourseName().matches(currentCourseName)) {
						//Add corresponding prerequisite to the course in the catalog
						currentCourse.getPrerequisits().add(prerequisiteName);
					}
				}
				count++;
			}
			if(count == 0) {
				return null;
			}
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		return catalog;
	}
	
	/**
	 * If studentId = -1 and courseId = -1: returns all schedule requests in the system
	 * If studentId != -1: filter by student
	 * If courseId != -1: filter by course
	 * @return Returns a list of ScheduleRequests with filters if necessary
	 */
	public static List<ScheduleRequest> getScheduleRequests(int studentId, int courseId) {
		List<ScheduleRequest> scheduleRequests = new ArrayList<ScheduleRequest>(); 
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String query = new String();
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		//Get all schedule requests in the system
		if ( studentId != -1 ) 
		{
			query = "select * from ScheduleRequests where StudentID = ? ";
		}
		else 
		{
			query = "select * from ScheduleRequests ";
		} 
		try {	
			 connection = arms.dataAccess.DbConnection.dbConnector();
			 pst = connection.prepareStatement(query);
			if (studentId != -1)
			{
				pst.setInt(1,studentId);
			}
			 rs = pst.executeQuery();
			int count = 0;
			while (rs.next()) {
				HashMap<Integer, Integer> requestedCourses = null;
				String submitTimeStr = rs.getString("SubmitTime");
				Date submitTime = df.parse(submitTimeStr);
				int studentIdFromDb = rs.getInt("StudentID");
				int SRID = rs.getInt("SRId");
				ScheduleRequest newScheduleRequest = new ScheduleRequest(studentIdFromDb, submitTime, requestedCourses);
				newScheduleRequest.setSRID(SRID);
				scheduleRequests.add(count,newScheduleRequest);
				count++;
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
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
				 connection = arms.dataAccess.DbConnection.dbConnector();
				 pst = connection.prepareStatement(query);
				pst.setInt(1, SRId);
				 rs = pst.executeQuery();
				int count = 0;

				//Iterate over rows of SRDetails table that matches SRId
				while (rs.next()) {
					if ((courseId == rs.getInt("CourseID")) || (courseId == -1))
					{
						coursesOfCurrentRequest.put(rs.getInt("CourseID"), rs.getInt("OfferingID"));
					}
					count++;
				}
				currentRequest.setRequestedCourses(coursesOfCurrentRequest);
				

				if(count == 0) {
					return null;
				}
			}  
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, e);
				return null;
			} finally {
				try {
					rs.close();
					pst.close();
					connection.commit();
					connection.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		}

//		//Filter by student
//		if(studentId != -1) {
//			//Create a list of requests to be removed because they don't contain studentId
//			List<ScheduleRequest> requestsToRemove = new ArrayList<ScheduleRequest>();
//			//Go over schduleRequests list and add all entries where the student id != studentID to the rquestsToRemove list
//			for(ScheduleRequest currentRequest : scheduleRequests) {
//				if(currentRequest.getStudentId() != studentId) {
//					requestsToRemove.add(currentRequest);
//				}
//			}
//			scheduleRequests.removeAll(requestsToRemove);
//		}
//		//Filter by course
//		if(courseId != -1) {
//			//Create a list of requests to be removed because they don't contain a course with courseId
//			List<ScheduleRequest> requestsToRemove = new ArrayList<ScheduleRequest>();
//			//Go over schduleRequests list and add all entries that don't contain courseIs to the rquestsToRemove list
//			for(ScheduleRequest currentRequest : scheduleRequests) {
//				//Find out if the requested courses of the currentRequest contain courseId
//				if(!currentRequest.getRequestedCourses().keySet().contains(courseId)) {
//					requestsToRemove.add(currentRequest);	
//				}
//			}
//			scheduleRequests.removeAll(requestsToRemove);
//		}
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
	        //Group by student, order by submit time, return top 1
	        
	        List<ScheduleRequest> scheduleRequests = new ArrayList<ScheduleRequest>(); 
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			//Get all schedule requests in the system
			String query = "select * from ScheduleRequests group by StudentId order by datetime(SubmitTime) desc";
			Connection connection = null;
			PreparedStatement pst = null;
			ResultSet rs = null;
			try {	
				 connection = arms.dataAccess.DbConnection.dbConnector();
				 pst = connection.prepareStatement(query);
				 rs = pst.executeQuery();
				int count = 0;
				while (rs.next()) {
					HashMap<Integer, Integer> requestedCourses = null;
					int SRId = rs.getInt("SRID");
					String submitTimeStr = rs.getString("SubmitTime");
					Date submitTime = df.parse(submitTimeStr);
					int studentIdFromDb = rs.getInt("StudentID");
					ScheduleRequest newScheduleRequest = new ScheduleRequest(studentIdFromDb, submitTime, requestedCourses);
					newScheduleRequest.setSRID(SRId);
					scheduleRequests.add(count, newScheduleRequest);
					count++;
				}
			}
			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
				return null;
			} finally {
				try {
					rs.close();
					pst.close();
					connection.commit();
					connection.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
			//Get requestedCourses from SRDetails table
			//Iterate over all requests
			for(ScheduleRequest currentRequest : scheduleRequests ) {
				int SRId = currentRequest.getSRID();
				//Create hashMap that will contain the courses of the currentRequest
				HashMap<Integer, Integer> coursesOfCurrentRequest = new HashMap<Integer, Integer>();

				//HashMap<Integer, Integer> requestedCoursesPerStudent = new HashMap<Integer, Integer>();
				try 
				{	
					//Get only the rows with SRID of the currentRequest
					query = "select * from SRDetails where SRID=? ";
					 connection = arms.dataAccess.DbConnection.dbConnector();
					 pst = connection.prepareStatement(query);
					pst.setInt(1, SRId);
					 rs = pst.executeQuery();

					//Iterate over rows of SRDetails table that matches SRId
					while (rs.next()) {
						coursesOfCurrentRequest.put(rs.getInt("CourseID"), rs.getInt("OfferingID"));
					}
					currentRequest.setRequestedCourses(coursesOfCurrentRequest);
				}  
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
					return null;
				} finally {
					try {
						rs.close();
						pst.close();
						connection.commit();
						connection.close();
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e);
					}
				}
			}
			return scheduleRequests;
	}

	public static List<Student> getStudents(){
		List<Student> students = new ArrayList<Student>();
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try 
		{
			String query = "select * from Students";
			 pst = connection.prepareStatement(query);
			 rs = pst.executeQuery();

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
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		return students;
	}
	
	public static boolean insertCourseOffering(CourseInstance courseInstance) {
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		PreparedStatement pst = null;
		
		try 
		{
			String query = "insert into CourseOfferings (Id, CourseId, SemesterId, ClassSize, RemSeats) " +
					"values ((Select MAX(Id)+1 from CourseOfferings),(Select CourseID from Courses where Name = ?)" +
					",(Select SemesterID from Semesters where SemesterName = ?),?,?)";
			 pst = connection.prepareStatement(query);
			pst.setString(1, courseInstance.getCourseName());
			pst.setString(2, courseInstance.getSemester());
			pst.setInt(3, courseInstance.getClassSize());
			pst.setInt(4, courseInstance.getRemSeats());
			pst.executeUpdate();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		} finally {
			try {
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		return true;				
	}
	
	/**
	 * Updates CourseOfferings entry with new class size and remaining seats
	 * @param courseInstance
	 * @return Returns true if update is successful, false otherwise
	 */
	public static boolean updateCourseOffering(CourseInstance courseInstance) {
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		PreparedStatement pst = null;
		
		try 
		{
			String query = "update CourseOfferings set ClassSize = ?, RemSeats = ? where Id = ? ";
			 pst = connection.prepareStatement(query);
			pst.setInt(1, courseInstance.getClassSize());
			pst.setInt(2, courseInstance.getRemSeats());
			pst.setInt(3, courseInstance.getId());
			pst.executeUpdate();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		} finally {
			try {
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		return true;				
	}
	
	public static List<Semester> getSemesters() {
		List<Semester> semesters = new ArrayList<Semester>();
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try 
		{
			String query = "select * from Semesters "; 
			 connection = arms.dataAccess.DbConnection.dbConnector();
			 pst = connection.prepareStatement(query);
			 rs = pst.executeQuery();
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
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		return semesters;
	}
	
	public static void updateScheduleRequests(List<ScheduleRequest> recentStudentRequests) {
		//TODO: Implement.
		//If SRID exists, update SRDetails accordingly. Otherwise, add a new ScheduleRequest
		//row for the object with a missing id and add SRDetails accordingly.
		for (ScheduleRequest request : recentStudentRequests)
		{
			Connection connection = null;
			PreparedStatement pst = null;
			ResultSet rs = null;
			Date submitTime = request.getSubmitTime();
			boolean SRIDexists = false;
			int newSRID = 0;
			
			// Check if SRID exists
			try {
				connection = arms.dataAccess.DbConnection.dbConnector();
				String query = "select * from SRDetails where SRID=? ";
				 pst = connection.prepareStatement(query);
				pst.setInt(1, request.getSRID());

				 rs = pst.executeQuery();

				int count = 0;

				while (rs.next()) {
					count++;
				}

				if (count > 0) {
					SRIDexists = true;
				} 

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			} finally {
				try {
					rs.close();
					pst.close();
					connection.commit();
					connection.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
			
			// If SRID exists, perform UPDATE, otherwise perform INSERT
			if (SRIDexists)
			{
				for (HashMap.Entry<Integer, Integer> entry : request.getRequestedCourses().entrySet())
				{
					try 
					{
						connection = arms.dataAccess.DbConnection.dbConnector();
						String query = "update SRDetails set CourseID=?, OfferingID=? where SRID=?"; 
						 pst = connection.prepareStatement(query);
						pst.setInt(1, entry.getKey());
						pst.setInt(2, entry.getValue());
						pst.setInt(3, request.getSRID());
						pst.executeUpdate();

					}  
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(null, e);
					} finally {
						try {
							pst.close();
							connection.commit();
							connection.close();
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, e);
						}
					}
				}
			} else {
				// Inserts new row into ScheduleRequests table, SRID is autoincremented
				try 
				{
					connection = arms.dataAccess.DbConnection.dbConnector();
					String query = "insert into ScheduleRequests (StudentID, SubmitTime) values (?,?)"; 
					 pst = connection.prepareStatement(query);
					pst.setInt(1, request.getStudentId());
					pst.setString(2, submitTime.toString());
					pst.executeUpdate();
					
					// Retrieve new SRID
					 rs = pst.getGeneratedKeys();
					newSRID = rs.getInt("SRId");
					
					rs.close();
					pst.close();
					connection.commit();
				}  
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
				} finally {
					try {
						rs.close();
						pst.close();
						connection.commit();
						connection.close();
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e);
					}
				}
				for (HashMap.Entry<Integer, Integer> entry : request.getRequestedCourses().entrySet())
				{
					try 
					{
						connection = arms.dataAccess.DbConnection.dbConnector();
						String query = "insert into SRDetails (SRID, CourseID, OfferingID) values (?,?,?)"; 
						 pst = connection.prepareStatement(query);
						pst.setInt(1, newSRID);
						pst.setInt(2, entry.getKey());
						pst.setInt(3, entry.getValue());
						pst.executeUpdate();
						pst.close();
						connection.commit();
						connection.close();
					}  
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(null, e);
					} finally {
						try {
							pst.close();
							connection.commit();
							connection.close();
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, e);
						}
					}
				}
			}
		}
	}

	public static void updateCourseOfferings(List<CourseInstance> offerings) {
		//TODO:Implement.
		//For each offering, update remaining seats in the row corresponding to the offering id in the db.
		//Only meant for existing course offerings.
		Connection connection = null;
		PreparedStatement pst = null;
		
		for (CourseInstance instance : offerings)
		{
			 connection = arms.dataAccess.DbConnection.dbConnector();
			try 
			{
				String update = "update CourseOfferings set RemSeats = ? where Id = ?"; 
				 pst = connection.prepareStatement(update);
				pst.setInt(1, instance.getId());
				pst.setInt(2, instance.getRemSeats());
				pst.executeUpdate();
			}  
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, e);
			} finally {
				try {
					pst.close();
					connection.commit();
					connection.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		}
	}

	/**
	 * Counts the total number of schedule requests in the system
	 * @return Returns number of total schedule requests in the system
	 */
	public static int getScheduleRequestsCount(){
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try 
		{
			String query = "select count(*) as rowcount from ScheduleRequests "; 
			 connection = arms.dataAccess.DbConnection.dbConnector();
			 pst = connection.prepareStatement(query);
			 rs = pst.executeQuery();
			int count = 0;
			//Get all rows in Courses table
			while (rs.next()) {
				count = rs.getInt("rowcount");
			}
			return count;
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		return -1;
	}
	
	/**
	 * Counts the total number of students in the system
	 * @return Returns an int of the total number of students in the system
	 */
	private static int getStudentCount(){
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try 
		{
			String query = "select count(*) from Students"; 
			 connection = arms.dataAccess.DbConnection.dbConnector();
			 pst = connection.prepareStatement(query);
			 rs = pst.executeQuery();
			int count = 0;
			//Get all rows in Courses table
			while (rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		return -1;
	}
	
	/**
	 * For each course, counts the number of distinct students who have requested that course
	 * @return Returns a list of updated CourseInstances with their request count
	 */
	private static HashMap<Integer, Integer> getCourseRequestCount(){
		HashMap<Integer, Integer> courseRequestCount = new HashMap<Integer, Integer>();
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try 
		{
			String query = "select CourseID, count(distinct StudentId) as rowcount from ScheduleRequests t1 LEFT JOIN SRDetails t2 ON t1.SRId = t2.SRID group by CourseId"; 
			connection = arms.dataAccess.DbConnection.dbConnector();
			pst = connection.prepareStatement(query);
			rs = pst.executeQuery();
			while (rs.next()) {
				courseRequestCount.put(rs.getInt("CourseID"), rs.getInt("rowcount"));
			}
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		return courseRequestCount;
	}
	
	/**
	 * For each student, a set of three values representing how many of their requested course
	 * (per their most recent request, if it exists) could be taken during the next semester,
	 * could be taken during some future semester, or could not be taken at all
	 * @return Returns a list of updated CourseInstances with their request count
	 */
	private static List<Student> getStudentRequestCount(){
		List<Student> students = getStudents(); //Current student list
		List<ScheduleRequest> requests = getAllRecentScheduleRequests();

		
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		for (Student student : students)
		{
			int nextSemester = 0;
			int futureSemester = 0;
			int noSemester = 0;
			
			for (ScheduleRequest request: requests)
			{
				if ( student.getId() == request.getStudentId() )
				{
					for (HashMap.Entry<Integer, Integer> entry : request.getRequestedCourses().entrySet())
					{
						if (entry.getValue() == 0 || entry.getValue() == null)
						{
							noSemester++;
						} else {
							try 
							{
								String query = "select SemesterId from CourseOfferings where Id=?"; 
								connection = arms.dataAccess.DbConnection.dbConnector();
								pst = connection.prepareStatement(query);
								pst.setInt(1, entry.getValue());
								rs = pst.executeQuery();
								while (rs.next()) {
									int semId = rs.getInt("SemesterId");
									if (semId == 0)
									{
										nextSemester++;
									}
									else 
									{
										futureSemester++;
									}
								}
							}  
							catch (Exception e)
							{
								JOptionPane.showMessageDialog(null, e);
							} finally {
								try {
									rs.close();
									pst.close();
									connection.commit();
									connection.close();
								} catch (SQLException e) {
									JOptionPane.showMessageDialog(null, e);
								}
							}
						}
					}
				}
			}
			student.setCoursesNextSemester(nextSemester);
			student.setCoursesFutureSemester(futureSemester);
			student.setCoursesNoSemester(noSemester);
		}
		return students;
	}
	
	/**
	 * Gets a particular courseInstance
	 * @param cname course name
	 * @param sname semester name
	 * @return
	 */
	public static CourseInstance getCourseInstanceByID(int offeringId) {
		List<CourseInstance> catalog = getCatalog();
		// Iterate through catalog and store course ID and course names into
		// hash set
		// HashSet will contain non-duplicate course Ids
		// We are trying to just capture the course list.
		if ( catalog != null)
		{
			for (CourseInstance course : catalog) {
				if (course.getId() == offeringId) {
					return course;
				}
			}
		}
	
		return null;

	}
	
	/**
	 * Generates system report
	 * @return Returns a SystemReport object
	 */
	public static SystemReport generateSystemReport() {
		SystemReport report = new SystemReport();
		Integer studentCount = 0;
		studentCount = getStudentCount();
		report.setStudentsNum(studentCount);
		report.setScheduleRequestsNum(getScheduleRequestsCount());
		
		HashMap<Integer, Integer>  courses = getCourseRequestCount();
		report.setCourseDemand(courses);
		
		List<Student> students = getStudentRequestCount();
		HashMap<Integer, ArrayList<Integer>> studentDemand = new HashMap<Integer, ArrayList<Integer>>();
		if (students != null)
		{
			for (Student student : students)
			{
				ArrayList<Integer> counts = new ArrayList<Integer>();
				counts.add(student.getCoursesNextSemester());
				counts.add(student.getCoursesFutureSemester());
				counts.add(student.getCoursesNoSemester());
				studentDemand.put(student.getId(), counts);
			}
		}
		report.setRequestResultsInfo(studentDemand);
		
		HashMap<String, Float> config = new HashMap<String, Float>();
		List<CourseInstance> catalog = getCatalog();
		for (CourseInstance courseConfig : catalog)
		{
			System.out.println(courseConfig.getSemesterId());
			config.put("course_"+courseConfig.getCourseId()+"_offering_"+courseConfig.getId()+"_classSize", (float) courseConfig.getClassSize());
			config.put("course_"+courseConfig.getCourseId()+"_offering_"+courseConfig.getId()+"_semester", (float) courseConfig.getSemesterId());
		}	
		report.setConfigParameters(config);
		
		return report;
	}
	
	
	/**
	 * Returns Student ID based on user ID
	 */
	public static String getStudentID(String userName){
		
		String stdID = "";
		
		String query = "select ID from Students where UserName=?";		
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = connection.prepareStatement(query);
			pst.setString(1, userName);
			 rs = pst.executeQuery();
			while(rs.next()){
				stdID = rs.getString("ID");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
		return stdID;		
	}
	
	/**
	 * Returns user ID based on Student ID
	 */
	public static String getUserName(String stdID){
		
		String userName = "";
		
		String query = "select UserName from Students where ID=?";
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try{
			pst = connection.prepareStatement(query);
			pst.setString(1, stdID);
			 rs = pst.executeQuery();
			while(rs.next()){
				userName = rs.getString("UserName");
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
		return userName;
	}
	
	public static HashMap<Integer, List<Integer>> getCoursePrerequisites() {
		HashMap<Integer, List<Integer>> coursePrerequisites = new HashMap<Integer, List<Integer>>();
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try 
		{
			 connection = arms.dataAccess.DbConnection.dbConnector();
			String query = "select * from CoursePrerequisites";
			 pst = connection.prepareStatement(query);
			 rs = pst.executeQuery();
			int count = 0;
			//Get all rows in CoursePrerequisites table
			while (rs.next()) {
				int currentCourseID = rs.getInt("CourseID");
				int currentPrerequisitesID = rs.getInt("PrerequisiteID");
				//If the courseId is not in the coursePrerequisites list yet
				if(coursePrerequisites.get(currentCourseID) == null) {
					List<Integer> currentCoursePrerequisites = new ArrayList<Integer>();
					currentCoursePrerequisites.add(currentPrerequisitesID);
					coursePrerequisites.put(currentCourseID, currentCoursePrerequisites);
					}
				//If the courseId is in the coursePrerequisites list already
				else {
					coursePrerequisites.get(currentCourseID).add(currentPrerequisitesID);
				}
				count++;
			}
			if(count == 0) {
				return null;
			}
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		return coursePrerequisites;
	}
	
	
	/**
	 * Returns course name based on course ID
	 */
	public static String getCourseName(int courseID){
		
		String courseName = "";
		
		String query = "select Name from Courses where CourseID=?";
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try{
			pst = connection.prepareStatement(query);
			pst.setInt(1, courseID);
			 rs = pst.executeQuery();
			while (rs.next()){
				courseName = rs.getString("Name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
		
		return courseName;
	}
	
	
	/**
	 * Returns all Course Offering IDs based on Course ID
	 */
	public static ArrayList<Integer> getCourseOfferings(int courseID){
		
		ArrayList<Integer> offerings = new ArrayList<Integer>();
		
		String query = "select Id from CourseOfferings where CourseId=?";
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try{
			pst = connection.prepareStatement(query);
			pst.setInt(1, courseID);
			rs = pst.executeQuery();
			while (rs.next()){
				offerings.add(rs.getInt("Id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pst.close();
				connection.commit();
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
		
		return offerings;		
	}
	 
	 
	
}
