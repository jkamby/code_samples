package edu.umb.cs.cs680.filesystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author JohnPaul
 *
 */
public class FileSystem {

	/**
	 * 
	 */
	private static FileSystem instance = null;
	private Directory current;
	private Directory root;
	private int indent = 0;
	private Comparator<FSElement> comparator;
	private HashMap<String, Command> cmdList;
	private FileSystem() {
		this.cmdList = new HashMap<String, Command>();
		this.comparator = new AlphabeticalComparator();
	}
	
	public FileSystem getFileSystem() {
		return this;
	}
	
	public static FileSystem getInstance() {
		if(instance == null)
			instance = new FileSystem();
		return instance;
	}
	
	public void showAllElements() {
		HashMap<String, Object> tree = growTree(this.root);
		printTree(tree);
	}
	
	private HashMap<String, Object> growTree(Directory d) {
		HashMap<String, Object> dir = new HashMap<>();
		String dirName = d.getName();
		ArrayList<Object> kids = new ArrayList<> ();
		for(FSElement f: d.getChildren()) {
			if(!f.isFile())
				if(f.getClass() == Directory.class) {
					HashMap<String, Object> dir2 = new HashMap<>();
					dir2 = growTree((Directory)f);
					kids.add(0, dir2); // Need dir's to be inserted at the top of the ArrayList
				}
				else
					kids.add(f);
			else
				kids.add(f);
		}
		dir.put(dirName, kids);
		return dir;
	}
	
	private void printTree(HashMap<String, Object> tree) {
		for(String s: tree.keySet()) {
			String fmtSpacer;
			if(indent == 0)
				fmtSpacer = "-r-> ";
			else
				fmtSpacer = "\t" + "|";
			for(int i = 0; i < indent; i++) {
				if(i != indent -1)
					fmtSpacer = "\t" + fmtSpacer;
				else
					fmtSpacer += "-d->";
			}
			System.out.println(fmtSpacer + s);
			indent++;
			
			ArrayList<Object> theKids = (ArrayList<Object>)tree.get(s);
			for(int i = 0; i < theKids.size(); i++) {
				Object o = theKids.get(i);
				if ( o instanceof HashMap)
					printTree((HashMap<String, Object>)o);
				else if (o instanceof Link) {
					fmtSpacer = "\t" + "|";
					for(int j = 0; j < indent; j++) {
						if(j != indent -1)
							fmtSpacer = "\t" + fmtSpacer;
						else
							fmtSpacer += "-l->";
					}
					System.out.println(fmtSpacer + ((Link)o).getName());
				} else if (o instanceof File) {
					fmtSpacer = "\t" + "|";
					for(int k = 0; k < indent; k++) {
						if(k != indent -1)
							fmtSpacer = "\t" + fmtSpacer;
						else
							fmtSpacer += "-f->";
					}
					System.out.println(fmtSpacer + ((File)o).getName());
				}
			}
			indent--;
		}
	}
	
	public void setRoot(Directory root) {
		this.root = root;
		this.current = this.root; // just to hold things in place until we deliberately reset it
	}
	
	public Directory getRoot() {
		return this.root;
	}
	
	public void setCurrent(FSElement current) {
		try {
			this.current = (Directory) current;
		} catch (ClassCastException e) {
			Link temp = (Link) current;
			this.current = (Directory) temp.getTarget();
		}
	}
	
	public Directory getCurrent() {
		return this.current;
	}
	
	public Directory findDirectory(String toDir) {
		Directory result = null;
		ArrayList<FSElement> fellowFSElements = this.current.getChildren();
		for (FSElement f: fellowFSElements) {
			if (!f.isFile() && f.getName() == toDir) {
				result = (Directory) f;
				break;
			} else {
				result = null;
			}
		}
		return result;
	}
	
	public HashMap<String, Command> getCommands() {
		return this.cmdList;
	}
	
	public void addCommand(Command c) {
		
	}
	
	public void addChild(Directory parent, FSElement child) {
		parent.addChild(child, getInsertionLocation(parent, child));
	}
	
	private int getInsertionLocation(Directory parent, FSElement child) {
		int index = 0;
		Comparator<FSElement> standard = new AlphabeticalComparator();
		for(FSElement i: parent.getChildren()) {
			if(standard.compare(i, child)>0)
				continue;
			else
				index = parent.getChildIndex(i);
		}
		return index;
	}
	
	public ArrayList<FSElement> getChildren(Directory current) {
		return null;
	}
	
	public ArrayList<FSElement> sort(Directory dir) {
		ArrayList<FSElement> list = dir.getChildren();
		Collections.sort(list, this.comparator);
		return list;
	}

	public Comparator<FSElement> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<FSElement> comparator) {
		this.comparator = comparator;
	}

}
