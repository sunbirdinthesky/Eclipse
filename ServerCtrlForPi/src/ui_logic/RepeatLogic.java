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

public class RepeatLogic extends AdsSetUp {
	Vector<Button> ModifyIMEIs = new Vector<>();
	Vector<Text> ModifyPlayCounts = new Vector<>();
	boolean first = true;

	public RepeatLogic(AdFile target, Vector<Device> DeviceList) {
		super(target, DeviceList);
	}

	@Override
	public void logicInit() {
		TextBox_Name.setText(target.name);
		TextBox_PlayCount.setVisible(false);
		Label_PlayCount.setVisible(false);
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
//		Vector<Integer> PlayCounts = new Vector<>();

		ModifyIMEIs.clear();
		ModifyPlayCounts.clear();

		for (UploadIndex index : target.Config) {
			IMEIs.add(index.imei);
//			PlayCounts.add(index.playCount);
		}

		int i = 1;
		for (Device tmp : DeviceList) {
			Button selected = new Button(ListConfig, SWT.CHECK);
			selected.setBounds(10, i * 23, 500, 23);
			selected.setBackground(
					SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
			selected.setText(tmp.location + '\t' + tmp.nItem);

//			Text specialPlayCount = new Text(ListConfig, SWT.BORDER);
//			specialPlayCount.setBounds(510, i * 23, 138, 23);
//			specialPlayCount.setText("����д���Ŵ���");

			int mark = IMEIs.indexOf(tmp.imei);
			if (mark != -1) {
				selected.setSelection(true);
//				specialPlayCount.setText(PlayCounts.get(mark).toString());
			}

			ModifyIMEIs.add(selected);
//			ModifyPlayCounts.add(specialPlayCount);
			i++;
		}
		if (first) {
			buttonInit();
			first = false;
		}
	}

	void dateInit() {
		System.err.println(target.begin + target.end);

		String yearPre = Integer.valueOf(target.begin.substring(0, 4))
				.toString();
		String monthPre = Integer.valueOf(target.begin.substring(5, 7))
				.toString();
		String daPre = Integer.valueOf(target.begin.substring(8)).toString();

		String yearAft = Integer.valueOf(target.end.substring(0, 4)).toString();
		String monthAft = Integer.valueOf(target.end.substring(5, 7))
				.toString();
		String dayAft = Integer.valueOf(target.end.substring(8)).toString();

		Combo_Upload_YearPre.select(Combo_Upload_YearPre.indexOf(yearPre));
		Combo_Upload_MonthPre.select(Combo_Upload_MonthPre.indexOf(monthPre));

		Combo_Upload_YearAfter.select(Combo_Upload_YearAfter.indexOf(yearAft));
		Combo_Upload_MonthAfter
				.select(Combo_Upload_MonthAfter.indexOf(monthAft));

		Combo_Upload_DayPre.removeAll();
		int x = Combo_Upload_MonthPre.getSelectionIndex();
		int y = Combo_Upload_YearPre.getSelectionIndex();
		if (x == -1 || y == -1)
			return;
		if (x < 7) {
			if (x % 2 == 0) { // ����
				for (int i = 1; i <= 31; i++) {
					Combo_Upload_DayPre.add(String.valueOf(i));
				}
			} else {
				if (x == 1) {// ����
					for (int i = 1; i < 29; i++) {
						Combo_Upload_DayPre.add(String.valueOf(i));
					}
					if (Integer.valueOf(Combo_Upload_YearPre.getText())
							% 4 == 0) {// ����
						Combo_Upload_DayPre.add(String.valueOf(29));
					}
				} else { // С��
					for (int i = 1; i < 31; i++) {
						Combo_Upload_DayPre.add(String.valueOf(i));
					}
				}
			}
		} else {
			if (x % 2 == 1) { // ����
				for (int i = 1; i <= 31; i++) {
					Combo_Upload_DayPre.add(String.valueOf(i));
				}
			} else { // С��
				for (int i = 1; i < 31; i++) {
					Combo_Upload_DayPre.add(String.valueOf(i));
				}
			}
		}

		Combo_Upload_DayAfter.removeAll();
		x = Combo_Upload_MonthAfter.getSelectionIndex();
		y = Combo_Upload_YearAfter.getSelectionIndex();
		if (x == -1 || y == -1)
			return;
		if (x < 7) {
			if (x % 2 == 0) { // ����
				for (int i = 1; i <= 31; i++) {
					Combo_Upload_DayAfter.add(String.valueOf(i));
				}
			} else {
				if (x == 1) {// ����
					for (int i = 1; i < 29; i++) {
						Combo_Upload_DayAfter.add(String.valueOf(i));
					}
					if (Integer.valueOf(Combo_Upload_YearAfter.getText())
							% 4 == 0) {// ����
						Combo_Upload_DayAfter.add(String.valueOf(29));
					}
				} else { // С��
					for (int i = 1; i < 31; i++) {
						Combo_Upload_DayAfter.add(String.valueOf(i));
					}
				}
			}
		} else {
			if (x % 2 == 1) { // ����
				for (int i = 1; i <= 31; i++) {
					Combo_Upload_DayAfter.add(String.valueOf(i));
				}
			} else { // С��
				for (int i = 1; i < 31; i++) {
					Combo_Upload_DayAfter.add(String.valueOf(i));
				}
			}
		}

		Combo_Upload_DayAfter.select(Combo_Upload_DayAfter.indexOf(dayAft));
		Combo_Upload_DayPre.select(Combo_Upload_DayPre.indexOf(daPre));
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
				if (JOptionPane.showConfirmDialog(null, "����", "����",
						JOptionPane.YES_NO_OPTION) == 0) {
					Label_Attention.setText("ɾ����");
					new Thread() {
						public void run() {
							if (Delete()) {
								Display.getDefault().asyncExec(new Runnable() {
									public void run() {
										shell.dispose();
									}
								});
								JOptionPane.showMessageDialog(null, "��ɾ��", "��ʾ",
										JOptionPane.INFORMATION_MESSAGE);
							} else
								Label_Attention.setText("ɾ��ʧ��");
						}
					}.start();
				}
			}
		});
	}
	boolean Delete() {
		if (isConnected() || SqlInit()) {
			SqlUpdate("drop table repeats" + target.name);
			SqlUpdate(
					"delete from repeats where name = \"" + target.name + "\"");
			FtpOperations ftpOperations = new FtpOperations();
			ftpOperations.delete("/AdMachine/repeats/", target.name);
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
				return "�ַ���ֻ�ܺ��д�Сд��ĸ�����ֺ��»���";
			}
		}

		for (int i = 0; i < TextBox_Name.getText().length(); i++) {
			char ch = TextBox_Name.getText().charAt(i);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
					|| (ch >= '0' && ch <= '9') || (ch == '_')) {
			} else {
				return "�ַ���ֻ�ܺ��д�Сд��ĸ�����ֺ��»���";
			}
		}

		if (Combo_Upload_YearPre.getSelectionIndex() == -1
				|| Combo_Upload_YearAfter.getSelectionIndex() == -1
				|| Combo_Upload_MonthPre.getSelectionIndex() == -1
				|| Combo_Upload_MonthAfter.getSelectionIndex() == -1
				|| Combo_Upload_DayPre.getSelectionIndex() == -1
				|| Combo_Upload_DayAfter.getSelectionIndex() == -1) {
			return "����д��������";
		}

		String begin = ""; // ��ʽ����ʹ���ڣ���"2016-02-01"
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

		String end = ""; // ��ʽ����ֹ���ڣ���"2016-02-01"
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
			return "�������ڲ�ӦС�ڿ�ʼ����";
		}

