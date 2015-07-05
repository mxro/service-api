package de.mxro.service.internal;

import delight.async.callbacks.SimpleCallback;

import de.mxro.concurrency.Concurrency;
import de.mxro.concurrency.wrappers.SimpleAtomicBoolean;
import de.mxro.concurrency.wrappers.SimpleAtomicInteger;
import de.mxro.service.utils.OperationCounter;
import de.mxro.service.utils.ShutdownHelper;

public class ShutdownHelperImpl implements ShutdownHelper {

    private final OperationCounter operationCounter;

    private final SimpleAtomicInteger shutdownAttempts;
    private final SimpleAtomicBoolean isShutdown;
    private final SimpleAtomicBoolean isShuttingDown;
    private final Concurrency con;

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

        con.newTimer().scheduleOnce(DEFAULT_DELAY, new Runnable() {

            @Override
            public void run() {

                final int attempts = shutdownAttempts.incrementAndGet();

                if (attempts > MAX_ATTEMPTS) {
                    callback.onFailure(new Exception("Service could not be shut down in timeout."));
                    return;
                }

                shutdown(callback);
            }
        });

    }

    public ShutdownHelperImpl(final OperationCounter operationCounter, final Concurrency con) {
        super();
        this.operationCounter = operationCounter;
        this.con = con;
        this.shutdownAttempts = con.newAtomicInteger(0);
        this.isShutdown = con.newAtomicBoolean(false);
        this.isShuttingDown = con.newAtomicBoolean(false);
    }

}
