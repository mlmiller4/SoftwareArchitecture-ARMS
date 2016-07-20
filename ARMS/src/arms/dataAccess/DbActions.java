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

import arms.dataAccess.DbConnection;
import arms.api.CourseInstance;
import arms.api.ScheduleRequest;
import arms.api.Student;

public class DbActions {


	public boolean addStudent(Student student) {
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
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		} 
		return true;
	}

	/** Remove student using Student object **/
	public boolean removeStudent(Student student) {
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		try 
		{
			String query = "delete from Students where ID=? ";   
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, student.getId());
			pst.executeUpdate();
			pst.close();
			connection.commit();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		} 
		return true;
	}

	/** Remove student using student id **/
	public boolean removeStudent(int studentID) {
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		try 
		{
			String query = "delete from Students where ID=? ";   
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, studentID);
			pst.executeUpdate();
			pst.close();
			connection.commit();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		} 
		return true;
	}
	//TODO complete method
	public List<CourseInstance> getCatalog() {
		List<CourseInstance> catalog = new ArrayList<CourseInstance>();
		Connection connection = arms.dataAccess.DbConnection.dbConnector();
		String query = "select * from Courses where StudentID=? "; 

		try 
		{
			PreparedStatement pst = connection.prepareStatement(query);
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
	private List<ScheduleRequest> getScheduleRequests(int studentId, int courseId) {
		List<ScheduleRequest> scheduleRequests = new ArrayList<ScheduleRequest>(); 
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		HashMap<Integer, Integer> requestedCourses = null;
		//Get all schedule requests in the system
		String query = "select * from ScheduleRequests ";
		try {	
			Connection connection = arms.dataAccess.DbConnection.dbConnector();
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			int count = 0;
			while (rs.next()) {
				int SRId = rs.getInt("SRId");
				String submitTimeStr = rs.getString("SubmitTime");
				Date submitTime = df.parse(submitTimeStr);
				int studentIdFromDb = rs.getInt("StudentID");
				ScheduleRequest newScheduleRequest = new ScheduleRequest(studentIdFromDb, SRId, submitTime, requestedCourses);
				scheduleRequests.add(count, newScheduleRequest);
				count++;
			}
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
			query = "select * from SRDetails where SRId=? ";
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
				}
				currentRequest.setRequestedCourses(coursesOfCurrentRequest);
				count++;

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
		System.out.println(scheduleRequests.size());
		return scheduleRequests;
	}
	
	/**
	 * @return Returns a list of all schedule requests in the system
	 * Returns an empty list if there are no requests in the system
	 */
	public List<ScheduleRequest> getAllScheduleRequests() {
		return getScheduleRequests(-1, -1);
	}
	
	/**
	 * @return Returns a list of ScheduleRequests placed by the student with studentId
	 * Returns an empty list if student did not place any requests
	 */
	public List<ScheduleRequest> getStudentScheduleRequests(int studentId) {
		return getScheduleRequests(studentId, -1);
	}
	
	/**
	 * @return Returns a list of ScheduleRequests that contains courseId
	 * Returns an empty list if there are no requests with courseId
	 */
	public List<ScheduleRequest> getCourseScheduleRequests(int courseId) {
		return getScheduleRequests(-1, courseId);
	}
	
}