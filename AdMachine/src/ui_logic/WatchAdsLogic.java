package ui_logic;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import omxInterface.player;
import ui.MainUI;
import ui.WatchAds;
import vars.Vars;

public class WatchAdsLogic extends WatchAds {
	public WatchAdsLogic(Composite composite) {
		super(composite);
	}

	@Override
	protected void logicInit() {
		System.out.println("WatchAds:init...");
		final Thread normal = new Thread(new Runnable() {
			public void run() {
				File file = fileSelect();
				if (file == null) { // if no file founded
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							message = new Label(composite, SWT.NONE);
							message.setBounds(userWidth * 5 / 16,
									userHight * 3 / 8, userWidth * 6 / 16,
									userHight / 4);
							message.setAlignment(SWT.CENTER);
							message.setText("您没有任何广告哦~");
							message.setFont(SWTResourceManager.getFont("Ubuntu",
									userWidth / 50, SWT.NORMAL));
							message.setBackgroundImage(
									SWTResourceManager.getImage(MainUI.class,
											"/UIImage/label_background.png"));
						}
					});
					try {
						Thread.sleep(5000);
					} catch (Exception e) {
						// TODO: handle exception
					}
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							message.dispose();
							isDisposed = true;
						}
					});
					return;
				}

				try {// if file founded
					player.play(file.getAbsolutePath());
					System.out.println("WatchAds:play over, try to connect SQL");
					onComplete(file.getName());

					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							try {
								message.dispose();
							} catch (Exception e) {
								// TODO: handle exception
							}
							FinishLogic finishLogic = new FinishLogic(
									composite);
							finishLogic.open();
							isDisposed = true;
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (!isDisposed) { // if player.play() crashed
					System.err.println(
							"WatchAds:OMXPlayer error, did OMX player installed?");
					System.err.println("process will now restart");
					Display.getDefault().syncExec(new Runnable() {
						public void run() {
							try {
								message.dispose();
							} catch (Exception e) {
							}
							isDisposed = true;
						}
					});
				}

			}
		});
		normal.start();

		// if system not function for 5 minutes
		// kill this thread and initialize main thread
		new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					Thread.sleep(5 * 60 * 1000);
					if (normal.isAlive()) {
						System.err.println(
								"system not function for 5 minutes, process will now restart");
					} else {
						normal.stop();
						Display.getDefault().syncExec(new Runnable() {
							public void run() {
								try {
									message.dispose();
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
						});
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			};
		}.start();
	}

	File fileSelect() {// select a file from ads
		File ads = new File(Vars.localPathRoot + "/AdMachine/ads/");
		final File[] file = ads.listFiles();
		Vector<File> files = new Vector<>();

		if (file == null || file.length == 0) {
			return null;
		}

		for (File f : file) {
			if (f.getName().length() <= 4)
				files.add(f);
			else if (!f.getName().substring(0, 4).equals("tmp_")) {
				files.add(f);
			}
		}

		if (files.size() == 0) {
			return null;
		}

		if (Vars.adID == files.size() - 1)
			Vars.adID = 0;
		else
			Vars.adID++;
		int tmp = Vars.adID;
		return files.get(tmp);
	}

	// send log to server and save it local
	// final String name = files.get(tmp).getName();
	void onComplete(final String name) {
		new Thread() {
			@Override
			public void run() {
				String driver = "com.mysql.jdbc.Driver";
				String url = "jdbc:mysql://" + Vars.ip + ":3306/AdMachine";
				String user = Vars.sqlName;
				String password = Vars.sqlPasswd;

				try {
					File log = new File(
							Vars.localPathRoot + "/AdMachine/logs/");
					if (!log.exists())
						log.createNewFile();
					FileWriter writer = new FileWriter(log, true);
					writer.write(Vars.imei.substring(1) + " " + name + " "
							+ getNowTime() + "\n");
					writer.flush();
					writer.close();

					Class.forName(driver);
					Connection conn = DriverManager.getConnection(url, user,
							password);
					Statement statement = conn.createStatement();
					statement.executeUpdate("insert into log values(" + "\""
							+ Vars.imei.substring(1) + "\"," + "\"" + name
							+ "\"," + "\"" + getNowTime() + "\")");

					ResultSet rs = statement
							.executeQuery("select playCount from ads" + name
									+ " where imei = \""
									+ Vars.imei.substring(1) + "\"");
					if (rs.next()) {
						if (rs.getInt("playCount") != 0) {
							statement.executeUpdate(
									"update ads set playCount = playCount-1 where name = \""
											+ name + "\"");
							statement.executeUpdate("update ads" + name
									+ " set playCount = playCount-1 where imei = \""
									+ Vars.imei.substring(1) + "\"");
						}
					}
					rs.close();
					conn.close();
				} catch (Exception e) {
					System.out.println("WatchAds:adOncomplete sql failed");
				}
				System.out.println("WatchAds:sql ops finish");
			}
		}.start();

	}

	public String getNowTime() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}

}
