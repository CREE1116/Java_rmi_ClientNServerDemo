package Exceptions;


public class UserTokenDifferenceException extends MyException {
	private static final long serialVersionUID = 1L;
	public UserTokenDifferenceException(String errorMassage){
		super(errorMassage);
	}
}
