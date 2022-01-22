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