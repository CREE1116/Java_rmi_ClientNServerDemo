package studentNCourseDataManagement;

import java.io.Serializable;
import java.util.StringTokenizer;

public class Password implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected String StudentId;
	protected String Password;
	

	public Password(String inputString) {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);
    	this.StudentId = stringTokenizer.nextToken();
    	this.Password = stringTokenizer.nextToken();
    }
	
	public boolean PasswordCheck(String SID, String Password) {
		if(this.StudentId.equals(SID) && this.Password.equals(Password)) return true;
		else return false;
	}
	public String getStudentId() {
		return this.StudentId;
	}

	public String toString() {
		return StudentId+" "+Password;
	}
}
