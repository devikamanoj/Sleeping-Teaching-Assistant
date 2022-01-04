
/**
 * Test class for Exercise on Blocking MessageQueue class. See constants
 * and their comments below
 * 
 * @author Girts Strazdins, 2016-02-08
 */
public class Main {
    /** Max items in the queue */
    private static final int QUEUE_SIZE = 3;

    /** How many items to produce */
    private static final int MAX_STUDENTS = 10;
    
    /** How long the producer should sleep after producing an item */
    private static final int STUDENT_SLEEP_TIME = 500;
    
    /* If you have a non-blocking queue:
     * 1) If consumer sleep time is lower than producer sleep, consumer will
     *     get null items
     * 2) If consumer sleep time is greater, the producer will overflow the 
     *     queue 
     */
    /** How long the consumer should sleep after consuming an item */
    private static final int TEACHER_SLEEP_TIME = 2000;
    
    public static void main(String args[]) {
        // Create a shared Message Queue
        MessageQueue queue = new MessageQueue(QUEUE_SIZE);
        
        // Create a producer and a consumer
        Student s1 = new Student(queue, STUDENT_SLEEP_TIME, "Olav");
        Student s2 = new Student(queue, STUDENT_SLEEP_TIME, "Simon");
        Student s3 = new Student(queue, STUDENT_SLEEP_TIME, "Einar");
        Student s4 = new Student(queue, STUDENT_SLEEP_TIME, "Jonathan");
        
        Teacher c = new Teacher(queue, TEACHER_SLEEP_TIME);
       
        // Run the produce+consumer processes
        s1.start();
        s2.start();
        s3.start();
        s4.start();
        c.start();
        
        // Wait for producer and consumer to finish
        try {
            s1.join();
            s2.join();
            s3.join();
            s4.join();
            c.join();
        } catch (InterruptedException ex) {
            System.out.println("Parent process interrupted"); 
        }
        
    }
}
