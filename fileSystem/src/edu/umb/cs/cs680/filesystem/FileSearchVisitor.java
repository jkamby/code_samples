package edu.umb.cs.cs680.filesystem;
/**
 * 
 */


import java.util.ArrayList;

/**
 * @author JohnPaul
 *
 */
public class FileSearchVisitor implements FSVisitor {

	private String extension;
	private ArrayList<FSElement> foundFiles;
	/**
	 * 
	 */
	public FileSearchVisitor(String extension) {
		this.extension = extension;
		this.foundFiles = new ArrayList<FSElement>();
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.FSVisitor#visit(edu.umb.cs.cs680.filesystem.Link)
	 */
	@Override
	public void visit(Link link) {}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.FSVisitor#visit(edu.umb.cs.cs680.filesystem.Directory)
	 */
	@Override
	public void visit(Directory dir) {
		for(FSElement n: dir.getChildren())
			if(n.isFile())
				this.visit((File)n);
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.FSVisitor#visit(edu.umb.cs.cs680.filesystem.File)
	 */
	@Override
	public void visit(File file) {
		if (file.getName().contains(this.extension))
			foundFiles.add(file);
	}

	public ArrayList<FSElement> getFoundFiles() {
		return foundFiles;
	}
}
