package ui_logic;

import java.util.Vector;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import ftpOperation.FtpOperations;
import ui.AdsSetUp;
import vars.AdFile;
import vars.Device;
import vars.UploadIndex;

public class AdsLogic extends AdsSetUp {
	Vector<Button> ModifyIMEIs = new Vector<>();
	Vector<Text> ModifyPlayCounts = new Vector<>();
	boolean first = true;

	public AdsLogic(AdFile target, Vector<Device> DeviceList) {
		super(target, DeviceList);
	}

	@Override
	public void logicInit() {
		TextBox_Name.setText(target.name);
		TextBox_PlayCount.setText(String.valueOf(target.playCnt));
		TextBox_Company.setText(target.company);

		dateInit();

		ListConfig.dispose();
		ListConfig = new ScrolledComposite(composite,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		ListConfig.setExpandVertical(true);
		ListConfig.setExpandHorizontal(true);
		ListConfig.setBackground(
				SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		ListConfig.setBounds(26, 224, 640, 350);
		ListConfig.setMinSize(new Point(64, 64));

		Vector<String> IMEIs = new Vector<>();
		Vector<Integer> PlayCounts = new Vector<>();

		ModifyIMEIs.clear();
		ModifyPlayCounts.clear();

		for (UploadIndex index : target.Config) {
			IMEIs.add(index.imei);
			PlayCounts.add(index.playCount);
		}

		int i = 1;
		for (Device tmp : DeviceList) {
			Button selected = new Button(ListConfig, SWT.CHECK);
			selected.setBounds(10, i * 23, 500, 23);
			selected.setBackground(
					SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			selected.setText(tmp.location + '\t' + tmp.nItem);

			Text specialPlayCount = new Text(ListConfig, SWT.BORDER);
			specialPlayCount.setBounds(510, i * 23, 138, 23);
			specialPlayCount.setText("请填写播放次数");

			int mark = IMEIs.indexOf(tmp.imei);
			if (mark != -1) {
				selected.setSelection(true);
				specialPlayCount.setText(PlayCounts.get(mark).toString());
			}

			ModifyIMEIs.add(selected);
			ModifyPlayCounts.add(specialPlayCount);
			i++;
		}
		if (first) {
			buttonInit();
			first = false;
		}
	}

	void dateInit() {
		Combo_Upload_YearPre.setVisible(false);
		Combo_Upload_MonthPre.setVisible(false);
		Combo_Upload_YearAfter.setVisible(false);
		Combo_Upload_MonthAfter.setVisible(false);
		Combo_Upload_DayPre.setVisible(false);
		Combo_Upload_DayAfter.setVisible(false);
		Label_End.setVisible(false);
		Label_begin.setVisible(false);
	}

	void buttonInit() {
		Button_Close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.dispose();
			}
		});

		Button_Confirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Label_Attention.setText(Upload());
			}
		});

		Button_Delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (JOptionPane.showConfirmDialog(null, "警告", "警告",
						JOptionPane.YES_NO_OPTION) == 0) {
					Label_Attention.setText("删除中");
					new Thread() {
						public void run() {
							if (Delete()) {
								Display.getDefault().asyncExec(new Runnable() {
									public void run() {
										shell.dispose();
									}
								});
								JOptionPane.showMessageDialog(null, "已删除", "提示",
										JOptionPane.INFORMATION_MESSAGE);
							} else
								Label_Attention.setText("删除失败");
						}
					}.start();
				}
			}
		});
	}

	boolean Delete() {
		if (isConnected() || SqlInit()) {
			SqlUpdate("drop table ads" + target.name);
			SqlUpdate("delete from ads where name = \"" + target.name + "\"");
			FtpOperations ftpOperations = new FtpOperations();
			ftpOperations.delete("/AdMachine/ads/", target.name);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	String Upload() {

		for (int i = 0; i < TextBox_Company.getText().length(); i++) {
			char ch = TextBox_Company.getText().charAt(i);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
					|| (ch >= '0' && ch <= '9') || (ch == '_')) {
			} else {
				return "字符中只能含有大小写字母，数字和下划线";
			}
		}

		for (int i = 0; i < TextBox_Name.getText().length(); i++) {
			char ch = TextBox_Name.getText().charAt(i);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
					|| (ch >= '0' && ch <= '9') || (ch == '_')) {
			} else {
				return "字符中只能含有大小写字母，数字和下划线";
			}
		}

		// if (Combo_Upload_YearPre.getSelectionIndex() == -1
		// || Combo_Upload_YearAfter.getSelectionIndex() == -1
		// || Combo_Upload_MonthPre.getSelectionIndex() == -1
		// || Combo_Upload_MonthAfter.getSelectionIndex() == -1
		// || Combo_Upload_DayPre.getSelectionIndex() == -1
		// || Combo_Upload_DayAfter.getSelectionIndex() == -1) {
		// return "请填写完整日期";
		// }
		//
		// String begin = ""; // 格式化初使日期，如"2016-02-01"
		// begin += Combo_Upload_YearPre.getText();
		// begin += "-";
		// if (Combo_Upload_MonthPre.getText().length() == 1) {
		// begin += "0";
		// }
		// begin += Combo_Upload_MonthPre.getText();
		// begin += "-";
		// if (Combo_Upload_DayPre.getText().length() == 1) {
		// begin += "0";
		// }
		// begin += Combo_Upload_DayPre.getText();
		//
		// String end = ""; // 格式化终止日期，如"2016-02-01"
		// end += Combo_Upload_YearAfter.getText();
		// end += "-";
		// if (Combo_Upload_MonthAfter.getText().length() == 1) {
		// end += "0";
		// }
		// end += Combo_Upload_MonthAfter.getText();
		// end += "-";
		// if (Combo_Upload_DayAfter.getText().length() == 1) {
		// end += "0";
		// }
		// end += Combo_Upload_DayAfter.getText();
		//
		// if (end.compareTo(begin) < 0) {
		// return "结束日期不应小于开始日期";
		// }

		try {
			Integer.valueOf(TextBox_PlayCount.getText());
		} catch (Exception e) {
			return "请填写正确的整数";
		}

		boolean checked = false;
		int cnt = 0;
		Vector<UploadIndex> pre = new Vector<>();
		for (Button tmp : ModifyIMEIs) {
			if (tmp.getSelection()) {
				checked = true;
				UploadIndex up = new UploadIndex();
				up.imei = DeviceList.get(ModifyIMEIs.indexOf(tmp)).imei;
				try {
					up.playCount = Integer
							.valueOf(ModifyPlayCounts.get(cnt).getText());
				} catch (Exception e) {
					up.playCount = -1;
				}
				pre.add(up);
			}
			cnt++;
		}

		if (!checked) {
			return "请至少勾选一个设备";
		}

		final String lastName = target.name;
		target.name = TextBox_Name.getText();
		target.playCnt = Integer.valueOf(TextBox_PlayCount.getText());
		target.company = TextBox_Company.getText();
		// target.begin = begin;
		// target.end = end;
		target.Config = (Vector<UploadIndex>) pre.clone();

		System.out.println(lastName + ' ' + target.name);

		boolean isSameName = TextBox_Name.getText().equals(target.name);
		Button_Confirm.setEnabled(false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (!isSameName && checkSQL(TextBox_Name.getText())) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							logicInit();
							Label_Attention.setText("修改失败，服务器上有同名文件存在？");
							Button_Confirm.setEnabled(true);
						}
					});
					return;
				}

				FtpOperations ftpOperations = new FtpOperations();
				ftpOperations.rename(target.name, "/AdMachine/ads/", lastName);

				SqlInit();
				SqlUpdate("update ads set name = " + "\"" + target.name + "\""
						+ ", playCount = " + target.playCnt + ", md5 = \""
						+ target.md5 + "\", company = \"" + target.company
						+ "\" where name = \"" + lastName + "\"");

				int def = 0;
				int sum = target.playCnt;
				int acc = 0;
				int mod = 0;
				for (UploadIndex index : target.Config) {// 初始化，计算每个广告机该播几个
					if (index.playCount == -1)
						def++;
					else
						sum -= index.playCount;
				}
				if (def == 0) {
					acc = sum / target.Config.size();
					mod = sum % target.Config.size();
				} else {
					acc = sum / def;
					mod = sum % def;
				}
				for (int i = 0; i < target.Config.size(); i++) {// 开始写入数据库
					if (target.Config.get(i).playCount == -1) {
						target.Config.get(i).playCount = acc;
					} else {
						target.Config.get(i).playCount += acc;
					}
					if (i == 0) {
						target.Config.get(i).playCount += mod;
					}
				}

				SqlUpdate("alter table ads" + lastName + " rename ads"
						+ target.name);
				SqlUpdate("delete from ads" + target.name);
				for (UploadIndex index : target.Config) {
					SqlUpdate("insert into ads" + target.name + " values (\""
							+ index.imei + "\", " + index.playCount + ")");
				}

				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						logicInit();
						Label_Attention.setText("所有工作完成，已就绪");
						Button_Confirm.setEnabled(true);
					}
				});
			}
		}).start();

		return "正在操作数据库";
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

}
