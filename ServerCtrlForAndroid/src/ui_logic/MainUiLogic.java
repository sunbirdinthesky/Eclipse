package ui_logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import backGround.AdsRefresh;
import backGround.DeviceRefresh;
import backGround.RepeatsRefresh;
import fileTreeCtrl.FileTreeDlg;
import ftpOperation.FtpOperations;
import ui.MainUI;
import vars.AdFile;
import vars.Device;
import vars.UploadIndex;
import vars.Vars;

public class MainUiLogic extends MainUI {
	private AdsRefresh adsRefresh;
	private DeviceRefresh deviceRefresh;
	private RepeatsRefresh repeatsRefresh;
	private Thread update;
	private Vector<UploadIndex> uploadIndex = new Vector<>();
	private Vector<Text> UploadPlayCount = new Vector<>();

	public static void main(String[] s) {
		try {
			MainUiLogic window = new MainUiLogic();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void LogicInit() {
		OptionSelect(1);
		ButtonsInit();
		adsRefresh = new AdsRefresh();
		deviceRefresh = new DeviceRefresh();
		repeatsRefresh = new RepeatsRefresh();
		update = new Thread() {
			public void run() {
				while (!Thread.interrupted()) {
					try {
						if (isConnected() || SqlInit()) {
							SqlQuery("select background from versionCtrl");
							if (!rSet.next() || rSet.getString("background")
									.compareTo(Vars.version) > 0) {
								Display.getDefault().asyncExec(new Runnable() {
									public void run() {
										Attention.setText("请使用新版本");
									}
								});
								throw new Exception();
							}
							Display.getDefault().asyncExec(new Runnable() {
								public void run() {
									Attention.setText("");
								}
							});
						} else {
							Display.getDefault().asyncExec(new Runnable() {
								public void run() {
									Attention.setText("与服务器的连接已断开");
								}
							});
							throw new Exception();
						}

						Update();
						Thread.sleep(1500);
					} catch (Exception e) {
						System.err.println("主界面更新发生错误");
						e.printStackTrace();
					}
				}
			};
		};
		update.start();
	}

	/**
	 * @throws Exception
	 * @wbp.parser.entryPoint
	 */
	public void Update() throws Exception {
		if (!deviceRefresh.isAlive()) {
			deviceRefresh = new DeviceRefresh();
			deviceRefresh.start();
		}
		if (!adsRefresh.isAlive()) {
			adsRefresh = new AdsRefresh();
			adsRefresh.start();
		}
		if (!repeatsRefresh.isAlive()) {
			repeatsRefresh = new RepeatsRefresh();
			repeatsRefresh.start();
		}
		deviceListRefresh();
		adsListRefresh();
		RepeatListRefresh();
	}

	@SuppressWarnings("unchecked")
	String upload() {
		File file = new File(TextBox_Upload_FilePath.getText());

		if (!file.exists() || file.isDirectory()) {
			return "文件路径错误";
		}

		if (Combo_Upload_FileType.getSelectionIndex() == -1) {
			return "请选择文件的类别";
		}

		String name = file.getName();
		if (name.indexOf('.') != -1)
			name = name.substring(0, name.indexOf('.'));
		if (name.equals(""))
			return "文件名首字母不能为小数点";

		boolean checked = false;
		int cnt = 0;
		uploadIndex.clear();
		for (Button tmp : Vars.UploadDevices) {
			if (tmp.getSelection()) {
				checked = true;
				UploadIndex up = new UploadIndex();
				up.imei = tmp.getText().substring(0,
						tmp.getText().indexOf(' '));////////////
				try {
					up.playCount = Integer
							.valueOf(UploadPlayCount.get(cnt).getText());
				} catch (Exception e) {
					up.playCount = -1;
				}
				uploadIndex.add(up);
			}
			cnt++;
		}

		if (!checked) {
			return "请至少勾选一个设备";
		}
		for (int i = 0; i < name.length(); i++) {
			char ch = name.charAt(i);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
					|| (ch >= '0' && ch <= '9') || (ch == '_')) {
			} else {
				return "文件名中只能含有大小写字母，数字和下划线";
			}
		}

		for (int i = 0; i < TextBox_Upload_Company.getText().length(); i++) {
			char ch = TextBox_Upload_Company.getText().charAt(i);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
					|| (ch >= '0' && ch <= '9') || (ch == '_')) {
			} else {
				return "公司中只能含有大小写字母，数字和下划线";
			}
		}

		String begin = "2000-01-01";
		String end = "3000-01-01";
		int playConut = -1;
		if (Combo_Upload_FileType.getSelectionIndex() == 0) {
			try {
				playConut = Integer.valueOf(TextBox_Upload_PlayConut.getText());
			} catch (Exception e) {
				return "请填写正确的整数";
			}
		} else {
			if (Combo_Upload_YearPre.getSelectionIndex() == -1
					|| Combo_Upload_YearAfter.getSelectionIndex() == -1
					|| Combo_Upload_MonthPre.getSelectionIndex() == -1
					|| Combo_Upload_MonthAfter.getSelectionIndex() == -1
					|| Combo_Upload_DayPre.getSelectionIndex() == -1
					|| Combo_Upload_DayAfter.getSelectionIndex() == -1) {
				return "请填写完整日期";
			}
			begin = ""; // 格式化初使日期，如"2016-02-01"
			begin += Combo_Upload_YearPre.getText();
			begin += "-";
			if (Combo_Upload_MonthPre.getText().length() == 1) {
				begin += "0";
			}
			begin += Combo_Upload_MonthPre.getText();
			begin += "-";
			if (Combo_Upload_DayPre.getText().length() == 1) {
				begin += "0";
			}
			begin += Combo_Upload_DayPre.getText();

			end = ""; // 格式化终止日期，如"2016-02-01"
			end += Combo_Upload_YearAfter.getText();
			end += "-";
			if (Combo_Upload_MonthAfter.getText().length() == 1) {
				end += "0";
			}
			end += Combo_Upload_MonthAfter.getText();
			end += "-";
			if (Combo_Upload_DayAfter.getText().length() == 1) {
				end += "0";
			}
			end += Combo_Upload_DayAfter.getText();

			if (end.compareTo(begin) < 0) {
				return "结束日期不应小于开始日期";
			}
		}
		AdFile tmp = new AdFile();
		tmp.name = name;
		tmp.playCnt = playConut;
		tmp.begin = begin;
		tmp.end = end;
		tmp.company = TextBox_Upload_Company.getText();
		tmp.Config = (Vector<vars.UploadIndex>) uploadIndex.clone();

		Button_Upoad.setEnabled(false);
		final int Mark = Combo_Upload_FileType.getSelectionIndex();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Lable_Upload_Attention.setText("计算MD5中");
						}
					});
					tmp.md5 = getMd5ByFile(file);
				} catch (Exception e) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Button_Upoad.setEnabled(true);
						}
					});
					return;
				}

				if (checkSQL(tmp.name)) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Lable_Upload_Attention.setText("上传失败，服务器上有同名文件存在？");
							Button_Upoad.setEnabled(true);
						}
					});
					return;
				}

				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						Lable_Upload_Attention.setText("上传中，请耐心等待");
					}
				});
				FtpOperations ftpOperations = new FtpOperations();
				switch (Mark) {
				case 0:
					ftpOperations.upload(file, "/AdMachine/ads", tmp.name);
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Lable_Upload_Attention.setText("上传完成,开始写入数据库");
						}
					});
					SqlUpdate("insert into ads values (" + "\"" + tmp.name
							+ "\"" + ", " + tmp.playCnt + ", \"" + tmp.md5
							+ "\", \"" + tmp.begin + "\", \"" + tmp.end
							+ "\", \"" + tmp.company + "\")");
					SqlUpdate("create table ads" + tmp.name
							+ "(imei char(64) not null, playCount int not null)");

					int def = 0;
					int sum = tmp.playCnt;
					int acc = 0;
					int mod = 0;
					for (UploadIndex index : tmp.Config) {// 初始化，计算每个广告机该播几个
						if (index.playCount == -1)
							def++;
						else
							sum -= index.playCount;
					}
					if (def == 0) {
						acc = sum / tmp.Config.size();
						mod = sum % tmp.Config.size();
					} else {
						acc = sum / def;
						mod = sum % def;
					}
					for (int i = 0; i < tmp.Config.size(); i++) {// 开始写入数据库
						if (tmp.Config.get(i).playCount == -1) {
							tmp.Config.get(i).playCount = acc;
						} else {
							tmp.Config.get(i).playCount += acc;
						}
						if (i == 0) {
							tmp.Config.get(i).playCount += mod;
						}
					}

					for (UploadIndex index : tmp.Config) {
						SqlUpdate("insert into ads" + tmp.name + " values (\""
								+ index.imei + "\", " + index.playCount + ")");
					}

					break;

				case 1:
					ftpOperations.upload(file, "/AdMachine/repeats", tmp.name);
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							Lable_Upload_Attention.setText("上传完成,开始写入数据库");
						}
					});

					SqlUpdate("insert into repeats values (" + "\"" + tmp.name
							+ "\"" + ", " + tmp.playCnt + ", \"" + tmp.md5
							+ "\", \"" + tmp.begin + "\", \"" + tmp.end
							+ "\", \"" + tmp.company + "\")");
					SqlUpdate("create table repeats" + tmp.name
							+ "(imei char(64) not null, playCount int not null)");

					for (UploadIndex index : tmp.Config) {
						SqlUpdate("insert into repeats" + tmp.name
								+ " values (\"" + index.imei + "\", "
								+ index.playCount + ")");
					}
					break;
				}

				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						Lable_Upload_Attention.setText("所有工作完成，已就绪");
						Button_Upoad.setEnabled(true);
					}
				});
			}
		}).start();

		return "连接服务器中，请耐心等待";
	}

	String getMd5ByFile(File file) throws FileNotFoundException {
		String value = null;
		FileInputStream in = new FileInputStream(file);
		try {
			MappedByteBuffer byteBuffer = in.getChannel()
					.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	boolean checkSQL(String name) {
		boolean state = false;
		try {
			if (isConnected() || SqlInit()) {
				SqlQuery("select name from ads where name = " + "\"" + name
						+ "\"");
				if (rSet.next()) {
					state = true;
				}
				SqlQuery("select name from repeats where name = " + "\"" + name
						+ "\"");
				if (rSet.next()) {
					state = true;
				}
			}
			if (rSet != null && !rSet.isClosed())
				rSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("SQL连接失败");
			state = true;
		}

		return state;
	}

	void deviceListRefresh() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {

				if (!Vars.DevicesFlag)
					return;

				Vars.DevicesFlag = false;
				ScrolledComposite tmpListDevice = new ScrolledComposite(
						ContainerDevice,
						SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
				tmpListDevice.setExpandVertical(true);
				tmpListDevice.setExpandHorizontal(true);
				tmpListDevice.setBackground(
						SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
				tmpListDevice.setBounds(26, 138, 640, 534);

				int i = 1;
				for (Device tmp : Vars.Devices) {
					final int x = i - 1;

					Label location = new Label(tmpListDevice, SWT.NONE);
					location.setBounds(10, i * 23, 120, 23);
					location.setBackground(
							SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
					location.setText(tmp.location);
					location.setAlignment(SWT.CENTER);

					Label name = new Label(tmpListDevice, SWT.NONE);
					name.setBounds(163, i * 23, 200, 23);
					name.setText(tmp.imei);
					name.setBackground(
							SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
					name.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseUp(MouseEvent e) {
							DeviceLogic deviceLogic = new DeviceLogic(
									Vars.Devices.get(x));
							shell.setEnabled(false);
							deviceLogic.open();
							shell.setEnabled(true);
						}
					});
					Label onlineMark = new Label(tmpListDevice, SWT.NONE);
					onlineMark.setBounds(150, i * 23+5, 13, 13);

					try {
						SimpleDateFormat ft = new SimpleDateFormat(
								"yyyy-MM-dd hh:mm:ss");
						Date device = ft.parse(tmp.time);
						Date server = ft.parse(Vars.now);

						long diff = device.getTime() - server.getTime();
						diff = diff / 1000;
						if (diff >= 5 || diff <= -5) {
							onlineMark.setBackground(
									SWTResourceManager.getColor(SWT.COLOR_RED));
						} else {
							onlineMark.setBackground(SWTResourceManager
									.getColor(SWT.COLOR_GREEN));
						}
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						onlineMark.setBackground(
								SWTResourceManager.getColor(SWT.COLOR_RED));
					}

					Label setUp = new Label(tmpListDevice, SWT.NONE);
					setUp.setBounds(363, i * 23, 80, 23);
					setUp.setBackground(
							SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
					setUp.setAlignment(SWT.CENTER);

					if (tmp.setUp == 0) {
						setUp.setText("不可设定");
					} else {
						setUp.setText("可设定");
					}

					Label nItem = new Label(tmpListDevice, SWT.NONE);
					nItem.setBounds(473, i * 23, 90, 23);
					nItem.setBackground(
							SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
					Label nItemMark = new Label(tmpListDevice, SWT.NONE);
					nItemMark.setBounds(460, i * 23+5, 13, 13);
					if (tmp.nItem > 0) {
						nItem.setText(String.valueOf(tmp.nItem));
						nItemMark.setBackground(SWTResourceManager
								.getColor(SWT.COLOR_TRANSPARENT));
					} else {
						nItem.setText("缺纸");
						nItemMark.setBackground(SWTResourceManager
								.getColor(SWT.COLOR_MAGENTA));
					}
					nItem.setAlignment(SWT.CENTER);

					Label blueooth = new Label(tmpListDevice, SWT.NONE);
					blueooth.setBounds(583, i * 23, 40, 23);
					blueooth.setBackground(
							SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
					Label blueoothMark = new Label(tmpListDevice, SWT.NONE);
					blueoothMark.setBounds(570, i * 23+5, 13, 13);
					if (tmp.bluetoothState == 0) {
						blueooth.setText("离线");
						blueoothMark.setBackground(
								SWTResourceManager.getColor(SWT.COLOR_YELLOW));
					} else {
						blueooth.setText("在线");
						blueoothMark.setBackground(
								SWTResourceManager.getColor(SWT.COLOR_CYAN));
					}
					blueooth.setAlignment(SWT.CENTER);
					i++;
				}
				ScrolledComposite tmp = ListDevice;
				ListDevice = tmpListDevice;
				tmp.dispose();
				Vars.DevicesFlag = true;

			}
		});
	}

	void adsListRefresh() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!Vars.adsFlag)
					return;
				Vars.adsFlag = false;
				ListAds.dispose();
				ListAds = new ScrolledComposite(ContainerADs,
						SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
				ListAds.setExpandVertical(true);
				ListAds.setExpandHorizontal(true);
				ListAds.setBackground(
						SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
				ListAds.setBounds(26, 138, 640, 534);
				int i = 1;
				for (AdFile tmp : Vars.ads) {
					Label name = new Label(ListAds, SWT.NONE);
					name.setBounds(10, i * 23, 120, 23);
					name.setBackground(
							SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
					name.setText(tmp.name);
					name.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseUp(MouseEvent e) {
							while (!Vars.DevicesFlag) {
							}
							Vars.DevicesFlag = false;
							@SuppressWarnings("unchecked")
							Vector<Device> swap = (Vector<Device>) Vars.Devices
									.clone();
							Vars.DevicesFlag = true;

							shell.setEnabled(false);
							AdsLogic adsLogic = new AdsLogic(tmp, swap);
							adsLogic.open();
							shell.setEnabled(true);
						}
					});

					Label playcnt = new Label(ListAds, SWT.NONE);
					playcnt.setBounds(140, i * 23, 80, 23);
					playcnt.setBackground(
							SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
					playcnt.setText(String.valueOf(tmp.playCnt));

					Label company = new Label(ListAds, SWT.NONE);
					company.setBounds(230, i * 23, 60, 23);
					company.setBackground(
							SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
					company.setText(tmp.company);

					i++;
				}

				Vars.adsFlag = true;

			}
		});
	}

	void RepeatListRefresh() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (!Vars.repeatsFlag)
					return;
				Vars.repeatsFlag = false;
				ListRepeats.dispose();
				ListRepeats = new ScrolledComposite(ContainerRepeats,
						SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
				ListRepeats.setExpandVertical(true);
				ListRepeats.setExpandHorizontal(true);
				ListRepeats.setBackground(
						SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
				ListRepeats.setBounds(26, 138, 640, 534);
				int i = 1;
				for (AdFile tmp : Vars.repeats) {
					Label name = new Label(ListRepeats, SWT.NONE);
					name.setBounds(10, i * 23, 120, 23);
					name.setBackground(
							SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
					name.setText(tmp.name);
					name.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseUp(MouseEvent e) {
							while (!Vars.DevicesFlag) {
							}
							Vars.DevicesFlag = false;
							@SuppressWarnings("unchecked")
							Vector<Device> swap = (Vector<Device>) Vars.Devices
									.clone();
							Vars.DevicesFlag = true;

							shell.setEnabled(false);
							RepeatLogic repeatsLogic = new RepeatLogic(tmp,
									swap);
							repeatsLogic.open();
							shell.setEnabled(true);
						}
					});

					Label company = new Label(ListRepeats, SWT.NONE);
					company.setBounds(230, i * 23, 60, 23);
					company.setBackground(
							SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
					company.setText(tmp.company);

					Label during = new Label(ListRepeats, SWT.NONE);
					during.setBounds(300, i * 23, 320, 23);
					during.setBackground(
							SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
					during.setText(tmp.begin + '~' + tmp.end);
					i++;
				}

				Vars.repeatsFlag = true;

			}
		});
	}

	void uploadDeviceListRefresh(final boolean withPlaycnt) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Vars.UploadDevicesFlag || !Vars.DevicesFlag) {
					try {
						Thread.sleep(30);
					} catch (Exception e) {
					}
					System.err.println(Vars.DevicesFlag);
					System.err.println(Vars.UploadDevicesFlag );
				}
				Vars.DevicesFlag = false;
				Vars.UploadDevicesFlag = false;
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						Vars.UploadDevices.clear();
						ListUpload.dispose();
						ListUpload = new ScrolledComposite(ContainerUpload,
								SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
						ListUpload.setExpandVertical(true);
						ListUpload.setExpandHorizontal(true);
						ListUpload.setBackground(SWTResourceManager
								.getColor(SWT.COLOR_TRANSPARENT));
						ListUpload.setBounds(28, 203, 648, 416);

						int i = 1;
						for (Device tmp : Vars.Devices) {
							Button selected = new Button(ListUpload, SWT.CHECK);
							selected.setBounds(10, i * 23, 500, 23);
							selected.setBackground(SWTResourceManager
									.getColor(SWT.COLOR_TRANSPARENT));
							selected.setText(tmp.imei + ' ' + tmp.location + ' '
									+ tmp.nItem);

							Text specialPlayCount = new Text(ListUpload,
									SWT.BORDER);
							specialPlayCount.setBounds(510, i * 23, 138, 23);
							specialPlayCount.setText("请填写播放次数");
							if (!withPlaycnt)
								specialPlayCount.setVisible(false);

							Vars.UploadDevices.add(selected);
							UploadPlayCount.add(specialPlayCount);
							i++;
						}

					}
				});

				Vars.UploadDevicesFlag = true;
				Vars.DevicesFlag = true;

			}
		}).start();
	}

	void ButtonsInit() {

		Button_Close.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseUp(MouseEvent e) {
				update.stop();
				deviceRefresh.stop();
				adsRefresh.stop();
				repeatsRefresh.stop();
				shell.dispose();
				try {
					excel();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Button_Upoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Lable_Upload_Attention.setText(upload());
			}
		});

		Button_Upload_ChooseFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				FileTreeDlg dlg = new FileTreeDlg(shell, SWT.ALL);
				dlg.open();
				TextBox_Upload_FilePath.setText(Vars.filePath);
			}
		});

		Button_AboutDevice.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseExit(MouseEvent arg0) {
				Button_AboutDevice.setBackground(
						SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			}

			public void mouseEnter(MouseEvent arg0) {
				Button_AboutDevice
						.setBackground(SWTResourceManager.getColor(38, 18, 10));
			}
		});
		Button_AboutDevice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				OptionSelect(1);
			}
		});

		Button_AboutADs.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseExit(MouseEvent arg0) {
				Button_AboutADs.setBackground(
						SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			}

			public void mouseEnter(MouseEvent arg0) {
				Button_AboutADs
						.setBackground(SWTResourceManager.getColor(38, 18, 10));
			}
		});
		Button_AboutADs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				OptionSelect(2);
			}
		});

		Button_AboutUpload.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseExit(MouseEvent arg0) {
				Button_AboutUpload.setBackground(
						SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			}

			public void mouseEnter(MouseEvent arg0) {
				Button_AboutUpload
						.setBackground(SWTResourceManager.getColor(38, 18, 10));
			}
		});
		Button_AboutUpload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				OptionSelect(3);
				if (Combo_Upload_FileType.getSelectionIndex() == 0) {
					Combo_Upload_YearPre.setVisible(false);
					Combo_Upload_MonthPre.setVisible(false);
					Combo_Upload_DayPre.setVisible(false);

					Combo_Upload_YearAfter.setVisible(false);
					Combo_Upload_MonthAfter.setVisible(false);
					Combo_Upload_DayAfter.setVisible(false);

					Lable_Upload_to.setVisible(false);
					Lable_Upload_During.setVisible(false);

					Lable_Upload_PlayCount.setVisible(true);
					TextBox_Upload_PlayConut.setVisible(true);
					
					uploadDeviceListRefresh(true);
				} else {
					Combo_Upload_YearPre.setVisible(true);
					Combo_Upload_MonthPre.setVisible(true);
					Combo_Upload_DayPre.setVisible(true);

					Combo_Upload_YearAfter.setVisible(true);
					Combo_Upload_MonthAfter.setVisible(true);
					Combo_Upload_DayAfter.setVisible(true);

					Lable_Upload_to.setVisible(true);
					Lable_Upload_During.setVisible(true);

					Lable_Upload_PlayCount.setVisible(false);
					TextBox_Upload_PlayConut.setVisible(false);
					
					uploadDeviceListRefresh(false);
				}
			}
		});

		Button_AboutRepeat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				OptionSelect(4);
			}
		});
		Button_AboutRepeat.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseExit(MouseEvent arg0) {
				Button_AboutRepeat.setBackground(
						SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			}

			public void mouseEnter(MouseEvent arg0) {
				Button_AboutRepeat
						.setBackground(SWTResourceManager.getColor(38, 18, 10));
			}
		});

		Combo_Upload_FileType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (Combo_Upload_FileType.getSelectionIndex() == 0) {
					Combo_Upload_YearPre.setVisible(false);
					Combo_Upload_MonthPre.setVisible(false);
					Combo_Upload_DayPre.setVisible(false);

					Combo_Upload_YearAfter.setVisible(false);
					Combo_Upload_MonthAfter.setVisible(false);
					Combo_Upload_DayAfter.setVisible(false);

					Lable_Upload_to.setVisible(false);
					Lable_Upload_During.setVisible(false);

					Lable_Upload_PlayCount.setVisible(true);
					TextBox_Upload_PlayConut.setVisible(true);
					uploadDeviceListRefresh(true);
				} else {
					Combo_Upload_YearPre.setVisible(true);
					Combo_Upload_MonthPre.setVisible(true);
					Combo_Upload_DayPre.setVisible(true);

					Combo_Upload_YearAfter.setVisible(true);
					Combo_Upload_MonthAfter.setVisible(true);
					Combo_Upload_DayAfter.setVisible(true);

					Lable_Upload_to.setVisible(true);
					Lable_Upload_During.setVisible(true);

					Lable_Upload_PlayCount.setVisible(false);
					TextBox_Upload_PlayConut.setVisible(false);

					uploadDeviceListRefresh(false);
				}
			}
		});
	}

	void OptionSelect(int OptionNum) {
		switch (OptionNum) {
		case 1:
			ContainerDevice.setEnabled(true);
			ContainerDevice.setVisible(true);

			ContainerADs.setEnabled(false);
			ContainerADs.setVisible(false);

			RootUpload.setEnabled(false);
			RootUpload.setVisible(false);

			ContainerRepeats.setEnabled(false);
			ContainerRepeats.setVisible(false);

			break;

		case 2:
			ContainerDevice.setEnabled(false);
			ContainerDevice.setVisible(false);

			ContainerADs.setEnabled(true);
			ContainerADs.setVisible(true);

			RootUpload.setEnabled(false);
			RootUpload.setVisible(false);

			ContainerRepeats.setEnabled(false);
			ContainerRepeats.setVisible(false);
			break;

		case 3:
			ContainerDevice.setEnabled(false);
			ContainerDevice.setVisible(false);

			ContainerADs.setEnabled(false);
			ContainerADs.setVisible(false);

			RootUpload.setEnabled(true);
			RootUpload.setVisible(true);

			ContainerRepeats.setEnabled(false);
			ContainerRepeats.setVisible(false);

			break;

		case 4:
			ContainerDevice.setEnabled(false);
			ContainerDevice.setVisible(false);

			ContainerADs.setEnabled(false);
			ContainerADs.setVisible(false);

			RootUpload.setEnabled(false);
			RootUpload.setVisible(false);

			ContainerRepeats.setEnabled(true);
			ContainerRepeats.setVisible(true);
			break;

		}
	}

	@SuppressWarnings("resource")
	void excel() throws Exception {
		// 11:创建一个excel
		// 1：分析数据
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(url, user,
				password);
		DatabaseMetaData dmd = connection.getMetaData();
		// 2:获取所有表名
		ResultSet rs = dmd.getTables("AdMachine", null, null,
				new String[] { "TABLE" });
		List<String> tables = new ArrayList<String>();

		while (rs.next()) {
			String tn = rs.getString("TABLE_NAME");
			tables.add(tn);
		}
		Statement st = connection.createStatement();

		for (String tn : tables) {
			HSSFWorkbook book = new HSSFWorkbook();
			HSSFSheet sheet = book.createSheet(tn);
			String sql = "select * from " + tn;
			rs = st.executeQuery(sql);
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			int cols = rsmd.getColumnCount();
			HSSFRow row = sheet.createRow(0);
			for (int i = 0; i < cols; i++) {
				String cname = rsmd.getColumnName(i + 1);
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(cname);
			}

			while (rs.next()) {
				row = sheet.createRow(sheet.getLastRowNum() + 1);
				for (int i = 0; i < cols; i++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellValue(rs.getString(i + 1));
				}
			}

			File file = new File("d:/AdMachine/");
			if (!file.exists())
				file.mkdirs();
			book.write(new FileOutputStream("d:/AdMachine/" + tn + ".xls"));
		}

	}
}
