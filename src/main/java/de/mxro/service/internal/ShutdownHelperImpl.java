package de.mxro.service.internal;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import de.mxro.async.callbacks.SimpleCallback;
import de.mxro.service.utils.OperationCounter;
import de.mxro.service.utils.ShutdownHelper;

public class ShutdownHelperImpl implements ShutdownHelper {

    private final OperationCounter operationCounter;

    private final AtomicInteger shutdownAttempts;
    private final AtomicBoolean isShutdown;
    private final AtomicBoolean isShuttingDown;

    private final static int DEFAULT_DELAY = 10;
    private final static int MAX_ATTEMPTS = 300;

    @Override
    public boolean isShutdown() {
        return isShutdown.get();
    }

    @Override
    public boolean isShuttingDown() {
        return isShuttingDown.get();
    }

    @Override
    public void shutdown(final SimpleCallback callback) {
        assert !this.isShutdown() : "Cannot shut down already shut down server.";
        assert !this.isShuttingDown() : "Cannot shut down server which is already shutting down.";

        this.isShuttingDown.set(true);

        if (operationCounter.count() == 0) {
            this.isShutdown.set(true);
            callback.onSuccess();

            return;
        }

        new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(DEFAULT_DELAY);
                } catch (final InterruptedException e) {
                    throw new RuntimeException(e);
                }
                final int attempts = shutdownAttempts.incrementAndGet();

                if (attempts > MAX_ATTEMPTS) {
                    callback.onFailure(new Exception("Service could not be shut down in timeout."));
                    return;
                }

                shutdown(callback);
            }

        }.start();
    }

    public ShutdownHelperImpl(final OperationCounter operationCounter) {
        super();
        this.operationCounter = operationCounter;

        this.shutdownAttempts = new AtomicInteger(0);
        this.isShutdown = new AtomicBoolean(false);
        this.isShuttingDown = new AtomicBoolean(false);
    }

}
