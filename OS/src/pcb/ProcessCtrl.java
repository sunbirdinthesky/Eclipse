package pcb;

import classes.PCB;
import classes.Proce;

public class ProcessCtrl extends PCB {

	private Proce getPid(int pid) {
		if (pid == 0)
			return root;
		for (Proce tmp : root.subProce) {
			if (tmp.pid == pid)
				return tmp;
			Proce result = getPid(pid, tmp);
			if (result != null) 
				return result;
		}
		return null;
	}

	private Proce getPid(int pid, Proce root) {
		for (Proce tmp : root.subProce) {
			if (tmp.pid == pid)
				return tmp;
			Proce result = getPid(pid, tmp);
			if (result != null) 
				return result;
		}
		return null;
	}

	boolean createpc (int pid, int ppid, int prio, int arriveTime, int serviceTime) {
		if (getPid(pid) != null) {
			System.err.println("ERROR:pid already excist");
			return false;
		}
		Proce targetProce = getPid(ppid);
		if (targetProce == null) {
			System.err.println("ERROR:ppid can not be found");
			return false;
		}
		
		Proce tmp = new Proce(pid, ppid, prio, arriveTime, serviceTime);
		targetProce.subProce.add(tmp);
		arrivedQueue.add(tmp);
		return true;
	}
}
