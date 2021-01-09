public class Order {
	int ready;
	String restaurant;
	String order;
	
	public Order(int r, String a, String b) {
			ready = r;
			restaurant = a;
			order = b;
		}
	public String toString() {
		String a = new String(order);
		return a;
	}
	private String Str(int ready2) {
		// TODO Auto-generated method stub
		String st = Integer.toString(ready2);
		return st;
	}
	}
