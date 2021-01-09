import java.util.concurrent.Semaphore;

public class Delivery implements Runnable {
	
	Order o;
	Restaurant restaurant;
	Semaphore sem;
	
	public Delivery(Order order, Restaurant restaurant) {
		this.o = order;
		this.restaurant = restaurant;
		this.sem = restaurant.sem;
	}
	

	public void run() {
		try {
			Thread.sleep(1000*o.ready);
		} catch (InterruptedException g) {
			g.printStackTrace();
		}
		try {
			sem.acquire();
		} catch (InterruptedException r) {
			r.printStackTrace();
		}
		String time = TimestampUtil.getTimestampDiff();
		System.out.println(time + " Starting delivery of " + o + " from " + o.restaurant + "!");
		double temo = main.getDistance(restaurant.l.latitude,restaurant.l.longitude);
		try {
			Thread.sleep((long) (1000*temo));
		} catch (InterruptedException s) {
			s.printStackTrace();
		}
		time = TimestampUtil.getTimestampDiff();
		System.out.println(time + " Finished delivery of " + o + " from " + o.restaurant + "!");
		sem.release();
	}
	
}
