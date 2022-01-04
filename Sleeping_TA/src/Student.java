
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Student Thread. You should not change anything in this class.
 * See TeachingAssistang class for more details
 *
 * Programming Project 6.1, From the book "Operating System Concepts, 9th
 * International Student Edition" by Silberschatz, Galvin, Gagne
 *
 * @author Girts Strazdins, 2016-02-08
 */
public class Student extends Thread {

    /**
     * Teaching assistant used by the student
     */
    private TeachingAssistant ta;

    /**
     * Random number generator
     */
    private final Random random = new Random();

    /**
     * The students get tired with this probability after each study period
     */
    private static final float TIREDNESS_PROBABILITY = 0.1f;

    /**
     * Minimum time it takes to study, in milliseconds
     */
    private final static int MIN_STUDY_TIME = 5000;

    void setAssistant(TeachingAssistant ta) {
        this.ta = ta;
    }

    @Override
    public void run() {
        // Student's life is an endless loop of individual studies and seeking
        // for help
        try {

            do {
                study();
                askForHelp();
            } while (!isTired());

            System.out.println("S: Student " + getId() + " going home");

        } catch (InterruptedException ex) {
            System.out.println("S: Someone interrupted student " + getId());
        } catch (Exception ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void study() throws InterruptedException {
        System.out.println("Student " + getId() + " studying hard...");
        // The study duration is unpredictable... But usually some seconds :)
        int studyDuration = MIN_STUDY_TIME + random.nextInt(1000);
        sleep(studyDuration);
    }

    /**
     * Seek help from the teaching assistant
     */
    private boolean askForHelp() throws Exception {
        if (ta == null) {
            System.out.println("S: No TA assigned for Student " + getId());
            return false;
        }
        System.out.println("S: Student " + getId() + " needs help...");
        boolean taAvailable = ta.getIntoOffice(this);
        if (!taAvailable) {
            System.out.println("S: TA is busy, Student " + getId()
                    + " tries to take a seat...");
            if (ta.placeStudentInQueue(this)) {
                System.out.println("S: Student " + getId()
                        + " got a place on a chair, waiting...");
                waitForTAToBecomeAvailable();  // This should be blocking
            } else {
                System.out.println("S: Student " + getId()
                        + " failed to get a chair - no more places. Going away...");
                return false;
            }
        }
        
        System.out.println("S: TA available, Student " + getId()
                + " got into his office. Starting consultation...");
        
        waitForConsultationToBeDone(); // This should be blocking
        System.out.println("S: Student " + getId() + " done with consultation!");
        
        return true;
    }

    /**
     * Return true if the student is tired and wants to go home
     *
     * @return
     */
    private boolean isTired() {
        // Students get tired with a specific probability
        float r = random.nextFloat();
        // Check if r is in the range 0..p 
        return r <= TIREDNESS_PROBABILITY;
    }

    /**
     * This method should block while the student is waiting in the queue.
     */
    private void waitForTAToBecomeAvailable() {
        // TODO - wait for the TA to become available
    }

    /**
     * This method should block while the consultation is in progress.
     */
    private void waitForConsultationToBeDone() {
        // TODO - wait for the consultation to be done
    }

    /**
     * A method called from the TA thread to let this student know that helping
     * session is finished.
     */
    void notifyHelpDone() {
        // TODO - Notify the student letting him/her 
        // know that the helping is done and it's time to wake up :)
    }
    
    /**
     * A method called from the TA thread to let this student know that
     * the waiting is finished and he/she can come in.
     */
    void notifyWaitingDone() {
        // TODO - Notify the student that the waiting is finished and 
        // he/she can come in.
    }
}
