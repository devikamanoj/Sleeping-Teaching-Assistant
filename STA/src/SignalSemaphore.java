class SignalSemaphore 
{
	private boolean signal = false;
	// Used to send the signal.
	public synchronized void take() 
	{
		this.signal = true;
		this.notify();
	}
	// Will wait until it receives a signal before continuing.
	public synchronized void release() throws InterruptedException
	{
		while(!this.signal) wait();
		this.signal = false;
	}
}
/**
This is the student thread. It will alternate between programming and seeking
help from the TA.
*/
