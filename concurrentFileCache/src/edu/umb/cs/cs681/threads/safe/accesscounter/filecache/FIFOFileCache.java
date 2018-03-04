package edu.umb.cs.cs681.threads.safe.accesscounter.filecache;
/**
 * 
 */


import java.nio.file.Path;

/**
 * @author JohnPaul
 *
 */
public class FIFOFileCache extends FileCache {
	
	private static FIFOFileCache instance = null;
	private FIFOFileCache() {}
	
	
	public static FIFOFileCache getInstance() {
		if(instance == null)
			instance = new FIFOFileCache();
		return instance;
	}
	
	@Override
	protected String replace(Path filePath) {
		CacheMetrix toAxe = new CacheMetrix(null, filePath);
		for(CacheMetrix c: FIFOFileCache.instance.cache().values())
			if (c.getIndex() == 0)
				toAxe = c;
		FIFOFileCache.instance.cache().values().remove(toAxe);
		for(CacheMetrix c: FIFOFileCache.instance.cache().values())
			c.setIndex(c.getIndex() - 1);
		CacheMetrix newEntry = new CacheMetrix(this.dummyDiskRetrieval(filePath), filePath);
		newEntry.setIndex(FIFOFileCache.instance.cache().size());
		FIFOFileCache.instance.cache().put(filePath, newEntry);
		return newEntry.getContents(filePath);
	}
}
