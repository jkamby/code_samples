/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

import java.util.Comparator;

/**
 * @author JohnPaul
 *
 */
public class TimeStampComparator implements Comparator<FSElement> {

	/**
	 * 
	 */
	public TimeStampComparator() {}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(FSElement o1, FSElement o2) {
		FSElement fse1 = (FSElement) o1;
		FSElement fse2 = (FSElement) o2;
		return fse1.getLastModified().compareTo(fse2.getLastModified());
	}
	
	/*@Override
	public boolean equals(Object obj) {
		return false;
		
	}*/

}
