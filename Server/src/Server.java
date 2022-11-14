import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import studentNCourseDataManagement.*;
import Exceptions.*;
import LogManage.*;

public class Server extends UnicastRemoteObject implements ServerIF {
	
	private static DataIF data;
	private static final long serialVersionUID = 1L;
	private ArrayList<Courses> tempCourseList = new ArrayList<Courses>();
	private static Logger Log;
	private static String UserId = null;
	private static MyLogFormatter LogFormatter;
	private String logInToken = "";
	protected Server() throws RemoteException{
		super();
	}
	public static void main(String[] args) throws AlreadyBoundException, NotBoundException, IOException {
		Log = Logger.getLogger(Server.class.getName());
		Handler handler = new FileHandler("/Users/leejongmin/Desktop/StudentMenageMent_Log/ServerLog.txt");
		LogFormatter = new MyLogFormatter();
		handler.setFormatter(LogFormatter);
		Log.addHandler(handler);
		Clear();
		try {
			Server server = new Server();
			Naming.bind("Server", server);
			System.out.println("\n"
					+ ".d88888b   88888888b  888888ba  dP     dP  88888888b  888888ba  \n"
					+ "88.    \"'  88         88    `8b 88     88  88         88    `8b \n"
					+ "`Y88888b. a88aaaa    a88aaaa8P' 88    .8P a88aaaa    a88aaaa8P' \n"
					+ "      `8b  88         88   `8b. 88    d8'  88         88   `8b. \n"
					+ "d8'   .8P  88         88     88 88  .d8P   88         88     88 \n"
					+ " Y88888P   88888888P  dP     dP 888888'    88888888P  dP     dP \n"
					+ "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo\n"
					+ "                                                                \n"
					+ "");
			
			data = (DataIF)Naming.lookup("Data");
		}
		catch(RemoteException e){
			System.out.println("server is fail");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public ArrayList<Student> getAllStudentData(String Token) throws RemoteException, NullDataException, IOException ,UserTokenDifferenceException {
		tokenChecker(Token);
		Log.info("getAllStudentData return");
		return data.getAllStudentData();
	}
	@Override
	public ArrayList<Courses> getAllCourseData(String Token) throws RemoteException, NullDataException,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		Log.info("getAllCourseData return");
		return data.getAllCourseData();
	}
	@Override
	public ArrayList<Reservations> getAllReservationsData(String Token) throws RemoteException, NullDataException,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		Log.info("getAllReservationsData return");
		return data.getAllReservationsData();
	}
	@Override
	/**
	 * 서버의 저장된 아이디의 학생이 수강 가능한 코스만 리턴한다.
	 * @param SID
	 * @return courseList
	 * @throws RemoteException
	 * @throws NullDataException
	 */
	public ArrayList<Courses> getAvailableCourseList(String Token) throws RemoteException, NullDataException ,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		Log.info("getAvailableCourseList");
		ArrayList<Courses> studentsCourseList = new ArrayList<Courses>();
		ArrayList<Courses> FinalCourseList = new ArrayList<Courses>();
		ArrayList<String> ReservationList;
		try { ReservationList = data.getStudentsReservationsRecords(UserId);
		}catch(NullDataException e) {
			Log.fine("at getAvailableCourseList/getStudentsReservationsRecords");
			ReservationList = new ArrayList<String>();}
		Student student = data.getStudentById(UserId);
		ArrayList<String> studentCompletedCourseId = student.getCompletedCourses();
		for(Courses course : data.getAllCourseData()) {
			if(!ReservationList.contains(course.getCourseId()))
				studentsCourseList.add(course);}
		for(Courses course : studentsCourseList) {
			if(course.getPrerequisitesList().size()==0)
				FinalCourseList.add(course);
			else if(studentCompletedCourseId.containsAll(course.getPrerequisitesList()))
				FinalCourseList.add(course);}
		this.tempCourseList = FinalCourseList;
		System.out.println("\n\n\n");
		return FinalCourseList;
	}
	
	 /**
	  * 서버에 저장된 학생의 수강완료 리스트를 리턴한다.
	  * @return courseList
	  * @throws RemoteException
	  * @throws NullDataException
	  */
	@Override
	 public ArrayList<Courses> getStudentsCourseList(String Token) throws RemoteException, NullDataException,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		Log.info("getStudentsCourseList");
		 ArrayList<String> CourseIDList =  data.getStudentById(Server.UserId).getCompletedCourses();
		 ArrayList<Courses> CourseDataList = new ArrayList<Courses>();
		 for(String CourseId : CourseIDList) {
			 CourseDataList.add(data.getCourseById(CourseId));
		 }
		 return CourseDataList;
	 }
	@Override
	public Student getStudentById(String SID,String Token) throws RemoteException,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		Log.info("getStudentById return");
		return data.getStudentById(SID);
	}
	@Override
	public Courses getCourseById(String CID,String Token) throws RemoteException ,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		Log.info("getCourseById return");
		return data.getCourseById(CID);
	}
	@Override
	public String getCurentUserId(String Token) throws NullDataException,RemoteException,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		Log.info("getCurentUserId");
		if(Server.UserId != null) return Server.UserId;
		else throw new NullDataException("로그인 안됨 ");
	}
	@Override
	public void addStudent(String studentInfo,String Token) throws RemoteException, SameValueExistsException ,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		Log.info("addStudent return");
		data.addStudent(studentInfo);
	}
	@Override
	public void addCourse(String courseInfo,String Token) throws RemoteException, SameValueExistsException ,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		Log.info("addCourse return");
		data.addCourse(courseInfo);
	}	
	@Override
	 /**
	  * 수강신청할 course ID를 받아 학번과 함께 reservationList에 저장한다.
	  * @param CID
	  * @throws RemoteException
	  * @throws NullDataException
	  * @throws SameValueExistsException
	  * @throws InvalidInputException
	  */
	public void makeReservation(String CID,String Token) throws RemoteException, NullDataException, SameValueExistsException, InvalidInputException ,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		Log.info(" makeReservation return");
		Log.info(" SID: "+Server.UserId+" CID: "+CID);
		if(!contains(tempCourseList,CID)) throw new InvalidInputException("*Must complete required prerequisite courses*");
			data.addReservations(Server.UserId+" "+CID);//1
	}
	@Override
	public void addPassowrd(String SID, String Password,String Token) throws RemoteException, SameValueExistsException ,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		Log.info("addPassword");
		data.addPassword(SID, Password);
	}
	@Override
	public void deleteStudent(String SID,String Token) throws RemoteException, NullDataException ,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		if(this.UserId.equals(SID))
		Log.info("deleteStudent return");
		try {
			data.deleteReservationsByStudentId(SID);
		}catch(NullDataException e) {
			Log.info("deleteStudent-Related Reservation is null: it's fine Exceptioin\n\t\t"+e.getLogMessage());
		}
		data.deleteStudent(SID);
		data.deletePassword(SID);
	}
	@Override
	public void deleteCourse(String CID,String Token) throws RemoteException, NullDataException ,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		try {
			data.deleteReservationsByCourseId(CID);
		}catch(NullDataException e) {
			Log.info("deleteCourse-Related Reservation is null: it's fine Exceptioin\n\t\t"+e.getLogMessage());
		}
		Log.info("deleteCourse return");
		data.deleteCourse(CID);
	}
	@Override
	public void deleteReservation(String CID, String Token)throws RemoteException, NullDataException,IOException,UserTokenDifferenceException{
		tokenChecker(Token);
		Log.info("deleteReservation return");
		data.deleteReservations(UserId, CID);
	}
	@Override
	public String checkPassword(String SID, String Password) throws RemoteException,IOException{
		 // data.checkPassword를 호출해 확인한다. 과정에서 서버에 현제 유저의 id를 저장한다.
		Log.info("checkPassword return, User: ["+SID+"]");
		if(data.checkPassword(SID, Password)) {
			Server.UserId = SID;
			LogFormatter.setUserId(SID);
	//		logFormatter.setUserId(UserId);
			Log.info("Login Success");
			return IDencoder(SID);
		}
			Log.info("Login Fail");
			return null;
	}
	@Override
	public void logOut() throws RemoteException ,IOException{
		Log.info("User "+Server.UserId+" logOut");
		this.logInToken = null;
		Server.UserId = null;
	}
	@Override
	public void saveToText() throws RemoteException ,IOException{
		Log.info("save!");
		data.saveToText();
	}
	public ArrayList<Reservations> getReservationByStudentId(String Token) throws RemoteException, NullDataException, UserTokenDifferenceException{
		tokenChecker(Token);
		ArrayList<Reservations> reservationData = new ArrayList<Reservations>();
		for(Reservations reservation : data.getAllReservationsData()) {
			if(reservation.matchStudentId(UserId))reservationData.add(reservation);
		}
		return reservationData;
	}
