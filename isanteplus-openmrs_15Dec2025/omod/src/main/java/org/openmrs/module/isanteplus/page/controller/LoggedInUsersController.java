package org.openmrs.module.isanteplus.page.controller;

import org.openmrs.User;
import org.openmrs.module.appui.UiSessionContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/openmrs/ws/rest/user/currentUser")
public class LoggedInUsersController extends UiSessionContext {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User getCurrentUser() {
        return new UiSessionContext().getCurrentUser();
    }
}