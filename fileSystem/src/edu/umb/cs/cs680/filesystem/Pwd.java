/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

/**
 * @author JohnPaul
 *
 */
public class Pwd implements Command {

	private FileSystem fileSystem;
	/**
	 * 
	 */
	public Pwd() {
		this.fileSystem = FileSystem.getInstance();
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.Command#execute()
	 */
	@Override
	public void execute() {
		System.out.println("Your present working directory is " + fileSystem.getCurrent().getName());
	}

	@Override
	public String toString() {
		return "pwd";
	}
}
