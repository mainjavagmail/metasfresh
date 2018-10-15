package de.metas.shipping;

import org.adempiere.service.ClientId;
import org.compiere.model.I_M_Shipper;

import de.metas.bpartner.BPartnerId;
import de.metas.util.ISingletonService;
import de.metas.util.i18n.ITranslatableString;

/**
 * 
 * @author tsa
 * 
 */
public interface IShipperDAO extends ISingletonService
{

	/**
	 * @return shipper; if no shippers found it will return an error message
	 */
	ShipperId getShipperIdByShipperPartnerId(BPartnerId shipperPartnerId);

	ShipperId getDefault(ClientId clientId);

	ITranslatableString getShipperName(ShipperId shipperId);

	I_M_Shipper getById(ShipperId id);

}
