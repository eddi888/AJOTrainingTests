package de.abas.documentation.advanced.record.transaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Test;

import de.abas.erp.db.EditorAction;
import de.abas.erp.db.exception.CommandException;
import de.abas.erp.db.schema.customer.Customer;
import de.abas.erp.db.schema.customer.CustomerContact;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.ProductEditor;
import de.abas.erp.db.schema.sales.Quotation;
import de.abas.erp.db.schema.sales.QuotationEditor;
import de.abas.training.tests.util.AbstractTest;
import de.abas.training.tests.util.Util;

public class MakeTransactionTest extends AbstractTest {

	MakeTransaction instance = new MakeTransaction();

	@After
	public void cleanup() {
		try {
			deleteQuotation();
		} catch (final CommandException e) {
			ctx.out().println("Error: " + e.getMessage());
		}
		Util.deleteObjects(ctx, Product.class, "TRACTOR");
		Util.deleteObjects(ctx, CustomerContact.class, "CUSCON1");
		Util.deleteObjects(ctx, Customer.class, "MUSTER");
	}

	@Test
	public void runRollbackTest() {
		assertThat("product 'TRACTOR' does not exist", Util.getObject(ctx, Product.class, "TRACTOR"), is(nullValue()));

		instance.run(new String[] {});

		assertThat("customer 'MUSTER' does not exist", Util.getObject(ctx, Customer.class, "MUSTER"), is(nullValue()));
		assertThat("customer contact 'CUSCON1' does not exist",
				Util.getObject(ctx, Customer.class, "CUSCON1"),
				is(nullValue()));
		assertThat("quotation 'AMUSTER' does not exist",
				Util.getObject(ctx, Quotation.class, "AMUSTER"),
				is(nullValue()));

	}

	@Test
	public void runTest() {
		final ProductEditor productEditor = ctx.newObject(ProductEditor.class);
		productEditor.setSwd("TRACTOR");
		productEditor.commit();

		assertThat("product 'TRACTOR' exists", Util.getObject(ctx, Product.class, "TRACTOR"), is(notNullValue()));

		instance.run(new String[] {});

		assertThat("customer 'MUSTER' exists", Util.getObject(ctx, Customer.class, "MUSTER"), is(notNullValue()));
		assertThat("customer contact 'CUSCON1' exists",
				Util.getObject(ctx, CustomerContact.class, "CUSCON1"),
				is(notNullValue()));
		assertThat("quotation 'AMUSTER' exists", Util.getObject(ctx, Quotation.class, "AMUSTER"), is(notNullValue()));

	}

	private void deleteQuotation() throws CommandException {
		final Quotation quotation = Util.getObject(ctx, Quotation.class, "AMUSTER");
		if (quotation != null) {
			final QuotationEditor editor = quotation.createEditor();
			editor.open(EditorAction.UPDATE);
			editor.table().getRow(1).setStatus("S");
			editor.commit();
		}
	}

}
