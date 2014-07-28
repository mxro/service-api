package de.mxro.service.internal;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import de.mxro.service.SafeCast;
import de.mxro.service.Service;
import de.mxro.service.ServiceRegistry;
import de.mxro.service.callbacks.GetServiceCallback;
import de.mxro.service.callbacks.StartCallback;

public class ServiceRegistryImpl implements ServiceRegistry {

	private final List<Service> services;
	private final IdentityHashMap<Service, Boolean> initialized;
	private final IdentityHashMap<Service, List<InitializationEntry>> initializing;
	
	private final class InitializationEntry {
		public Service service;
		public GetServiceCallback<?> callback;
	}
	
	@Override
	public void register(Service service) {
		services.add(service);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <InterfaceType> void get(final Class<InterfaceType> clazz, final GetServiceCallback<InterfaceType> callback) {
		for (final Service service: services) {
			if (clazz.equals(service.getClass()) || (service instanceof SafeCast && ((SafeCast) service).supports(clazz))) {
				if (initializing.containsKey(service)) {
					InitializationEntry e = new InitializationEntry();
					e.service = service;
					e.callback = callback;
					initializing.get(service).add(e);
					return;
				}
				
				
				
				if (initialized.get(service)) {
					callback.onSuccess((InterfaceType) service);
					return;
				}
				
				service.start(new StartCallback() {
					
					@Override
					public void onStarted() {
						callback.onSuccess((InterfaceType) service);
					}
					
					@Override
					public void onFailure(Throwable t) {
						callback.onFailure(t);
					}
				});
				
				return;
			}
		}
		throw new RuntimeException("No service in registry which supports interface "+clazz);
	}


	public ServiceRegistryImpl() {
		super();
		this.services = new ArrayList<Service>();
		this.initialized = new IdentityHashMap<Service, Boolean>();
	}

	
	
}
