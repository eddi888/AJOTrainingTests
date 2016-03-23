package de.abas.training.tests.util;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import de.abas.erp.db.DbContext;

public abstract class AbstractTest {

	public static Logger logger = TestLogger.getLogger();
	@Rule
	public TestName testName = new TestName();
	public DbContext ctx;

	public void setDefaultLogger(final String filename) {
		try {
			ctx.setLogger(new FileWriter(filename));
		} catch (final IOException e) {
			e.printStackTrace(ctx.out());
		}
	}

	@Before
	public void setup() {
		logger.debug(TestLogger.SETUP_MESSAGE);
		AbstractContextProvider contextProvider;
		if (Boolean.getBoolean("tests.useLocalContext")) {
			contextProvider = new LocalContextProvider();
		} else {
			contextProvider = new IDEContextProvider();

		}
		ctx = contextProvider.getContext();
		logger.debug(String.format(TestLogger.CONNECTION_MESSAGE,
				contextProvider.getClient(),
				contextProvider.getHostname()));
		setDefaultLogger(getClass().getName() + "." + testName.getMethodName() + ".edp.log");
	}

}
