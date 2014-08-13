package de.mxro.service.jre;

import de.mxro.service.jre.internal.OperationCounterImpl;
import de.mxro.service.jre.internal.ShutdownHelperImpl;
import de.mxro.service.utils.OperationCounter;
import de.mxro.service.utils.ShutdownHelper;

public class ServiceJre {

	public static final OperationCounter createOperationCounter() {
		return new OperationCounterImpl();
	}
	
	public static final ShutdownHelper createShutdownHelper(OperationCounter activityMonitor) {
		return new ShutdownHelperImpl(activityMonitor);
	}

}
