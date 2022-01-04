

import java.util.Random;

/**
 * A producer of items to be sent over the Channel
 *
 * @author Girts Strazdins, 2016-02-08
 */
public class Student extends Thread {

    final int pause; // how long to sleep after producing each item (milliseconds)
    final Channel channel;
    private int percentDone;
    private int percentBeforeHelp;
    private Random rand;
    private String name;

    /**
     * @param channel Channel to use
     * @param pause how long to sleep after producing each item (milliseconds)
     */
    public Student(Channel channel, int pause, String name) {
        this.channel = channel;
        this.pause = pause;
        this.name = name;
        percentDone = 0;
        rand = new Random();
        percentBeforeHelp = percentDone + rand.nextInt(40);
    }

    @Override
    public void run() {
        while (percentDone < 100) {
            try {
                sleep(pause);
                if (percentBeforeHelp < percentDone) {
                    String msg = name;
                    System.out.println("Receiving help");
                    channel.send(msg);
                    System.out.println("Done receiving help");
                    percentBeforeHelp = percentDone + rand.nextInt(40);
                    System.out.println("QueueStatus: " + channel.getQueueItemList());
                }
            } catch (InterruptedException ex) {
                System.out.println("Someone interrupted the consumer, cancelling");
            }
            percentDone++;
        }
        System.out.println("Consumer going home");
    }

}
