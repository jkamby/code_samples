/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

/**
 * @author JohnPaul
 *
 */
public class Exit implements Command {

	/**
	 * 
	 */
	public Exit() {
		FileSystem.getInstance();
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.Command#execute()
	 */
	@Override
	public void execute() {
		System.out.println("Logging out and shutting down. Thank you.");
		//System.exit(0); - commented for testing purposes.
	}

}
