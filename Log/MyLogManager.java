package LogManage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class MyLogManager {
	Logger logger = Logger.getLogger("MyLogManager");
	private static String FileName;
	private FileHandler logFile = null;
	
	public MyLogManager(String FileName) {
		MyLogManager.FileName = FileName;
	try {
		// path, append 방식으로 생성
		logFile = new FileHandler(FileName, true);
	}catch(SecurityException e) {
		e.printStackTrace();
	}catch(IOException e) {
		e.printStackTrace();
	}
	
	logFile.setFormatter(new MyLogFormatter());
	
	logFile.setLevel(java.util.logging.Level.ALL);
	
	logger.addHandler(logFile);
}
	public void info(String msg) {
		logger.fine(msg);
	}
	public void warning(String msg) {
		logger.warning(msg);
	}
}
