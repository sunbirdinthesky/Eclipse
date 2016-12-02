package classes;
import java.util.Vector;


public class Proce {
	public int pid;
	public int ppid;
	public int prio;
	
	public int arriveTime;
	public int serviceTime;
	
	public Proce () {
		
	}
	
	public Proce (int pid, int ppid, int prio, int arriveTime, int serviceTime) {
		this.pid = pid;
		this.ppid = ppid;
		this.prio = prio;
		this.arriveTime = arriveTime;
		this.serviceTime = serviceTime;
	}
	
	public Vector<Proce> subProce = new Vector<>();
}
