
/**
 * A consumer that will receive objects from the Channel
 *
 * @author Girts Strazdins, 2016-02-08
 */
public class Teacher extends Thread {

    Channel channel;
    final int pause; // how long to sleep after producing each item (milliseconds)

    /**
     * @param channel Channel to use
     * @param pause how long to sleep after receiving each item (milliseconds)
     */
    public Teacher(Channel channel, int pause) {
        this.channel = channel;
        this.pause = pause;

    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(pause);
                System.out.println("Giving help");
                Object o = channel.receive();
                System.out.println("Done giving help");
            } catch (InterruptedException ex) {
                System.out.println("Someone interrupted the consumer, cancelling");
            }
        }

        //System.out.println("Consumer going home");
    }
}
