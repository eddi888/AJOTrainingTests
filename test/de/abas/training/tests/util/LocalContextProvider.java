package de.abas.training.tests.util;

import de.abas.erp.common.ConnectionProperties;
import de.abas.erp.common.DefaultCredentialsProvider;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.util.ContextHelper;

public class LocalContextProvider extends AbstractContextProvider {

	@Override
	public DbContext getContext() {
		return ContextHelper.buildContextManager().createClientContext(new ConnectionProperties("", ""),
				new DefaultCredentialsProvider("sy"));
	}

}
