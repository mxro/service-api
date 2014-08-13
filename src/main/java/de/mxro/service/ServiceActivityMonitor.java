package de.mxro.service;

public interface ServiceActivityMonitor {

	
	public void notifyOperationStarted();
	
	public void notifyOperationCompleted();
	
	public int pendingOperations();
	
	
}
