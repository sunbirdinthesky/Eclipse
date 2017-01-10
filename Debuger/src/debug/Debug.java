package debug;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Debug {
	static File logFile;
	static String logMessge;
	static PrintStream printStream;
	static {
		try {
			logFile = new File("d:/启动日期" + getNowTime() + ".log");
			logFile.createNewFile();
			printStream = new PrintStream(logFile);
		} catch (IOException e) {
			error("can not create log file!");
			e.printStackTrace();
		}
	}
	
	public static void log(String message) {
		logMessge = getNowTime() + "LogMessage: " + message;
		System.out.println(logMessge);
		writeLog();
	}

	public static void warn(String message) {
		logMessge = getNowTime() + "WarnMessage: " + message;
		System.out.println(logMessge);
		writeLog();
	}

	public static void error(String message) {
		logMessge = getNowTime() + "ErrorMessage: " + message;
		System.out.println(logMessge);
		writeLog();
	}
	
	public static void writeException (Exception e) {
		try {
			printStream.print(getNowTime());
			e.printStackTrace(printStream);
			printStream.flush();
		} catch (Exception e1) {
			error("can not write log file!");
			e1.printStackTrace();
		}
	}
	
	static void writeLog () {
		try {
			printStream.println(logMessge);
			printStream.flush();
		} catch (Exception e) {
			error("can not write log file!");
			e.printStackTrace();
		}
	}

	static String getNowTime() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"[yyyy年MM月dd日HH时mm分ss秒] ");
		return dateFormat.format(now);
	}
}
