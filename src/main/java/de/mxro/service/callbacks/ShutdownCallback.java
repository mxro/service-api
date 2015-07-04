/*******************************************************************************
 * Copyright 2011 Max Erik Rohde http://www.mxro.de
 * 
 * All rights reserved.
 ******************************************************************************/
package de.mxro.service.callbacks;

import delight.async.callbacks.SimpleCallback;

/**
 * Callback, which can be used, when a service component is shut down.
 * 
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 * 
 */
public interface ShutdownCallback extends SimpleCallback {

	/**
	 * This method is called when the shutdown could be completed successfully.
	 */
	public abstract void onSuccess();

	/**
	 * This method is called when an error occurs in the shutdown process.
	 * 
	 * @param t
	 */
	public abstract void onFailure(Throwable t);
}
