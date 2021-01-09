/**
 ******************************************************************************
 *                    LAB 7
 ******************************************************************************

               Do not modify this file.

 *****************************************************************************/

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Customer extends Thread {

	private int customerName;
	private DoordashDriver driver;
	private Lock customerLock = new ReentrantLock();
	private Condition gettingDeliveryCondition = customerLock.newCondition();

	public Customer(int customerName, DoordashDriver driver) {
		this.customerName = customerName;
		this.driver = driver;
	}

	public int getCustomerName() {
		return customerName;
	}

	public void startingDelivery() {
		Util.printMessage("Customer " + customerName + " is getting delivery.");
	}

	public void finishingDelivery() {
		Util.printMessage("Customer " + customerName + " is done getting delivery.");
		try {
			customerLock.lock();
			gettingDeliveryCondition.signal();
		} finally {
			customerLock.unlock();
		}
	}

	public void run() {
		boolean spotsAvailable = driver.addCustomerToWaiting(this);
		if (!spotsAvailable) {
			Util.printMessage("Customer " + customerName + " leaving...no spots available.");
			return;
		}
		driver.wakeUpDriver();
		try {
			customerLock.lock();
			gettingDeliveryCondition.await();
		}
		catch (InterruptedException ie) {
			System.out.println("ie getting delivery: " + ie.getMessage());
		}
		finally {
			customerLock.unlock();
		}
		Util.printMessage("Customer " + customerName + " is leaving.");
	}
}
