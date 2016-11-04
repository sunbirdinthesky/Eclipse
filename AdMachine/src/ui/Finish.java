package ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class Finish {
	protected boolean isDisposed = false;
	protected Composite composite;
	protected int userHight;
	protected int userWidth;
	protected Label message;
	
	public Finish(final Composite container) {
		this.composite = container;		
		userHight = container.getBounds().height;
		userWidth = container.getBounds().width;

	}

	protected void logicInit () {
		
	}
	
	public void open() {
		Display display = Display.getDefault();
		logicInit();
		while (!isDisposed){
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}