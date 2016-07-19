package arms.api;

public class Student {

	private int id;
	private String firstName;
	private String lastName;
	private float earnedHours;
	private float gpa;
	private String password;
	
	// Constructor
	public Student(int id, String firstName, String lastName, float earnedHours, float gpa, String password ){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.earnedHours = earnedHours;
		this.gpa = gpa;
		this.password = password;
	}
	
	// Getter Methods
	public int getId() {return id;}

	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public float getEarnedHours(){
		return earnedHours;
	}
	
	public float getGpa(){
		return gpa;
	}

	public String getPassword() { return password; }
	
	// Setter Methods

	public void setId(int id) {this.id = id;}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public void setEarnedHours(float hours){
		earnedHours = hours;
	}
	
	public void setGpa(float gpa){
		this.gpa = gpa;
	}

	public void setPassword(String password) { this.password = password;}

}
