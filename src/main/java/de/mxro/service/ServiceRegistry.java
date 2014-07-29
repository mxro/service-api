package de.mxro.service;

import de.mxro.service.callbacks.GetServiceCallback;
import de.mxro.service.callbacks.ServiceUnsubscribedCallback;

public interface ServiceRegistry {

	/**
	 * <p>Seeks first match in registry of service which provides the provided
	 * interface of type clazz (e.g. service can be cast to clazz).
	 * <p>Subscribe as a user to this service
	 * 
	 * @param clazz
	 * @return
	 */
	public <InterfaceType> void subscribe(Class<InterfaceType> clazz, GetServiceCallback<InterfaceType> callback);

	/**
	 * Release a subscription for this service.
	 * @param service
	 */
	public void unsubscribe(Object service, ServiceUnsubscribedCallback callback);
	
	public void register(Service service);
	

	
}
