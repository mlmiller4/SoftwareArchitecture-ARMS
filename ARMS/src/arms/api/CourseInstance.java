package arms.api;

import java.util.List;

public class CourseInstance {
    private int id;
    private int courseId;
    private String courseName;
    private String semester;
    private int classSize;
    private int remSeats;
    private List<String> prerequisits;

    public CourseInstance(int id, int courseId, String courseName, String semester, int classSize, int remSeats,
                          List<String> prerequisits){
        this.id = id;
        this.courseId = courseId;
        this.courseName = courseName;
        this.semester = semester;
        this.classSize = classSize;
        this.remSeats = remSeats;
        this.prerequisits = prerequisits;
    }

    //Getters
    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }
    
    public String getCourseName() {
        return courseName;
    }

    public String getSemester() {
        return semester;
    }

    public int getClassSize() {
        return classSize;
    }

    public int getRemSeats() {
        return remSeats;
    }

    public List<String> getPrerequisits() {
        return prerequisits;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCourseId( int courseId) {
    	this.courseId = courseId;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setClassSize(int classSize) {
        this.classSize = classSize;
    }

    public void setRemSeats(int remSeats) {
        this.remSeats = remSeats;
    }

    public void setPrerequisits(List<String> prerequisits) {
        this.prerequisits = prerequisits;
    }
}