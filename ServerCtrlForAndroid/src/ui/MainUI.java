package ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import sqlOperation.SqlOperation;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;

public class MainUI extends SqlOperation {
	
	boolean test = false;

	public Shell shell;
	public Text TextBox_Upload_FilePath;
	public Text TextBox_Upload_PlayConut;
	public Composite ContainerDevice;
	public Label Lable_Ads_Name;
	public Label Lable_Upload_During;
	public Label Lable_Ads_SetUp;
	public Composite MoveBarExtend;
	public Label AboutApplicationName;
	public Label Lable_Ads_PlayCount;
	public Label Button_AboutUpload;
	public Label Lable_Upload_FileType;
	public Composite ContainerOption;
	public Label line1;
	public Combo Combo_Upload_YearPre;
	public Composite ContainerUpload;
	public Label Lable_Upload_PlayCount;
	public ScrolledComposite ListUpload;
	public Label Lable_Device_IMEI;
	public Combo Combo_Upload_DayPre;
	public Label Lable_Device_RemainItemNum;
	public Composite RootUpload;
	public Label Lable_Upload_FileLocation;
	public Label Lable_Upload_to;
	public Label Lable_Upload_Attention;
	public Composite ContainerMain;
	public Button Button_Upoad;
	public Combo Combo_Upload_FileType;
	public Label Lable_Device_Location;
	public Combo Combo_Upload_YearAfter;
	public Label Button_AboutDevice;
	public Combo Combo_Upload_MonthPre;
	public Label Button_AboutADs;
	public Label line2;
	public Combo Combo_Upload_DayAfter;
	public Composite ContainerADs;
	public Composite MoveBar;
	public Button Button_Close;
	public Combo Combo_Upload_MonthAfter;
	public ScrolledComposite ListAds;
	public ScrolledComposite ListDevice;
	public Text TextBox_Upload_Company;
	public Label label_Upload_Company;
	public Label Button_AboutRepeat;
	public Button Button_Upload_ChooseFile;
	public ScrolledComposite ListRepeats;
	public Label Lable_Repeats_Name;
	public Label Lable_Repeats_SetUp;
	public Label Lable_Repeats_During;
	public Composite ContainerRepeats;
	
	public boolean KeyDown;
	public int argx, argy;
	public Label Lable_Device_SetUp;
	public Label Attention;
	


	

	public void LogicInit () {}
	
	void Init () {
		if (!test) {
			shell.setSize(1000, 700);
			ContainerADs.setBounds(300, 0, 700, 700);
			RootUpload.setBounds(300, 0, 700, 700);
			ContainerRepeats.setBounds(300, 0, 700, 700);
		}
		MoveBarInit();
		TextInit();
		YearMonthDayInit();
		
		LogicInit ();
	}
	
