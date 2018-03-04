/**
 * 
 */
package edu.umb.cs.cs680.filesystem;


/**
 * @author JohnPaul
 *
 */
public class Cd implements Command {

	private FileSystem fileSystem;
	private FSElement toDir;
	/**
	 * 
	 */
	public Cd(String toDir) {
		this.fileSystem = FileSystem.getInstance();
		if (toDir == null || toDir.isEmpty())
			this.toDir = fileSystem.getRoot();
		else
			this.toDir = fileSystem.getCurrent().findDir(toDir);
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.Command#execute()
	 */
	@Override
	public void execute() {
		if (toDir != null)
			fileSystem.setCurrent(toDir);
		else
			System.out.println("Directory could not be found.");
	}
	
	@Override
	public String toString() {
		return "cd";
	}

}
