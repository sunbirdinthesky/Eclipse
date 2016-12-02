package classes;

import java.util.Queue;
import java.util.Vector;

public class PCB {
	static public Queue<Proce> arrivedQueue;
	static public Vector<Proce> finished;

	static public Proce root = new Proce();
}
