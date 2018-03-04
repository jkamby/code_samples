package edu.umb.cs.cs681.threads.safe.accesscounter.filecache;
/**
 * 
 */


import java.nio.file.Path;

/**
 * @author JohnPaul
 *
 */
public class FIFOFileCacheRW extends FileCacheRW {
	
	private static FIFOFileCacheRW instance = null;
	private FIFOFileCacheRW() {}
	
	
	public static FIFOFileCacheRW getInstance() {
		if(instance == null)
			instance = new FIFOFileCacheRW();
		return instance;
	}
	
	@Override
	protected String replace(Path filePath) {
		CacheMetrix toAxe = new CacheMetrix(null, filePath);
		for(CacheMetrix c: FIFOFileCacheRW.instance.cache().values())
			if (c.getIndex() == 0)
				toAxe = c;
		FIFOFileCacheRW.instance.cache().values().remove(toAxe);
		for(CacheMetrix c: FIFOFileCacheRW.instance.cache().values())
			c.setIndex(c.getIndex() - 1);
		CacheMetrix newEntry = new CacheMetrix(this.dummyDiskRetrieval(filePath), filePath);
		newEntry.setIndex(FIFOFileCacheRW.instance.cache().size());
		FIFOFileCacheRW.instance.cache().put(filePath, newEntry);
		return newEntry.getContents(filePath);
	}
}
