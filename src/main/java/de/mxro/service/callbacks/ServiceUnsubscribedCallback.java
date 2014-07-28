package de.mxro.service.callbacks;

public interface ServiceUnsubscribedCallback {

	public void onServiceUnsubscribed();
	
	public void onFailure(Throwable t);
	
}
