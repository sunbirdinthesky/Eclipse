package fileTreeCtrl;

import java.io.File;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import vars.Vars;

public class FileTreeDlg extends Dialog {

	protected Object result;
	protected Shell shlSwt;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public FileTreeDlg(Shell parent, int style) {
		super(parent, style);
		setText("ÇëÑ¡ÔñÎÄ¼þ");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlSwt.open();
		shlSwt.layout();
		Display display = getParent().getDisplay();
		while (!shlSwt.isDisposed()) {
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
		shlSwt = new Shell(getParent(),
				SWT.CLOSE | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);
		shlSwt.setSize(887, 537);
		shlSwt.setLayout(null);

		TreeViewer treeViewer = new TreeViewer(shlSwt, SWT.BORDER);
		Tree treeView = treeViewer.getTree();
		treeView.setBounds(10, 10, 814, 426);

		List<FileNode> dataList = DataFactory.getList();

		treeViewer.setContentProvider(new TreeViewerContentProvider());
		treeViewer.setLabelProvider(new TreeViewerLabelProvider());
		treeViewer.setInput(dataList);
		treeViewer.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				TreeItem[] selected = treeViewer.getTree().getSelection();
				FileNode obj = (FileNode) selected[0].getData();
				try {
					File tmp = new File(obj.getAbsolutePath());
					if (tmp.exists() && !tmp.isDirectory()) {
						Vars.filePath = obj.getAbsolutePath();
						shlSwt.dispose();
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
	}
}