//		try {
//			Integer.valueOf(TextBox_PlayCount.getText());
//		} catch (Exception e) {
//			return "����д��ȷ������";
//		}

		boolean checked = false;
		Vector<UploadIndex> pre = new Vector<>();
		for (Button tmp : ModifyIMEIs) {
			if (tmp.getSelection()) {
				checked = true;
				UploadIndex up = new UploadIndex();
				up.imei = DeviceList.get(ModifyIMEIs.indexOf(tmp)).imei;///////////
				up.playCount = -1;
				
				pre.add(up);
			}
		}

		if (!checked) {
			return "�����ٹ�ѡһ���豸";
		}

		final String lastName = target.name;
		System.out.println(lastName);
		target.name = TextBox_Name.getText();
		target.playCnt = -1;
		target.company = TextBox_Company.getText();
		target.begin = begin;
		target.end = end;
		target.Config = (Vector<UploadIndex>) pre.clone();

		boolean isSameName = TextBox_Name.getText().equals(target.name);
		Button_Confirm.setEnabled(false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (!isSameName && checkSQL(TextBox_Name.getText())) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							logicInit();
							Label_Attention.setText("�޸�ʧ�ܣ�����������ͬ���ļ����ڣ�");
							Button_Confirm.setEnabled(true);
						}
					});
					return;
				}
				
				FtpOperations ftpOperations = new FtpOperations();
				ftpOperations.rename(target.name, "/AdMachine/repeats/",
						lastName);

				SqlInit();
				SqlUpdate("update repeats set name = " + "\"" + target.name
						+ "\", md5 = \"" + target.md5 + "\", begin = \""
						+ target.begin + "\", end = \"" + target.end
						+ "\", company = \"" + target.company
						+ "\" where name = \"" + lastName + "\"");

				int def = 0;
				int sum = target.playCnt;
				int acc = 0;
				int mod = 0;
				for (UploadIndex index : target.Config) {// ��ʼ��������ÿ�������ò�����
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
				for (int i = 0; i < target.Config.size(); i++) {// ��ʼд�����ݿ�
					if (target.Config.get(i).playCount == -1) {
						target.Config.get(i).playCount = acc;
					} else {
						target.Config.get(i).playCount += acc;
					}
					if (i == 0) {
						target.Config.get(i).playCount += mod;
					}
				}

				SqlUpdate("alter table repeats" + lastName + " rename repeats"
						+ target.name);
				SqlUpdate("delete from repeats" + target.name);
				for (UploadIndex index : target.Config) {
					SqlUpdate("insert into repeats" + target.name
							+ " values (\"" + index.imei + "\", "
							+ index.playCount + ")");
				}

				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						logicInit();
						Label_Attention.setText("���й�����ɣ��Ѿ���");
						Button_Confirm.setEnabled(true);
					}
				});
			}
		}).start();

		return "���ڲ������ݿ�";
	}

	boolean checkSQL(String name) {
		boolean state = false;
		try {
			if (isConnected() || SqlInit()) {
				SqlQuery("select name from repeats where name = " + "\"" + name
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
			System.err.println("SQL����ʧ��");
			state = true;
		}

		return state;
	}

}