package de.mxro.service;

public interface ServiceRegistry {

	/**
	 * Seeks first match in registry of service which provides the provided
	 * interface of type (e.g. service can be cast to clazz).
	 * 
	 * @param clazz
	 * @return
	 */
	public Service get(Class<?> clazz);

}
