package de.metas.purchasing.api.impl;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.product.ProductId;
import de.metas.purchasing.api.IBPartnerProductBL;
import de.metas.purchasing.api.IBPartnerProductDAO;
import de.metas.purchasing.api.ProductExclude;
import de.metas.util.Services;
import de.metas.util.i18n.IMsgBL;

import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class BPartnerProductBL implements IBPartnerProductBL
{
	private static final String MSG_ProductSalesExclusionError = "ProductSalesExclusionError";

	@Override
	public void assertNotExcludedFromSaleToCustomer(@NonNull final ProductId productId, @NonNull final BPartnerId partnerId)
	{
		final IBPartnerProductDAO bpProductDAO = Services.get(IBPartnerProductDAO.class);

		bpProductDAO.getExcludedFromSaleToCustomer(productId, partnerId)
				.ifPresent(this::throwException);
	}

	private void throwException(final ProductExclude productExclude)
	{
		final IBPartnerDAO partnertDAO = Services.get(IBPartnerDAO.class);
		final String partnerName = partnertDAO.getBPartnerNameById(productExclude.getBpartnerId());
		final String msg = Services.get(IMsgBL.class).getMsg(
				Env.getCtx(),
				MSG_ProductSalesExclusionError,
				new Object[] { partnerName, productExclude.getReason() });

		throw new AdempiereException(msg);
	}
}
