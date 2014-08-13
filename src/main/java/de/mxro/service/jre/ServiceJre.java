package de.mxro.service.jre;

import de.mxro.service.jre.internal.ServiceActivityMonitorImpl;
import de.mxro.service.utils.ActivityMonitor;

public class ServiceJre {

	public static final ActivityMonitor createActivityMonitor() {
		return new ServiceActivityMonitorImpl();
	}

}
