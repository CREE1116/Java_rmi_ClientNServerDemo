package studentNCourseDataManagement;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Courses implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String courseID = null;
    protected String Advisor = null;
    protected String courseName = null;
    protected ArrayList<String> PrerequisitesList = null;

    public Courses(String inputString) {
    	try {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);
    	this.courseID = stringTokenizer.nextToken();
    	this.Advisor = stringTokenizer.nextToken();
    	this.courseName = stringTokenizer.nextToken();
    	this.PrerequisitesList = new ArrayList<String>();
    	while (stringTokenizer.hasMoreTokens()) {
    		this.PrerequisitesList.add(stringTokenizer.nextToken());
    	}
    		}catch(NoSuchElementException e) {
    		
    	}
    }
    public boolean match(String courseID) {
        return this.courseID.equals(courseID);
    }
    public String getAdvisor() {
        return this.Advisor;
    }
    public String getCourseId() {
    	return this.courseID;
    }
    public String getCourseName() {
    	return courseName;
    }
    public ArrayList<String> getPrerequisitesList() {
        return this.PrerequisitesList;
    }
    public String getPrerequisitesListString() {
    	if(this.PrerequisitesList == null)return null;
    	String temp ="";
    	for(String a:this.PrerequisitesList) {
    		temp =temp+" "+a;
    	}
    	return temp;
    }
    public String toString() {
        String stringReturn = this.courseID + " " + this.Advisor + " " + this.courseName+" ";
        for (int i = 0; i < this.PrerequisitesList.size(); i++) {
            stringReturn = stringReturn + " " + this.PrerequisitesList.get(i).toString();
        }
        return stringReturn;
    }
}

