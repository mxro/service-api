package de.mxro.service.utils;

public interface ShutdownHelper {

	public boolean isShutdown();
	
	public boolean isShuttingDown();
	
	public void shutdown();
	
}
