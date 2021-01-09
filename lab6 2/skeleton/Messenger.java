import java.util.Random;
import java.util.concurrent.*;

public class Messenger extends Thread
{
	Util je = new Util();
	MessageQueue que;
	public Messenger(MessageQueue que) {
		this.que = que;
	}
	public void run() {
		Random random = new Random();
		int high = 1000;
		int low = 0;
		for (int u = 0; u < 20; u++)
		{
			String s = u + " inserted";
			que.addMessage(s);
			try {
				Thread.sleep(random.nextInt(1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(je.getDate() + " Messenger " + s + " " + u);
		}
	}
}
