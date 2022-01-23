import java.util.concurrent.Semaphore;
import java.util.*;
public class SleepingTA
{
	static Scanner in = new Scanner(System.in);
	static int chair,TA_visit;
	public static void main(String[] args)
	{
		System.out.println();
		System.out.println("			SLEEPING TEACHING ASSISTANT");
		System.out.println("			---------------------------");
		System.out.println();
		System.out.print(" Enter the number of students in the lab: ");
		// Number of students.
		int numberofStudents = in.nextInt();

		// Create semaphores.
		SignalSemaphore wakeup = new SignalSemaphore();
		System.out.print("\n Enter the number of chairs available: ");
		chair=in.nextInt();
		System.out.print("\n Enter the number of times students can visit the Assistant: ");
		TA_visit=in.nextInt();
		Semaphore chairs = new Semaphore(chair);
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