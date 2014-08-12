package de.mxro.service;

import de.mxro.async.callbacks.SimpleCallback;
import de.mxro.service.callbacks.ShutdownCallback;

/**
 * <p>
 * A service which can be started and stopped.
 * <p>
 * Use {@link ServiceRegistry} to manage services implementing this interface.
 * 
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 *
 */
public interface Service {

	/**
	 * Stops this service and releases all resources held required for the
	 * service.
	 * 
	 * @param callback
	 */
	public void stop(ShutdownCallback callback);

	/**
	 * Starting up this service.
	 * 
	 * @param callback
	 */
	public void start(SimpleCallback callback);

	
}
