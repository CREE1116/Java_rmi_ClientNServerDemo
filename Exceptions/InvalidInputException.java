package Exceptions;

import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class InvalidInputException extends MyException{
	String Data="";
	private static final long serialVersionUID = 1L;
	public InvalidInputException(String errorMassage){
		super(errorMassage);
	}
	public InvalidInputException(String errorMassage,String Data){
		super(errorMassage);
		this.Data =Data;
	}
	public String getData() {
		return this.Data;
	}

	
}
