package arms.api;

public class Student implements Comparable<Student>{

	private int id;
	private String firstName;
	private String lastName;
	private float earnedHours;
	private float gpa;
	private String password;
	private String userName;
	
	// Add counts for # of next semester, future semester, or none
	private int coursesNextSemester;
	private int coursesFutureSemester;
	private int coursesNoSemester;
	
	// Constructor
	public Student(int id, String firstName, String lastName, float earnedHours, float gpa, String password, String userName ){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.earnedHours = earnedHours;
		this.gpa = gpa;
		this.password = password;
		this.userName = userName;
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
	
	public String getUserName() { return userName; }
	
	public int getCoursesNextSemester()
	{
		return coursesNextSemester;
	}
	
	public int getCoursesFutureSemester()
	{
		return coursesFutureSemester;
	}
	
	public int getCoursesNoSemester()
	{
		return coursesNoSemester;
	}
	
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
	
	public void setUserName(String userName) { this.userName = userName;}

	public int compareTo(Student student) {
		float theirRank = student.getGpa() * student.getEarnedHours();
		float myRank = this.getGpa() * this.getEarnedHours();
		return myRank > theirRank ? -1: myRank < theirRank? 1 :0; //Guarantees a descending-order sort.
	}
	
	public void setCoursesNextSemester(int coursesNextSemester)
	{
		this.coursesNextSemester = coursesNextSemester;
	}

	public void setCoursesFutureSemester(int coursesFutureSemester)
	{
		this.coursesFutureSemester = coursesFutureSemester;
	}
	
	public void setCoursesNoSemester(int coursesNoSemester)
	{
		this.coursesNoSemester = coursesNoSemester;
	}
}
