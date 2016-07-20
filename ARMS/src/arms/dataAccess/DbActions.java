package dataAccess;

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

import arms.sqliteConnection;
import arms.api.CourseInstance;
import arms.api.ScheduleRequest;
import arms.api.Student;

public class DbActions {


	public boolean addStudent(Student student) {
		Connection connection = sqliteConnection.dbConnector();
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
		Connection connection = sqliteConnection.dbConnector();
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
		Connection connection = sqliteConnection.dbConnector();
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
		Connection connection = sqliteConnection.dbConnector();
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

	public List<ScheduleRequest> getScheduleRequests(int studentId, int courseId) {
		List<ScheduleRequest> scheduleRequests = new ArrayList<ScheduleRequest>(); 
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		HashMap<Integer, Integer> requestedCourses = null;
		//If input is only studentId
		if (studentId != -1 && courseId == -1) {
			String query = "select * from ScheduleRequests where StudentID=? ";
			try 
			{	
				Connection connection = sqliteConnection.dbConnector();
				PreparedStatement pst = connection.prepareStatement(query);
				pst.setInt(1, studentId);
				ResultSet rs = pst.executeQuery();
				int count = 0;

				while (rs.next()) {
					int SRId = rs.getInt("SRId");
					String submitTimeStr = rs.getString("SubmitTime");
					Date submitTime = df.parse(submitTimeStr);
					ScheduleRequest newScheduleRequest = new ScheduleRequest(studentId, SRId, submitTime, requestedCourses);
					scheduleRequests.add(count, newScheduleRequest);
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
			//Get requestedCourses from SRDetails table
			query = "select * from SRDetails where SRId=? ";
			//Iterate over requests
			for(ScheduleRequest sr : scheduleRequests ) {
				int SRId = sr.getSRID();
				HashMap<Integer, Integer> requestedCoursesPerStudent = null;
				try 
				{	
					Connection connection = sqliteConnection.dbConnector();
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setInt(1, SRId);
					ResultSet rs = pst.executeQuery();
					int count = 0;
					//Iterate over rows with the same SRId
					while (rs.next()) {
						System.out.println();
						requestedCoursesPerStudent.put(rs.getInt("CourseID"), rs.getInt("OfferingID"));
						//ScheduleRequest newScheduleRequest = new ScheduleRequest(studentId, SRId, submitTime, requestedCourses);
						//scheduleRequests.add(count, newScheduleRequest);
						count++;
					}
					if(count == 0) {
						return null;
					}
					sr.setRequestedCourses(requestedCoursesPerStudent);
					rs.close();
					pst.close();
				}  
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, e);
					return null;
				}
			}
		}



		return scheduleRequests;
	}


	//	public List<ScheduleRequest> getScheduleRequests(int studentId, int courseId) {
	//		List<ScheduleRequest> scheduleRequests = new ArrayList<ScheduleRequest>();
	//		String query = "select * from ScheduleRequests ";
	//		int SRId;
	//		String submitTimeStr;
	//		Date submitTime;
	//		DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
	//		try 
	//		{	
	//			Connection connection = sqliteConnection.dbConnector();
	//			PreparedStatement pst = connection.prepareStatement(query);
	//			ResultSet rs = pst.executeQuery();
	//			int count = 0;
	//			System.out.println(rs);
	//
	//			while (rs.next()) {
	//				SRId = rs.getInt("SRId");
	//				submitTimeStr = rs.getString("SubmitTime");
	//				submitTime = df.parse(submitTimeStr);
	//				//TODO figure out where to get requestedCourses
	//				//ScheduleRequest newScheduleRequest = new ScheduleRequest(studentId, SRID, submitTime, requestedCourses);
	//				//scheduleRequests.add(count, newSchedultRequest);
	//				count++;
	//			}
	//			if(count != 1) {
	//				return null;
	//			}
	//
	//			rs.close();
	//			pst.close();
	//		}  
	//		catch (Exception e)
	//		{
	//			JOptionPane.showMessageDialog(null, e);
	//			return null;
	//		}
	//		return scheduleRequests;
	//	}
}


