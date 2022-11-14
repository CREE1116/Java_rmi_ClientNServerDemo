package LogManage;



import java.text.SimpleDateFormat;


import java.util.logging.Formatter;
import java.util.logging.Handler;

import java.util.logging.LogRecord;

import java.util.Date;
	
	
	public class MyLogFormatter extends Formatter {
		String UserId = "unknown";
		public void setUserId(String UserId) {
			this.UserId = UserId;
		}
		
		@Override
		public String format(LogRecord record) {
			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		     Date date = new Date(record.getMillis());

		        StringBuffer sb = new StringBuffer(1000);
		        sb.append(" [");
		        sb.append(record.getLevel());
		        sb.append("] \t");
		        sb.append(dateFormat.format(date));
		        sb.append(" --User [");
		        sb.append(UserId);
		        sb.append("] \t");
		        sb.append("class[");
		        sb.append(record.getSourceClassName());
		        sb.append("] ");
		        sb.append("Method [");
		        sb.append(record.getSourceMethodName());
		        sb.append("] \t");
		        sb.append(record.getMessage());
		        sb.append("\n");
		        return sb.toString();
		}
	}
	

