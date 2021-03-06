package de.abas.documentation.advanced.record.objectstoxml;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.abas.training.tests.util.AbstractTest;

public class XmlExportCustomersTest extends AbstractTest {

	private final XmlExportCustomers instance = new XmlExportCustomers();

	@After
	public void cleanup() {
		new File("Test.xml").delete();
	}

	@Test
	public void runTest() {
		instance.run(new String[] { "", "Test.xml" });

		try {
			final Document document = new SAXBuilder().build("Test.xml");
			assertThat("valid xml file exists", document, is(notNullValue()));
			assertThat("root element is <ABASData>", document.getRootElement().getName(), is("ABASData"));
			assertThat("next element is <RecordSet>",
					document.getRootElement().getChild("RecordSet").getName(),
					is("RecordSet"));
			assertThat("<RecordSet> has Attribute action with value export",
					document.getRootElement().getChild("RecordSet").getAttributeValue("action"),
					is("export"));
			for (final Element record : document.getRootElement().getChild("RecordSet").getChildren()) {
				assertThat("element name is 'Record'", record.getName(), is("Record"));
				assertThat("record has head element as child", record.getChild("Head").getName(), is("Head"));
				final List<Element> headFields = record.getChild("Head").getChildren();
				assertThat("head element has 6 field elements", headFields.size(), is(6));
				for (final Element field : headFields) {
					assertThat("field has attribute 'name'", field.getAttribute("name").getName(), is(not("")));
					assertThat("field has attribute 'name'", field.getAttribute("abasType").getName(), is(not("")));
					assertThat("field tag has value", field.getValue(), is(not("")));
				}
			}

		} catch (final JDOMException e) {
			fail("No valid xml");
		} catch (final IOException e) {
			fail("File not found or not writable");
		}
	}

	@Before
	@Override
	public void setup() {
		super.setup();
		cleanup();
	}

}
