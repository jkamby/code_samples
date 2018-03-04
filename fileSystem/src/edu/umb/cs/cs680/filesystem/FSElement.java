package edu.umb.cs.cs680.filesystem;


import java.util.Date;

/**
 * @author JohnPaul
 *
 */
public abstract class FSElement {
	
	public FSElement () {
	}
	
	public abstract Directory getParent();
	
	public abstract void setParent(Directory newParent);
		
	public abstract boolean isFile();
	
	public abstract int getSize();
	
	public abstract void setSize(int newSize);
	
	public abstract String getName();
	
	public abstract void setName(String newName);
	
	public abstract String getOwner();
	
	public abstract void setOwner(String newOwner);
	
	public abstract Date getCreated();
	
	public abstract Date getLastModified();
	
	public abstract void setLastModified();
	
	public abstract void accept(FSVisitor v);
	
	public abstract int getDiskUtil();
	
	public abstract String getInfo();
	
	public abstract String getDetails();
	
	public abstract void run(Shell shell);
}
