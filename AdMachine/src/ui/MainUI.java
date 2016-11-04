package ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;

public class MainUI {

	protected Shell shell;
	protected int userHight;
	protected int userWidth;
	protected Button btn_Close;
	protected Composite composite;
	protected Label message;
	
	private void init () {
		
		logicInit();
	}

	protected void logicInit () {
		
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
		init();
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
//		shell = new Shell(SWT.NO_TRIM | SWT.ON_TOP);
		shell = new Shell(SWT.ALL);
		shell.setSize(700, 600);
		
		shell.setLayout(new FillLayout());
		shell.setBounds(Display.getDefault().getPrimaryMonitor().getBounds());
		
		userHight = Display.getDefault().getPrimaryMonitor().getBounds().height;
		userWidth = Display.getDefault().getPrimaryMonitor().getBounds().width;
		
		composite = new Composite(shell, SWT.NONE);
		composite.setBackgroundImage(SWTResourceManager.getImage(MainUI.class, "/UIImage/main_background.png"));
		
		btn_Close = new Button(composite, SWT.NONE);
		btn_Close.setBounds(128, 55, 91, 29);
		btn_Close.setText("close");
		
//		Label label = new Label(composite, SWT.NONE);
//		label.setAlignment(SWT.CENTER);
//		label.setImage(SWTResourceManager.getImage(MainUI.class, "/UIImage/label_background.png"));
//		label.setBounds(492, 334, 76, 20);
//		label.setText("New Label");

	}
}
