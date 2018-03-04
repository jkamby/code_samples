package edu.umb.cs.cs681.threads.safe.futures;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Casher {
	private Future future = new Future();

	public Pizza order() {
		System.out.println("An order is made.");
		Thread t = new Thread(() -> {
			RealPizza realPizza = new RealPizza();
			future.setRealPizza(realPizza);
		});
		t.start();
		return future;
	}

	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		ArrayList<Future> futures = new ArrayList<>();
		Casher casher = new Casher();
		System.out.println("Ordering pizzas at a casher counter.");
		Pizza p1 = casher.order();
		futures.add((Future)p1);
		Pizza p2 = casher.order();
		futures.add((Future)p2);
		Pizza p3 = casher.order();
		futures.add((Future)p3);

		// System.out.println(
				// "Doing something, reading newspapers, magazines, etc., " + "until pizzas are ready to pick up...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

		for (Future future : futures) {
			while (true) {
				lock.lock();
				if (future.isReady()) {
					future.getPizza();
					break;
				}
				lock.unlock();
				System.out.println("Doing something");
			}
		}
		
	}
}
