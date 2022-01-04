public class University 
{
    private final static int NUMBER_OF_CHAIRS = 3;

    private final static int NUMBER_OF_STUDENTS = 5;

    public static void main(String[] args) 
    {
        TeachingAssistant ta = new TeachingAssistant(NUMBER_OF_CHAIRS);

        // Get the TA starting
        ta.start();

        // Start the students
        Student[] students = new Student[NUMBER_OF_STUDENTS];
        for (int i = 0; i < NUMBER_OF_STUDENTS; ++i) 
        {
            Student s = new Student();
            s.start();
            students[i] = s;
        }

        try 
        {
            // Wait for all students to get tired
            for (int i = 0; i < NUMBER_OF_STUDENTS; ++i) 
            {
                students[i].join();
            }

            // Send the TA home
            ta.goHome();
            ta.join();

        } 
        catch (InterruptedException ex) 
        {
            System.out.println("Someone crashed into the university business...");
        }
    }
}
