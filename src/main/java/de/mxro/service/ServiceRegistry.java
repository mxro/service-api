package de.mxro.service;

import de.mxro.async.PotentialPromise;
import de.mxro.async.callbacks.SimpleCallback;
import de.mxro.async.callbacks.ValueCallback;
import de.mxro.fn.Success;

public interface ServiceRegistry {

	/**
	 * <p>Seeks first match in registry of service which provides the provided
	 * interface of type clazz (e.g. service can be cast to clazz).
	 * <p>Subscribe as a user to this service
	 * 
	 * @param clazz
	 * @return
	 */
	public <InterfaceType> void subscribe(Class<InterfaceType> clazz, ValueCallback<InterfaceType> callback);

	public <InterfaceType> PotentialPromise<InterfaceType> subscribe(Class<InterfaceType> clazz);
	
	/**
	 * Release a subscription for this service.
	 * @param service
	 */
	public void unsubscribe(Object service, SimpleCallback callback);
	
	
	public PotentialPromise<Success> unsubscribe(Object service);
	
	public void register(Service service);
	

	
}
