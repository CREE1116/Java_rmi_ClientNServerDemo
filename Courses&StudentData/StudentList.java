package studentNCourseDataManagement;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import Exceptions.*;

public class StudentList {
	protected ArrayList<Student> vStudent;
	private String sStudentFileName;
	public StudentList(String sStudentFileName) throws FileNotFoundException, IOException {
		this.sStudentFileName = sStudentFileName;
		BufferedReader objStudentFile = new BufferedReader(new FileReader(this.sStudentFileName));
		this.vStudent = new ArrayList<Student>();
		while (objStudentFile.ready()) {
			String stuInfo = objStudentFile.readLine();
			if (!stuInfo.equals("")) {
				this.vStudent.add(new Student(stuInfo));
			}
		}
		objStudentFile.close();
	}

	public ArrayList<Student> getAllStudentRecords() throws NullDataException {
		if(this.vStudent.size() == 0) throw new NullDataException("*student Data does not exist*");
		return this.vStudent;
	}
	
	public boolean addStudentRecord(String studentInfo)throws SameValueExistsException {
		Student student = new Student(studentInfo);
		checkStudent(student);
		if(this.vStudent.add(student))return true;
		else return false;
	}
	private void checkStudent(Student student) throws SameValueExistsException {
		for(Student studentList:this.vStudent) {
			if(studentList.getStudentId().equals(student.getStudentId()))
				throw new SameValueExistsException("*same student ID already exists*");
		}
	}
	public void deleteStudentRecord(String SID) throws NullDataException {
		for (Student student:this.vStudent) {
			if (student.match(SID)) { // Exception넣
				if(this.vStudent.remove(student))return; // 지우는중 에러 
			}
		}
		throw new NullDataException("*The student "+SID+" was not found*");
	}
	
	public Student isRegisteredStudent(String sSID) {
		for (Student student:this.vStudent) {
			if (student.match(sSID)) {
				return student;
			}
		}
		return null;
	}
	public void saveToText() throws IOException {
		new textWriter(sStudentFileName,vStudent);
	}

}
