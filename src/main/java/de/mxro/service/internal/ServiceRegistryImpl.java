package de.mxro.service.internal;

import java.util.ArrayList;
import java.util.List;

import de.mxro.service.Service;
import de.mxro.service.ServiceRegistry;

public class ServiceRegistryImpl implements ServiceRegistry {

	private final List<Service> services;
	
	@Override
	public Service get(Class<?> clazz) {
		for (Service service: services) {
			if (service.supports(clazz)) {
				return service;
			}
		}
		throw new RuntimeException("No service in registry which supports interface: "+clazz);
	}

	@Override
	public void register(Service service) {
		services.add(service);
	}

	public ServiceRegistryImpl() {
		super();
		this.services = new ArrayList<Service>();
	}

	
	
}
