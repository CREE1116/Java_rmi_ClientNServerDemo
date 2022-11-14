package studentNCourseDataManagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import Exceptions.*;

public class ReservationList {
	
		protected ArrayList<Reservations> vReservations;
		private String sReservationFileName;
		public ReservationList(String sReservationFileName) throws FileNotFoundException, IOException {
			this.sReservationFileName = sReservationFileName;
			BufferedReader objStudentFile = new BufferedReader(new FileReader(this.sReservationFileName));
			this.vReservations = new ArrayList<Reservations>();
			while (objStudentFile.ready()) {
				String stuInfo = objStudentFile.readLine();
				if (!stuInfo.equals("")) {
					this.vReservations.add(new Reservations(stuInfo));
				}
			}
			objStudentFile.close();
		}
		public boolean addReservationsRecord(String ReservationInfo) throws SameValueExistsException{ 
			Reservations reservations = new Reservations(ReservationInfo);
			checkReservation(reservations.getStudentId(),reservations.getCourseId());
			if(this.vReservations.add(reservations))return true;
			else return false;
		}
		private void checkReservation(String SID, String CID)throws SameValueExistsException{
			for(Reservations reservation : this.vReservations) {
				if(reservation.matchReservation(SID, CID)) {
					throw new SameValueExistsException("A value that already exists");
				}
			}
		}
		public void deleteReservationRecord(String SID,String CID) throws NullDataException {
			for(Reservations reservation : this.vReservations) {
				if(reservation.matchReservation(SID, CID)) {
					this.vReservations.remove(reservation);
					return;
				}
			} throw new NullDataException(SID+" "+CID+" Reservation dose not exist");		
		}
		public void deleteReservationByStudentId(String SID) throws NullDataException {
			ArrayList<Reservations> temp = this.vReservations;
			for(Reservations reservation : temp) {
				if(reservation.matchStudentId(SID)) {
					this.vReservations.remove(reservation);
				}
			} throw new NullDataException("Student :"+SID+"'s Reservation dose not exist");		
		}
		public void deleteReservationByCourseId(String CID) throws NullDataException {
			ArrayList<Reservations> temp = this.vReservations;
			for(Reservations reservation : temp) {
				if(reservation.matchCourseId(CID)) {
					this.vReservations.remove(reservation);
				}
			} throw new NullDataException("Student :"+CID+"'s Reservation dose not exist");		
		}
		public ArrayList<Reservations> getAllReservationsRecords() throws NullDataException{
				if(this.vReservations.size() == 0) throw new NullDataException("Reservation Data does not exist");
				return this.vReservations;
		}
		public ArrayList<String> getStudentsReservationsRecords(String SID) throws NullDataException{
			ArrayList<String> tempList = new ArrayList<String>();
			for(Reservations reservation : this.vReservations) {
				if(reservation.matchStudentId(SID)) {
					tempList.add(reservation.getCourseId());
				}
			}
			if(tempList.size() == 0) throw new NullDataException(SID+"'s Reservation Data does not exist");
			return tempList;
			
		}
		public void saveToText() throws IOException {
			new textWriter(sReservationFileName,vReservations);
		}

	}
		



