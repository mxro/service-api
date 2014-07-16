package de.mxro.service;

import de.mxro.service.internal.ServiceRegistryImpl;

public class Services {

	public static ServiceRegistry create() {
		return new ServiceRegistryImpl();
	}
	
}
