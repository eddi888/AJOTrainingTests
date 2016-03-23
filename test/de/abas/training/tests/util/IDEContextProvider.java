package de.abas.training.tests.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import de.abas.erp.common.ConnectionProperties;
import de.abas.erp.common.DefaultCredentialsProvider;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.internal.ContextManagerImpl;

public class IDEContextProvider extends AbstractContextProvider {

	@Override
	public DbContext getContext() {
		loadProperties();
		return new ContextManagerImpl().createClientContext(new ConnectionProperties(hostname, port, mandant, "Test"),
				new DefaultCredentialsProvider(password));
	}

	private void loadProperties() {
		final Properties pr = new Properties();
		final File configFile = new File("ajo-access.properties");
		try {
			pr.load(new FileReader(configFile));
			hostname = pr.getProperty("hostname");
			mandant = pr.getProperty("mandant");
			port = Integer.parseInt(pr.getProperty("port", "6550"));
			password = pr.getProperty("password");
		} catch (final FileNotFoundException e) {
			throw new RuntimeException("Could not find configuration file " + configFile.getAbsolutePath());
		} catch (final IOException e) {
			throw new RuntimeException("Could not load configuration file " + configFile.getAbsolutePath());
		}
	}

}
