package de.mxro.service.internal;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import de.mxro.service.Service;
import de.mxro.service.ServiceRegistry;
import de.mxro.service.callbacks.GetServiceCallback;
import de.mxro.service.callbacks.StartCallback;

public class ServiceRegistryImpl implements ServiceRegistry {

	private final List<Service> services;
	private final IdentityHashMap<Service, Boolean> initialized;
	
	@SuppressWarnings("unchecked")
	@Override
	public <InterfaceType> void get(Class<InterfaceType> clazz, GetServiceCallback<InterfaceType> callback) {
		for (Service service: services) {
			if (service.supports(clazz)) {
				
				if (initialized.get(service)) {
					callback.onSuccess((InterfaceType) service);
					return;
				}
				
				service.start(new StartCallback() {
					
					@Override
					public void onStarted() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onFailure(Throwable t) {
						// TODO Auto-generated method stub
						
					}
				});
				
				return;
			}
		}
		throw new RuntimeException("No service in registry which supports interface "+clazz);
	}

	@Override
	public void register(Service service) {
		services.add(service);
	}

	public ServiceRegistryImpl() {
		super();
		this.services = new ArrayList<Service>();
		this.initialized = new IdentityHashMap<Service, Boolean>();
	}

	
	
}
