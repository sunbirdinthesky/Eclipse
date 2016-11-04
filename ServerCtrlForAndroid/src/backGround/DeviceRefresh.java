package backGround;

import sqlOperation.SqlOperation;
import vars.Device;
import vars.Vars;

/*********************
 * \ table device ( imei char(64) not null, location char(32) null default
 * "n/a", nItem int null default 0, time char(32) null default
 * "0000-00-00 00-00-00", setUp int null default 0 ); \
 *********************/

public class DeviceRefresh extends Thread {
	@Override
	public void run() {
		SqlOperation sqlOperation = new SqlOperation();

		System.out.println("�豸��Ϣ��ʼ��");
		while (!Thread.interrupted()) {
			try {
				if (!Vars.DevicesFlag) {
					System.err.println("device����ռ�ã�����");
					Thread.interrupted();
					continue;
				}

				Vars.DevicesFlag = false;
				if (sqlOperation.isConnected() || sqlOperation.SqlInit()) {
					sqlOperation.SqlQuery("select now()");
					sqlOperation.rSet.next();
					Vars.now = sqlOperation.rSet.getString("now()");
					sqlOperation.SqlQuery("select * from device");
				}

				Vars.Devices.clear();
				while (sqlOperation.rSet.next()) {
					Device tmp = new Device();
					tmp.imei = sqlOperation.rSet.getString("imei");
					tmp.location = sqlOperation.rSet.getString("location");
					tmp.nItem = sqlOperation.rSet.getInt("nItem");
					tmp.time = sqlOperation.rSet.getString("time");
					tmp.setUp = sqlOperation.rSet.getInt("setUp");
					tmp.passwd = sqlOperation.rSet.getString("passwd");
					tmp.bluetoothState = sqlOperation.rSet.getInt("bluetooth");
					Vars.Devices.add(tmp);
				}
				Vars.DevicesFlag = true;
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("�豸���ݻ�ȡʧ�ܣ�׼����������");
			}
		}
		sqlOperation.disconnect();
	}
}
