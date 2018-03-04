package edu.umb.cs.cs680.filesystem;
/**
 * 
 */


/**
 * @author JohnPaul
 *
 */
public class SizeCountingVisitor implements FSVisitor {

	private int totalSize;
	/**
	 * 
	 */
	public SizeCountingVisitor() {
		this.totalSize = 0;
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.FSVisitor#visit(edu.umb.cs.cs680.filesystem.Link)
	 */
	@Override
	public void visit(Link link) {
		this.totalSize += link.getDiskUtil();
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.FSVisitor#visit(edu.umb.cs.cs680.filesystem.Directory)
	 */
	@Override
	public void visit(Directory dir) {
		this.totalSize += dir.getDiskUtil();
		for(FSElement s: dir.getChildren())
			if(s instanceof Directory)
			    visit((Directory)s);
			else if(s instanceof Link)
			    visit((Link)s);
			else
			    visit((File)s);
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.FSVisitor#visit(edu.umb.cs.cs680.filesystem.File)
	 */
	@Override
	public void visit(File file) {
		this.totalSize += file.getDiskUtil();
	}

	public int getTotalSize() {
		return this.totalSize;
	}
}
