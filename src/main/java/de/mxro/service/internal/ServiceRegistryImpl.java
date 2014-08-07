package de.mxro.service.internal;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;

import de.mxro.async.AsyncPromise;
import de.mxro.async.callbacks.SimpleCallback;
import de.mxro.async.callbacks.ValueCallback;
import de.mxro.fn.Success;
import de.mxro.service.SafeCast;
import de.mxro.service.Service;
import de.mxro.service.ServiceRegistry;
import de.mxro.service.callbacks.GetServiceCallback;
import de.mxro.service.callbacks.ShutdownCallback;
import de.mxro.service.callbacks.StartCallback;

public class ServiceRegistryImpl implements ServiceRegistry {

	private final List<Service> services;
	private final IdentityHashMap<Service, Integer> subscribed;
	private final IdentityHashMap<Service, List<InitializationEntry>> initializing;
	private final IdentityHashMap<Service, List<DeinitializationEntry>> deinitializing;

	private final class DeinitializationEntry {
		public ShutdownCallback callback;
	}

	private final class InitializationEntry {

		public GetServiceCallback<Object> callback;
	}

	@Override
	public void register(Service service) {
		synchronized (services) {
			services.add(service);
		}
	}

	
	
	
	@Override
	public <InterfaceType> AsyncPromise<InterfaceType> subscribe(
			final Class<InterfaceType> clazz) {
		return new AsyncPromise<InterfaceType>() {

			@Override
			public void get(ValueCallback<InterfaceType> callback) {
				subscribe(clazz, callback);
			}
		};
	}




	@SuppressWarnings("unchecked")
	@Override
	public <InterfaceType> void subscribe(final Class<InterfaceType> clazz,
			final ValueCallback<InterfaceType> callback) {
		ArrayList<Service> servicesCopy;
		synchronized (services) {
			servicesCopy = new ArrayList<Service>(services);
		}

		for (final Service service : servicesCopy) {
			
			if (clazz.equals(service.getClass())
					|| (service instanceof SafeCast && ((SafeCast) service)
							.supports(clazz))) {
				
				final Integer subscribers;
				synchronized (subscribed) {
					subscribers = subscribed.get(service);
				}
				
				if (subscribers != null && subscribers > 0) {
					callback.onSuccess((InterfaceType) service);
					return;
				}

				synchronized (initializing) {
					if (initializing.containsKey(service)) {
						InitializationEntry e = new InitializationEntry();
						e.callback = (GetServiceCallback<Object>) callback;
						initializing.get(service).add(e);
						return;
					}

					initializing.put(service,
							new LinkedList<InitializationEntry>());
				}

				synchronized (deinitializing) {
					if (deinitializing.containsKey(service)) {
						DeinitializationEntry e = new DeinitializationEntry();
						e.callback = new ShutdownCallback() {

							@Override
							public void onSuccess() {
								subscribe(clazz, callback);
							}

							@Override
							public void onFailure(Throwable t) {
								callback.onFailure(new Exception(
										"Error during pending deinitialization.",
										t));
							}
						};
						deinitializing.get(service).add(e);
						return;
					}
				}
			
				service.start(new StartCallback() {

					@Override
					public void onStarted() {

						final Integer subscribers;
						synchronized (subscribed) {
							subscribers = subscribed.get(service);
							if (subscribers == null) {
								subscribed.put(service, 1);
							} else {
								subscribed.put(service, subscribers + 1);
							}
						}

						synchronized (initializing) {

							List<InitializationEntry> entries = initializing
									.get(service);
							for (InitializationEntry e : entries) {
								e.callback.onSuccess(service);
								return;
							}
							initializing.remove(service);
						}

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
		throw new RuntimeException(
				"No service in registry which supports interface " + clazz);

	}

	
	
	
	@Override
	public AsyncPromise<Success> unsubscribe(Object service) {
		return new AsyncPromise<Success>() {

			@Override
			public void get(final ValueCallback<Success> callback) {
				unsubscribe(service, new SimpleCallback() {
					
					@Override
					public void onFailure(Throwable t) {
						
					}
					
					@Override
					public void onSuccess() {
						callback.onSuccess(Success.INSTANCE);
					}
				});
			}
		};
	}




	@Override
	public void unsubscribe(final Object service_raw,
			final SimpleCallback callback) {
		final Service service = (Service) service_raw;
		
		synchronized (subscribed) {
			Integer subscribers = subscribed.get(service);

			if (subscribers == null || subscribers == 0) {
				throw new IllegalArgumentException(
						"Trying to unsubscribe service that has not been subscribed to.");
			}

			if (subscribers == 1) {
				subscribed.remove(service);

				synchronized (deinitializing) {

					assert !deinitializing.containsKey(service);

					deinitializing
							.put(service,
									new LinkedList<ServiceRegistryImpl.DeinitializationEntry>());

					service.stop(new ShutdownCallback() {

						@Override
						public void onSuccess() {
							synchronized (deinitializing) {

								for (DeinitializationEntry e : deinitializing
										.get(service)) {
									e.callback.onSuccess();
								}

								deinitializing.remove(service);

							}

							callback.onSuccess();
						}

						@Override
						public void onFailure(Throwable t) {
							callback.onFailure(t);
						}
					});

				}
				return;

			}

			subscribed.put(service, subscribers - 1);
		}
		callback.onSuccess();

	}

	public ServiceRegistryImpl() {
		super();
		this.services = new ArrayList<Service>();
		this.subscribed = new IdentityHashMap<Service, Integer>();
		this.initializing = new IdentityHashMap<Service, List<InitializationEntry>>();
		this.deinitializing = new IdentityHashMap<Service, List<DeinitializationEntry>>();
	}

}
