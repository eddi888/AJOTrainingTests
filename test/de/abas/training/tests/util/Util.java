package de.abas.training.tests.util;

import de.abas.erp.common.type.enums.EnumFilingModeSelection;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.Deletable;
import de.abas.erp.db.SelectableObject;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.util.QueryUtil;

public class Util {

	public static <C extends SelectableObject & Deletable> void deleteObjects(final DbContext ctx,
			final Class<C> className, final String swd) {
		for (final C object : ctx.createQuery(SelectionBuilder.create(className).add(Conditions.eq("swd", swd)).build())
				.execute()) {
			object.delete();
		}
	}

	public static <C extends SelectableObject> C getObject(final DbContext ctx, final Class<C> className,
			final String swd) {
		return QueryUtil.getFirst(ctx,
				SelectionBuilder.create(className).add(Conditions.eq("swd", swd))
						.setFilingMode(EnumFilingModeSelection.Active).build());
	}

}