	void TextInit () {
		
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
	
	void MoveBarInit () {
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

		MoveBarExtend.addMouseListener(new MouseListener() {

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
		MoveBarExtend.addMouseMoveListener(new MouseMoveListener() {

			@Override
			public void mouseMove(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (KeyDown)
					shell.setLocation(shell.getLocation().x + arg0.x - argx,
							shell.getLocation().y + arg0.y - argy);
			}
		});
	}
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
		shell.setSize(3000, 2800);
		shell.setText("ServerCtrl");
		
		ContainerMain = new Composite(shell, SWT.NONE);
		ContainerMain.setBackgroundImage(SWTResourceManager.getImage(MainUI.class, "/UIImage/background.png"));
		ContainerMain.setBounds(0, 0, 3000, 2800);
		
		MoveBar = new Composite(ContainerMain, SWT.NONE);
		MoveBar.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		MoveBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		MoveBar.setBounds(300, 0, 700, 20);
		
		ContainerOption = new Composite(ContainerMain, SWT.NONE);
		ContainerOption.setBackgroundImage(SWTResourceManager.getImage(MainUI.class, "/UIImage/OptionsBackground.png"));
		ContainerOption.setBounds(0, 0, 300, 700);
		
		Button_Close = new Button(ContainerOption, SWT.CENTER);
		Button_Close.setImage(SWTResourceManager.getImage(MainUI.class, "/UIImage/close.png"));
		Button_Close.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Button_Close.setBounds(20, 20, 20, 20);
		
		MoveBarExtend = new Composite(ContainerOption, SWT.NONE);
		MoveBarExtend.setForeground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		MoveBarExtend.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		MoveBarExtend.setBounds(0, 0, 300, 20);
		
		AboutApplicationName = new Label(ContainerOption, SWT.SHADOW_NONE | SWT.CENTER);
		AboutApplicationName.setText("Server Control System beta");
		AboutApplicationName.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		AboutApplicationName.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.BOLD));
		AboutApplicationName.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		AboutApplicationName.setAlignment(SWT.CENTER);
		AboutApplicationName.setBounds(10, 60, 280, 123);
		
		Button_AboutDevice = new Label(ContainerOption, SWT.CENTER);
		Button_AboutDevice.setTouchEnabled(true);
		Button_AboutDevice.setText("\u8BBE\u5907\u7BA1\u7406");
		Button_AboutDevice.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		Button_AboutDevice.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 26, SWT.NORMAL));
		Button_AboutDevice.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Button_AboutDevice.setAlignment(SWT.CENTER);
		Button_AboutDevice.setBounds(0, 250, 300, 70);
		
		Button_AboutUpload = new Label(ContainerOption, SWT.CENTER);
		Button_AboutUpload.setTouchEnabled(true);
		Button_AboutUpload.setText("\u6587\u4EF6\u4E0A\u4F20");
		Button_AboutUpload.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		Button_AboutUpload.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 26, SWT.NORMAL));
		Button_AboutUpload.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Button_AboutUpload.setAlignment(SWT.CENTER);
		Button_AboutUpload.setBounds(0, 478, 300, 70);
		
		Button_AboutADs = new Label(ContainerOption, SWT.CENTER);
		Button_AboutADs.setTouchEnabled(true);
		Button_AboutADs.setText("\u5B9A\u70B9\u6295\u653E");
		Button_AboutADs.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		Button_AboutADs.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 26, SWT.NORMAL));
		Button_AboutADs.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Button_AboutADs.setAlignment(SWT.CENTER);
		Button_AboutADs.setBounds(0, 326, 300, 70);
		
		Button_AboutRepeat = new Label(ContainerOption, SWT.CENTER);
		Button_AboutRepeat.setTouchEnabled(true);
		Button_AboutRepeat.setText("\u6EDA\u52A8\u5E7F\u544A");
		Button_AboutRepeat.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		Button_AboutRepeat.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 26, SWT.NORMAL));
		Button_AboutRepeat.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Button_AboutRepeat.setAlignment(SWT.CENTER);
		Button_AboutRepeat.setBounds(0, 402, 300, 70);
		
		Attention = new Label(ContainerOption, SWT.NONE);
		Attention.setVisible(true);
		Attention.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.NORMAL));
		Attention.setAlignment(SWT.CENTER);
		Attention.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		Attention.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Attention.setBounds(10, 604, 280, 59);
		
		ContainerDevice = new Composite(ContainerMain, SWT.NONE);
		ContainerDevice.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		ContainerDevice.setBounds(300, 0, 700, 700);
		
		line1 = new Label(ContainerDevice, SWT.NONE);
		line1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		line1.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		line1.setBounds(26, 130, 640, 1);
		
		Lable_Device_IMEI = new Label(ContainerDevice, SWT.NONE);
		Lable_Device_IMEI.setText("IMEI\u7F16\u53F7");
		Lable_Device_IMEI.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Lable_Device_IMEI.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Device_IMEI.setBounds(221, 99, 73, 25);
		
		Lable_Device_Location = new Label(ContainerDevice, SWT.NONE);
		Lable_Device_Location.setText("\u6240\u5728\u5730");
		Lable_Device_Location.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Lable_Device_Location.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Device_Location.setBounds(73, 99, 61, 25);
		
		Lable_Device_RemainItemNum = new Label(ContainerDevice, SWT.NONE);
		Lable_Device_RemainItemNum.setText("\u5269\u4F59\u7269\u54C1\u6570\u91CF");
		Lable_Device_RemainItemNum.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Lable_Device_RemainItemNum.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Device_RemainItemNum.setBounds(482, 99, 105, 25);
		
		ListDevice = new ScrolledComposite(ContainerDevice, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		ListDevice.setExpandVertical(true);
		ListDevice.setExpandHorizontal(true);
		ListDevice.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		ListDevice.setBounds(26, 138, 640, 534);
		ListDevice.setMinSize(new Point(64, 64));
		
		Lable_Device_SetUp = new Label(ContainerDevice, SWT.NONE);
		Lable_Device_SetUp.setText("\u662F\u5426\u53EF\u8BBE\u7F6E");
		Lable_Device_SetUp.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Lable_Device_SetUp.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Device_SetUp.setBounds(396, 99, 80, 25);
		
		Label lblNewLabel = new Label(ContainerDevice, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		lblNewLabel.setBounds(593, 100, 73, 24);
		lblNewLabel.setText("\u84DD\u7259\u72B6\u6001");
		
		ContainerADs = new Composite(ContainerMain, SWT.NONE);
		ContainerADs.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		ContainerADs.setBounds(300, 700, 700, 700);
		
		line2 = new Label(ContainerADs, SWT.NONE);
		line2.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		line2.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		line2.setBounds(26, 126, 640, 1);
		
		Lable_Ads_Name = new Label(ContainerADs, SWT.NONE);
		Lable_Ads_Name.setText("\u540D\u79F0");
		Lable_Ads_Name.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Lable_Ads_Name.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Ads_Name.setBounds(39, 102, 73, 25);
		
		Lable_Ads_PlayCount = new Label(ContainerADs, SWT.NONE);
		Lable_Ads_PlayCount.setText("\u5269\u4F59\u64AD\u653E\u6B21\u6570");
		Lable_Ads_PlayCount.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Lable_Ads_PlayCount.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Ads_PlayCount.setBounds(131, 102, 105, 25);
		
		Lable_Ads_SetUp = new Label(ContainerADs, SWT.NONE);
		Lable_Ads_SetUp.setText("\u6240\u5C5E\u5355\u4F4D");
		Lable_Ads_SetUp.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Lable_Ads_SetUp.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Ads_SetUp.setBounds(242, 102, 80, 25);
		
		ListAds = new ScrolledComposite(ContainerADs, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		ListAds.setExpandVertical(true);
		ListAds.setExpandHorizontal(true);
		ListAds.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		ListAds.setBounds(26, 138, 640, 534);
		ListAds.setMinSize(new Point(64, 64));
		
		RootUpload = new Composite(ContainerMain, SWT.NONE);
		RootUpload.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		RootUpload.setBounds(300, 1400, 700, 700);
		
		ContainerUpload = new Composite(RootUpload, SWT.NONE);
		ContainerUpload.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		ContainerUpload.setBounds(0, 0, 700, 700);
		
		Lable_Upload_FileType = new Label(ContainerUpload, SWT.NONE);
		Lable_Upload_FileType.setText("\u6587\u4EF6\u7C7B\u578B         \uFF1A");
		Lable_Upload_FileType.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		Lable_Upload_FileType.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Upload_FileType.setBounds(28, 33, 111, 23);
		
		Combo_Upload_FileType = new Combo(ContainerUpload, SWT.READ_ONLY);
		Combo_Upload_FileType.setItems(new String[] {"\u5B9A\u70B9\u6295\u653E", "\u6EDA\u52A8\u5E7F\u544A"});
		Combo_Upload_FileType.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Combo_Upload_FileType.setBounds(144, 26, 531, 32);
		Combo_Upload_FileType.select(0);
		
		Lable_Upload_FileLocation = new Label(ContainerUpload, SWT.NONE);
		Lable_Upload_FileLocation.setText("\u6587\u4EF6\u7EDD\u5BF9\u8DEF\u5F84  \uFF1A");
		Lable_Upload_FileLocation.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		Lable_Upload_FileLocation.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Upload_FileLocation.setBounds(28, 78, 111, 23);
		
		TextBox_Upload_FilePath = new Text(ContainerUpload, SWT.BORDER);
		TextBox_Upload_FilePath.setText("\u8BF7\u586B\u5199\u6587\u4EF6\u7EDD\u5BF9\u8DEF\u5F84");
		TextBox_Upload_FilePath.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		TextBox_Upload_FilePath.setBounds(144, 78, 498, 23);
		
		Lable_Upload_PlayCount = new Label(ContainerUpload, SWT.NONE);
		Lable_Upload_PlayCount.setText("\u603B\u64AD\u653E\u6B21\u6570");
		Lable_Upload_PlayCount.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		Lable_Upload_PlayCount.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Upload_PlayCount.setBounds(28, 134, 111, 23);
		
		TextBox_Upload_PlayConut = new Text(ContainerUpload, SWT.BORDER);
		TextBox_Upload_PlayConut.setText("\u8BF7\u586B\u5199\u64AD\u653E\u6B21\u6570");
		TextBox_Upload_PlayConut.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		TextBox_Upload_PlayConut.setBounds(144, 134, 531, 23);
		
		Button_Upoad = new Button(ContainerUpload, SWT.NONE);
		Button_Upoad.setText("\u4E0A\u4F20");
		Button_Upoad.setBounds(250, 650, 216, 27);
		
		ListUpload = new ScrolledComposite(ContainerUpload, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		ListUpload.setExpandVertical(true);
		ListUpload.setExpandHorizontal(true);
		ListUpload.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		ListUpload.setBounds(28, 203, 648, 416);
		ListUpload.setMinSize(new Point(64, 64));
		
		Lable_Upload_Attention = new Label(ContainerUpload, SWT.NONE);
		Lable_Upload_Attention.setText("\u8B66\u544A\uFF1A\u8BF7\u4F7F\u7528\u82F1\u6587\u5B57\u7B26");
		Lable_Upload_Attention.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Upload_Attention.setAlignment(SWT.CENTER);
		Lable_Upload_Attention.setBounds(474, 635, 216, 65);
		
		Lable_Upload_During = new Label(ContainerUpload, SWT.NONE);
		Lable_Upload_During.setVisible(false);
		Lable_Upload_During.setText("\u5B58\u50A8\u65F6\u957F:");
		Lable_Upload_During.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		Lable_Upload_During.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Upload_During.setBounds(28, 134, 90, 23);
		
		Combo_Upload_YearPre = new Combo(ContainerUpload, SWT.READ_ONLY);
		Combo_Upload_YearPre.setVisible(false);
		Combo_Upload_YearPre.setItems(new String[] {"2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"});
		Combo_Upload_YearPre.setBounds(130, 136, 80, 32);
		
		Combo_Upload_MonthPre = new Combo(ContainerUpload, SWT.READ_ONLY);
		Combo_Upload_MonthPre.setVisible(false);
		Combo_Upload_MonthPre.setItems(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
		Combo_Upload_MonthPre.setBounds(213, 136, 80, 32);
		
		Combo_Upload_DayPre = new Combo(ContainerUpload, SWT.READ_ONLY);
		Combo_Upload_DayPre.setVisible(false);
		Combo_Upload_DayPre.setItems(new String[] {});
		Combo_Upload_DayPre.setBounds(299, 136, 80, 32);
		
		Lable_Upload_to = new Label(ContainerUpload, SWT.NONE);
		Lable_Upload_to.setVisible(false);
		Lable_Upload_to.setText("\u81F3");
		Lable_Upload_to.setBounds(385, 139, 20, 24);
		
		Combo_Upload_YearAfter = new Combo(ContainerUpload, SWT.READ_ONLY);
		Combo_Upload_YearAfter.setVisible(false);
		Combo_Upload_YearAfter.setItems(new String[] {"2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"});
		Combo_Upload_YearAfter.setBounds(411, 136, 80, 32);
		
		Combo_Upload_MonthAfter = new Combo(ContainerUpload, SWT.READ_ONLY);
		Combo_Upload_MonthAfter.setVisible(false);
		Combo_Upload_MonthAfter.setItems(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
		Combo_Upload_MonthAfter.setBounds(497, 136, 80, 32);
		
		Combo_Upload_DayAfter = new Combo(ContainerUpload, SWT.READ_ONLY);
		Combo_Upload_DayAfter.setVisible(false);
		Combo_Upload_DayAfter.setItems(new String[] {});
		Combo_Upload_DayAfter.setBounds(585, 136, 80, 32);
		
		label_Upload_Company = new Label(ContainerUpload, SWT.NONE);
		label_Upload_Company.setText("\u6240\u5C5E\u5355\u4F4D:");
		label_Upload_Company.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		label_Upload_Company.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_Upload_Company.setBounds(28, 174, 90, 23);
		
		TextBox_Upload_Company = new Text(ContainerUpload, SWT.BORDER);
		TextBox_Upload_Company.setText("\u8BF7\u586B\u5199\u5355\u4F4D\u540D\u79F0");
		TextBox_Upload_Company.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		TextBox_Upload_Company.setBounds(144, 174, 531, 23);
		
		Button_Upload_ChooseFile = new Button(ContainerUpload, SWT.NONE);
		Button_Upload_ChooseFile.setBounds(648, 78, 25, 25);
		Button_Upload_ChooseFile.setText("...");
		
		ContainerRepeats = new Composite(ContainerMain, SWT.NONE);
		ContainerRepeats.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		ContainerRepeats.setBounds(1000, 0, 700, 700);
		
		line2 = new Label(ContainerRepeats, SWT.NONE);
		line2.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		line2.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		line2.setBounds(26, 126, 640, 1);
		
		Lable_Repeats_Name = new Label(ContainerRepeats, SWT.NONE);
		Lable_Repeats_Name.setText("\u540D\u79F0");
		Lable_Repeats_Name.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Lable_Repeats_Name.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Repeats_Name.setBounds(39, 102, 73, 25);
		
		Lable_Repeats_SetUp = new Label(ContainerRepeats, SWT.NONE);
		Lable_Repeats_SetUp.setText("\u6240\u5C5E\u5355\u4F4D");
		Lable_Repeats_SetUp.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Lable_Repeats_SetUp.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Repeats_SetUp.setBounds(242, 102, 80, 25);
		
		Lable_Repeats_During = new Label(ContainerRepeats, SWT.NONE);
		Lable_Repeats_During.setText("\u6301\u7EED\u65F6\u95F4");
		Lable_Repeats_During.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 8, SWT.NORMAL));
		Lable_Repeats_During.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		Lable_Repeats_During.setBounds(415, 102, 105, 25);
		
		ListRepeats = new ScrolledComposite(ContainerRepeats, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		ListRepeats.setExpandVertical(true);
		ListRepeats.setExpandHorizontal(true);
		ListRepeats.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		ListRepeats.setBounds(26, 138, 640, 534);
		ListRepeats.setMinSize(new Point(64, 64));
	}
}
