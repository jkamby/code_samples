/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

import java.util.ArrayList;

/**
 * @author JohnPaul
 *
 */
public class Dir implements Command {

	private FileSystem fileSystem;
	private FSElement fse;
	/**
	 * 
	 */
	public Dir(String fse) {
		this.fileSystem = FileSystem.getInstance();
		if (fse == null || fse.isEmpty())
			this.fse = fileSystem.getCurrent();
		else
			this.fse = fileSystem.getCurrent().findFSE(fse);
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.Command#execute()
	 */
	@Override
	public void execute() {
		if(fse != null && fse instanceof Directory) {
			ArrayList<FSElement> dirChildren = ((Directory)fse).getChildren();
			for (FSElement child: dirChildren) {
				System.out.println(child.getDetails()); // Shd diff'te btwn files/links/dirs
			}
		} else if(fse != null && fse instanceof Link) {
			if(((Link)fse).getTarget() instanceof Directory) {
				ArrayList<FSElement> dirChildren = ((Directory)((Link)fse).getTarget()).getChildren();
				for (FSElement child: dirChildren) {
					System.out.println(child.getDetails());
				}
			} else {
				System.out.println(((File)(((Link)fse).getTarget())).getDetails());
			}
		} else if(fse != null && fse instanceof File) {
			System.out.println(((File)fse).getDetails());
		} else {
			System.out.println("Unrecognized directory.");
		}
	}
	
	@Override
	public String toString() {
		return "dir";
	}
}
