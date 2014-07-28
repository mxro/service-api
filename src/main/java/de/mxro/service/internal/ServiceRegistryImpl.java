package de.mxro.service.internal;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;

import de.mxro.service.SafeCast;
import de.mxro.service.Service;
import de.mxro.service.ServiceRegistry;
import de.mxro.service.callbacks.GetServiceCallback;
import de.mxro.service.callbacks.StartCallback;

public class ServiceRegistryImpl implements ServiceRegistry {

	private final List<Service> services;
	private final IdentityHashMap<Service, Integer> subscribed;
	private final IdentityHashMap<Service, List<InitializationEntry>> initializing;

	private final class InitializationEntry {

		public GetServiceCallback<Object> callback;
	}

	@Override
	public void register(Service service) {
		synchronized (services) {
			services.add(service);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <InterfaceType> void subscribe(final Class<InterfaceType> clazz,
			final GetServiceCallback<InterfaceType> callback) {
		synchronized (services) {
			for (final Service service : services) {
				if (clazz.equals(service.getClass())
						|| (service instanceof SafeCast && ((SafeCast) service)
								.supports(clazz))) {
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

					final Integer subscribers;
					synchronized (subscribed) {
						subscribers = subscribed.get(service);
					}
					if (subscribers != null && subscribers > 0) {
						callback.onSuccess((InterfaceType) service);
						return;
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
	}

	@Override
	public void unsubscribe(Service service) {
		synchronized (subscribed) {
			Integer subscribers = subscribed.get(service);

			if (subscribers == null || subscribers == 0) {
				throw new IllegalArgumentException(
						"Trying to unsubscribe service that has not been subscribed to.");
			}

			if (subscribers == 1) {
				subscribed.remove(service);

			} else {
				subscribed.put(service, subscribers - 1);
			}
		}

	}

	public ServiceRegistryImpl() {
		super();
		this.services = new ArrayList<Service>();
		this.subscribed = new IdentityHashMap<Service, Integer>();
		this.initializing = new IdentityHashMap<Service, List<InitializationEntry>>();
	}

}
