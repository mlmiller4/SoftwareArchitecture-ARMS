package arms;

import java.awt.EventQueue;
import java.awt.Font;

import java.sql.*;
import java.util.List;
import java.util.Set;

import javax.swing.*;

public class DatabaseActions {

	
	Connection connection = null;

	/**
	 * Constructor
	 * Establishes database connection.  SQLite is thread safe so multiple threads
	 * can be run on only one database connection.  Only call the constructor once
	 * during the entire run of the application and close the connection upon exit.
	 */
	public DatabaseActions() {
		initialize();
		connection = sqliteConnection.dbConnector();		
		connection.setAutoCommit(false);
	}
	
	/**
	 * closeDBConnection
	 * Disconnect from database
	 * 
	 */
	public void closeDBConnection()
	{
		try
		{
			connection.commit();
			connection.close();
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/**
	 * InsertStudent
	 * Inserts new student information into the database (same as register student)
	 * 
	 * @param[in] firstName		First name of student
	 * @param[in] lastName		Last name of student
	 * @param[in] gtId			Georgia Tech ID # of student
	 * @param[in] userName		Username of the student
	 * @param[in] password		Password of the student
	 * @return Returns true if operation succeeds, otherwise returns false.
	 */
	public boolean InsertStudent(String firstName, String lastName, Long gtId, String userName, String password)
	{ 				
		try 
		{
			String sqlst = "insert into UserInfo (ID,FirstName,LastNAme,Username,password,Admin) " +
						   "values (?,?,?,?,?,'False')";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			pst.setLong(1, gtId.longValue());
			pst.setString(2, firstName);
			pst.setString(3,  lastName);
			pst.setString(4, userName);
			pst.setString(5,  password);
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
	
	/**
	 * DeleteStudent
	 * Deletes the student from the database (unregister)
	 * 
	 * @param Id
	 * @return Returns true if operation succeeds, otherwise returns false.
	 */
	public boolean DeleteStudent(Long Id)
	{
		try 
		{
			String sqlst = "delete from UserInfo where ID=?";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			pst.setLong(1, Id.longValue());
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
	
	/**
	 * UserExists
	 * Checks if the user exists in the database
	 * @param userName		Username of the user logging in
	 * @param password		Password of the user logging in
	 * @param admin			Boolean flag indicating whether user is an administrator
	 * @return Returns true if user exists in the database, false otherwise.
	 */
	public boolean UserExists(String userName, String password, boolean admin)
	{
		try 
		{
			String sqlst = "select * from UserInfo where Username=? and password=? and Admin=? ";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			pst.setString(1, userName);
			pst.setString(2,  password);
			pst.setBoolean(3,  admin);
			ResultSet rs = pst.executeQuery();
			
			int count = 0;
			while(rs.next())
			{
				count++;
			}
			
			if ( count != 1)
			{
				return false;
			}
			rs.close();
			pst.close();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		} 
		return true;
	}
	
	/**
	 * InsertSchedule
	 * Inserts the schedule into the ScheduleRequests table
	 * 
	 * @param gtId			GATech ID of the student
	 * @param CourseId		Course ID of the course
	 * @param Semester		Semester the course can be taken
	 * @param Type			"request" if student request, or "response" if system generated 
	 * 						response.
	 * @return Returns true if operation succeeds, otherwise returns false.
	 */
	public boolean InsertSchedule(Long gtId, Integer CourseId, String Semester, String schedType)
	{ 				
		try 
		{
			String sqlst = "insert into ScheduleRequests (ID,CourseId,Semester, Type, TimeStamp) " +
						   "values (?,?,?,?,CURRENT_TIMESTAMP);";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			pst.setLong(1, gtId.longValue());
			pst.setInt(2, CourseId.intValue());
			pst.setString(3,  Semester);
			pst.setString(4,  schedType);
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
	
	/**
	 * RetrieveSchedule
	 * Retrieves all student request and system generated responses based
	 * on the GATech ID
	 * @param gtId
	 * @return Returns Schedule object.
	 */
	public Object RetrieveSchedule(Long gtId)
	{
		Object schedule = new Object(gtId);
		try 
		{
			String sqlst = "select * from ScheduleRequests where ID=?";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			pst.setLong(1, gtId.longValue());
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				/**
				 * Return schedule properties
				 * Either use individual strings or use a Schedule object
				 * that contains the following information
				 */
				String courseId = rs.getString("CourseId");
				String semester = rs.getString("Semester");
				String schedType = rs.getString("Type");
				String timeStamp = rs.getString("TimeStamp");
				/**
				 * Perform actions with retrieved data here
				 *
				 */
			}
			rs.close();
			pst.close();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		} 
		return schedule;		
	}
	
	/**
	 * DeleteSchedule
	 * Deletes the oldest entry of a schedule from the database
	 * 
	 * @param Id		GATech ID of the student
	 * @return Returns true if operation succeeds, otherwise returns false.
	 */
	public boolean DeleteSchedule(Long Id)
	{
		try 
		{
			/**
			 * TBD:
			 * Run RetrieveSchedule to grab all the time stamps of a particular student.
			 * Identify which is the oldest time stamp and run the delete against the 
			 * oldest entry
			 */
			
			/**
			 * Below is the actual delete command
			 */
			String sqlst = "delete from ScheduleRequests where ID=? and TimeStamp=?";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			pst.setLong(1, Id.longValue());
			pst.setString(2, timeStamp);
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
	
	/**
	 * UpdateCourse
	 * Updates a course based on the given parameter name and value
	 * 
	 * @param courseId			Course ID 
	 * @param parameterName		The name of the parameter to update
	 * @param parameterValue	The value of the parameter
	 * @return Returns true if operation succeeds, otherwise returns false.
	 */
	public boolean UpdateCourse(Integer courseId, String parameterName, Object parameterValue)
	{ 				
		try 
		{
			String sqlst = "update CourseCatalog set " + parameterName + " = " + parameterValue +
						   " where ID=?";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			pst.setInt(1, CourseId.intValue());
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
	
	/**
	 * RetrieveCourse
	 * Retrieves course information
	 * @param CourseId		Course ID
	 * @return Returns Course object
	 */
	public boolean RetrieveCourse(Integer CourseId)
	{
		Object course = new Object(courseId);
		try 
		{
			String sqlst = "select * from CourseCatalog where ID=?";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			pst.setInt(1, CourseId.intValue());
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				/**
				 * TBD:
				 * Return the required columns in the table
				 */
				String courseId = rs.getString("CourseId");
				String semester = rs.getString("Semester");
				String maxClassSize = rs.getString("ClassSize");

				/**
				 * Perform actions with retrieved data here
				 * ex: display back to UI
				 */
			}
			rs.close();
			pst.close();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		} 
		return course;		
	}
	
	/**
	 * retrieveStudentCount
	 * Returns the total number of students registered
	 * @return	Returns the total number of students, or 0 if there is an error
	 */
	public Integer retrieveStudentCount()
	{
		try 
		{
			String sqlst = "select count(*) as total from UserInfo where Admin='False'";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			ResultSet rs = pst.executeQuery();
			Integer studentTotal = new Integer(rs.getInt("total"));
			rs.close();
			pst.close();
			return studentTotal;
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return new Integer(0);
		} 
	}
	
	/**
	 * retrieveStudentList
	 * Returns the list of students registered
	 * @return	Returns a list of all the students in the database
	 */
	public List<Student> retrieveStudentList()
	{
		List<Student> students;
		Student student = null;
		try 
		{
			String sqlst = "select * as total from UserInfo where Admin='False'";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
				student.setID(rs.getString("ID"));
				student.setFirstName(rs.getString("FirsName"));
				student.setLastName(rs.getString("LastName"));
				students.add(student);
			}
			Integer studentTotal = new Integer(rs.getInt("total"));
			rs.close();
			pst.close();
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		} 
		return students;
	}
	
	/**
	 * retrieveScheduleCount
	 * Returns the total number of schedule requests
	 * @return	Returns the total number of requests, or 0 if there is an error
	 */
	public Integer retrieveScheduleCount()
	{
		try 
		{
			String sqlst = "select count(*) as total from ScheduleRequests where Type = 'Request'";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			ResultSet rs = pst.executeQuery();
			Integer scheduleTotal = new Integer(rs.getInt("total"));
			rs.close();
			pst.close();
			return scheduleTotal;
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return new Integer(0);
		} 
	}
	
	/**
	 * retrieveCourseStudentCount
	 * For the course id passed in, the number of distinct students who have requested that course
	 * @param CourseId		Course Id
	 * @return	Returns the total number of distinct students who have requested the course
	 */
	public Integer retrieveCourseStudentCount(Integer CourseId)
	{
		try 
		{
			String sqlst = "select count(distinct gtId) as total from ScheduleRequests where CourseId = ?";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			pst.setInt(1, CourseId.intValue());
			ResultSet rs = pst.executeQuery();
			
			Integer scheduleTotal = new Integer(rs.getInt("total"));
			rs.close();
			pst.close();
			return scheduleTotal;
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return new Integer(0);
		} 
	}
	
	/**
	 * retrieveStudentCourseCount
	 * Retrieves for each student a set of three values representing how many of their requested course (per
	 * their most recent request, if it exists) coudl be taken during the next semester, could be taken during
	 * some future semester, or could not be taken at all
	 * @param gtId				GATech Id of the student		
	 * @param CurrentSemester	The current semester
	 * @return	Returns a set of three integer values 
	 */
	public Set<Integer> retrieveStudentCourseCount(Long gtId, Integer CurrentSemester)
	{
		Set<Integer> studentCounts;
		try 
		{
			int nextSemester = CurrentSemester.intValue() + 1;
			String sqlst = "select count(distinct CourseId) as total from ScheduleRequests where Semester = ? and ID = ?";
			PreparedStatement pst = connection.prepareStatement(sqlst);
			pst.setInt(1, nextSemester);
			pst.setLong(2, gtId.longValue());
			ResultSet rs = pst.executeQuery();
			
			Integer nextSemesterTotal = new Integer(rs.getInt("total"));
			studentCounts.add(nextSemesterTotal);
			
			rs.close();
			pst.close();
			
			sqlst = "select count(distinct CourseId) as total from ScheduleRequests where Semester > ? and ID = ?";
			pst = connection.prepareStatement(sqlst);
			pst.setInt(1, nextSemester);
			pst.setLong(2, gtId.longValue());
			rs = pst.executeQuery();
			
			nextSemesterTotal = new Integer(rs.getInt("total"));
			studentCounts.add(nextSemesterTotal);
			
			rs.close();
			pst.close();	
			
			sqlst = "select count(distinct CourseId) as total from ScheduleRequests where Semester = 0 and ID = ?";
			pst = connection.prepareStatement(sqlst);
			pst.setLong(1, gtId.longValue());
			rs = pst.executeQuery();
			
			nextSemesterTotal = new Integer(rs.getInt("total"));
			studentCounts.add(nextSemesterTotal);
			
			rs.close();
			pst.close();	
			return studentCounts;
		}  
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
		} 
	}
	
}
