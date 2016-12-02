import classes.Proce;



class createpc {
	private Proce root = new Proce();
	
	private Proce getProce (int pid, Proce root) {
		if (root.pid == pid)
			return root;
		for (int i = 0; i < root.subProce.size(); i++) {
			Proce tmProce = getProce(pid, root.subProce.get(i));
			if (tmProce != null)
				return tmProce;
		}
		return null;
	}
	
	private void addProc (Proce root, Proce target) {
		root.subProce.add(target);
	}
 	
	boolean createPc (int pid, int ppid, int prio) throws Exception {
		Proce tmp = getProce(ppid, root);
		if (tmp == null) {
			System.err.println("ppid:" + ppid + " can not be found");
			return false;
		}
		Proce tmpl = getProce(pid, root);
		if (tmpl != null) {
			System.err.println("pid:" + pid + " already exices");
			return false;
		}
		Proce newProce = new Proce();
		newProce.pid = pid;
		newProce.ppid = ppid;
		newProce.prio = prio;
		addProc(tmp, newProce);
		return true;
	}
	
	boolean deletepc (int pid) {
		if (pid == 0) {
			System.err.println("process 0 can not be deleted");
			return false;
		}
			
		Proce tmp = getProce(pid, root);
		if (tmp == null) {
			System.err.println("pid:" + pid + " can not be found");
			return false;
		}
		
		tmp = getProce(tmp.ppid, root);
		deletepc(pid, tmp);
		System.gc();
		System.out.println("deleted " + pid);
		return true;
	}
	
	private void deletepc (int pid, Proce root) {
		for (int i = 0; i < root.subProce.size(); i++) {
			deletepc(pid, root.subProce.get(i));
		}
		root.subProce.clear();
	}
	
	void showDetail () {
		showDetail(root);
	}
	
	private void showDetail (Proce root) {
		System.out.println("process properties: pid=" + root.pid + " ppid=" 
				+ root.ppid + " prio=" + root.prio);
		
		if (root.subProce.size() == 0)
			return;
		
		System.out.println("sub process(es) is(are):");
		
		for (int i = 0; i < root.subProce.size(); i++) {
			System.out.printf("pid=" + root.subProce.get(i).pid + "  ");
		}
		System.out.println();
		System.out.println();
		for (int i = 0; i < root.subProce.size(); i++) {
			showDetail(root.subProce.get(i));
		}
	}
}
