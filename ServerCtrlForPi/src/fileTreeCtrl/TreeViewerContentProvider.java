package fileTreeCtrl;

import java.io.File;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TreeViewerContentProvider implements ITreeContentProvider {

	public Object[] getChildren(Object parentElement) {
		List<FileNode> childs = ((FileNode) parentElement).getChild();
		
		if (childs.isEmpty())
			return childs.toArray();
		
		for (int i = 0; i < childs.size(); i++) {
			File tmpFile = new File(childs.get(i).getAbsolutePath());
			File[] tmpChilds = tmpFile.listFiles();

			if (tmpChilds == null) 
				continue;
			
			for (File file : tmpChilds) {
				FileNode tmpNode = new FileNode();
				tmpNode.setName(file.getName());
				tmpNode.setAbsolutePath(file.getAbsolutePath());
				childs.get(i).getChild().add(tmpNode);
			}
		}
		
		return childs.toArray();
	}


	public boolean hasChildren(Object element) {
		List <FileNode> childList = ((FileNode) element).getChild();
		if (childList.size() > 0)
			return true;
		else
			return false;
	}

	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List) {
			@SuppressWarnings("unchecked")
			List <FileNode> list = (List<FileNode>) inputElement;
			return list.toArray();
		} else
			return new Object[] { inputElement };
	}

	public void dispose() {

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@Override
	public Object getParent(Object arg0) {
		return null;
	}

}