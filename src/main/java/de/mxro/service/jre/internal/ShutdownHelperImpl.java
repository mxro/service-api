package de.mxro.service.jre.internal;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import de.mxro.async.callbacks.SimpleCallback;
import de.mxro.service.utils.ShutdownHelper;

public class ShutdownHelperImpl implements ShutdownHelper {

	private final AtomicInteger shutdownAttempts;
	private final AtomicBoolean isShutdown;
	private final AtomicBoolean isShuttingDown;
	
	@Override
	public boolean isShutdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isShuttingDown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void shutdown(SimpleCallback callback) {
		assert !this.isShutdown() && !this.isShuttingDown();
		
		this.isShuttingDown.set(true);
		
	}

	public ShutdownHelperImpl() {
		super();
		this.shutdownAttempts = new AtomicInteger(0);
		this.isShutdown = new AtomicBoolean(false);
		this.isShuttingDown = new AtomicBoolean(false);
	}

	
	
}
