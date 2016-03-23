package de.abas.documentation.advanced.record.objectsfromxml;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Test;

import de.abas.erp.db.schema.customer.Customer;
import de.abas.training.tests.util.AbstractTest;
import de.abas.training.tests.util.Util;

public class CreateNewCustomersFromXmlTest extends AbstractTest {

	CreateNewCustomersFromXml instance = new CreateNewCustomersFromXml();

	@After
	public void cleanup() {
		Util.deleteObjects(ctx, Customer.class, "PLANET");
		Util.deleteObjects(ctx, Customer.class, "MISSIONG");
	}

	public void runInvalidAbasXmlTest() {
		instance.run(new String[] { "", "files/InvalidAbasXmlTest.xml" });

		assertThat("Customer PLANET does not exist", Util.getObject(ctx, Customer.class, "PLANET"), is(nullValue()));
		assertThat("Customer MISSIONG does not exist",
				Util.getObject(ctx, Customer.class, "MISSIONG"),
				is(nullValue()));
	}

	@Test
	public void runTest() {
		instance.run(new String[] {});

		final Customer planet = Util.getObject(ctx, Customer.class, "PLANET");
		assertThat("Customer PLANET exists", planet, is(notNullValue()));
		assertThat("field addr is 'Planet Profit'", planet.getAddr(), is("Planet Profit"));
		assertThat("field street is 'Wallstrasse 18'", planet.getStreet(), is("Wallstrasse 18"));
		assertThat("field zipCode is '53547'", planet.getZipCode(), is("53547"));
		assertThat("field town is 'Dattenberg'", planet.getTown(), is("Dattenberg"));
		assertThat("field ctryCode is 'D'", planet.getCtryCode(), is("D"));
		assertThat("field phoneNo is '02638 55 13 61'", planet.getPhoneNo(), is("02638 55 13 61"));
		final Customer missiong = Util.getObject(ctx, Customer.class, "MISSIONG");
		assertThat("Customer MISSIONG exists", missiong, is(notNullValue()));
		assertThat("field addr is 'Mission G'", missiong.getAddr(), is("Mission G"));
		assertThat("field street is '95, Chemin des Bateliers'", missiong.getStreet(), is("95, Chemin des Bateliers"));
		assertThat("field zipCode is '61000'", missiong.getZipCode(), is("61000"));
		assertThat("field town is 'Alençon'", missiong.getTown(), is("Alençon"));
		assertThat("field ctryCode is 'F'", missiong.getCtryCode(), is("F"));
		assertThat("field phoneNo is '02.51.38.83.52'", missiong.getPhoneNo(), is("02.51.38.83.52"));
	}

}
