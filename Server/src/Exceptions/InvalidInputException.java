package Exceptions;

import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class InvalidInputException extends MyException{
	private static final long serialVersionUID = 1L;
	public InvalidInputException(String errorMassage){
		super(errorMassage);
	}

	
}
