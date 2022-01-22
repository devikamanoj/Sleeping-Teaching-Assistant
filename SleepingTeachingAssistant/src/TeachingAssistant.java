import java.util.concurrent.Semaphore;
public class TeachingAssistant implements Runnable
{
	// Semaphore used to wakeup TA.
	private SignalSemaphore wakeup;
	// Semaphore used to wait in chairs outside office.
	private Semaphore chairs;
	// Mutex lock (binary semaphore) used to determine if TA is available.
	private Semaphore available;
	// A reference to the current thread.
	private Thread t;
	public TeachingAssistant(SignalSemaphore w, Semaphore c, Semaphore a)
	{
		t = Thread.currentThread();
		wakeup = w;
		chairs = c;
		available = a;
	}
	@Override
	public void run()
	{
		for(int i=0;i<SleepingTA.TA_visit;i++)
		{
			try
			{
				System.out.println("\n No students left. The TA is going to nap.");
				wakeup.release();
				System.out.println(" The TA was awoke by a student.");
				Thread.sleep(5);
				// If there are other students waiting.
				if (chairs.availablePermits() != SleepingTA.chair)
				{
					do
					{
						Thread.sleep(1);
						chairs.release();
					}
					while (chairs.availablePermits() != SleepingTA.chair);
				}
			}
			catch (InterruptedException e)
			{
				continue;
			}
		}
	}
}
