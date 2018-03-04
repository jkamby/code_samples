package edu.umb.cs.cs680.filesystem;
/**
 * 
 */


/**
 * @author JohnPaul
 *
 */
public interface FSVisitor {

	void visit(Link link);
	void visit(Directory dir);
	void visit(File file);
}
