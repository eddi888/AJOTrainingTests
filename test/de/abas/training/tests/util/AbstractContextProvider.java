package de.abas.training.tests.util;

import de.abas.erp.db.DbContext;

public abstract class AbstractContextProvider {

	protected String hostname;
	protected String mandant;
	protected int port;
	protected String password;

	public String getClient() {
		return mandant;
	}

	public abstract DbContext getContext();

	public String getHostname() {
		return hostname;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

}
