import java.util.concurrent.Semaphore;

public class Restaurant {
	location l;
	Semaphore sem = null;
	
	public Restaurant(location e) {
		l = e;
		sem = new Semaphore(l.drivers);
	}
}
