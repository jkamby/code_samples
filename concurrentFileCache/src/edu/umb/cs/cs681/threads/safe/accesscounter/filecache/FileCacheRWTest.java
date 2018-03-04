package edu.umb.cs.cs681.threads.safe.accesscounter.filecache;

// import java.util.concurrent.locks.ReentrantLock;

public class FileCacheRWTest {

	public static void main(String[] args) {
		// ReentrantLock lock = new ReentrantLock();
		Thread[] rHands = new Thread[10];
		for (int j = 0; j < 10; j++) {
			RequestHandlerRW rHandler = new RequestHandlerRW(j*10, j*10 + 10);
			rHands[j] = new Thread(rHandler);
			rHands[j].start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			
			rHands[j].interrupt();
			
			rHandler.setDone();
			
			try {
				rHands[j].join();
			} catch (InterruptedException e) {}
		}
        
	}
}
