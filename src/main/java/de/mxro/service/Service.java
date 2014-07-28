package de.mxro.service;

import de.mxro.service.callbacks.ShutdownCallback;
import de.mxro.service.callbacks.StartCallback;

public interface Service {

	/**
	 * Call to shutdown this components 'gracefully'.
	 * 
	 * @param callback
	 */
	public void stop(ShutdownCallback callback);

	/**
	 * Starting up this service.
	 * 
	 * @param callback
	 */
	public void start(StartCallback callback);
	
	
}
