class SignalSemaphore 
{
	private boolean signal = false;
	// Used to send the signal.
	public synchronized void take() 
	{
		this.signal = true;
		this.notify(); // to wake a thread up
	}
	// Will wait until it receives a signal before continuing.
	public synchronized void release() throws InterruptedException
	{
		while(!this.signal) 
			wait();  //causes the current thread to wait indefinitely until another thread either invokes notify() 
		this.signal = false;
	}
}