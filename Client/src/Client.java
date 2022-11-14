import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import studentNCourseDataManagement.*;
import Exceptions.*;
import Exceptions.MyException;
import LogManage.*;



public class Client {
	
	private static String logInToken = "";
	private static Logger Log;
	private static MyLogFormatter logFormatter;
	
	public static void main(String[]args) throws NotBoundException, IOException {
		Clear();
		ServerIF server;
		BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
		//logging ready
		Log = Logger.getLogger(Client.class.getName());
		Handler handler = new FileHandler("/Users/leejongmin/Desktop/StudentMenageMent_Log/ClientLog.txt");
		logFormatter = new MyLogFormatter();
		handler.setFormatter(logFormatter);
		Log.addHandler(handler);
        // rmi lookup
		server = (ServerIF)Naming.lookup("Server");
		//start
		service(server, userInputReader);
	}
	private static void service( ServerIF server, BufferedReader userInputReader) throws IOException, RemoteException, NotBoundException {
		logIn(server, userInputReader);
		Log.info("Login");
		topMenu(server, userInputReader);
	}
	private static void topMenu(ServerIF server,BufferedReader userInputReader) throws IOException, NotBoundException {
		try {
			while(true) {
				Clear();
				System.out.println("Current User is "+server.getCurentUserId(logInToken));
				showTopMenu();
				String selectedMenu = userInputReader.readLine().trim();
				Clear();
				switch(selectedMenu) {
				case "1": studentMenu(server, userInputReader);
					break;
				case "2": courseMenu(server, userInputReader);
					break;
				case "3": reservationMenu(server, userInputReader);
					break;
				case "4": TokenTampering(userInputReader);
					break;
				case "0": Log.info("LogOut");
						 logOut(server,userInputReader);
					break;
				case "X":Log.info("EXIT");
						EXIT(server);
					break;
				case "x": Log.info("EXIT");
						 EXIT(server);
					break;
				default: System.out.println("Wrong input");
					break;
				}
				Clear();
			}
		}catch (RemoteException e) {
			StackTraceElement[] ste = e.getStackTrace();
			Log.warning("RemoteException! [ "+e.getMessage()+" ] line"+ ste[0].getLineNumber());
			e.printStackTrace();
		}catch(UserTokenDifferenceException e) {
			Log.warning(e.getLogMessage());
			System.out.println("Soon Log out");
			WateSec(2);
			try { logOut(server,userInputReader);
			} catch (NullDataException | NotBoundException | IOException e1) {
				e1.printStackTrace();
			}
		}catch(Exception e) {
			StackTraceElement[] ste = e.getStackTrace();
			Log.warning("RemoteException! [ "+e.getMessage()+" ] line"+ ste[0].getLineNumber());
			e.printStackTrace();
			}
		finally {
			FinalExceptionHandling(server, userInputReader);
		}
	}
	private static void TokenTampering(BufferedReader userInputReader) throws IOException, InvalidInputException {
		Clear();
		System.out.println("Current Token is: "+logInToken);
		System.out.print("new Token?: ");
		String newToken = userInputReader.readLine().trim();
		if(newToken.length() == 0)throw new InvalidInputException("ok safe");
		logInToken = newToken;
	}
	private static void studentMenu( ServerIF server,BufferedReader userInputReader) throws IOException, NotBoundException, NullDataException, UserTokenDifferenceException, SameValueExistsException, InvalidInputException {
		try {
		while(true) {
			Clear();
			System.out.println("Current User is "+server.getCurentUserId(logInToken));
			showStudentMenu();
			String selectedMenu = userInputReader.readLine().trim();
			Clear();
			switch(selectedMenu) {
				case "1": Log.info("list student");
					Clear();
					showData(server.getAllStudentData(logInToken));
					Delay(userInputReader);
					break;
				case "2": Log.info("Add Student");
					Clear();
					addStudentByStudentInfo(server,userInputReader,"");	
					break;
				case "3": Log.info("Delete Student");
					Clear();
					deleteStudentById(server, userInputReader);
					break;
				case "4": Log.info("List completed courses");
					Clear();
			 		listCompletedCoursesByID(server);
			 		Delay(userInputReader);
					break;
				case "=": topMenu(server, userInputReader);
					break;
				case "0": Log.info("LogOut");
					Clear();
					logOut(server,userInputReader);
					break;
				case "X": Log.info("EXIT");
					EXIT(server);
					break;
				case "x": Log.info("EXIT");
					EXIT(server);
					break;
				default: System.out.println("Wrong input");
					break;
				}
			}
	}catch (NullDataException |SameValueExistsException e) {
		if(!MyExceptionHandler(userInputReader,e,true))
			studentMenu(server,userInputReader);
		}
	}
	private static void courseMenu( ServerIF server,
			BufferedReader userInputReader) throws IOException, NotBoundException, NullDataException, UserTokenDifferenceException, SameValueExistsException, InvalidInputException {
		try {
		while(true) {
				Clear();
				System.out.println("Current User is "+server.getCurentUserId(logInToken));
				showCourseMenu();
				String selectedMenu = userInputReader.readLine().trim();
				Clear();
				switch(selectedMenu) {
				case "1": Log.info("List courses");
					Clear();
					showData(server.getAllCourseData(logInToken));
					Delay(userInputReader);
					break;
				case "2": Log.info("Add courses");
					Clear();
					addCourseByCourseInfo(server,userInputReader,"");	
					break;
				case "3": Log.info("Delete courses");
					Clear();
					deleteCourseById(server, userInputReader);	
					break;
				case "=": topMenu(server,userInputReader);
					break;
				case "0": Log.info("LogOut");
					Clear();
					logOut(server,userInputReader);
					break;
				case "X": Log.info("EXIT");
					EXIT(server);
					break;
				case "x": Log.info("EXIT");
					EXIT(server);
					break;
				default: System.out.println("Wrong input");
					break;
				}
			}
		}catch (NullDataException |SameValueExistsException e) {
			if(!MyExceptionHandler(userInputReader,e,true))
				courseMenu(server,userInputReader);
			}
		}
	private static void reservationMenu(ServerIF server,
			BufferedReader userInputReader) throws IOException, NotBoundException, NullDataException, UserTokenDifferenceException, SameValueExistsException {
		try {
		while(true) {
				Clear();
				System.out.println("Current User is "+server.getCurentUserId(logInToken));
				showReservationMenu();
				String selectedMenu = userInputReader.readLine().trim();
				Clear();
				switch(selectedMenu) {
				case "1": Log.info("Make Reservation");
					Clear();
					makeReservation(server,userInputReader);
					break;
				case "2":Log.info("List Reservation");
					Clear();
					showData(server.getAllReservationsData(logInToken));
					Delay(userInputReader);
					break;
				case "3":Log.info("deleteReservation");
					Clear();
					deleteReservation(server,userInputReader);
					break;
				case "=": topMenu(server, userInputReader);
					break;
				case "0": Log.info("LogOut");
					Clear();
					logOut(server,userInputReader);
				case "X": Log.info("EXIT");
					EXIT(server);
					break;
				case "x": Log.info("EXIT");
					EXIT(server);
					break;
				default: System.out.println("Wrong input");
					break;
				}
				Clear();
			}
		}catch (NullDataException e) {
			if(!MyExceptionHandler(userInputReader,e,true))
				reservationMenu(server,userInputReader);
			}
		}
	/**
	 * 서버에 로그아웃 명령 후 로그인 전으로 클라이언트 되돌림 
	 * @param server
	 * @param args
	 * @throws NotBoundException
	 * @throws IOException
	 * @throws NullDataException
	 * @throws UserTokenDifferenceException 
	 */
	private static void logOut(ServerIF server,BufferedReader userInputReader) throws NotBoundException, IOException, NullDataException {
		Clear();
		server.logOut();
		logInToken = null;
		service(server, userInputReader);
	}
	/**
	 * 서버에 로그인 정보를 보내고 토큰을 받아와서 저장함, 로그에 남을 학생 이름 지정
	 * @param server
	 * @param userInputReader
	 * @throws IOException
	 */
	private static void logIn(ServerIF server, BufferedReader userInputReader) throws IOException {
		while(true) {
			System.out.print("enter studentId: ");String studentId = userInputReader.readLine().trim();
			System.out.print("enter Password: ");String password = userInputReader.readLine().trim();
			Clear();
			String Token = server.checkPassword(studentId,password);
			if(Token!=null) {
				logInToken = Token;
				logFormatter.setUserId(studentId);
				return ;
			}else System.out.println("Login fail");
		}
	}
	private static void FinalExceptionHandling( ServerIF server, BufferedReader userInputReader) throws IOException, NotBoundException{
		System.out.println("\nPress enter to go back");
		userInputReader.readLine().trim();
		topMenu(server, userInputReader);
	}
	/**
	 * 로그 남기고, 메시지 표시하고 이후 입력값에 따라 true false반
	 * @param userInputReader
	 * @param e
	 * @param Menus
	 * @return boolean
	 * @throws IOException
	 */
	private static boolean MyExceptionHandler(BufferedReader userInputReader,MyException e,boolean Menus) throws IOException {
		Log.warning(e.getLogMessage());
		Clear();
		System.out.println(e.getMessage());
		if(Menus)System.out.println("\ngo TopMenu: M/m\nGo back:any key");
		else System.out.println("\ngo Menu: M/m\nDo again:any key");
		String userInput = userInputReader.readLine().trim();
		Clear();
		return (userInput.equals("m")||userInput.equals("M"));
	}
	
