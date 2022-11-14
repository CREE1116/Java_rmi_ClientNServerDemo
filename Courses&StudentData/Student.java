package studentNCourseDataManagement;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Student implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String studentId = null;
    protected String lastName = null;
    protected String firstName = null;
    protected String department = null;
    protected ArrayList<String> completedCoursesList = new ArrayList<String>();;

    public Student(String inputString) {
    	try{
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);
    	this.studentId = stringTokenizer.nextToken();
    	this.lastName = stringTokenizer.nextToken();
    	this.firstName = stringTokenizer.nextToken();
    	this.department = stringTokenizer.nextToken();
    	while (stringTokenizer.hasMoreTokens()) {
    		this.completedCoursesList.add(stringTokenizer.nextToken());
    	}
    	}catch(NoSuchElementException e) {
    		
    	}
    }
    public boolean match(String studentId) {
        return this.studentId.equals(studentId);
    }
    public String getName() {
        return this.lastName+" "+this.firstName;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getDepartment() {
        return this.department;
    }
    public String getStudentId() {
    	return this.studentId;
    }
    public ArrayList<String> getCompletedCourses() {
        return this.completedCoursesList;
    }
    public String getCompletedCoursesString() {
    	if(this.completedCoursesList.size() == 0)return null;
    	String temp ="";
    	for(String a:this.completedCoursesList) {
    		temp =temp+" "+a;
    	}
    	return temp;
    }
    public String toString() {
        String stringReturn = this.studentId + " " + this.lastName+" "+this.firstName + " " + this.department+"";
        for (int i = 0; i < this.completedCoursesList.size(); i++) {
            stringReturn = stringReturn +" "+ this.completedCoursesList.get(i).toString();
        }
        return stringReturn;
    }
}
