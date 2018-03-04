/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

import java.util.Comparator;

/**
 * @author JohnPaul
 *
 */
public class ReverseAlphabeticalComparator implements Comparator<FSElement> {

	/**
	 * 
	 */
	public ReverseAlphabeticalComparator() {}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(FSElement o1, FSElement o2) {
		FSElement fse1 = (FSElement) o1;
		FSElement fse2 = (FSElement) o2;
		return fse2.getName().compareTo(fse1.getName());
	}
	
	/*@Override
	public boolean equals(Object obj) {
		return false;
		
	}*/

}
