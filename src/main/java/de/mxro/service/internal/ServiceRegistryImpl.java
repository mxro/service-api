package de.mxro.service.internal;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import mx.gwtutils.concurrent.SingleInstanceQueueWorker;
import de.mxro.service.Service;
import de.mxro.service.ServiceRegistry;
import de.mxro.service.callbacks.GetServiceCallback;
import de.mxro.service.callbacks.StartCallback;

public class ServiceRegistryImpl implements ServiceRegistry {

	private final List<Service> services;
	private final IdentityHashMap<Service, Boolean> initialized;
	private final SingleInstanceQueueWorker<ServiceRegistryOperation> worker;
	
	
	@Override
	public void register(Service service) {
		services.add(service);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <InterfaceType> void get(final Class<InterfaceType> clazz, final GetServiceCallback<InterfaceType> callback) {
		for (final Service service: services) {
			if (clazz.equals(service.getClass()) || service.supports(clazz)) {
				
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


	
	@Override
	public <InterfaceType> void start(List<Class<InterfaceType>> services,
			StartCallback callback) {
		start()
	}


	public ServiceRegistryImpl() {
		super();
		this.services = new ArrayList<Service>();
		this.initialized = new IdentityHashMap<Service, Boolean>();
	}

	
	
}
