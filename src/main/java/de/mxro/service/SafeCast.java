package de.mxro.service;

public interface SafeCast {

	/**
	 * Return true if this service can be cast to the provided type.
	 * 
	 * @param interfaceType
	 * @return
	 */
	public boolean supports(Class<?> interfaceType);
	
}
