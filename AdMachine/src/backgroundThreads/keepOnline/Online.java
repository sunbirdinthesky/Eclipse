package backgroundThreads.keepOnline;

import sqlOperation.SqlOperation;
import vars.Vars;

/**
 * Created by SunBird on 2016/4/24.
 * for online check
 */
public class Online extends Thread {
	SqlOperation sqlOperation = new SqlOperation();

	@Override
	public void run() {
		while (!Thread.interrupted()) {

			try {
				if (sqlOperation.isConnected() || sqlOperation.SqlInit()) {

					sqlOperation.SqlUpdate("update device set time = now(), bluetooth = \"" + 1 + "\" where imei = \""
							+ Vars.imei.substring(1) + "\"");

					sqlOperation
							.SqlQuery("select setup from device where imei = " + "\"" + Vars.imei.substring(1) + "\"");
					if (sqlOperation.rSet.next() && sqlOperation.rSet.getInt("setup") == 1) {
						Vars.isSet = true;
					} else {
						Vars.isSet = false;
					}
				}
				else {
					System.out.println("SQL:Online thread failed to access sql");
					System.out.println("SQL:Online thread will retry 10s later");
					Thread.sleep(10000);
				}
				Thread.sleep(1500);
			} catch (Exception e) {
				System.out.println("SQL:Online thread crashed, restarting");
				try {
					Thread.sleep(10000);
				} catch (Exception e2) {
				}
			}
		}
		sqlOperation.disconnect();
	}
}
