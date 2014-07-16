package de.mxro.service;

public interface ServiceRegistry {

	/**
	 * Seeks first match in registry of service which provides the provided
	 * interface of type clazz (e.g. service can be cast to clazz).
	 * 
	 * @param clazz
	 * @return
	 */
	public <InterfaceType> InterfaceType get(Class<InterfaceType> clazz);

	public void register(Service service);
	
	/**
	 * Disallow registration of any more services.
	 */
	public void lock();
	
}
