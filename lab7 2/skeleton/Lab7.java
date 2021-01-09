/**
 ******************************************************************************
 *                    LAB 7
 ******************************************************************************

               Implement the main method()

 *****************************************************************************/

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Lab7
{
	public static final int totalCustomers = 15;
	public static final int totalSpots = 3; //waiting queue
	public static int customersSoFar=1;

	public static void main(String [] args)
	{
				/*
				 * Your code here
				 */
		ExecutorService exec = Executors.newCachedThreadPool();
		DoordashDriver myDriver = new DoordashDriver();
		myDriver.start();
		while (customersSoFar<=totalCustomers) {
			exec.execute(new Customer(customersSoFar++,myDriver));
			try {
				Thread.sleep(ThreadLocalRandom.current().nextLong(0,1000));
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		exec.shutdown(); 
		while (!exec.isTerminated())
		{
			Thread.yield();
		}
		Util.printMessage("No more customers ordering today...");
		Util.printMessage("All done for today!  Time to go home!");
	}
}
