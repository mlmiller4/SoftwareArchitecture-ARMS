package arms;

public class Student {
	
	private int StudentID;
	private String firstName;
	private String lastName;
	private float earnedHours;
	private float GPA;
	
	// Constructor
	public Student(int ID, String name1, String name2, float hours, float gpa ){
		StudentID = ID;
		firstName = name1;
		lastName = name2;
		earnedHours = hours;
		GPA = gpa;
	}
	
	// Getter Methods
	public int getStudentID(){
		return StudentID;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public float getEarnedHours(){
		return earnedHours;
	}
	
	public float getGPA(){
		return GPA;
	}
	
	// Setter Methods
	public void setStudentID(int ID){
		StudentID = ID;
	}
	
	public void setEarnedHours(float hours){
		earnedHours = hours;
	}
	
	public void setGPA(float gpa){
		GPA = gpa;
	}

}
