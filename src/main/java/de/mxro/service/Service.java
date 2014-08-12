package de.mxro.service;

import de.mxro.async.callbacks.SimpleCallback;

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
	 * @param callback The callback to be called upon successful or failed shutdown of the service.
	 */
	public void stop(SimpleCallback callback);

	/**
	 * Starting up this service.
	 * 
	 * @param callback The callback to be called upon successful or failed startup of the service.
	 */
	public void start(SimpleCallback callback);

	
}
