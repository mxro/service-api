package de.mxro.service.utils;

import delight.async.callbacks.SimpleCallback;

public interface ShutdownHelper {

	public boolean isShutdown();
	
	public boolean isShuttingDown();
	
	public void shutdown(SimpleCallback callback);
	
}
