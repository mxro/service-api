package de.mxro.service.jre;

import de.mxro.service.jre.internal.OperationCounterImpl;
import de.mxro.service.jre.internal.ShutdownHelperImpl;
import de.mxro.service.utils.OperationCounter;
import de.mxro.service.utils.ShutdownHelper;

public class ServiceJre {

	/**
	 * <p>A simple counter for how many operations a service is processing at any
	 * point in time.
	 * 
	 * @return A new operation counter instance.
	 */
	public static final OperationCounter createOperationCounter() {
		return new OperationCounterImpl();
	}

	/**
	 * <p>A helper to make shutdown operations safer.
	 * 
	 * @param operationCounter
	 * @return
	 */
	public static final ShutdownHelper createShutdownHelper(
			OperationCounter operationCounter) {
		return new ShutdownHelperImpl(operationCounter);
	}

}