// private method! only use in server Class
	private static void Clear() {
		System.out.print("\033\143");
		System.out.flush();
	}
	
	private boolean contains(ArrayList<Courses> arrayList,String string) { 
		for(Courses a : arrayList) {
			if(a.match(string))return true;
		}
		return false;
	}
	private String IDencoder(String UserId) {
		AES256 aes256 = new AES256();
		try {logInToken = aes256.encrypt(UserId+aes256.getRandomStr(6));
			return logInToken;
		} catch (Exception e) {
			StackTraceElement[] ste = e.getStackTrace();
			Log.warning("RemoteException! [ "+e.getMessage()+" ] line"+ ste[0].getLineNumber());
			return null;
		}
	}
	/**
	 * 토큰이 같지 않으면 익셉션 발생 
	 * @param Token
	 * @throws UserTokenDifferenceException
	 */
	private void tokenChecker(String Token) throws UserTokenDifferenceException{
		if(Token.length() != 24 || logInToken == null)throw new UserTokenDifferenceException("Token values ​​does not match");
		try { 	
			if(!Token.equals(logInToken)) {
				throw new UserTokenDifferenceException("Token values ​​does not match");
			}
		} catch (Exception e) {
			StackTraceElement[] ste = e.getStackTrace();
			Log.warning("Exception! [ "+e.getMessage()+" ] line"+ ste[0].getLineNumber());
			throw new UserTokenDifferenceException("Token values ​​does not match ->\n\t"+e.getMessage());
		}
	}
	

}
