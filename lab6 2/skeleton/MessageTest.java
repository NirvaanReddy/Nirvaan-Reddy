

import java.util.concurrent.*;

public class MessageTest
{
	public static void main(String [] args)
	{
		MessageQueue que = new MessageQueue();
		Messenger j = new Messenger(que);
		Subscriber sub = new Subscriber(que);
		ExecutorService s = Executors.newFixedThreadPool(2);
		s.execute(sub);
		s.execute(j);
		s.shutdown();
		if (s.isTerminated()) return;
		
	}
}
