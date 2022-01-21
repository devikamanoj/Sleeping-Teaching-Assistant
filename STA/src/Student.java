import java.util.concurrent.Semaphore;
class Student implements Runnable
{
	// Time to program before asking for help (in seconds).
	private int programTime;
	// Student number.
	private int studentNum;
	// Semaphore used to wakeup TA.
	private SignalSemaphore wakeup;
	// Semaphore used to wait in chairs outside office.
	private Semaphore chairs;
	// Mutex lock (binary semaphore) used to determine if TA is available.
	private Semaphore available;
	// A reference to the current thread.
	private Thread t;
	// Non-default constructor.
	public Student(int program, SignalSemaphore w, Semaphore c, Semaphore a, int num)
	{
		programTime = program;
		wakeup = w;
		chairs = c;
		available = a;
		studentNum = num;
		t = Thread.currentThread();
	}
	/**
	* The run method will infinitely loop between programming and
	* asking for help until the thread is interrupted.
	*/
	@Override 
	public void run()
	{
	// Infinite loop.
		while(true)
		{
			try
			{
				// Program first.
				System.out.println("Student " + studentNum + " has started programming for " + programTime + " seconds.");
				Thread.sleep(programTime * 1000);
				// Check to see if TA is available first.
				System.out.println("Student " + studentNum + " is checking to see if TA is available.");
				if (available.tryAcquire())
				{
					try
					{
						// Wakeup the TA.
						wakeup.take();
						System.out.println("Student " + studentNum + " has woke up the TA.");
						System.out.println("Student " + studentNum + " has started working with the TA.");
						Thread.sleep(5000);
						System.out.println("Student " + studentNum + " has stopped working with the TA.");
					}
					catch (InterruptedException e)
					{
						// Something bad happened.
						continue;
					}
					finally
					{
						available.release();
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
							System.out.println("Student " + studentNum + " is sitting outside the office. "+ "He is #" + ((Main.chair - chairs.availablePermits())) + " in line.");
							available.acquire();
							System.out.println("Student " + studentNum + " has started working with the TA.");
							Thread.sleep(5000);
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
	}
}
/**
*
*
*/
