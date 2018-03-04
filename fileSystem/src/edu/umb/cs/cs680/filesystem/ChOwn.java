/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

/**
 * @author JohnPaul
 *
 */
public class ChOwn implements Command {

	private FileSystem fileSystem;
	private String target;
	private String newOwner;
	/**
	 * 
	 */
	public ChOwn(String target, String destDir) {
		this.fileSystem = FileSystem.getInstance();
		if (target == null || target.isEmpty()) {
			this.target = "";
			this.newOwner = "";
		} else if(destDir == null || destDir.isEmpty()) {
			this.target = target;
			this.newOwner = "";
		} else {
			this.target = target;
			this.newOwner = destDir;
		}
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.Command#execute()
	 */
	@Override
	public void execute() {
		if(this.target.equals(""))
			System.out.println("Please enter valid arguments.");
		else if(this.newOwner.equals(""))
			System.out.println("Please enter a valid new Owner.");
		else if(this.fileSystem.getCurrent().findFSE(this.target) != null) {
			FSElement fse = this.fileSystem.getCurrent().findFSE(this.target);
			fse.setOwner(this.newOwner);
		} else
			System.out.println("The target " + this.target + " cannot be found.");
	}
	
	@Override
	public String toString() {
		return "chown";
	}
}
