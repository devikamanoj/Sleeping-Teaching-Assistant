

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageQueue implements Channel {

    //private final ArrayList<Object> queue;
    private final int size;
    private Object[] list;
    private int currentSendPlace;
    private int currentReceivePlace;
    private int currentArraySize;

    public MessageQueue(int size) {
        if (size < 1) {
            size = 1; // Do not allow weird size
        }
        this.size = size;
        list = new Object[size];
        //queue = new ArrayList<>(size);
        currentArraySize = 0;
        currentSendPlace = 0;
        currentReceivePlace = 0;
    }

    @Override
    public synchronized void send(Object item) {

        while (currentArraySize == size) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MessageQueue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        list[currentSendPlace] = item;
        currentArraySize++;
        currentSendPlace = ++currentSendPlace % size;
        notify();
    }

    // implements a nonblocking receive
    @Override
    public synchronized Object receive() {
        // TODO - block if the queue is empty, and always return the first 
        // element in the queue, OK!

        Object result = null;
        while (currentArraySize == 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MessageQueue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        int bc = currentReceivePlace;
        currentReceivePlace = ++currentReceivePlace % size;
        result = list[bc];
        list[bc] = null;
        currentArraySize--;
        notify();
        return result;
    }

    @Override
    public int getNumQueuedItems() {
        return list.length;
    }

    /**
     * Return comma-separated objects
     *
     * @return
     */
    @Override
    public String getQueueItemList() {
        String res = "";
        for (Object item : list) {
            res += item + ",";
        }
        return res;
    }
}
