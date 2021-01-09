import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

public class main {
	protected static Scanner scanner = new Scanner(System.in);
	public static String dest;
	public static FileReader f; 
	public static Double latitude;
	public static Double longitude;
	public static void main(String args[])
	{
		Store stores;
		//initial JSON parsing
		while (true) {
			System.out.println("What is the name of the file containing the restaurant information?");
			dest = scanner.nextLine();
			try {
				f = new FileReader(dest);
				Gson gson = new Gson();
				stores = gson.fromJson(f, Store.class);
				break;
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		BufferedReader br;
		String g;
		Vector <Order> orders = new Vector<Order>(); 
		System.out.println("What is the name of the file containing the schedule information?");
		//filereading below
		while (true) { 
			g = scanner.nextLine();
		
			try {
				br = new BufferedReader(new FileReader(g));
				break;
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		String x;
		while (true) {
			try {
				x = br.readLine();
				if (x == null) break;
				else {
					String[] tempe = x.split(", ");
					int e = Integer.parseInt(tempe[0]);
					orders.add(new Order(e,tempe[1],tempe[2]));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
		System.out.println("What is the latitude?");
		g = scanner.nextLine();
		latitude = Double.parseDouble(g);

		
		System.out.println("What is the longitude?");
		g = scanner.nextLine();
		longitude = Double.parseDouble(g);
		
		
		System.out.println("Starting execution of program...");
		
		Map<String,Restaurant> map = new HashMap<String,Restaurant>();
		//creating semaphores
		for (int i = 0; i < stores.data.size(); i++) {
			 map.put(stores.data.get(i).name,new Restaurant(stores.data.get(i)));
		}
		//creating thread pool
		ExecutorService executorService = Executors.newCachedThreadPool();
		//threading below
		for (int i = 0; i < orders.size(); ++i) 
		{
			Restaurant r = map.get(orders.get(i).restaurant);
			if (r == null) System.out.println("Bug");
			Delivery delivery = new Delivery(orders.get(i),r);
			executorService.execute(delivery);
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(100, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All orders complete!");
	}
	public static double getDistance(double lat, double lon) {
		double delta = lon - longitude;
		double dist = Math.sin(Math.toRadians(lat)) * Math.sin(Math.toRadians(latitude)) 
				+ Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(delta));
		dist = Math.acos(dist); 
		dist = 3963.0 * dist; 
		return dist;
	}
	
	
}
