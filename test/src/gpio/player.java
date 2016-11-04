package gpio;

import java.io.IOException;
import java.util.Date;

/*
 * OMXplayer Interface, by using command line, work for linux
 */

public class player {
	static private Process process;
	static private Date start;
	static private String during = new String();
	static private String path;
	static private boolean isPaused = false;
	
	static public int play(final String filePath) throws Exception {
		if (isPaused) 
			return -1;
		stop();
		path = filePath;
		System.out.println("OMXPlayer:file path = " + filePath);
		process = Runtime.getRuntime().exec("omxplayer " + filePath);
		System.out.println("OMXPlayer:start playing");
		start = new Date();
		int exitCode = process.waitFor();
		System.out.println("OMXPlayer:exit code:" + exitCode);
		return exitCode;
	}
	
	static public void stop () {
		if (process != null) {
			try {
				Runtime.getRuntime().exec("killall -2 omxplayer.bin");
				process = null;
				isPaused = false;
			} catch (IOException e) {
				System.out.println("OMXPlayer:player killed");
			}
		}
		System.out.println("OMXPlayer:player killed");
	}
	
	static public void pause () {
		stop();
		isPaused = true;
		Date now = new Date();
		long x = (now.getTime() - start.getTime())/1000;
		long ss = x%60;
		long mm = x/60%60;
		long hh = x/360;
		during = String.format("%02d:%02d:%02d", hh, mm, ss);
		System.out.println("OMXPlayer:pause at " + during);
	}
	
	static public void resume () throws Exception {
		if (!isPaused)
			return;
		isPaused = false;
		process = Runtime.getRuntime().exec("omxplayer " + path + " -l " + during);
		System.out.println("OMXPlayer:resume at " + during);
		int exitCode = process.waitFor();
		System.out.println("OMXPlayer:exit code:" + exitCode);
	}
}
