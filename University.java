
/**
 * University. You should not change anything in this class, but you can adjust
 * the constants to simulate different results.
 *
 * Run this class as a simulation.
 *
 * Programming Project 6.1, From the book "Operating System Concepts, 9th
 * International Student Edition" by Silberschatz, Galvin, Gagne
 *
 * @author Girts Strazdins, 2016-02-08
 */
public class University {

    /**
     * How many chairs does the Teaching Assistant have in front of his office
     */
    private final static int NUMBER_OF_CHAIRS = 3;

    /**
     * Number of students which will be studying in parallel
     */
    private final static int NUMBER_OF_STUDENTS = 5;

    public static void main(String[] args) {
        TeachingAssistant ta = new TeachingAssistant(NUMBER_OF_CHAIRS);
        // Get the TA starting
        ta.start();

        // Start the students
        Student[] students = new Student[NUMBER_OF_STUDENTS];
        for (int i = 0; i < NUMBER_OF_STUDENTS; ++i) {
            Student s = new Student();
            s.setAssistant(ta);
            s.start();
            students[i] = s;
        }

        try {
            // Wait for all students to get tired
            for (int i = 0; i < NUMBER_OF_STUDENTS; ++i) {
                students[i].join();
            }

            // Send the TA home
            ta.goHome();
            ta.join();

        } catch (InterruptedException ex) {
            System.out.println("Someone crashed into the university business...");
        }
    }
}
