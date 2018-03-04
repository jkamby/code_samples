/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

import java.util.ArrayList;

/**
 * @author JohnPaul
 *
 */
public class TheClient {

	/**
	 * 
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File f = new File(null, "f", "Felix");
		File e = new File(null, "e", "Ernie");
		Link y = new Link (e); y.setName("y");
		Directory pictures = new Directory (null, new ArrayList<FSElement>(), "pictures", "Pisces");
		pictures.appendChild(y); pictures.appendChild(e); pictures.appendChild(f);
		File d = new File(null, "d", "Derrick");
		Directory home = new Directory (null, new ArrayList<FSElement>(), "home", "Harry");
		home.appendChild(pictures); home.appendChild(d);
		File c = new File(null, "c", "Collin");
		File b = new File(null, "b", "Bart");
		File a = new File(null, "a", "Angela");
		Directory system = new Directory (null, new ArrayList<FSElement>(), "system", "System");
		Link x = new Link (system); x.setName("x");
		home.appendChild(x);
		system.appendChild(a); system.appendChild(b); system.appendChild(c);
		Directory root = new Directory (null, new ArrayList<FSElement>(), "root", "System");
		root.appendChild(system); root.appendChild(home);
		FileSystem jpksys = FileSystem.getInstance();
		jpksys.setRoot(root);
		
		System.out.println("The size of x is: " + x.getSize());
		System.out.println("The size of y is: " + y.getSize());
		System.out.println("The size of root is: " + root.getSize());
		
		System.out.println("The targetSize of x is: " + x.getTargetSize());
		System.out.println("The targetSize of y is: " + y.getTargetSize());
		
		jpksys.showAllElements();
		
		Shell jpkOS = new Shell("JPKK->");
		while(true)// bad idea
			jpkOS.processInput();
		
	}

}
