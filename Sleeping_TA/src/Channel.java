public interface Channel
{
    public void send(Object item);
    
    /**
     * Receive an item from the channel
     * @return 
     */
    public Object receive();
    
    /** 
     * Return number of sent items not yet received on the other side 
     * @return 
     */
    public int getNumQueuedItems(); 
    
    /** 
     * Return a string representing the whole list of items waiting in the queue
     * @return 
     */
    public String getQueueItemList();
}
