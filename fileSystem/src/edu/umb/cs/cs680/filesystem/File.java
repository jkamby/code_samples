package edu.umb.cs.cs680.filesystem;


import java.util.Date;

/**
 * @author JohnPaul
 *
 */
public class File extends FSElement {

	/**
	 * @param parent
	 * @param children
	 * @param name
	 * @param owner
	 */
	private Directory parent;
	private int size;
	private String name;
	private String owner;
	private Date created;
	private Date lastModified;
	
		public File(Directory parent, String name, String owner) {
		this.parent = parent;
		this.size = 5;// basic overhead
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
	
	public boolean isFile() {
		return true;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void setSize(int newSize) {
		this.size = newSize;
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
	}
	
	public int getDiskUtil() {
		return getSize(); // Why not?
	}

	@Override
	public String getInfo() {
		return " " + this.getName() + "\t" + this.getSize() + "\t" + this.getLastModified();
	}
	
	@Override
	public String getDetails() {
		return "<f> " + this.getName() + "\t" + this.getSize() + "\t" + this.getOwner();
	}
	
	public void run(Shell shell) {
		shell.processInput();
	}
}
