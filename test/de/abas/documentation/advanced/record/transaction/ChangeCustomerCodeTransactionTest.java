package de.abas.documentation.advanced.record.transaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.javafx.scene.control.skin.VirtualFlow.ArrayLinkedList;

import de.abas.erp.db.EditorAction;
import de.abas.erp.db.exception.CommandException;
import de.abas.erp.db.schema.customer.Customer;
import de.abas.erp.db.schema.customer.CustomerEditor;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.training.tests.util.AbstractTest;

public class ChangeCustomerCodeTransactionTest extends AbstractTest {

	ChangeCustomerCodeTransaction instance = new ChangeCustomerCodeTransaction();
	ArrayLinkedList<String> codes = new ArrayLinkedList<String>();
	CustomerEditor editor = null;

	@After
	public void cleanup() {
		for (final Customer customer : getCustomers()) {
			changeCode(customer, codes.getFirst());
			codes.removeFirst();
		}

	}

	@Test
	public void runRollbackTest() {
		for (final Customer customer : getCustomers()) {
			changeCode(customer, "test");
		}

		for (final Customer customer : getCustomers()) {
			assertThat("code is not empty", customer.getCode(), is(not("")));
		}

		instance.run(new String[] {});

		for (final Customer customer : getCustomers()) {
			assertThat("code is test", customer.getCode(), is("TEST"));
		}
	}

	@Test
	public void runTest() {
		for (final Customer customer : getCustomers()) {
			changeCode(customer, "");
		}

		for (final Customer customer : getCustomers()) {
			assertThat("code is empty", customer.getCode(), is(""));
		}

		instance.run(new String[] {});

		for (final Customer customer : getCustomers()) {
			assertThat("code is test", customer.getCode(), is("CODE: " + customer.getIdno()));
		}
	}

	@Before
	@Override
	public void setup() {
		super.setup();
		for (final Customer customer : getCustomers()) {
			codes.addLast(customer.getCode());
		}
	}

	private void abortEditor() {
		if (editor != null) {
			if (editor.active()) {
				editor.abort();
			}
		}
	}

	private void changeCode(final Customer customer, String code) {
		try {
			editor = customer.createEditor();
			editor.open(EditorAction.UPDATE);
			editor.setCode(code);
			editor.commit();
		} catch (final CommandException e) {
			ctx.out().println("Error: " + e.getMessage());
		} finally {
			abortEditor();
		}
	}

	private List<Customer> getCustomers() {
		ctx.flush();
		return ctx.createQuery(SelectionBuilder.create(Customer.class)
				.add(Conditions.between(Customer.META.idno, "70000", "70020")).build()).execute();
	}

}
