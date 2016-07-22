package arms.ProcessingEngine;

import arms.api.CourseInstance;
import arms.api.ScheduleRequest;
import arms.api.Student;
import gurobi.*;

import java.util.List;

public class GurobiAdapter {

    private GRBVar[][][] yij;/*Student with id i taking offering with id j. This design, as opposed to using semester
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
}
