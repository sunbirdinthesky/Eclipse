package ui_logic;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import sqlOperation.SqlOperation;
import vars.Vars;

public class Login extends SqlOperation {
	protected Shell shell;
	protected boolean online = false;

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
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
		shell = new Shell(SWT.ALL);
		shell.setSize(300, 400);
		shell.setText("ServerCtrl");
	}

	Thread checkServer = new Thread() {
		public void run() {
			try {
				if (isConnected() || SqlInit()) {
					SqlQuery("select background from versionCtrl");
					if (!rSet.next() || rSet.getString("background")
							.compareTo(Vars.version) > 0) {
						Display.getDefault().asyncExec(new Runnable() {
							@SuppressWarnings("deprecation")
							public void run() {
								online = true;
								Thread.currentThread().stop();
							}
						});
						throw new Exception();
					}
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
	};
}
