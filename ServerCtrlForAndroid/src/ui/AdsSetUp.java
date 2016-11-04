package ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import sqlOperation.SqlOperation;
import vars.AdFile;
import vars.Device;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

public class AdsSetUp extends SqlOperation {

	protected Shell shell;
	public AdFile target = new AdFile();
	public Vector<Device> DeviceList = new Vector<>();
	public boolean KeyDown;
	public int argx, argy;
	
	public Text TextBox_Name;
	public Text TextBox_PlayCount;
	public Text TextBox_Company;
	public Label Label_begin;
	public Label Line;
	public Label Label_Company;
	public Label Label_PlayCount;
	public Label Label_Name;
	public Composite composite;
	public ScrolledComposite ListConfig;
	public Composite MoveBar;
	public Button Button_Close;
	public Label Label_End;
	public Label Label_Attention;
	public Button Button_Confirm;
	public Button Button_Delete;
	public Combo Combo_Upload_YearPre;
	public Combo Combo_Upload_MonthPre;
	public Combo Combo_Upload_DayPre;
	public Combo Combo_Upload_YearAfter;
	public Combo Combo_Upload_MonthAfter;
	public Combo Combo_Upload_DayAfter;
	
	@SuppressWarnings("unchecked")
	public AdsSetUp(AdFile target, Vector<Device> DeviceList) {
		this.target = target;
		this.DeviceList = (Vector<Device>) DeviceList.clone();
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
		YearMonthDayInit();
		logicInit();
	}
	
