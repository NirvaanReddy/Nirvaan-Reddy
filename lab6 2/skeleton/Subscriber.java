

import java.util.Random;
import java.util.concurrent.*;

public class Subscriber extends Thread
{
	Util je = new Util();
	MessageQueue que;
	public Subscriber (MessageQueue que)
	{
		this.que = que;
	}
	public void run() {
		Random random = new Random();
		int read = 0;
		int high = 1000;
		int low = 0;
		while (read < 20) {
			String temp = que.getMessage();
			if (temp == "") {
				continue;
			}
			else {
				try {
					Thread.sleep(random.nextInt(high));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(je.getDate() + " Subscriber read " + temp + " " + read);
				read++;
			}
		}
	}
}
