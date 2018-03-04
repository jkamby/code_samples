/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

/**
 * @author JohnPaul
 *
 */
public class Ln implements Command {

	private FileSystem fileSystem;
	private String target;
	private String link;
	/**
	 * 
	 */
	public Ln(String target, String link) {
		this.fileSystem = FileSystem.getInstance();
		if (target == null || target.isEmpty()) {
			this.target = "";
			this.link = "";
		} else if(link == null || link.isEmpty()) {
			this.target = target;
			this.link = "";
		} else {
			this.target = target;
			this.link = link;
		}
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.Command#execute()
	 */
	@Override
	public void execute() {
		if(this.target.equals("")) {
			System.out.println("Please enter valid arguments.");
		} else if(this.link.equals("")) {
			System.out.println("Please enter a valid link name.");
		} else if(this.fileSystem.getCurrent().findFSE(this.target) != null) {
			FSElement fse = this.fileSystem.getCurrent().findFSE(this.target);
			Link link = new Link(fse);
			link.setName(this.link);
			this.fileSystem.getCurrent().appendChild(link);
		} else {
			System.out.println("The target " + this.target + " cannot be found.");
		}
	}
	
	@Override
	public String toString() {
		return "ln";
	}
}
