package arms.api;

import java.util.ArrayList;
import java.util.HashMap;

public class SystemReport {

	private int studentsNum;
	private int scheduleRequestsNum;
	//Key is course id. Value is the number of distinct students who have 
	//requested that course (in any one or more of their individual schedule requests)
	private HashMap<Integer, Integer> courseDemand;
	//key is student id. Value is an set of three values representing how many of the student's requested course (per their
	//most recent request, if it exists) could be taken during the next semester; could be taken during
	//some future semester; or, could not be taken at all
	private HashMap<Integer, ArrayList<Integer>> requestResultsInfo;
	//Key is configuration parameter name (parameters that can be modified by the administrator). Value is parameter value
	private HashMap<String, Float> configParameters;
	
	public int getStudentsNum() {
		return studentsNum;
	}
	public void setStudentsNum(int studentsNum) {
		this.studentsNum = studentsNum;
	}
	public int getScheduleRequestsNum() {
		return scheduleRequestsNum;
	}
	public void setScheduleRequestsNum(int scheduleRequestsNum) {
		this.scheduleRequestsNum = scheduleRequestsNum;
	}
	public HashMap<Integer, Integer> getCourseDemand() {
		return courseDemand;
	}
	public void setCourseDemand(HashMap<Integer, Integer> courseDemand) {
		this.courseDemand = courseDemand;
	}
	public HashMap<Integer, ArrayList<Integer>> getRequestResultsInfo() {
		return requestResultsInfo;
	}
	public void setRequestResultsInfo(HashMap<Integer, ArrayList<Integer>> requestResultsInfo) {
		this.requestResultsInfo = requestResultsInfo;
	}
	public HashMap<String, Float> getConfigParameters() {
		return configParameters;
	}
	public void setConfigParameters(HashMap<String, Float> configParameters) {
		this.configParameters = configParameters;
	}
}

