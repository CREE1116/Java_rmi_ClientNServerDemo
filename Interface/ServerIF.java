import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import studentNCourseDataManagement.*;
import Exceptions.*;

public interface ServerIF extends Remote{
	 ArrayList<Student> getAllStudentData(String Token) throws RemoteException, NullDataException, UserTokenDifferenceException,IOException;
	 ArrayList<Courses> getAllCourseData(String Token) throws RemoteException, NullDataException,IOException,UserTokenDifferenceException;
	 ArrayList<Courses> getAvailableCourseList(String Token)throws RemoteException, NullDataException,IOException,UserTokenDifferenceException;
	 ArrayList<Reservations> getAllReservationsData(String Token) throws RemoteException, NullDataException,IOException,UserTokenDifferenceException;
	 ArrayList<Courses> getStudentsCourseList(String Token) throws RemoteException, NullDataException,IOException,UserTokenDifferenceException;
	 ArrayList<Reservations> getReservationByStudentId(String Token) throws RemoteException, NullDataException, UserTokenDifferenceException;
	 Student getStudentById(String SID,String Token) throws RemoteException,IOException,UserTokenDifferenceException;
	 Courses getCourseById(String CID,String Token) throws RemoteException,IOException,UserTokenDifferenceException;
	 String getCurentUserId(String Token) throws NullDataException,RemoteException,IOException,UserTokenDifferenceException;
	 void addStudent(String studentInfo,String Token) throws RemoteException, SameValueExistsException,IOException,UserTokenDifferenceException;
	 void addCourse(String courseInfo,String Token) throws RemoteException, SameValueExistsException,IOException,UserTokenDifferenceException;
	 void makeReservation(String CID,String Token) throws RemoteException, NullDataException, SameValueExistsException, InvalidInputException,IOException,UserTokenDifferenceException;
	 void addPassowrd(String SID, String Password,String Token)throws RemoteException, SameValueExistsException,IOException,UserTokenDifferenceException;
	 void deleteStudent(String SID,String Token) throws RemoteException, NullDataException,IOException,UserTokenDifferenceException;
	 void deleteCourse(String CID,String Token) throws RemoteException, NullDataException,IOException,UserTokenDifferenceException;
	 void deleteReservation(String CID, String Token)throws RemoteException, NullDataException,IOException,UserTokenDifferenceException;
	 String checkPassword(String SID, String Password)throws RemoteException,IOException;
	 void logOut() throws RemoteException, IOException;
	 void saveToText()throws RemoteException,IOException;
}
