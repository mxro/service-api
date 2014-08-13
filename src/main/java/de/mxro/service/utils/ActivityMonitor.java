package de.mxro.service.utils;

public interface ActivityMonitor {

	
	public void notifyOperationStarted();
	
	public void notifyOperationCompleted();
	
	public int pendingOperations();
	
	
}
