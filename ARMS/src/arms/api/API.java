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
    public List<CourseInstance> getCatalog(){
        return DbActions.getCatalog();
    }
}
