/**
 ******************************************************************************
 *                    LAB 7
 ******************************************************************************

               Implement these

 *****************************************************************************/

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class DoordashDriver extends Thread
{
	private ArrayList<Customer> customersWaiting = new ArrayList<Customer>();
	private Lock driverLock = new ReentrantLock();
	private Condition sleepingCondition = driverLock.newCondition();

	public synchronized boolean addCustomerToWaiting(Customer customer)
	{
		/*
		 * Your code here
		 */
		if (customersWaiting.size()>=3) {
			return false;
		}
		
		customersWaiting.add(customer);
		Util.printMessage("Customer " + customer.getCustomerName() + " is waiting");
		StringBuilder customersString = new StringBuilder();
		
		for (int i =0;i<customersWaiting.size()-1;i++) {
			customersString.append(customersWaiting.get(i).getCustomerName());
			customersString.append(",");
		}
		customersString.append(customersWaiting.get(customersWaiting.size()-1).getCustomerName());
		/*
		 * Your code here
		 */
		Util.printMessage("Customers currently waiting: " + customersString);
		return true;
	}

	public void wakeUpDriver() {
			/*
			 * Your code here
			 */
		try {
			driverLock.lock();
			sleepingCondition.signal();
			
		}
		catch(IllegalMonitorStateException e) {
			System.out.println("Cannot signal");
		}
		finally {
			driverLock.unlock();
		}
	}

	public void run() {
		while(true) {
			while(!customersWaiting.isEmpty()) {
				/*
				 * Your code here
				 */
				Customer current;
				
				synchronized(this) {
				
				current = customersWaiting.get(0);
				customersWaiting.remove(0);
				}
				current.startingDelivery();
				try {
					Thread.sleep(ThreadLocalRandom.current().nextLong(0,1000));
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				current.finishingDelivery();
				Util.printMessage("Checking for more customers...");
			}
			try {
				/*
				 * 
				 * Your code here
				 */
				driverLock.lock();
				
				Util.printMessage("No customers, so time to sleep...");
				/*
				 * Your code here
				 */
				sleepingCondition.await();
				Util.printMessage("Someone woke me up!");
			} catch (InterruptedException ie) {
				System.out.println("ie while sleeping: " + ie.getMessage());
			}finally {
				driverLock.unlock();
			}
		}
	}
}
