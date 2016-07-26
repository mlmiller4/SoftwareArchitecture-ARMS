package arms.api;

import arms.ProcessingEngine.ProcessingEngine;
import arms.dataAccess.DbActions;

import java.util.List;

public class API {
    public void submitRequest(ScheduleRequest request){
        if(request == null) return;
        ProcessingEngine.updateScheduleRequest(request);
        ProcessingEngine.executeRequest();
    }
    public List<CourseInstance> getCatalogFromDb(){
        return DbActions.getCatalog();
    }
    public static List<ScheduleRequest> getRecentScheduleRequestsFromMem(){
        return ProcessingEngine.getAllRecentScheduleRequests();
    }
    public void activateShadowMode(){
        ProcessingEngine.activateShadowMode();
    }
    public void deactivateDemoMode(){
        ProcessingEngine.deactivateShadowMode();
    }
    public List<ScheduleRequest> getRecentScheduleRequestsFromDb(){
        return DbActions.getAllRecentScheduleRequests();
    }
    public List<ScheduleRequest> getAllScheduleRequestsFromDb(){
        return DbActions.getAllScheduleRequests();
    }
    public void updateStudentList(){
    	ProcessingEngine.setUpdatedStudentList();
    }
}
