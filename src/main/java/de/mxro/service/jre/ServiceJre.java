package de.mxro.service.jre;

import de.mxro.service.jre.internal.ServiceActivityMonitorImpl;
import de.mxro.service.jre.internal.ShutdownHelperImpl;
import de.mxro.service.utils.ActivityMonitor;
import de.mxro.service.utils.ShutdownHelper;

public class ServiceJre {

	public static final ActivityMonitor createActivityMonitor() {
		return new ServiceActivityMonitorImpl();
	}
	
	public static final ShutdownHelper createShutdownHelper(ActivityMonitor activityMonitor) {
		return new ShutdownHelperImpl(activityMonitor);
	}

}
