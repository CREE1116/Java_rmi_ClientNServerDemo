package studentNCourseDataManagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import Exceptions.*;

public class PasswordList {
	protected ArrayList<Password> vPassword;
	private String sPasswordFileName;
	public PasswordList(String sPasswordFileName) throws FileNotFoundException, IOException {
		this.sPasswordFileName = sPasswordFileName;
		BufferedReader objStudentFile = new BufferedReader(new FileReader(this.sPasswordFileName));
		this.vPassword = new ArrayList<Password>();
		while (objStudentFile.ready()) {
			String info = objStudentFile.readLine();
			if (!info.equals("")) {
				this.vPassword.add(new Password(info));
			}
		}
		objStudentFile.close();
	}
	
	public boolean checkPassword(String SID,String Password) {
		for(Password password : vPassword) {
			if(password.PasswordCheck(SID,Password)) return true;
		}
		return false;
	}
	public void addPassword(String SID, String Password) throws SameValueExistsException{ 
		for(Password password : vPassword) {
			if(password.PasswordCheck(SID, Password)) throw new SameValueExistsException("same Passwordset already exist");
		}
		this.vPassword.add(new Password(SID+" "+Password));
	}
	public void deletePassowrd(String SID) throws NullDataException{
		for(Password password : vPassword) {
			if(password.getStudentId().equals(SID)) 
				if(this.vPassword.remove(password))return;
		}
		throw new NullDataException("*The Password of "+SID+"was not found*");
	}
	public void saveToText() throws IOException {
		new textWriter(sPasswordFileName,vPassword);
	}
}
