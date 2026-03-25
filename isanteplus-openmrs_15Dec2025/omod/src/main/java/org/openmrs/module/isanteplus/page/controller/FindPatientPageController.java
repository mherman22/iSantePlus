package org.openmrs.module.isanteplus.page.controller;

import org.openmrs.Location;
import org.openmrs.module.appui.UiSessionContext;

public class FindPatientPageController {

    public String get(UiSessionContext sessionContext) throws Exception {
        Location sessionLocation = sessionContext.getSessionLocation();
        if (sessionLocation == null)
            return "redirect:login.htm";
        return null;
    }
}