	private static void  listCompletedCoursesByID(ServerIF server) throws IOException, RemoteException, NullDataException, UserTokenDifferenceException {
		System.out.println("complete course: ");
		showData(server.getStudentsCourseList(logInToken));
	}
	/**
	 * 학생을 지우면 관련 수강신청, 비밀번호도 같이 삭제
	 * @param server
	 * @param userInputReader
	 * @throws RemoteException
	 * @throws IOException
	 * @throws UserTokenDifferenceException
	 * @throws NotBoundException
	 */
	private static void deleteStudentById(ServerIF server, BufferedReader userInputReader) throws RemoteException, IOException, UserTokenDifferenceException, NotBoundException {
		try {
		System.out.print("enter Student Id: ");
		String studentId = userInputReader.readLine().trim();
		if(studentId.length()!=8)throw new InvalidInputException("Student ID must be 8 characters");
		if(!isNumeric(studentId))throw new InvalidInputException("Student ID must be a number");
		server.deleteStudent(studentId,logInToken);
		System.out.println("deleteSuccess!");
		if(studentId.equals(server.getCurentUserId(logInToken))) {
			System.out.println("The user in use has been deleted, so log out!");
			WateSec(2);
			logOut(server,userInputReader);
		}
		}catch (NullDataException|InvalidInputException e) {
			if(!MyExceptionHandler(userInputReader,e,false))
				deleteStudentById(server,userInputReader);
			}
	}
	/**
	 * @param server
	 * @param userInputReader
	 * @throws RemoteException
	 * @throws IOException
	 * @throws UserTokenDifferenceException
	 */
	private static void deleteCourseById(ServerIF server, BufferedReader userInputReader) throws RemoteException, IOException, UserTokenDifferenceException {
		try {
		System.out.print("enter Course Id: ");
		String CourseId = userInputReader.readLine().trim();
		if(CourseId.length()!=5)throw new InvalidInputException("Course ID must be 5 characters");
		if(!isNumeric(CourseId))throw new InvalidInputException("Course ID must be a number");
		server.deleteCourse(CourseId,logInToken);
		System.out.println("deleteSuccess!");
		WateSec(1);
		}catch (NullDataException | InvalidInputException e) {
			if(!MyExceptionHandler(userInputReader,e,false))
				deleteCourseById(server,userInputReader);
			}
	}
	/**
	 * 지금 유저의 수강신청 리스트를 보여주고 지우고자 하는 코스id를 받아 지움
	 * @param server
	 * @param userInputReader
	 * @throws RemoteException
	 * @throws NullDataException
	 * @throws IOException
	 * @throws UserTokenDifferenceException
	 */
	private static void deleteReservation(ServerIF server, BufferedReader userInputReader) throws RemoteException, NullDataException, IOException, UserTokenDifferenceException {
		try {
		System.out.println("User: "+server.getCurentUserId(logInToken)+"\n Users Reservation:\n");
		showData(server.getReservationByStudentId(logInToken));
		System.out.print("\nenter Course Id: ");
		String deleteCid = userInputReader.readLine().trim();
		if(deleteCid.length() != 5)throw new InvalidInputException("Course ID must be 5 characters");
		server.deleteReservation(deleteCid,logInToken);
		System.out.println("deleteSuccess!");
		WateSec(1);
		}catch(InvalidInputException e) {
			if(!MyExceptionHandler(userInputReader, e,false))
				deleteReservation(server,userInputReader);
		}
	}
	/**
	 * 익셉션 발생시 정보를 받아와 전에 틩긴 부분부터 다시 시작
	 * @param server
	 * @param userInputReader
	 * @param StudentInfo
	 * @throws IOException
	 * @throws SameValueExistsException
	 * @throws UserTokenDifferenceException
	 */
	private static void addStudentByStudentInfo(ServerIF server, BufferedReader userInputReader,String StudentInfo) throws IOException, SameValueExistsException, UserTokenDifferenceException {
		Student student = new Student(StudentInfo);
		try {
			System.out.print("enter Student ID: "); String studentId = student.getStudentId();
			if(studentId != null)	System.out.println(studentId);
			else {studentId = userInputReader.readLine().trim();
			if(studentId.length()!=8)throw new InvalidInputException("Student ID must be 8 characters");
			if(!isNumeric(studentId))throw new InvalidInputException("Student ID must be a number");}
			
			System.out.print("enter Student LastName: "); String LastName = student.getLastName();
			if(LastName != null)System.out.println(LastName);
			else {LastName = userInputReader.readLine().trim();
			if(LastName.equals(""))throw new InvalidInputException("LastName must be entered",studentId);}
			
			System.out.print("enter Student FirstName: "); String FirstName = student.getFirstName();
			if(FirstName != null)System.out.println(FirstName);
			else{FirstName = userInputReader.readLine().trim();
			if(FirstName.equals(""))throw new InvalidInputException("FirstName must be entered",studentId+" "+LastName);}	
			
			System.out.print("enter Student department: "); String department = student.getDepartment();
			if(department != null)System.out.println(department);
			else{department = userInputReader.readLine().trim();
			if(department.equals(""))throw new InvalidInputException("department must be entered",studentId+" "+LastName+" "+FirstName);}
			
			System.out.print("enter Student CompletedCourse: "); String CompletedCourse = student.getCompletedCoursesString();
			if(CompletedCourse != null)System.out.println(CompletedCourse);
			else {CompletedCourse = userInputReader.readLine().trim();
			if(!isNumeric(CompletedCourse))throw new InvalidInputException("CompletedCourse must be a number",studentId+" "+LastName+" "+FirstName+" "+department);}
			
			System.out.print("enter Student Password: "); String Password = userInputReader.readLine().trim();
			if(Password.length() < 5)throw new InvalidInputException("password must be at least 5 characters long.",studentId+" "+LastName+" "+FirstName+" "+department+" "+CompletedCourse);
			server.addStudent(studentId+" "+LastName+" "+FirstName+" "+department+" "+CompletedCourse,logInToken);
			server.addPassowrd(studentId,Password,logInToken);
			System.out.println("addSuccess!");
			WateSec(1);
			}catch(InvalidInputException e) {
				if(!MyExceptionHandler(userInputReader, e,false))
				addStudentByStudentInfo(server,userInputReader,e.getData());	
			}
	}
	/**
	 * 익셉션 발생시 정보를 받아와 전에 틩긴 부분부터 다시 시작
	 * @param server
	 * @param userInputReader
	 * @param CourseInfo
	 * @throws RemoteException
	 * @throws IOException
	 * @throws SameValueExistsException
	 * @throws UserTokenDifferenceException
	 */
	public static void addCourseByCourseInfo(ServerIF server, BufferedReader userInputReader,String CourseInfo) throws RemoteException, IOException, SameValueExistsException, UserTokenDifferenceException {
		Courses course = new Courses(CourseInfo);
		try {
		System.out.print("enter Course ID: "); String courseId = course.getCourseId();
		if(courseId != null)System.out.println(courseId);
		else {courseId = userInputReader.readLine().trim();
		if(courseId.length() != 5)throw new InvalidInputException("Course ID must be 5 characters");
		if(!isNumeric(courseId))throw new InvalidInputException("Student ID must be a number");}
		
		System.out.print("enter professor name: "); String professerName = course.getAdvisor();
		if(professerName != null)System.out.println(professerName);
		else {professerName = userInputReader.readLine().trim();
		if(professerName.equals(""))throw new InvalidInputException("professerName must be entered",courseId);}
		
		System.out.print("enter Course name: "); String courseName = course.getCourseName();
		if(courseName != null)System.out.println(courseName);
		else {courseName = userInputReader.readLine().trim();
		if(courseName.equals(""))throw new InvalidInputException("courseName must be entered",courseId+" "+professerName);}
		
		System.out.print("enter Prerequisite Course: "); String prerequisiteCourse = userInputReader.readLine().trim();
		if(!isNumeric(prerequisiteCourse))throw new InvalidInputException("prerequisiteCourse ID must be a number",courseId+" "+professerName+" "+courseName);
		
		Clear();
		server.addCourse(courseId+" "+professerName+" "+courseName+" "+prerequisiteCourse,logInToken);
		System.out.println("addSuccess!");
		WateSec(1);
		}catch(InvalidInputException e) {
			if(!MyExceptionHandler(userInputReader, e,false))
			addCourseByCourseInfo(server,userInputReader,e.getData());	
		}
		
	}
/**
 * 번은 로그인 학번으로 자동 입력(그냥 서버쪽에서 할까)
	 * 신청 가능한 코스 보여준 후 코스 아이디 입력 받아서 신청함
 * @param server
 * @param userInputReader
 * @throws IOException
 * @throws UserTokenDifferenceException
 */
	private static void makeReservation(ServerIF server, BufferedReader userInputReader) throws IOException, UserTokenDifferenceException {
		Clear();
		try {
		System.out.println("\nAvailable Course \n");
		ArrayList<Courses> availableCourseList = server.getAvailableCourseList(logInToken);
		if(availableCourseList.size()==0)throw new NullDataException("No more Available course");
		showData(availableCourseList);
		System.out.print("\nEnter Course Id to reservation:");
		String CID = userInputReader.readLine().trim();
		if(CID.length() != 5) throw new InvalidInputException("CID must be 5 characters long.");
		server.makeReservation(CID,logInToken);
		System.out.println("reservation Sucess");
		WateSec(1);
		}catch(InvalidInputException|SameValueExistsException|NullDataException e) {
			if(!MyExceptionHandler(userInputReader, e,false))
				makeReservation(server,userInputReader);	
		}
	}
	/**
	 * 화면을 지우거나 끝까지 올려버린다
	 */
	private static void Clear() {
		System.out.print("\033\143");
		System.out.flush();
	}
	/**
	 * 입력값이 있을때까지 잠깐 정지
	 * @param userInputReader
	 * @throws IOException
	 */
	private static void Delay(BufferedReader userInputReader) throws IOException {
		System.out.println("\nPress enter to continue...");
		userInputReader.readLine().trim();
	}
	private static void EXIT(ServerIF server) throws RemoteException, IOException {
		server.saveToText();
		System.out.println("****************** Thanks! ******************");
		System.exit(0);
	}
	/**
	 * 입력받은 초 만큼 잠시 정지 
	 * @param waitingSec
	 */
	private static void WateSec(int waitingSec) {
		try {System.out.println("\n\n Return to the menu after "+waitingSec+" second");
			TimeUnit.SECONDS.sleep(waitingSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 어레이 리스트 정보를 문자열로 출력 
	 * @param <E>
	 * @param list
	 * @throws RemoteException
	 */
	private static <E> void showData(ArrayList<E> list) throws RemoteException {
		for(E Data : list) {
				System.out.println(Data.toString().replace(' ', '\t'));
			}	
	}
	/**
	 * 문자열이 숫자로만 이루어졌는지 판단 
	 * @param s
	 * @return
	 */
	 public static boolean isNumeric(String s) {
	      for(int i = 0; i<s.length();i++) {
	    	  char temp = s.charAt(i);
	    	  if(temp != ' '&&!Character.isDigit(temp))return false;
	      }
	      return true;
	 }
	 
	private static void showTopMenu() {
		System.out.println("******************** Menu ********************");
		System.out.println("1. studentMenu");
		System.out.println("2. courseMenu"); 
		System.out.println("3. ReservationMenu");
		System.out.println("4. Token tampering test");
		System.out.println("0. LogOut");
		System.out.println("X. EXIT");
		System.out.println("**********************************************");
		System.out.println("* Please enter the number of the corresponding menu*");
		System.out.print(":  ");
	}private static void showStudentMenu() {
		System.out.println("**************** Students Menu ****************");
		System.out.println("1. List Students");
		System.out.println("2. Add Student"); 
		System.out.println("3. Delete Student"); 
		System.out.println("4. List completed courses by each student");
		System.out.println("=. To Top Menu");
		System.out.println("0. LogOut");
		System.out.println("X. EXIT");
		System.out.println("**********************************************");
		System.out.println("* Please enter the number of the corresponding menu*");
		System.out.print(":  ");
	}private static void showCourseMenu() {
		System.out.println("**************** courses Menu ****************");
		System.out.println("1. List courses");
		System.out.println("2. Add courses"); 
		System.out.println("3. Delete courses"); 
		System.out.println("=. To Top Menu");
		System.out.println("0. LogOut");
		System.out.println("X. EXIT");
		System.out.println("**********************************************");
	}private static void showReservationMenu() {
		System.out.println("************** Reservation Menu **************");
		System.out.println("1. Make Reservation"); 
		System.out.println("2. List Reservation");
		System.out.println("3. Delete Reservation");
		System.out.println("=. To Top Menu");
		System.out.println("0. LogOut");
		System.out.println("X. EXIT");
		System.out.println("**********************************************");
	}
}
