
public class location {
	String name;
	String address;
	double latitude;
	double longitude;
	int drivers;
	String[] menu;
	
	public String toString() {
		String s = new String(name + " " + address + " " + latitude + " " + longitude + " " +drivers + " " + menu.toString());
		return s;
	}
}

