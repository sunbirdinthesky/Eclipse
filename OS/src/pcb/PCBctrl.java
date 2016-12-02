package pcb;

import java.util.Comparator;
import java.util.PriorityQueue;

import javax.naming.ldap.SortControl;
import javax.xml.soap.Node;

import classes.PCB;
import classes.Proce;

public class PCBctrl extends PCB {
	Comparator<Proce> FCFS = new Comparator<Proce>() {
		public int compare(Proce arg0, Proce arg1) {
			return arg0.arriveTime - arg1.arriveTime;
		};
	}; 
	
	PriorityQueue<Proce> readyQueue;

	/**
	 * id = 1:FCFS
	 * @param id
	 */
	
	void init (int id) {
		if (id == 1) {
			readyQueue = new PriorityQueue<Proce>(20, FCFS);
			for (Proce tmp : arrivedQueue) {
				readyQueue.add(tmp);
			}
		}
	}
	
	void showArriveQueue () {
		int time = 0;
		for (Proce tmp : arrivedQueue) {
			System.out.println("process time=" + time + " pid=" + tmp.pid 
					+ " arrived time=" + tmp.arriveTime 
					+ " service time=" + tmp.serviceTime
					+ " process over time=");
		}
	}
}
