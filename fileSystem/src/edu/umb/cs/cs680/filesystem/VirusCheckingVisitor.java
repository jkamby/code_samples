package edu.umb.cs.cs680.filesystem;
/**
 * 
 */


//import java.util.Random;

/**
 * @author JohnPaul
 *
 */
public class VirusCheckingVisitor implements FSVisitor {

	private int quarantined;
	/**
	 * 
	 */
	public VirusCheckingVisitor() {
		this.quarantined = 0;
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
		for(FSElement m: dir.getChildren())
			if(m.isFile())
				this.visit((File)m);
	}

	/* (non-Javadoc)
	 * @see edu.umb.cs.cs680.filesystem.FSVisitor#visit(edu.umb.cs.cs680.filesystem.File)
	 */
	@Override
	public void visit(File file) {
		// System.out.println("Performing routine virus checking...");
		// int infected = (new Random()).nextInt(2); // Simulating file infection.
		// if (infected == 1)
		this.quarantined++; //assuming it always finds the file infected - for testing purposes
	}
	
	public int getQuarantinedNum() {
		return quarantined;
	}

}
