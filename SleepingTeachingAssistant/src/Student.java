import java.util.concurrent.Semaphore;
class Student implements Runnable
{
	// Student number.
	private int studentNum;
	// Semaphore used to wakeup TA.
	private SignalSemaphore wakeup;
	// Semaphore used to wait in chairs outside office.
	private Semaphore chairs;
	// Mutex lock (binary semaphore) used to determine if TA is available.
	private Semaphore available;
	// A reference to the current thread.
	//private Thread t;
	// Non-default constructor.
	public Student(SignalSemaphore w, Semaphore c, Semaphore a, int num)
	{
		wakeup = w;
		chairs = c;
		available = a;
		studentNum = num;
		//t = Thread.currentThread(); //returns a reference to the currently executing thread object.
	}
	public void run()
	{
		int i=0;
		for(;i<SleepingTA.TA_visit;)
		{
			try
			{
				// Program first.
				System.out.println("Student " + studentNum + " has started programming ");
				Thread.sleep(100); //used to pause the execution of current thread for specified time in milliseconds

				// Check to see if TA is available first.
				System.out.println("Student " + studentNum + " is checking to see if TA is available.");
				if (available.tryAcquire()) // checks if the TA is sleeping
				{
					try
					{
						// Wakeup the TA.
						wakeup.take(); //goes to the method take in class SignalSemaphore
						System.out.println("Student " + studentNum + " woke up the TA.");
						System.out.println("Student " + studentNum + " has started working with the TA.");
						Thread.sleep(100);
						System.out.println("Student " + studentNum + " has stopped working with the TA.");
						i++;
					}
					catch (InterruptedException e)
					{
						continue;
					}
					finally
					{
						available.release(); //return to semaphore
					}
				}
				else
				{
					// Check to see if any chairs are available.
					System.out.println("Student " + studentNum + " could not see the TA. Checking for available chairs.");
					if (chairs.tryAcquire())
					{
						try
						{
							// Wait for TA to finish with other student.
							System.out.println("Student " + studentNum + " is sitting outside the office. "+ "He is #" + ((SleepingTA.chair - chairs.availablePermits())) + " in line.");
							available.acquire();
							System.out.println("Student " + studentNum + " has started working with the TA.");
							i++;
							Thread.sleep(100);
							System.out.println("Student " + studentNum + " has stopped working with the TA.");
							available.release();
						}
						catch (InterruptedException e)
						{
							continue;
						}
					}
					else
					{
						System.out.println("Student " + studentNum + " could not see the TA and all chairs were taken. Back to programming!");
					}
				}
			}
			catch (InterruptedException e)
			{
				break;
			}
		}
		if(i==SleepingTA.TA_visit)
		{
			System.out.println("Student "+studentNum+" HAS COMPLETED THEIR PROGRAMMING ASSIGNMENT");
		}
	}
}