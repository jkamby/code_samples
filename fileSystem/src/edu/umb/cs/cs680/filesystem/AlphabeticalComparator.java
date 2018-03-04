/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

import java.util.Comparator;

/**
 * @author JohnPaul
 *
 */
public class AlphabeticalComparator implements Comparator<FSElement> {

	/**
	 * 
	 */
	public AlphabeticalComparator() {}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(FSElement o1, FSElement o2) {
		FSElement fse1 = (FSElement) o1;
		FSElement fse2 = (FSElement) o2;
		return fse1.getName().compareTo(fse2.getName());
	}
	
	/*@Override
	public boolean equals(Object obj) {
		return false;
		
	}*/

}
