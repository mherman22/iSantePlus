//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.openmrs.module.isanteplus.page.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.isanteplus.PatientSearchInfos;
import org.openmrs.module.isanteplus.api.IsantePlusService;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

public class FindPatientPageController {

    public String get(PageModel model, @RequestParam("app") AppDescriptor app, UiSessionContext sessionContext) {
        Location sessionLocation = sessionContext.getSessionLocation();

        if (sessionLocation == null) {
            return "redirect:login.htm";
        } else {
            String patientPageUrl = app.getConfig().get("patientPageUrl").getTextValue();
            model.addAttribute("patientPageUrl", patientPageUrl);
            return null;
        }
    }

    public String post(@RequestParam("criteria") String criteria, PageModel model, PageRequest request) {
        IsantePlusService isantePlusService = Context.getService(IsantePlusService.class);
        List<PatientSearchInfos> patientSearchInfosList = isantePlusService.getAllPatientSearchInfos(criteria);
        HttpServletRequest httpRequest = request.getRequest();
        HttpServletResponse httpResponse = request.getResponse();
        String requestedWith = httpRequest.getHeader("X-Requested-With");
        if (requestedWith != null && "XMLHttpRequest".equals(requestedWith)) {
            try {
                httpResponse.setContentType("application/json");
                httpResponse.setCharacterEncoding("UTF-8");
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(httpResponse.getWriter(), patientSearchInfosList);
                httpResponse.getWriter().flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        model.addAttribute("patientSearchInfosList", patientSearchInfosList);
        return null;
    }

}
