package edu.umb.cs.cs680.filesystem;


import java.util.Date;

/**
 * @author JohnPaul
 *
 */
public class Link extends FSElement {

	/**
	 * @param target
	 */
	private FSElement target;
	private String name;
	private Date created;
	
	
	public Link(FSElement target) {
		this.target = target;
		this.name = target.getName()+"L";
		this.created = new Date();
	}
	
	public int getTargetSize() {
		return this.target.getSize();
	}
	
	public void accept(FSVisitor v) {
		v.visit(this);
	}
	
	public int getDiskUtil() {
		return 0;
	}
	
	public String getInfo() {
		return " " + this.getName() + "\t" + this.getSize() + "\t" + this.getLastModified();
	}
	
	public String getDetails() {
		return "<l> " + this.getName() + "\t" + this.getSize() + "\t" + this.getOwner();
	}

	@Override
	public Directory getParent() {
		return null;
	}

	@Override
	public void setParent(Directory newParent) {
		// Does nothing.
	}

	@Override
	public boolean isFile() {
		return false;
	}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public void setSize(int newSize) {
		// Does nothing
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String newName) {
		this.name = newName;
		
	}

	@Override
	public String getOwner() {
		return this.target.getOwner();
	}

	@Override
	public void setOwner(String newOwner) {
		// Does nothing.
	}

	@Override
	public Date getCreated() {
		return this.created;
	}

	@Override
	public Date getLastModified() {
		return this.target.getLastModified();
	}

	@Override
	public void setLastModified() {}
	
	public FSElement getTarget() {
		return this.target;
	}
	
	public void run(Shell shell) {
		shell.processInput();
	}
	
}
