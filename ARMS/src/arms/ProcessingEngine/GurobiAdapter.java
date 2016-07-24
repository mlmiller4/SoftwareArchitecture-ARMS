package arms.ProcessingEngine;

import arms.api.CourseInstance;
import arms.api.ScheduleRequest;
import arms.api.Student;
import arms.dataAccess.DbActions;
import gurobi.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class GurobiAdapter {

	private static GRBVar[][] yij;/*Student with id i taking offering with id j. This design, as opposed to using semester
    and course id, supports multiple instances of the same course in the same semester.*/
    private static GRBEnv env;
    private static GRBModel model;
    private static final String LOG_PATH = "Scheduler.log";

    static{
        try {
            initializeOptimizer();
        } catch (GRBException e) {
            e.printStackTrace();
        }
    }

    private static void initializeOptimizer() throws GRBException {
        env = new GRBEnv(LOG_PATH);
        model = new GRBModel(env);
        model.getEnv().set(GRB.IntParam.LogToConsole,0);
        initializeVariables();
        setObjective();
        updateOfferingConstraints();
        updatePriorityConstraints();
        updatePrerequisitesConstraints();
        updateRequestConstraints();
    }

    private static void initializeVariables() throws GRBException {
        yij = new GRBVar[ProcessingEngine.getPrioritizedStudents().size()][ProcessingEngine.getCourseOfferings().size()];
        for(int i = 0; i < ProcessingEngine.getPrioritizedStudents().size(); i++){
            for(int j = 0; j < ProcessingEngine.getCourseOfferings().size(); j++){
                yij[i][j] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "");
            }
        }
        model.update();
    }

    private static void setObjective() throws GRBException {
        GRBLinExpr expr = new GRBLinExpr();
        for(int i = 0; i < ProcessingEngine.getPrioritizedStudents().size(); i++){
            for(int j = 0; j < ProcessingEngine.getCourseOfferings().size(); j++){
                expr.addTerm(1.0, yij[i][j]);
            }
        }
        model.setObjective(expr, GRB.MAXIMIZE);
        model.update();
    }

    private static void updateOfferingConstraints() throws GRBException {
        List<CourseInstance> offerings = ProcessingEngine.getCourseOfferings();
        for (int j = 0; j < offerings.size(); j++) {
            GRBLinExpr expr = new GRBLinExpr();
            for(int i = 0; i <yij.length; i++){
                expr.addTerm(1.0, yij[i][j]);
            }
            model.addConstr(expr, GRB.LESS_EQUAL,offerings.get(j).getClassSize(),"");
        }
        model.update();
    }

    private static void updateRequestConstraints() throws GRBException {
        List<ScheduleRequest> requests = ProcessingEngine.getAllRecentScheduleRequests();
        for (int i = 0; i < requests.size(); i++){
            Set<Integer> requestCourses = requests.get(i).getRequestedCourses().keySet();
            for (Integer course : requestCourses){
                GRBLinExpr expr = new GRBLinExpr();
                for(Integer courseInstance : ProcessingEngine.getCourseToInstances().get(course)){
                    expr.addTerm(1.0, yij[requests.get(i).getStudentId()][courseInstance]);
                }
                model.addConstr(expr, GRB.LESS_EQUAL,1,""); //A student does not necessarily have to get a course they asked for.
            }
            //And nothing else:
            GRBLinExpr expr = new GRBLinExpr();
            for(Integer course : ProcessingEngine.getCourseToInstances().keySet()){
                if(!requests.get(i).getRequestedCourses().keySet().contains(course)){
                    for(Integer courseInstance : ProcessingEngine.getCourseToInstances().get(course)){
                        expr.addTerm(1.0, yij[requests.get(i).getStudentId()][courseInstance]);
                    }
                }
            }
            model.addConstr(expr, GRB.EQUAL,0,"");
        }
        model.update();
    }

    private static void updatePrerequisitesConstraints() throws GRBException {
     /*Pseudo-code:
      For each student s:
        For each class c:
            For each offering of c, c_i:
                For each prerequisite of c, p:
                    Generate a linear expression with the term yij[s][c_i]
                    For each offering of p earlier than c_i, p_i:
                        add to the expression the term -1*yij[s][p_i]
                    Add to the model the expression as constraint. --> We require ci-sum(pi) <= 0,
                    meaning the student took an instance of each prerequisite for c_i before they
                    took c_i.
    */
         for(int i = 0; i < ProcessingEngine.getPrioritizedStudents().size(); i++){
             for(Integer courseId : ProcessingEngine.getCourseToInstances().keySet()){
                 for(Integer offeringId: ProcessingEngine.getCourseToInstances().get(courseId)){
                     for(Integer prereqCourseId: ProcessingEngine.getCourseToPrerequisites().get(courseId)){
                         GRBLinExpr expr = new GRBLinExpr();
                         expr.addTerm(1.0, yij[i][offeringId]);
                         for(Integer prereqOfferingId: ProcessingEngine.getOfferingToPrerequisiteOfferings().get(offeringId)){
                             if(ProcessingEngine.getOfferingsMap().get(prereqOfferingId).getCourseId() == prereqCourseId){
                                 expr.addTerm(-1.0, yij[i][prereqOfferingId]);
                             }
                         }
                         model.addConstr(expr, GRB.LESS_EQUAL,0,"");
                     }
                 }
             }
         }
         model.update();
    }

    private static void updatePriorityConstraints() throws GRBException {
        List<Student> pStudents = ProcessingEngine.getPrioritizedStudents();
        List<CourseInstance> offerings = ProcessingEngine.getCourseOfferings();
        for (int i = 0; i < pStudents.size(); i++) {
            for (int k = i + 1; k < pStudents.size(); i++) {
                for (int j = 0; j < offerings.size(); j++) {
                    //y_i1o - y_i2o >= 0
                    GRBLinExpr expr = new GRBLinExpr();
                    expr.addTerm(1.0, yij[pStudents.get(i).getId()][offerings.get(j).getId()]);
                    expr.addTerm(-1.0, yij[pStudents.get(k).getId()][offerings.get(j).getId()]);
                    model.addConstr(expr, GRB.GREATER_EQUAL, 0.0, "");
                }
            }
        }
        model.update();
    }


    public static List<ScheduleRequest> processConstraints(){
        List<ScheduleRequest> res = null;
        try {
            initializeOptimizer();
            model.optimize();
            if(model.get(GRB.IntAttr.Status) == GRB.Status.OPTIMAL){
                res = populateResults(ProcessingEngine.getAllRecentScheduleRequests());
            }
            model.dispose();
            env.dispose();
            return res;
        } catch (GRBException e) {
            e.printStackTrace();
            return null;
        }
    }

	private static List<ScheduleRequest> populateResults(List<ScheduleRequest> requests) throws GRBException{
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
    						int courseId = newCourseInstance.getCourseId();
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
