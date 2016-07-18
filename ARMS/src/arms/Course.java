package arms;

public class Course {

	private int courseID;
	private String courseNumber;
	private String courseTitle;
	private String semesters;
	private int classSize;
	private int remSeats;
	
	// Constructor
	public Course(int ID, String cNumber, String cTitle, String sems, int size, int seats){
		courseID = ID;
		courseNumber = cNumber;
		courseTitle = cTitle;
		semesters = sems;
		classSize = size;
		remSeats = seats;
	}
	
	// Getter Methods
	public int getCourseID(){
		return courseID;
	}
	
	public String getCourseNumber(){
		return courseNumber;
	}
	
	public String getCourseTitle(){
		return courseTitle;
	}
	
	public String getSemesters(){
		return semesters;
	}
	
	public int getClassSize(){
		return classSize;
	}
	
	public int getRemainingSeats(){
		return remSeats;
	}
	
	
	// Setter Methods
	public void setClassSize(int newSize){
		classSize = newSize;
	}
	
	public void setRemainingSeats(int newRemSeats){
		remSeats = newRemSeats;
	}
	
	
	
	
}
