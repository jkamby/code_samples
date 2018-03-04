package edu.umb.cs.cs680.filesystem;


import java.util.ArrayList;
import java.util.Date;

/**
 * @author JohnPaul
 *
 */
public class Directory extends FSElement {

	/**
	 * @param parent
	 * @param children
	 * @param name
	 * @param owner
	 */
	private Directory parent;
	private ArrayList<FSElement> children;
	//private int size;
	private String name;
	private String owner;
	private Date created;
	private Date lastModified;
	
	public Directory(Directory parent, ArrayList<FSElement> children, String name, String owner) {
		this.parent = parent;
		this.children = children;
		//this.size = 0;
		this.name = name;
		this.owner = owner;
		this.created = new Date();
		this.lastModified = this.created;
	}
	
	public Directory getParent() {
		return this.parent;
	}
	
	public void setParent(Directory newParent) {
		this.parent = newParent;
	}
	
	public ArrayList<FSElement> getChildren() {
		return this.children;
	}
	
	public void appendChild(FSElement child) { 
		child.setParent(this);
		this.children.add(child);
	}
	
	public void addChildren(ArrayList<FSElement> newChildren) {
		for(FSElement child: newChildren)
			child.setParent(this);
		this.children.addAll(newChildren);
	}
	
	public void removeChild(FSElement unChild) {
		this.children.remove(unChild);
	}

	public boolean isFile() {
		return false;
	}
	
	public int getSize() {
		return 0;// getTotalSize(this);
	}
	
	public void setSize(int newSize) {
		// Does nothing.
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String newOwner) {
		this.owner = newOwner;
	}
	
	public Date getCreated() {
		return this.created;
	}
	
	public Date getLastModified() {
		return this.lastModified;
	}
	
	public void setLastModified() {
		this.lastModified = new Date();
	}
	
	public void accept(FSVisitor v) {
		v.visit(this);
		for(FSElement e: this.children)
			e.accept(v);
	}
	
	public int getDiskUtil() {
		return getSize();
	}

	@Override
	public String getInfo() {
		return " " + this.getName() + "\t" + this.getSize() + "\t" + this.getLastModified();
	}
	
	@Override
	public String getDetails() {
		return "<d> " + this.getName() + "\t" + this.getSize() + "\t" + this.getOwner();
	}
	
	public void addChild(FSElement child, int index) {
		this.children.add(index, child);
	}
	
	public int getChildIndex(FSElement child) {
		return this.children.indexOf(child);
	}
	
	public FSElement getChild(int index) {
		return this.children.get(index);
	}
	
	public void run(Shell shell) {
		shell.processInput();
	}
	
	public FSElement findDir(String dir) {
		FSElement result = null;
		if(dir.equals(".."))
			result = this.getParent();
		else {
		ArrayList<FSElement> toCheck = getChildren();
		for(FSElement y: toCheck)
			if(!y.isFile() && y.getName().equals(dir))
				result = y;
		}
		return result;
	}
	
	public FSElement findFSE(String fse) {
		FSElement result = null;
		if(fse.equals(".."))
			result = this.getParent();
		else {
			ArrayList<FSElement> toCheck = this.getChildren();
			for(FSElement y: toCheck)
				if(y.getName().equals(fse))
					result = y;
		}
		return result;
	}
}
