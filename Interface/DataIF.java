import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import studentNCourseDataManagement.*;
import Exceptions.*;

public interface DataIF extends Remote{
	 ArrayList<Student> getAllStudentData() throws RemoteException, NullDataException;
	 ArrayList<Courses> getAllCourseData() throws RemoteException, NullDataException;
	 ArrayList<Reservations> getAllReservationsData() throws RemoteException, NullDataException;
	 ArrayList<String> getStudentsReservationsRecords(String SID) throws RemoteException, NullDataException;
	 Student getStudentById(String SID) throws RemoteException;
	 Courses getCourseById(String CID) throws RemoteException;
	 void addStudent(String studentInfo) throws RemoteException, SameValueExistsException;
	 void addCourse(String courseInfo) throws RemoteException, SameValueExistsException;
	 void addReservations(String reservationInfo) throws RemoteException, SameValueExistsException;
	 void addPassword(String SID, String Password) throws SameValueExistsException, RemoteException;
	 void deleteStudent(String SID) throws RemoteException, NullDataException;
	 void deleteCourse(String CID) throws RemoteException, NullDataException;
	 void deleteReservations(String SID, String CID)throws RemoteException, NullDataException;
	 void deleteReservationsByStudentId(String SID)throws RemoteException, NullDataException;
	 void deleteReservationsByCourseId(String CID)throws RemoteException, NullDataException;
	 void deletePassword(String SID)throws RemoteException, NullDataException;
	 boolean checkPassword(String SID, String Password)throws RemoteException;
	 void saveToText()throws  IOException ,RemoteException;
}
