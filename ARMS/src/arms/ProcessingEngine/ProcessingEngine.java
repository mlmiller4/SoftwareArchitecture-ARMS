package arms.ProcessingEngine;

import arms.api.CourseInstance;
import arms.api.ScheduleRequest;
import arms.api.Student;
import arms.dataAccess.DbActions;

import java.util.*;

public class ProcessingEngine {
    private static List<CourseInstance> courseOfferings;
    private static List<ScheduleRequest> recentStudentRequests;
    private static List<Student> students;
    private static HashMap<Integer,CourseInstance> offeringsMap;
    private static HashMap<Integer,ScheduleRequest> requestsMap;
    private static HashMap<Integer, List<Integer>> courseToInstances;
    private static HashMap<Integer,List<Integer>> courseToPrerequisites;
    private static HashMap<Integer, List<Integer>> offeringToPrerequisiteOfferings;
    private static boolean shadowMode = false;

    static {
       initSystem();
    }
    private static void initSystem(){
        courseOfferings = DbActions.getCatalog();
        recentStudentRequests = DbActions.getAllRecentScheduleRequests();
        students = DbActions.getStudents();
        prioritizeStudents();
        offeringsMap = new HashMap<Integer,CourseInstance>();
        requestsMap = new HashMap<Integer,ScheduleRequest>();
        for(CourseInstance offering : courseOfferings){
            offeringsMap.put(offering.getId(), offering);
        }
        for(ScheduleRequest request : recentStudentRequests){
            requestsMap.put(request.getStudentId(), request);
        }
        courseToPrerequisites = DbActions.getCoursePrerequisites();
        populateCourseInstances();
        populateOfferingToPrerequisiteOfferings();
    }
    private static void populateCourseInstances(){
        if (courseOfferings == null) return;
        //key is courseId and value is a list of course offerings id
        courseToInstances = new HashMap<Integer, List<Integer>>();
        for(CourseInstance offering : courseOfferings){
            if(courseToInstances.containsKey(offering.getCourseId())){
            	int offeringId = offering.getId();            	
                courseToInstances.get(offering.getCourseId()).add(offering.getId());
            }
            else{
            	List<Integer> offeringsList = new ArrayList<Integer>();
            	offeringsList.add(offering.getId());
            	courseToInstances.put(offering.getCourseId(), offeringsList);
            }
        }
    }
    private static void populateOfferingToPrerequisiteOfferings(){
        offeringToPrerequisiteOfferings = new HashMap<Integer, List<Integer>>();
        for(CourseInstance o1 : courseOfferings){
            offeringToPrerequisiteOfferings.put(o1.getId(),new ArrayList<Integer>());
            for(Integer prerequisiteId : courseToPrerequisites.get(o1.getCourseId())){
                for(CourseInstance o2 : courseOfferings){
                    if(o2.getCourseId() == prerequisiteId && o2.getSemesterId() < o1.getSemesterId()){
                        offeringToPrerequisiteOfferings.get(o1.getId()).add(o2.getId());
                    }
                }
            }
        }
    }
    public static void activateShadowMode(){
        shadowMode = true;
        initSystem();
    }
    public static void deactivateShadowMode(){
        shadowMode = false;
        initSystem();
    }
    public static HashMap<Integer,CourseInstance> getOfferingsMap(){
        return offeringsMap;
    }
    public static HashMap<Integer,List<Integer>> getOfferingToPrerequisiteOfferings(){
        return offeringToPrerequisiteOfferings;
    }
    public static HashMap<Integer,List<Integer>> getCourseToPrerequisites(){
        return courseToPrerequisites;
    }
    public static HashMap<Integer,List<Integer>> getCourseToInstances(){
        return courseToInstances;
    }
    public static List<CourseInstance> getCourseOfferings() {
        return courseOfferings;
    }
    public static List<ScheduleRequest> getAllRecentScheduleRequests() {
        return recentStudentRequests;
    }
    public static List<Student> getPrioritizedStudents() {
        return students;
    }
    public static void setCourseOfferings(List<CourseInstance> offerings) {
        courseOfferings = offerings;
    }
    public static void setRecentStudentRequests(List<ScheduleRequest> requests) {
        recentStudentRequests = requests;
    }
    public static void prioritizeStudents(){
        Collections.sort(students); //Sorts descending based on rank - See impl. in Student.compareTo
    }
    public static void updateScheduleRequest(ScheduleRequest newRequest){
        for (Iterator<ScheduleRequest> it = recentStudentRequests.listIterator(); it.hasNext();) {
            ScheduleRequest request = it.next();
            if(request.getStudentId() == newRequest.getStudentId()){
                it.remove();
            }
        }
        recentStudentRequests.add(newRequest);
    }
    public static void executeRequest(){
        List<ScheduleRequest> res = GurobiAdapter.processConstraints();
        if(res == null) return; //Problem unfeasible.
        //Update student requests in-memory with recent information.
        recentStudentRequests = res;
        //Update course instances in-memory with recent information.
        for(CourseInstance instance : courseOfferings){
            instance.setRemSeats(instance.getClassSize()); //Reset remaining seats.
        }
        for(ScheduleRequest request : res){
            for(Integer offeringId : request.getRequestedCourses().values()){
                if(offeringId == null)continue;
                CourseInstance offering = offeringsMap.get(offeringId);
                offering.setRemSeats(offering.getRemSeats()-1);
            }
        }
        if(!shadowMode){
            DbActions.updateScheduleRequests(recentStudentRequests);
            DbActions.updateCourseOfferings(courseOfferings);
            //Update SRID in-memory for the student request which triggered the re-calculation.
            //We do not do it beforehand, since having the id as -1 tells the updateScheduleRequest
            //logic it should add a new schedule request for the student.
            for(ScheduleRequest request: recentStudentRequests){
                if(request.getSRID() == -1){
                    request.setSRID(DbActions.getScheduleRequestsCount());
                }
            }
        }
    }
}
