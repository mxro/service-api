package de.mxro.service.callbacks;

public interface GetServiceCallback<InterfaceType> {

	public void onSuccess(InterfaceType service);
	
	public void onFailure(Throwable t);
	
}
