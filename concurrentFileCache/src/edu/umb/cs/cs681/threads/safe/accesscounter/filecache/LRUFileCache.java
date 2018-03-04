package edu.umb.cs.cs681.threads.safe.accesscounter.filecache;
/**
 * 
 */


import java.nio.file.Path;
import java.util.HashMap;

/**
 * @author JohnPaul
 *
 */
public class LRUFileCache extends FileCache {
	
	private static LRUFileCache instance = null;
	private LRUFileCache() {}
	
	
	public static LRUFileCache getInstance() {
		if(instance == null)
			instance = new LRUFileCache();
		return instance;
	}
	
	@Override
	protected String replace(Path filePath) {
		CacheMetrix oldE = new CacheMetrix(null, filePath);
		for(CacheMetrix e: LRUFileCache.instance.cache().values()) {
			if(oldE.getDate() == null)
				oldE = e;
			else if (e.getDate().before(oldE.getDate()))
				oldE = e;
		}
		LRUFileCache.instance.cache().values().remove(oldE);
		for(CacheMetrix c: LRUFileCache.instance.cache().values())
			c.setIndex(c.getIndex() - 1);
		CacheMetrix newEntry = new CacheMetrix(this.dummyDiskRetrieval(filePath), filePath);
		newEntry.setIndex(LRUFileCache.instance.cache().size());
		LRUFileCache.instance.cache().put(filePath, newEntry);
		return newEntry.getContents(filePath);
	}
}
