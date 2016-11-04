package fileTreeCtrl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataFactory {
	public static List<FileNode> getList() {
		List<FileNode> root = new ArrayList<FileNode>();

		File[] string = File.listRoots();
		for (File file : string) {
			FileNode tmp = new FileNode();
			tmp.setName(file.getPath());
			File[] childs = file.listFiles();
			if (childs != null) {
				for (File child : childs) {
					FileNode tmpl = new FileNode();
					tmpl.setName(child.getName());
					tmpl.setAbsolutePath(child.getAbsolutePath());
					tmp.getChild().add(tmpl);
				}
			}
			root.add(tmp);
		}

		return root;
	}
}