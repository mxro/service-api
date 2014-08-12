package de.mxro.service.callbacks;

import de.mxro.async.callbacks.SimpleCallback;

/**
 * Callback to receive notifcation when a server component has been started
 * completely.
 * 
 * @author <a href="http://www.mxro.de">Max Rohde</a>
 * 
 */
public interface StartCallback extends SimpleCallback {

	/**
	 * Called once when the component has been started successfully.
	 */
	public void onSuccess();

	/**
	 * Called once when an error occured during component startup.
	 * 
	 * @param t
	 */
	public void onFailure(Throwable t);

}
