package arms.api;

public class Semester {

	private int id;
	private String semesterName;
	
	// Constructor
	public Semester(int id, String semesterName ){
		this.id = id;
		this.semesterName = semesterName;
	}
	
	// Getter Methods
	public int getId() {return id;}

	public String getSemesterName(){
		return semesterName;
	}
	
	// Setter Methods

	public void setId(int id) {this.id = id;}

	public void setSemesterName(String semesterName){
		this.semesterName = semesterName;
	}

}