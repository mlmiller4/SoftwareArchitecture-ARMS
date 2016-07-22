package arms.api;

import java.util.Date;
import java.util.HashMap;

public class ScheduleRequest {
	private int studentId;
	private int SRID;
	private Date submitTime;
	private HashMap<Integer,Integer> requestedCourses;

	// Constructor
	public ScheduleRequest(int studentId, Date submitTime, HashMap<Integer,Integer> requestedCourses) {
		this.studentId = studentId;
		this.SRID = -1; //SRID is generated in the db - auto-incremented.
		this.submitTime = submitTime;
		this.requestedCourses = requestedCourses;
	}
	
	//Getter Methods
	public int getStudentId() {
		return studentId;
	}

	public int getSRID() {
		return SRID;
	}

	public Date getSubmitTime() {
		return submitTime;
	}
	
	public HashMap<Integer, Integer> getRequestedCourses() {
		return requestedCourses;
	}

	//Setter Methods
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public void setSRID(int sRID) {
		SRID = sRID;
	}
	
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public void setRequestedCourses(HashMap<Integer, Integer> requestedCourses) {
		this.requestedCourses = requestedCourses;
	}
}
