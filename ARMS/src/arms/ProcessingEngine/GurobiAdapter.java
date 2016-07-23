package arms.ProcessingEngine;

import arms.api.CourseInstance;
import arms.api.ScheduleRequest;
import arms.api.Student;
import arms.dataAccess.DbActions;
import gurobi.*;

import java.util.HashMap;
import java.util.List;

public class GurobiAdapter {

	private static GRBVar[][] yij;/*Student with id i taking offering with id j. This design, as opposed to using semester
    and course id, supports multiple instances of the same course in the same semester.*/

	public static void updateOfferingConstraints(List<CourseInstance> offerings){
		//TODO: Implement
	}
	public static void updateRequestConstraints(List<ScheduleRequest> scheduleRequests){
		//TODO: Implement
	}
	public static void updatePriorityConstraints(List<Student> studentsByPriority){
		//TODO: Implement
	}
	public static List<ScheduleRequest> processConstraints(){
		return null;
		//TODO: Implement
		//Returns a list of schedule requests.
	}

	private static List<ScheduleRequest> populateRestuls(List<ScheduleRequest> requests) throws GRBException{
	  	//Go over each student
    	for(int i = 0; i < yij.length; i++) {
    		HashMap<Integer,Integer> currentRequestedCourses = new HashMap<Integer, Integer>();
    		//Find student request
    		for(ScheduleRequest currentRequest : requests) {
    			if(currentRequest.getStudentId() == i) {
    				//Get student offering ids
    				for(int j = 0; j < yij[i].length; j++) {
    					//If student taking offering with id j
    					if(yij[i][j].get(GRB.DoubleAttr.X) == 1) {
    						//Get course id using offeringId
    						CourseInstance newCourseInstance = DbActions.getCourseInstanceByID(j);
    						int courseId = newCourseInstance.getId();
    						currentRequestedCourses.put(courseId, j);
    					}
    				}
    			}
				//Update student's requestedCourses
				currentRequest.setRequestedCourses(currentRequestedCourses);
    			break;
    		}
    	}
		return requests;
	}
}
