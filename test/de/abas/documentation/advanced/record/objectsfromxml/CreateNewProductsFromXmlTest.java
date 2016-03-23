package de.abas.documentation.advanced.record.objectsfromxml;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.After;
import org.junit.Test;

import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.referencetypes.ProductionListItem;
import de.abas.training.tests.util.AbstractTest;
import de.abas.training.tests.util.Util;

public class CreateNewProductsFromXmlTest extends AbstractTest {

	MakeSubBomProducts dependentInstance = new MakeSubBomProducts();
	CreateNewProductsFromXml instance = new CreateNewProductsFromXml();

	@After
	public void cleanup() {
		Util.deleteObjects(ctx, Product.class, "DINGHOLD");
		Util.deleteObjects(ctx, Product.class, "ALPHAIN");
		Util.deleteObjects(ctx, Product.class, "SILLAB");
		Util.deleteObjects(ctx, Product.class, "PLATE");
		Util.deleteObjects(ctx, Product.class, "CUP");
		Util.deleteObjects(ctx, Product.class, "DESK");
		Util.deleteObjects(ctx, Product.class, "CABLETUN");
		Util.deleteObjects(ctx, Product.class, "BOX");
	}

	public void runInvalidAbasXmlTest() {
		dependentInstance.run(new String[] {});
		instance.run(new String[] { "", "files/InvalidAbasXmlTest.xml" });

		assertThat("Product DINGHOLD does not exist", Util.getObject(ctx, Product.class, "DINGHOLD"), is(nullValue()));
		assertThat("Product ALPHAIN does not exist", Util.getObject(ctx, Product.class, "ALPHAIN"), is(nullValue()));
		assertThat("Product SILLAB does not exist", Util.getObject(ctx, Product.class, "SILLAB"), is(nullValue()));
	}

	@Test
	public void runTest() {
		dependentInstance.run(new String[] {});
		instance.run(new String[] {});

		final Product dinghold = Util.getObject(ctx, Product.class, "DINGHOLD");
		assertThat("Product DINGHOLD exists", dinghold, is(notNullValue()));
		assertThat("field descrOperLang is 'Dinghold'", dinghold.getDescrOperLang(), is("Dinghold"));
		assertThat("field salesPrice is '5.72'",
				dinghold.getSalesPrice(),
				is(new BigDecimal(5.72).setScale(2, RoundingMode.HALF_UP)));
		assertThat("field packDimLength is '11.25'",
				dinghold.getPackDimLength(),
				is(new BigDecimal(11.25).setScale(2)));
		assertThat("field packDimWidth is '15.26'",
				dinghold.getPackDimWidth(),
				is(new BigDecimal(15.26).setScale(2, RoundingMode.HALF_UP)));
		assertThat("field packDimHeight is '58.66'",
				dinghold.getPackDimHeight(),
				is(new BigDecimal(58.66).setScale(2, RoundingMode.HALF_UP)));
		final Product alphain = Util.getObject(ctx, Product.class, "ALPHAIN");
		assertThat("Product ALPHAIN exists", alphain, is(notNullValue()));
		assertThat("field descrOperLang is 'Alphain'", alphain.getDescrOperLang(), is("Alphain"));
		assertThat("field salesPrice is '6.57'",
				alphain.getSalesPrice(),
				is(new BigDecimal(6.57).setScale(2, RoundingMode.HALF_UP)));
		assertThat("field packDimLength is '30.52'",
				alphain.getPackDimLength(),
				is(new BigDecimal(30.52).setScale(2, RoundingMode.HALF_UP)));
		assertThat("field packDimWidth is '92.67'",
				alphain.getPackDimWidth(),
				is(new BigDecimal(92.67).setScale(2, RoundingMode.HALF_UP)));
		assertThat("field packDimHeight is '21.96'",
				alphain.getPackDimHeight(),
				is(new BigDecimal(21.96).setScale(2, RoundingMode.HALF_UP)));
		assertThat("has 2 rows", alphain.table().getRowCount(), is(2));
		assertThat("field prodListElem of row <1> is 'PLATE'",
				alphain.table().getRow(1).getProdListElem(),
				is((ProductionListItem) Util.getObject(ctx, Product.class, "PLATE")));
		assertThat("field elemQty of row <1> is '6'",
				alphain.table().getRow(1).getElemQty(),
				is(new BigDecimal(6).setScale(4, RoundingMode.HALF_UP)));
		assertThat("field prodListElem of row <2> is 'CUP'",
				alphain.table().getRow(2).getProdListElem(),
				is((ProductionListItem) Util.getObject(ctx, Product.class, "CUP")));
		assertThat("field elemQty of row <2> is '6'",
				alphain.table().getRow(2).getElemQty(),
				is(new BigDecimal(6).setScale(4, RoundingMode.HALF_UP)));
		final Product sillab = Util.getObject(ctx, Product.class, "SILLAB");
		assertThat("Product SILLAB exists", sillab, is(notNullValue()));
		assertThat("field descrOperLang is 'Sillab'", sillab.getDescrOperLang(), is("Sillab"));
		assertThat("field salesPrice is '5.68'",
				sillab.getSalesPrice(),
				is(new BigDecimal(5.68).setScale(2, RoundingMode.HALF_UP)));
		assertThat("field packDimLength is '42.93'",
				sillab.getPackDimLength(),
				is(new BigDecimal(42.93).setScale(2, RoundingMode.HALF_UP)));
		assertThat("field packDimWidth is '93.39'",
				sillab.getPackDimWidth(),
				is(new BigDecimal(93.39).setScale(2, RoundingMode.HALF_UP)));
		assertThat("field packDimHeight is '74.98'",
				sillab.getPackDimHeight(),
				is(new BigDecimal(74.98).setScale(2, RoundingMode.HALF_UP)));
		assertThat("has 3 rows", sillab.table().getRowCount(), is(3));
		assertThat("field prodListElem of row <1> is 'DESK'",
				sillab.table().getRow(1).getProdListElem(),
				is((ProductionListItem) Util.getObject(ctx, Product.class, "DESK")));
		assertThat("field elemQty of row <1> is '1'",
				sillab.table().getRow(1).getElemQty(),
				is(new BigDecimal(1).setScale(4, RoundingMode.HALF_UP)));
		assertThat("field prodListElem of row <2> is 'CABLETUN'",
				sillab.table().getRow(2).getProdListElem(),
				is((ProductionListItem) Util.getObject(ctx, Product.class, "CABLETUN")));
		assertThat("field elemQty of row <2> is '5'",
				sillab.table().getRow(2).getElemQty(),
				is(new BigDecimal(5).setScale(4, RoundingMode.HALF_UP)));
		assertThat("field prodListElem of row <3> is 'BOX'",
				sillab.table().getRow(3).getProdListElem(),
				is((ProductionListItem) Util.getObject(ctx, Product.class, "BOX")));
		assertThat("field elemQty of row <3> is '2'",
				sillab.table().getRow(3).getElemQty(),
				is(new BigDecimal(2).setScale(4, RoundingMode.HALF_UP)));
	}

}
