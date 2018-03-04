/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

/**
 * @author JohnPaul
 *
 */
public class Mv implements Command {

	private FileSystem fileSystem;
	private String target;
	private String destDir;
	/**
	 * 
	 */
	public Mv(String target, String destDir) {
		this.fileSystem = FileSystem.getInstance();
		if (target == null || target.isEmpty()) {
			this.target = "";
			this.destDir = "";
		} else if(destDir == null || destDir.isEmpty()) {
			this.target = target;
			this.destDir = "";
		} else {
			this.target = target;
			this.destDir = destDir;
		}
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.Command#execute()
	 */
	@Override
	public void execute() {
		if(this.target.equals("")) {
			System.out.println("Please enter valid arguments.");
		} else if(this.destDir.equals("")) {
			System.out.println("Please enter a valid destination directory.");
		} else if(this.fileSystem.getCurrent().findFSE(this.target) != null && this.fileSystem.getCurrent().findDir(this.destDir) != null) {
			FSElement fse = this.fileSystem.getCurrent().findFSE(this.target);
			Directory dDir = (Directory)this.fileSystem.getCurrent().findDir(this.destDir);
			dDir.appendChild(fse);
			this.fileSystem.getCurrent().removeChild(fse);
		} else if(this.fileSystem.getCurrent().findFSE(this.target) == null && this.fileSystem.getCurrent().findDir(this.destDir) == null){
			System.out.println("The target " + this.target + " and destination " + this.destDir + " cannot be found.");
		} else if(this.fileSystem.getCurrent().findFSE(this.target) == null) {
			System.out.println("The target " + this.target + " cannot be found.");
		} else if(this.fileSystem.getCurrent().findDir(this.destDir) == null) {
			System.out.println("The destination " + this.destDir + " cannot be found.");
		}
	}
	
	@Override
	public String toString() {
		return "mv";
	}
}
