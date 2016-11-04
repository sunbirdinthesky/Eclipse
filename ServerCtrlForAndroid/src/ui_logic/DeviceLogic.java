package ui_logic;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;

import ui.DeviceSetUp;
import vars.Device;

public class DeviceLogic extends DeviceSetUp {

	public DeviceLogic(Device target) {
		super(target);
	}
	
	@Override
	public void logicInit() {
		TextBox_IMEI.setText(target.imei);
		TextBox_Location.setText(target.location);
		TextBox_nItem.setText(String.valueOf(target.nItem));
		Combo_SetUp.select(target.setUp);
		TextBox_Passwd.setText(target.passwd);
		buttonInit();
	}
	
	public void buttonInit () {
		Button_Close.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		
		Button_Confirm.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				target.location = TextBox_Location.getText();
				target.setUp = Combo_SetUp.getSelectionIndex();
				target.passwd = TextBox_Passwd.getText();
				
				for (int i = 0; i < target.location.length(); i++) {
					char ch = target.location.charAt(i);
					if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
							|| (ch >= '0' && ch <= '9') || (ch == '_')) {
					} else {
						Label_Attention.setText("地点中只能含有大小写字母，数字和下划线");
						return;
					}
				}
				
				for (int i = 0; i < target.passwd.length(); i++) {
					char ch = target.passwd.charAt(i);
					if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
							|| (ch >= '0' && ch <= '9') || (ch == '_')) {
					} else {
						Label_Attention.setText("密码中只能含有大小写字母，数字和下划线");
						return;
					}
				}
				
				try {
					target.nItem = Integer.valueOf(TextBox_nItem.getText());
				} catch (Exception e2) {
					Label_Attention.setText("请填写正确的整数");
					return;
				}
				Label_Attention.setText("正在操作数据库，请稍后");
				new Thread() {
					public void run() {
						if (isConnected() || SqlInit()) {
							SqlUpdate("update device set location = \"" 
									+ target.location + "\", setup = " 
									+ target.setUp + ", passwd = \"" 
									+ target.passwd + "\" where imei = \""
									+ target.imei + "\"");
							Display.getDefault().asyncExec(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									Label_Attention.setText("修改完毕");
								}
							});
						}
					};
				}.start();
			}
		});
	}
	
}
