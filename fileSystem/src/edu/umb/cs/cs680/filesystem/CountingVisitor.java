package edu.umb.cs.cs680.filesystem;
/**
 * 
 */


/**
 * @author JohnPaul
 *
 */
public class CountingVisitor implements FSVisitor {

	private int fileNum;
	private int dirNum;
	private int linkNum;
	/**
	 * 
	 */
	public CountingVisitor() {
		this.fileNum = 0;
		this.dirNum = 0;
		this.linkNum = 0;
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.FSVisitor#visit(edu.umb.cs.cs680.filesystem.Link)
	 */
	@Override
	public void visit(Link link) {
		this.linkNum++;
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.FSVisitor#visit(edu.umb.cs.cs680.filesystem.Directory)
	 */
	@Override
	public void visit(Directory dir) {
		this.dirNum++; //- not this simple! Shoud have been, also:
		for(FSElement q: dir.getChildren())
		if(q instanceof Directory)
		    visit((Directory) q);
		else if(q instanceof Link)
		    visit((Link)q);
		else
		    visit((File)q);
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.FSVisitor#visit(edu.umb.cs.cs680.filesystem.File)
	 */
	@Override
	public void visit(File file) {
		this.fileNum++;
	}
	
	protected int getLinkNum() {
		return this.linkNum;
	}
	
	protected int getDirNum() {
		return this.dirNum;
	}
	
	protected int getFileNum() {
		return this.fileNum;
	}
	
}
