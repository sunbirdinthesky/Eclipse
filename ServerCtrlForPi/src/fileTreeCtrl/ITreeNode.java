package fileTreeCtrl;

import java.util.List;

public interface ITreeNode {
	public String getName() ;
	public void setName(String name);
	public abstract List<FileNode> getChild() ;
	public abstract void setChild(List<FileNode> child);
}
