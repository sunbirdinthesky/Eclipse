package ui_logic;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import backgroundThreads.keepOnline.Online;
import backgroundThreads.update.GetFile;
import gpio.GPIOinterface;
import omxInterface.player;
import sqlOperation.SqlOperation;
import ui.MainUI;
import ui.SetUp;
import vars.Vars;

public class MainUILogic extends MainUI {
	boolean play = true;

	public static void main(String s[]) {
		MainUILogic mainUI = new MainUILogic();
		mainUI.open();
	}

	@Override
	protected void logicInit() {
		btn_Close.dispose();
		makeUUID();
		Thread repeatKeeper = new Thread() { // keep playing ads
			public void run() {
				try {
					Thread.sleep(5000); //wait for gpio
				} catch (Exception e) {
					// TODO: handle exception
				}

				while (!shell.isDisposed()) {
					try {
						Thread.sleep(1000);
						if (play) {
							File file = fileSelect();
							if (file == null)
								continue;
							player.play(file.getAbsolutePath());
							onComplete(file.getName());
						}
					} catch (Exception e) {
					}
				}
			};
		};

		repeatKeeper.start();

		Thread checker = new Thread() { // keep background thread running
			@Override
			public void run() {
				while (!shell.isDisposed()) {
					try {
						checkThread();
						Thread.sleep(10000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Vars.online.interrupt();
				Vars.getfile.interrupt();
			}
		};
		checker.start();

		startKernel();
	}

	private void startKernel() {
		// gpio signal,card reader
		new Thread() {
			@Override
			public void run() {

				boolean error = false;
				int errorCode = 0;

				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				new Thread() {
					public void run() {
						while (!shell.isDisposed()) {
							System.out.println("main thread:starting gpio()");
							int i = GPIOinterface.gpio(); //gpio init
							System.err.println(
									"main thread:WARNING! gpio() terminated at  "
											+ i);
							System.err.println("main thread:restarting gpio()");
						}
					};
				}.start();
				
				try {
					Thread.sleep(500); //for gpio init
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				while (true) { // catch gpio signal
					try {			
						String cardNumber = GPIOinterface.getCardNumber();
						while (Vars.isAdminListUsed);
						Vars.isAdminListUsed = true;
						if (cardNumber.length() != 0 && Vars.administrator.contains(cardNumber)) {
							System.out.println("main thread:getCardNumber() exit with #"
									+ cardNumber + "#");
							play = false;
							Vars.isAdminListUsed = false;
							player.stop();
							GPIOinterface.resetOutOfPaper();
							Display.getDefault().syncExec(new Runnable() {
								public void run() {
									SetUp setUp = new SetUp(shell,
											SWT.ON_TOP);
									setUp.open();
								}
							});
							System.out.println("main thread:status reset");
							GPIOinterface.resetFlag();
							Display.getDefault().syncExec(new Runnable() {
								public void run() {
									try {
										message.dispose();
										System.out.println("main thread:error message removed");
									} catch (Exception e) {
										System.out.println("main thread:error message remove failed");
									}
								}
							});
							play = true;
							error = false;
							continue;
						}
						Vars.isAdminListUsed = false;
					} catch (Exception e) {
						System.out.println("error:get card number failed");
					}
					
					

//					System.out.println("main thread:starting getUserFlag()");
					int i = GPIOinterface.getUserFlag();
//					System.out.println("main thread:getUserFlag() over at " + i);
					
					
					if (!error && i > 0) { // button pressed or card read
						System.out.println(
								"KeyPressed(Card Read)Action:gpio signal received");
						play = false;
						System.out.println(
								"KeyPressed(Card Read)Action:play = " + play);
						Display.getDefault().syncExec(new Runnable() {
							public void run() {
								WatchAdsLogic watchAdsLogic = new WatchAdsLogic(
										composite);
								watchAdsLogic.open();
							}
						});
						System.out.println(
								"KeyPressed(Card Read)Action:starting resetFlag()");
						i = GPIOinterface.resetFlag();
						System.out.println(
								"KeyPressed(Card Read)Action:resetFlag() over at "
										+ i);
						play = true;
						System.out.println(
								"KeyPressed(Card Read)Action:play = " + play);
					} 
					

					if (i < 0 && !error) { // out of paper and error
						play = false;
						error = true;
						errorCode = i;
						if (errorCode == -1)
							System.out.println(
									"error:out of paper,halt,player disabled");
						else
							System.out.println(
									"error:hardware error,halt,player disabled");
						System.out.println("error:play = " + play);
						player.stop();

						final int errorCodeSwap = errorCode;
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								System.out.println("error:showing message");
								message = new Label(composite, SWT.NONE);
								message.setBounds(userWidth * 5 / 16,
										userHight * 3 / 8, userWidth * 6 / 16,
										userHight / 4);
								message.setAlignment(SWT.CENTER);
								if (errorCodeSwap == -1) {
									message.setText("抱歉，礼品箱已经空了~");
									Vars.nItem = 0;
								}
								else
									message.setText("抱歉，系统故障");
								message.setFont(SWTResourceManager.getFont("Ubuntu",
										userWidth / 50, SWT.NORMAL));
								message.setBackgroundImage(SWTResourceManager
										.getImage(MainUI.class,
												"/UIImage/label_background.png"));
							}
						});
						System.out.println("error:play = " + play);
					} 

					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}
				}
			}
		}.start();
	}

	private void checkThread() {
		if (!Vars.getfile.isAlive()) {
			Vars.getfile = new GetFile();
			Vars.getfile.start();
		}
		if (!Vars.online.isAlive()) {
			Vars.online = new Online();
			Vars.online.start();
		}
	}

	private File fileSelect() { // select a file from repeats
		File pictures = new File(Vars.localPathRoot + "/AdMachine/repeats/");
		File[] file = pictures.listFiles();
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

		if (Vars.repeatID == files.size() - 1)
			Vars.repeatID = 0;
		else
			Vars.repeatID++;
		int tmp = Vars.repeatID;

		return files.get(tmp);
	}

	// send log to server and save it local
	// final String name = files.get(tmp).getName();
	private void onComplete(final String name) {
		new Thread() {
			@Override
			public void run() {
				SqlOperation sqlOperation = new SqlOperation();

				if (sqlOperation.SqlInit()) {
					try {
						File log = new File(
								Vars.localPathRoot + "/AdMachine/logs/");
						if (!log.exists())
							log.createNewFile();
						FileWriter writer = new FileWriter(log, true);
						writer.write(Vars.imei.substring(1) + " " + name + " "
								+ GetNowTime() + "\n");
						writer.flush();
						writer.close();

						sqlOperation.SqlUpdate("insert into log values(" + "\""
								+ Vars.imei.substring(1) + "\"," + "\"" + name
								+ "\"," + "\"" + GetNowTime() + "\")");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				sqlOperation.disconnect();
				System.out.println("sql finish");
			}
		}.start();

	}

	// get local date
	public String GetNowTime() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}

	// make a random UUID, just like IMEI
	void makeUUID() {
		File file = new File(Vars.localPathRoot + "/AdMachine/");
		if (!file.exists())
			file.mkdir();
		file = new File(Vars.localPathRoot + "/AdMachine/uuid");
		if (file.exists()) {
			try {
				FileReader fileReader = new FileReader(file);
				char[] tmp = new char[130];
				fileReader.read(tmp);
				fileReader.close();
				Vars.imei = tmp.toString();
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				file.delete();
			}
		}
		try {
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			UUID uuid = UUID.randomUUID();
			Vars.imei = "/" + uuid.toString();
			fileWriter.write(Vars.imei);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
