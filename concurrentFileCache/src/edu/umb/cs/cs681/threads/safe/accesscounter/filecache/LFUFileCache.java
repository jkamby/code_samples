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
public class LFUFileCache extends FileCache {
	
	private static LFUFileCache instance = null;
	private LFUFileCache() {}
	
	
	public static LFUFileCache getInstance() {
		if(instance == null)
			instance = new LFUFileCache();
		return instance;
	}
	
	@Override
	protected String replace(Path filePath) {
		CacheMetrix minE = new CacheMetrix(null, filePath);
		for(CacheMetrix e: LFUFileCache.instance.cache().values()) {
			if(minE.getCount(minE.getPath()) == 0)
				minE = e;
			else if (e.getCount(e.getPath()) < minE.getCount(minE.getPath()))
				minE = e;
		}
		LFUFileCache.instance.cache().values().remove(minE);
		for(CacheMetrix c: LFUFileCache.instance.cache().values())
			c.setIndex(c.getIndex() - 1);
		CacheMetrix newEntry = new CacheMetrix(this.dummyDiskRetrieval(filePath), filePath);
		newEntry.setIndex(LFUFileCache.instance.cache().size());
		LFUFileCache.instance.cache().put(filePath, newEntry);
		return newEntry.getContents(filePath);
	}
}