	void YearMonthDayInit () {
		Combo_Upload_YearPre.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Combo_Upload_DayPre.removeAll();
				int x = Combo_Upload_MonthPre.getSelectionIndex();
				int y = Combo_Upload_YearPre.getSelectionIndex();
				if (x == -1 || y == -1)
					return;
				if (x < 7) {
					if (x % 2 == 0) { // 大月
						for (int i = 1; i <= 31; i++) {
							Combo_Upload_DayPre.add(String.valueOf(i));
						}
					} else {
						if (x == 1) {// 二月
							for (int i = 1; i < 29; i++) {
								Combo_Upload_DayPre.add(String.valueOf(i));
							}
							if (Integer.valueOf(Combo_Upload_YearPre.getText()) % 4 == 0) {// 闰月
								Combo_Upload_DayPre.add(String.valueOf(29));
							}
						} else { // 小月
							for (int i = 1; i < 31; i++) {
								Combo_Upload_DayPre.add(String.valueOf(i));
							}
						}
					}
				} else {
					if (x % 2 == 1) { // 大月
						for (int i = 1; i <= 31; i++) {
							Combo_Upload_DayPre.add(String.valueOf(i));
						}
					} else { // 小月
						for (int i = 1; i < 31; i++) {
							Combo_Upload_DayPre.add(String.valueOf(i));
						}
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Combo_Upload_MonthPre.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Combo_Upload_DayPre.removeAll();
				int x = Combo_Upload_MonthPre.getSelectionIndex();
				int y = Combo_Upload_YearPre.getSelectionIndex();
				if (x == -1 || y == -1)
					return;
				if (x < 7) {
					if (x % 2 == 0) { // 大月
						for (int i = 1; i <= 31; i++) {
							Combo_Upload_DayPre.add(String.valueOf(i));
						}
					} else {
						if (x == 1) {// 二月
							for (int i = 1; i < 29; i++) {
								Combo_Upload_DayPre.add(String.valueOf(i));
							}
							if (Integer.valueOf(Combo_Upload_YearPre.getText()) % 4 == 0) {// 闰月
								Combo_Upload_DayPre.add(String.valueOf(29));
							}
						} else { // 小月
							for (int i = 1; i < 31; i++) {
								Combo_Upload_DayPre.add(String.valueOf(i));
							}
						}
					}
				} else {
					if (x % 2 == 1) { // 大月
						for (int i = 1; i <= 31; i++) {
							Combo_Upload_DayPre.add(String.valueOf(i));
						}
					} else { // 小月
						for (int i = 1; i < 31; i++) {
							Combo_Upload_DayPre.add(String.valueOf(i));
						}
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		Combo_Upload_YearAfter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Combo_Upload_DayAfter.removeAll();
				int x = Combo_Upload_MonthAfter.getSelectionIndex();
				int y = Combo_Upload_YearAfter.getSelectionIndex();
				if (x == -1 || y == -1)
					return;
				if (x < 7) {
					if (x % 2 == 0) { // 大月
						for (int i = 1; i <= 31; i++) {
							Combo_Upload_DayAfter.add(String.valueOf(i));
						}
					} else {
						if (x == 1) {// 二月
							for (int i = 1; i < 29; i++) {
								Combo_Upload_DayAfter.add(String.valueOf(i));
							}
							if (Integer.valueOf(Combo_Upload_YearAfter.getText()) % 4 == 0) {// 闰月
								Combo_Upload_DayAfter.add(String.valueOf(29));
							}
						} else { // 小月
							for (int i = 1; i < 31; i++) {
								Combo_Upload_DayAfter.add(String.valueOf(i));
							}
						}
					}
				} else {
					if (x % 2 == 1) { // 大月
						for (int i = 1; i <= 31; i++) {
							Combo_Upload_DayAfter.add(String.valueOf(i));
						}
					} else { // 小月
						for (int i = 1; i < 31; i++) {
							Combo_Upload_DayAfter.add(String.valueOf(i));
						}
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Combo_Upload_MonthAfter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Combo_Upload_DayAfter.removeAll();
				int x = Combo_Upload_MonthAfter.getSelectionIndex();
				int y = Combo_Upload_YearAfter.getSelectionIndex();
				if (x == -1 || y == -1)
					return;
				if (x < 7) {
					if (x % 2 == 0) { // 大月
						for (int i = 1; i <= 31; i++) {
							Combo_Upload_DayAfter.add(String.valueOf(i));
						}
					} else {
						if (x == 1) {// 二月
							for (int i = 1; i < 29; i++) {
								Combo_Upload_DayAfter.add(String.valueOf(i));
							}
							if (Integer.valueOf(Combo_Upload_YearAfter.getText()) % 4 == 0) {// 闰月
								Combo_Upload_DayAfter.add(String.valueOf(29));
							}
						} else { // 小月
							for (int i = 1; i < 31; i++) {
								Combo_Upload_DayAfter.add(String.valueOf(i));
							}
						}
					}
				} else {
					if (x % 2 == 1) { // 大月
						for (int i = 1; i <= 31; i++) {
							Combo_Upload_DayAfter.add(String.valueOf(i));
						}
					} else { // 小月
						for (int i = 1; i < 31; i++) {
							Combo_Upload_DayAfter.add(String.valueOf(i));
						}
					}
				}
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
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
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.NO_TRIM);
		shell.setBackgroundImage(SWTResourceManager.getImage(AdsSetUp.class, "/UIImage/background.png"));
		shell.setSize(700, 700);
		
		Button_Close = new Button(shell, SWT.CENTER);
		Button_Close.setImage(SWTResourceManager.getImage(AdsSetUp.class, "/UIImage/close2.png"));
		Button_Close.setBackgroundImage(SWTResourceManager.getImage(AdsSetUp.class, "/UIImage/close2.png"));
		Button_Close.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Button_Close.setBounds(10, 10, 20, 20);
		
		composite = new Composite(shell, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		composite.setBounds(0, 20, 700, 680);
		
		Line = new Label(composite, SWT.NONE);
		Line.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		Line.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		Line.setBounds(26, 47, 640, 1);
		
		Label_Name = new Label(composite, SWT.NONE);
		Label_Name.setText("\u540D\u79F0");
		Label_Name.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Label_Name.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label_Name.setBounds(57, 16, 73, 25);
		
		Label_PlayCount = new Label(composite, SWT.NONE);
		Label_PlayCount.setText("\u603B\u64AD\u653E\u6B21\u6570");
		Label_PlayCount.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Label_PlayCount.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label_PlayCount.setBounds(272, 16, 80, 25);
		
		Label_Company = new Label(composite, SWT.NONE);
		Label_Company.setText("\u6240\u5C5E\u5355\u4F4D");
		Label_Company.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Label_Company.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label_Company.setBounds(532, 10, 80, 25);
		
		Label_begin = new Label(composite, SWT.NONE);
		Label_begin.setText("\u5F00\u59CB\u65F6\u95F4");
		Label_begin.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Label_begin.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label_begin.setBounds(26, 124, 105, 25);
		
		ListConfig = new ScrolledComposite(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		ListConfig.setExpandVertical(true);
		ListConfig.setExpandHorizontal(true);
		ListConfig.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		ListConfig.setBounds(26, 224, 640, 350);
		ListConfig.setMinSize(new Point(64, 64));
		
		TextBox_Name = new Text(composite, SWT.BORDER);
		TextBox_Name.setBounds(26, 65, 105, 30);
		
		TextBox_PlayCount = new Text(composite, SWT.BORDER);
		TextBox_PlayCount.setBounds(272, 65, 80, 30);
		
		TextBox_Company = new Text(composite, SWT.BORDER);
		TextBox_Company.setBounds(499, 65, 130, 30);
		
		Label_End = new Label(composite, SWT.NONE);
		Label_End.setText("\u7ED3\u675F\u65F6\u95F4");
		Label_End.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Label_End.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label_End.setBounds(26, 173, 105, 25);
		
		Label_Attention = new Label(composite, SWT.NONE);
		Label_Attention.setAlignment(SWT.CENTER);
		Label_Attention.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Label_Attention.setBounds(428, 602, 235, 68);
		Label_Attention.setText("\u8B66\u544A\uFF1A\u8BF7\u4F7F\u7528\u82F1\u6587\u5B57\u7B26");
		
		Button_Delete = new Button(composite, SWT.NONE);
		Button_Delete.setText("\u5220\u9664");
		Button_Delete.setBounds(26, 597, 114, 34);
		
		Button_Confirm = new Button(composite, SWT.NONE);
		Button_Confirm.setBounds(260, 597, 114, 34);
		Button_Confirm.setText("\u63D0\u4EA4");
		
		MoveBar = new Composite(shell, SWT.NONE);
		MoveBar.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		MoveBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		MoveBar.setBounds(0, 0, 700, 22);

		
		Combo_Upload_YearPre = new Combo(composite, SWT.READ_ONLY);
		Combo_Upload_YearPre.setItems(new String[] {"2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"});
		Combo_Upload_YearPre.setBounds(140, 119, 80, 32);
		
		Combo_Upload_MonthPre = new Combo(composite, SWT.READ_ONLY);
		Combo_Upload_MonthPre.setItems(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
		Combo_Upload_MonthPre.setBounds(251, 119, 80, 32);
		
		Combo_Upload_DayPre = new Combo(composite, SWT.READ_ONLY);
		Combo_Upload_DayPre.setItems(new String[] {});
		Combo_Upload_DayPre.setBounds(357, 119, 80, 32);
		
		Combo_Upload_YearAfter = new Combo(composite, SWT.READ_ONLY);
		Combo_Upload_YearAfter.setItems(new String[] {"2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"});
		Combo_Upload_YearAfter.setBounds(140, 168, 80, 32);
		
		Combo_Upload_MonthAfter = new Combo(composite, SWT.READ_ONLY);
		Combo_Upload_MonthAfter.setItems(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
		Combo_Upload_MonthAfter.setBounds(251, 168, 80, 32);
		
		Combo_Upload_DayAfter = new Combo(composite, SWT.READ_ONLY);
		Combo_Upload_DayAfter.setItems(new String[] {});
		Combo_Upload_DayAfter.setBounds(357, 168, 80, 32);
	}
}
