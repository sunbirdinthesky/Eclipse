import java.util.Scanner;

public class test {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		createpc createpc = new createpc();
		System.out.println("ready");
		
		while (scanner.hasNext()) {
			String command = scanner.next();
			if (command.equals("createpc")) {
				int pid = scanner.nextInt();
				int ppid = scanner.nextInt();
				int prio = scanner.nextInt();
				if (createpc.createPc(pid, ppid, prio))
					System.out.println("created process " + pid + " "+ ppid + " " + prio);
				continue;
			}
			if (command.equals("showdetail")) {
				createpc.showDetail();
				continue;
			}
			if (command.equals("deletepc")) {
				int pid = scanner.nextInt();
				createpc.deletepc(pid);
				continue;
			}
			if (command.equals("exit")) {
				break;
			}
			System.err.println("Invalid command");
		}
		scanner.close();
	}

}
