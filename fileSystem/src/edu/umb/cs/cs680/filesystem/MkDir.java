/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

import java.util.ArrayList;

/**
 * @author JohnPaul
 *
 */
public class MkDir implements Command {

	private FileSystem fileSystem;
	private String dir;
	/**
	 * 
	 */
	public MkDir(String dir) {
		this.fileSystem = FileSystem.getInstance();
		if (dir == null || dir.isEmpty()) {
			this.dir = "";
		}
		else
			this.dir = dir;
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.Command#execute()
	 */
	@Override
	public void execute() {
		if(this.dir.equals("")) {
			System.out.println("Please enter a valid directory name.");
		} else if((Directory)this.fileSystem.getCurrent().findDir(this.dir) == null) {
			Directory newDir = new Directory(this.fileSystem.getCurrent(), new ArrayList<FSElement>(), this.dir, this.fileSystem.getCurrent().getOwner());
			this.fileSystem.getCurrent().appendChild(newDir);
		} else {
			System.out.println("The directory already exists.");
		}
	}
	
	@Override
	public String toString() {
		return "mkdir";
	}
}
