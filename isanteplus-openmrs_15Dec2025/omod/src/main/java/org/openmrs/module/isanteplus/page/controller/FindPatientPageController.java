package org.openmrs.module.isanteplus.page.controller;

import org.openmrs.Location;
import org.openmrs.Role;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.ui.framework.page.PageModel;

public class FindPatientPageController {

    public String get(UiSessionContext sessionContext, PageModel model) throws Exception {
        Location sessionLocation = sessionContext.getSessionLocation();
        if (sessionLocation == null)
            return "redirect:login.htm";

        model.addAttribute("sessionContextRole", sessionContext.getCurrentUser()
                .getAllRoles()
                .stream()
                .map(Role::getRole) // 👈 IMPORTANT
                .findFirst()
                .orElse(null));
        return null;
    }
}