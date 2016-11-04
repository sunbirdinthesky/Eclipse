package ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import vars.Vars;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Label;

public class SetUp extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;
	private Label label;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SetUp(Shell parent, int style) {
		super(parent, style);
		setText("设置");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 300);
		shell.setBounds(200, 200, 450, 300);
		shell.setText(getText());
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(70, 110, 306, 62);
		text.forceFocus();
		text.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.keyCode == SWT.KEYPAD_CR) {
					arg0.doit = false;
					try {
						Vars.nItem = Integer.valueOf(text.getText());
						shell.dispose();
					} catch (Exception e) {
						label.setText("输入错误，请重试");
					}
				}
				
				if (arg0.keyCode == SWT.KEYPAD_ADD) {
					arg0.doit = false;
					String tmp = text.getText();
					if (tmp.length() != 0)
						tmp = tmp.substring(0, tmp.length()-1);
					text.setText(tmp);
					text.setSelection(tmp.length());
				}
			}
		});
		
		Label itemNum = new Label(shell, SWT.NONE);
		itemNum.setAlignment(SWT.CENTER);
		itemNum.setBounds(70, 60, 306, 39);
		itemNum.setText("请输入物品数量");
		itemNum.setFont(SWTResourceManager.getFont("Ubuntu",
									20, SWT.NORMAL));
		
		label = new Label(shell, SWT.CENTER);
		label.setText("输错了按加号退格，按回车结束输入");
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label.setBounds(90, 178, 268, 77);

	}
}
