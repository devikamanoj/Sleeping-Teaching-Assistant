import java.util.concurrent.Semaphore;
import java.util.Random;
public class Main
{
	public static void main(String[] args)
	{
		// Number of students.
		int numberofStudents = 5;
		// Create semaphores.
		SignalSemaphore wakeup = new SignalSemaphore();
		Semaphore chairs = new Semaphore(3);
		Semaphore available = new Semaphore(1);
		// Used for randomly generating program time.
		Random studentWait = new Random();
		// Create student threads and start them.
		for (int i = 0; i < numberofStudents; i++)
		{
			Thread student = new Thread(new Student(studentWait.nextInt(20), wakeup,chairs, available, i+1));
			student.start();
		}
		// Create and start TA Thread.
		Thread ta = new Thread(new TeachingAssistant(wakeup, chairs, available));
		ta.start();
	}
}
/**
* This semaphore implementation is used to "wakeup" the TA.
*/
