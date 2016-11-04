package fileTreeCtrl;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class TreeViewerLabelProvider implements ILabelProvider {
	public TreeViewerLabelProvider() {

	}

	public Image getImage(Object element) {
		return null;
	}

	public String getText(Object element) {
		return ((FileNode) element).getName();
	}

	public void addListener(ILabelProviderListener listener) {
		
	}

	public void dispose() {

	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {

	}

}