
import java.util.ArrayList;
import java.util.Random;

/**
 * Template for Teaching Assistant (TA) Thread. Fill in and update code in this
 * class to make it work correctly. Some code places are marked with TODO.
 * Something is missing there. But could be that some other things are needed in
 * addition.
 * There might be bugs in the template. If you fine some, please, inform
 * the author!
 *
 * There are several main public methods: 
 * 1) getIntoOffice(s) - interface for students to try to get into the TAs office
 * 2) placeStudentInQueue(s) - try to place a student in the waiting queue
 * 3) run - the main thread method to run the "TAs office activities"
 * 4) goHome() - ask the TA to stop activities
 *
 * Programming Project 6.1, From the book "Operating System Concepts, 9th
 * International Student Edition" by Silberschatz, Galvin, Gagne
 *
 * @author Girts Strazdins, 2016-02-08
 */
public class TeachingAssistant extends Thread {
    /**
     * The student currently sitting in the TAs office
     */
    private Student currentStudent = null;

    /** Random number generator */
    private final Random random = new Random();
    
    /** Students waiting in the queue */
    private final ArrayList<Student> waitingStudents;
    
    /** Max number of chairs available in the waiting queue */
    private final int numberOfChairs;

    /** The TA will continue to nap/work while this flag is true */
    private boolean shouldWork;
    
    /** Minimum time it takes to help, in milliseconds */
    private final static int MIN_HELP_TIME = 5000; 
    
    /**
     * Set up the TAs office
     * @param numberOfChairs number of chairs in the waiting queue in the front
     * of the TAs office
     */
    public TeachingAssistant(int numberOfChairs) {
        // Create the chairs
        this.numberOfChairs = numberOfChairs;
        this.waitingStudents = new ArrayList<>(numberOfChairs);
        this.shouldWork = true;
    }

    /**
     * A student tries to approach TAs office and get a consultation. If
     * the TA is available (sleeping), the student is accepted, 
     * and the consultation starts. If the TA is busy, false is returned, no
     * consultation started in this case.
     * @param s
     * @return true if student accepted, false otherwise
     */
    public boolean getIntoOffice(Student s) {
        if (isBusy()) return false;
        
        // Not busy, accept the student
        this.currentStudent = s;
        
        // TODO - notify the TA to wake up
        
        return true;
    }
    
    /**
     * A student wants to take a place in the waiting queue. Place him/her
     * in the queue if there is a space
     * 
     * @param s
     * 
     * @return true if the student is placed in the queue, false if there 
     *   were no more free spaces in the queue and the student should 
     *   come another time
     * @throws java.lang.Exception
     */
    public boolean placeStudentInQueue(Student s) throws Exception {
        if (waitingStudents.contains(s)) {
            // Should normally not experience this situation
            throw new Exception("TA: Error!!! Student " + s.getId() 
                    + " wants to wait, but he/she is already in the waiting queue!"
                + " Are we experiencing quantum effects?");
        }
        
        System.out.println("TA: Student " + s.getId() + " wants to wait, "
            + waitingStudents.size() + " student(s) already waiting");
        if (getNumberOfFreeChairs() < 1) return false;
        // Add the student to the queue
        waitingStudents.add(s);
        return true;
    }
    
    /**
     * Ask the TA to go home once he has helped all the waiting students
     * Will not accept any new students in the queue
     */
    public void goHome() {
        shouldWork = false;
    }
    
    /***************************************************
     * Teaching Assistants life cycle functions
     ***************************************************/

    /**
     * Run the nap/help cycle. The TA will help the first student approaching
     * the office, and all students waiting in the queue, in FCFS order. When
     * there are no more students waiting, the TA goes back to sleep. And the
     * next student coming should wake the TA up again.
     */
    @Override
    public void run() {
        try {
            while (shouldWork) {
                // The TA can sleep indefinitely, until someone wakes him up
                sleepUntilFirstStudent();
                // Help the student which wake the TA up, and all the
                // students waiting in the queue
                while (currentStudent != null) {
                    helpStudent();
                    currentStudent = getNextWaitingStudent();
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Someone interrupted the TA, closing the office...");
        }
    }

    /**
     * Help the student and notify him/her when help is done. A blocking method.
     * @throws InterruptedException 
     */
    private void helpStudent() throws InterruptedException {
        // Help takes some time
        System.out.println("TA: TA helping student " + currentStudent.getId() + "...");
        int sleepTime = MIN_HELP_TIME + random.nextInt(1000);
        sleep(sleepTime);
        System.out.println("TA: TA done with helping student " 
                + currentStudent.getId() + "...");
        
        // Send "Help done" signal to the student letting him/her 
        // know that the helping is done and it's time to wake up and go home :)
        this.currentStudent.notifyHelpDone();
        
        // Done with this student
        this.currentStudent = null;
    }

    /**
     * Wait until a student arrives, assuming that currently the TA does not
     * have any student to help to. A blocking method
     * @throws InterruptedException 
     */
    private void sleepUntilFirstStudent() throws InterruptedException {
        System.out.println("TA: TA taking a nap. ZzZz...");
        
        // TODO - Wait for "Wake up" signal from a student
    }

    /**
     * Get the first student waiting in the queue (the one which came first).
     * Notify the student that he/she can come in
     * @return the waiting student or null if the queue is empty
     */
    private Student getNextWaitingStudent() {
        if (waitingStudents.size() < 1) return null;
        System.out.println("TA: TA asking a sitting student to come in.");
        Student s = waitingStudents.remove(0);
        // Notify the student that he/she can come in
        s.notifyWaitingDone();
        return s;
    }
    
    /***************************************************
     * Helper functions
     ***************************************************/
    
    /**
     * Return true if the TA is currently busy helping a student
     *
     * @return
     */
    private boolean isBusy() {
        return currentStudent != null;
    }

    /**
     * Return the number of free places in the waiting queue
     * @return 
     */
    private int getNumberOfFreeChairs() {
        return this.numberOfChairs - waitingStudents.size();
    }
}
