import java.io.FileNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import studentNCourseDataManagement.*;
import Exceptions.*;
import LogManage.*;


public class Data extends UnicastRemoteObject implements DataIF {
	
	protected static StudentList studentList;
	protected static CourseList courseList;
	protected static ReservationList reservationList;
	protected static PasswordList passwordList;
	private static Logger Log;
	private static final long serialVersionUID = 1L;
	protected Data() throws RemoteException{
		super();
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Log = Logger.getLogger(Data.class.getName());
		Handler handler = new FileHandler("/Users/leejongmin/Desktop/StudentMenageMent_Log/DataLog.txt");
		handler.setFormatter(new MyLogFormatter());
		Log.addHandler(handler);
		Clear();
			try {
				Data data = new Data();
				Naming.rebind("Data", data);
				System.out.println("\n"
						+ "888888ba   .d888888  d888888P  .d888888  \n"
						+ "88    `8b d8'    88     88    d8'    88  \n"
						+ "88     88 88aaaaa88a    88    88aaaaa88a \n"
						+ "88     88 88     88     88    88     88  \n"
						+ "88    .8P 88     88     88    88     88  \n"
						+ "8888888P  88     88     dP    88     88  \n"
						+ "ooooooooooooooooooooooooooooooooooooooooo\n"
						+ "                                         \n"
						+ "");
				
				studentList = new StudentList("/Users/leejongmin/eclipse-workspace/Courses&StudentData/Students.txt");
				courseList = new CourseList("/Users/leejongmin/eclipse-workspace/Courses&StudentData/Courses.txt");
				reservationList = new ReservationList("/Users/leejongmin/eclipse-workspace/Courses&StudentData/Reservations.txt");
				passwordList = new PasswordList("/Users/leejongmin/eclipse-workspace/Courses&StudentData/Password.txt");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
// 메소드 시작 
	@Override
	public ArrayList<Student> getAllStudentData() throws RemoteException, NullDataException{
		Log.info("getAllStudentData return");
		return studentList.getAllStudentRecords();
	}
	@Override
	public ArrayList<Courses> getAllCourseData() throws RemoteException, NullDataException{
		Log.info("getAllCourseData return");
		return courseList.getAllCoursesRecords();
	}
	@Override
	public ArrayList<Reservations> getAllReservationsData() throws RemoteException,NullDataException{
		Log.info("getAllReservationsData return");
		return reservationList.getAllReservationsRecords();
	}
	@Override
	public ArrayList<String> getStudentsReservationsRecords(String SID) throws RemoteException,NullDataException{
		Log.info("getStudentsReservationsRecords return");
		return reservationList.getStudentsReservationsRecords(SID);
	}
	@Override
	public Student getStudentById(String SID) throws RemoteException{
		Log.info("getStudentById return");
		return studentList.isRegisteredStudent(SID);
	}
	@Override
	public Courses getCourseById(String CID) throws RemoteException{
		Log.info("getStudentById return");
		return courseList.isRegisteredCourses(CID);
	}
	@Override
	public void addStudent(String studentInfo) throws RemoteException, SameValueExistsException {
		Log.info("addStudent return");
		studentList.addStudentRecord(studentInfo);
	}
	@Override
	public void addCourse(String courseInfo) throws RemoteException, SameValueExistsException {
		Log.info("addCourse return");
		courseList.addCourseRecord(courseInfo);
	}
	@Override
	public void addReservations(String reservationInfo) throws RemoteException, SameValueExistsException {
		Log.info("addReservations return");
		reservationList.addReservationsRecord(reservationInfo);
	}
	@Override
	public void addPassword(String SID, String Password) throws SameValueExistsException , RemoteException{
		Log.info("addPassword return");
		passwordList.addPassword(SID, Password);
	}
	@Override
	public void deleteStudent(String SID) throws RemoteException, NullDataException {
		Log.info("deleteStudent return");
		studentList.deleteStudentRecord(SID);
	}
	@Override
	public void deleteCourse(String CID) throws RemoteException, NullDataException {
		Log.info("deleteCourse return");
		courseList.deleteCourseRecord(CID);
	}
	@Override
	public void deleteReservations(String SID, String CID) throws RemoteException, NullDataException {
		Log.info("deleteReservations return");
		reservationList.deleteReservationRecord(SID, CID);
	}
	@Override
	public void deletePassword(String SID)throws RemoteException, NullDataException {
		Log.info("deleteReservations return");
		passwordList.deletePassowrd(SID);
	}
	public boolean checkPassword(String SID, String Password) throws RemoteException{
		Log.info("checkPassword return");
		return passwordList.checkPassword(SID, Password);
	}
	// private method! only use in Data Class
	private static void Clear() {
		System.out.print("\033\143");
		System.out.flush();
	}
	@Override
	public void saveToText() throws IOException ,RemoteException{
		Log.info("saveToText");
		reservationList.saveToText();
		studentList.saveToText();
		courseList.saveToText();
		passwordList.saveToText();
		
	}
	@Override
	public void deleteReservationsByStudentId(String SID) throws RemoteException, NullDataException {
		Log.info("deleteReservationsByStudentId");
		reservationList.deleteReservationByStudentId(SID);
		
	}
	@Override
	public void deleteReservationsByCourseId(String CID) throws RemoteException, NullDataException {
		Log.info("deleteReservationsByCourseId");
		reservationList.deleteReservationByCourseId(CID);
		
	}


}
