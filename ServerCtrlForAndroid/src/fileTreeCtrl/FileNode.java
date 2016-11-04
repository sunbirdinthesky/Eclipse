package fileTreeCtrl;

import java.util.ArrayList;
import java.util.List;

public class FileNode implements ITreeNode{
	
	public String name;
	public String absolutePath;
	public List<FileNode> child = new ArrayList<>();
	
	public String getAbsolutePath () {
		return absolutePath;
	}
	
	public void setAbsolutePath (String absolutePath) {
		this.absolutePath = absolutePath;
	}
	
	public List<FileNode> getChild() {
		return child;
	}

	public void setChild(List<FileNode> child) {
		this.child = child;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}