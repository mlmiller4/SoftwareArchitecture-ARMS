package arms.api;

import java.util.Date;
import java.util.HashMap;

public class ScheduleRequest {
	private int id;
	private int SRID;
	private Date submitTime;
	private HashMap<Integer,Integer> requestedCourses;

	// Constructor
	public ScheduleRequest(int id, int SRID, Date submitTime, HashMap<Integer,Integer> requestedCourses) {
		this.id = id;
		this.SRID = SRID;
		this.submitTime = submitTime;
		this.requestedCourses = requestedCourses;
	}
	
	//Getter Methods
	public int getId() {
		return id;
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
	public void setId(int id) {
		this.id = id;
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
