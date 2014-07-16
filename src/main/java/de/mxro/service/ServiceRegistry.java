package de.mxro.service;

import java.util.List;

import de.mxro.service.callbacks.GetServiceCallback;
import de.mxro.service.callbacks.StartCallback;

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
	
	/**
	 * Eagerly starts services providing the supplied interfaces. Otherwise, services should be started on demand.
	 * @param clazz
	 */
	public <InterfaceType> void start(List<Class<InterfaceType>> services, StartCallback callback);
	
}
