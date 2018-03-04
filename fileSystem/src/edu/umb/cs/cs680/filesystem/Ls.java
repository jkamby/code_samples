/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

import java.util.ArrayList;

/**
 * @author JohnPaul
 *
 */
public class Ls implements Command {

	private FileSystem fileSystem;
	private FSElement dir;
	/**
	 * 
	 */
	public Ls(String dir) {
		this.fileSystem = FileSystem.getInstance();
		if (dir == null || dir.isEmpty())
			this.dir = fileSystem.getCurrent();
		else
			this.dir = fileSystem.getCurrent().findDir(dir);
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.Command#execute()
	 */
	@Override
	public void execute() {
		if(dir != null && /*|| was causing a NullPointerException*/((Directory)dir).getChildren() != null) {
			ArrayList<FSElement> dirChildren = ((Directory)dir).getChildren();
			for (FSElement child: dirChildren) {
				System.out.println(child.getInfo()); // Shd diff'te btwn files/links/dirs
			}
		} else {
			System.out.println("Unrecognized directory.");
		}
	}

	@Override
	public String toString() {
		return "ls";
	}
}
