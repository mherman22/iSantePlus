package org.openmrs.module.isanteplusreports.page;

import org.openmrs.api.context.Context;

public class ReportsHomeDataExportsPageController {
	
	private static final String GET_LOCATIONS = "Get Locations";
	private static final String VIEW_LOCATIONS = "View Locations";

	public void controller() {
		try {
			Context.getAuthenticatedUser();
			Context.addProxyPrivilege(GET_LOCATIONS);
			Context.addProxyPrivilege(VIEW_LOCATIONS);
		} finally {
			Context.removeProxyPrivilege(GET_LOCATIONS);
			Context.removeProxyPrivilege(VIEW_LOCATIONS);
		}
	}
}
