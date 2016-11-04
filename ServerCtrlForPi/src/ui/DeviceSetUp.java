package ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import vars.Device;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.wb.swt.SWTResourceManager;

import sqlOperation.SqlOperation;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class DeviceSetUp extends SqlOperation {

	protected Shell shell;
	protected Device target = new Device();
	protected Text TextBox_Location;
	protected Label Label_Location;
	protected Label Label_nItem;
	protected Label Label_IMEI;
	protected Label TextBox_IMEI;
	protected Label Label_Attention;
	protected Button Button_Confirm;
	protected Button Button_Close;
	protected Label Label_SetUp;
	protected Combo Combo_SetUp;
	private Composite MoveBar;
	
	protected boolean KeyDown;
	protected int argx, argy;
	protected Label TextBox_nItem;
	protected Label Label_Passwd;
	protected Text TextBox_Passwd;
	
	public DeviceSetUp(Device target) {
		this.target = target;
	}
	
	public void Init () {
		MoveBar.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// TODO Auto-generated method stub
				KeyDown = false;
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				// TODO Auto-generated method stub
				KeyDown = true;
				argx = arg0.x;
				argy = arg0.y;
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		MoveBar.addMouseMoveListener(new MouseMoveListener() {

			@Override
			public void mouseMove(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (KeyDown)
					shell.setLocation(shell.getLocation().x + arg0.x - argx,
							shell.getLocation().y + arg0.y - argy);
			}
		});
		logicInit();
	}
	
	public void logicInit () {}
	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		Init();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(SWT.NO_TRIM);
		shell.setBackgroundImage(SWTResourceManager.getImage(DeviceSetUp.class, "/UIImage/background.png"));
		shell.setSize(400, 325);
		
		
		Label_IMEI = new Label(shell, SWT.NONE);
		Label_IMEI.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label_IMEI.setBounds(10, 68, 104, 23);
		Label_IMEI.setText("imei\u7F16\u53F7");
		
		Label_Location = new Label(shell, SWT.NONE);
		Label_Location.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label_Location.setBounds(10, 94, 104, 23);
		Label_Location.setText("\u6240\u5728\u5730");
		
		Label_nItem = new Label(shell, SWT.NONE);
		Label_nItem.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label_nItem.setBounds(10, 158, 104, 23);
		Label_nItem.setText("\u7269\u54C1\u5269\u4F59\u91CF");
		
		TextBox_Location = new Text(shell, SWT.BORDER);
		TextBox_Location.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		TextBox_Location.setBounds(120, 91, 264, 26);
		TextBox_Location.setText("");
		
		TextBox_IMEI = new Label(shell, SWT.NONE);
		TextBox_IMEI.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		TextBox_IMEI.setBounds(120, 68, 264, 20);
		TextBox_IMEI.setText("");
		
		Button_Confirm = new Button(shell, SWT.NONE);
		Button_Confirm.setBounds(135, 230, 120, 47);
		Button_Confirm.setText("\u63D0\u4EA4");
		
		Label_Attention = new Label(shell, SWT.CENTER);
		Label_Attention.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label_Attention.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 9, SWT.NORMAL));
		Label_Attention.setText("\u8B66\u544A\uFF1A\u8BF7\u4F7F\u7528\u82F1\u6587\u5B57\u7B26");
		Label_Attention.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		Label_Attention.setBounds(10, 283, 374, 32);
		
		Label_SetUp = new Label(shell, SWT.NONE);
		Label_SetUp.setText("\u662F\u5426\u53EF\u8BBE\u5B9A");
		Label_SetUp.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label_SetUp.setBounds(10, 123, 104, 29);
		
		Combo_SetUp = new Combo(shell, SWT.READ_ONLY);
		Combo_SetUp.setItems(new String[] {"\u4E0D\u53EF\u8BBE\u5B9A \uFF08SetUp = 0\uFF09", "\u53EF\u8BBE\u5B9A\uFF08SetUp = 1\uFF09"});
		Combo_SetUp.setBounds(120, 123, 264, 32);
		Combo_SetUp.select(0);
		
		Button_Close = new Button(shell, SWT.CENTER);
		Button_Close.setLocation(10, 10);
		Button_Close.setSize(20, 20);
		Button_Close.setBackgroundImage(SWTResourceManager.getImage(DeviceSetUp.class, "/UIImage/close2.png"));
		Button_Close.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Button_Close.setImage(SWTResourceManager.getImage(DeviceSetUp.class, "/UIImage/close2.png"));
		
		MoveBar = new Composite(shell, SWT.NONE);
		MoveBar.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		MoveBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		MoveBar.setBounds(0, 0, 400, 20);
		
		TextBox_nItem = new Label(shell, SWT.NONE);
		TextBox_nItem.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		TextBox_nItem.setBounds(120, 158, 264, 24);
		
		Label_Passwd = new Label(shell, SWT.NONE);
		Label_Passwd.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label_Passwd.setBounds(10, 187, 104, 24);
		Label_Passwd.setText("\u8BBE\u5907\u5BC6\u7801");
		
		TextBox_Passwd = new Text(shell, SWT.BORDER);
		TextBox_Passwd.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		TextBox_Passwd.setBounds(120, 188, 264, 30);
	}
}
