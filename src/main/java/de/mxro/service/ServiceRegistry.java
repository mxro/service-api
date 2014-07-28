package de.mxro.service;

import de.mxro.service.callbacks.GetServiceCallback;

public interface ServiceRegistry {

	/**
	 * Seeks first match in registry of service which provides the provided
	 * interface of type clazz (e.g. service can be cast to clazz).
	 * 
	 * @param clazz
	 * @return
	 */
	public <InterfaceType> void get(Class<InterfaceType> clazz, GetServiceCallback<InterfaceType> callback);

	public void register(Service service);
	

	
}
