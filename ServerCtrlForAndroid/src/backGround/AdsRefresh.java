package backGround;

import sqlOperation.SqlOperation;
import vars.AdFile;
import vars.UploadIndex;
import vars.Vars;

/**************************
 * \ table ads ( name char(32) not null, playCount int null default 0, md5
 * char(64) not null, begin char(12) null default "0000-00-00", end char(12)
 * null default "0000-00-00", company char(32) null default "n/a" ); \
 **************************/
public class AdsRefresh extends Thread {
	public void run() {
		SqlOperation sqlOperation = new SqlOperation();
		System.out.println("定点广告初始化");
		while (!Thread.interrupted()) {
			try {
				if (!Vars.adsFlag){
					System.err.println("ads数组占用，跳过");
					Thread.interrupted();
					continue;
				}
				
				Vars.adsFlag = false;
				if (sqlOperation.isConnected() || sqlOperation.SqlInit()) {
					sqlOperation.SqlQuery("select * from ads");
				}

				Vars.ads.clear();
				while (sqlOperation.rSet.next()) {
					AdFile tmp = new AdFile();
					tmp.name = sqlOperation.rSet.getString("name");
					tmp.playCnt = sqlOperation.rSet.getInt("playCount");
					tmp.md5 = sqlOperation.rSet.getString("md5");
					tmp.begin = sqlOperation.rSet.getString("begin");
					tmp.end = sqlOperation.rSet.getString("end");
					tmp.company = sqlOperation.rSet.getString("company");
					Vars.ads.add(tmp);
				}

				int pos = 0;
				for (AdFile tmp : Vars.ads) {
					sqlOperation.SqlQuery("select * from ads" + tmp.name);
					while (sqlOperation.rSet.next()) {
						UploadIndex index = new UploadIndex();
						index.imei = sqlOperation.rSet.getString("imei");
						index.playCount = sqlOperation.rSet.getInt("PlayCount");
						tmp.Config.add(index);
					}
					Vars.ads.set(pos, tmp);
					pos++;
				}
				Vars.adsFlag = true;

				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("定点广告数据获取失败，准备重新启动");
			}
		}
		sqlOperation.disconnect();
	};
}
