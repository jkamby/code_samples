/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

/**
 * @author JohnPaul
 *
 */
public class RmDir implements Command {

	private FileSystem fileSystem;
	private String dir;
	/**
	 * 
	 */
	public RmDir(String dir) {
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
		} else if((Directory)this.fileSystem.getCurrent().findDir(this.dir) != null) {
			Directory unDir = (Directory)this.fileSystem.getCurrent().findDir(this.dir);
			if (unDir.getChildren().size() != 0)
				System.out.println("The directory " + unDir.getName() + " is not empty.");
			else {
				Directory parentDir = unDir.getParent();
				parentDir.removeChild(unDir);
			}
		} else {
			System.out.println("The directory " + this.dir + " cannot be found.");
		}
	}
	
	@Override
	public String toString() {
		return "rmdir";
	}
}
