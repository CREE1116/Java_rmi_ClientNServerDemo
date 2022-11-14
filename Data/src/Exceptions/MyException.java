package Exceptions;

public class MyException extends Exception{
	private StackTraceElement[] ste = this.getStackTrace();
	public MyException(String errorMassage){
		super(errorMassage);
	}
	public String getLogMessage() {
		return this.getClass().getName()+ "["+this.getMessage()+"] line"+ ste[0].getLineNumber() +"\n"+getLogMessage(ste,1);
	}
	private String getLogMessage(StackTraceElement[] ste, int num) {
		if(ste.length-1 < num) return "\n";
		String className = ste[num].getClassName();
		if(className.equals("Client") || className.equals("Data") || className.equals("Server"))
		return "\t\t-> class: ["+ste[num].getClassName()+"] Method["+ste[num].getMethodName()+"] line["+ste[num].getLineNumber()+"]"+"\n"+getLogMessage(ste,num+1);
		return getLogMessage(ste,num+1);
	}

}
