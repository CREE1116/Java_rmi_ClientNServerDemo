package studentNCourseDataManagement;


import java.io.BufferedReader;
import Exceptions.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CourseList {
	protected ArrayList<Courses> vCourses;
	private String sCoursesFileName;
	public CourseList(String sCoursesFileName) throws FileNotFoundException, IOException {
		this.sCoursesFileName = sCoursesFileName;
		BufferedReader objStudentFile = new BufferedReader(new FileReader(this.sCoursesFileName));
		this.vCourses = new ArrayList<Courses>();
		while (objStudentFile.ready()) {
			String stuInfo = objStudentFile.readLine();
			if (!stuInfo.equals("")) {
				this.vCourses.add(new Courses(stuInfo));
			}
		}
		objStudentFile.close();
	}

	public ArrayList<Courses> getAllCoursesRecords() throws NullDataException {
		if(this.vCourses.size() == 0) throw new NullDataException("*course Data does not exist*");
		return this.vCourses;
	}
	public void addCourseRecord(String courseInfo) throws SameValueExistsException{
		Courses courses = new Courses(courseInfo);
		checkCourse(courses);
		this.vCourses.add(courses);
	}
	private void checkCourse(Courses courses) throws SameValueExistsException {
		for(Courses courseList:this.vCourses) {
			if(courseList.getCourseId().equals(courses.getCourseId()))
				throw new SameValueExistsException("*same Course ID already exists*");
		}
	}
	
	public void deleteCourseRecord(String CID) throws NullDataException {
		for (Courses courses:this.vCourses) {
			if (courses.match(CID)) {
				if(this.vCourses.remove(courses))return; 
			}
		}
		throw new NullDataException("*The Course "+CID+" was not found*");
	}

	public Courses isRegisteredCourses(String sCID) {
		for (int i = 0; i < this.vCourses.size(); i++) {
			Courses objCourses = (Courses) this.vCourses.get(i);
			if (objCourses.match(sCID)) {
				return objCourses;
			}
		}
		return null;
	}
	public void saveToText() throws IOException {
		 new textWriter(sCoursesFileName,vCourses);
	}

}
