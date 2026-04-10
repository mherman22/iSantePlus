/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.outgoingmessageexceptions.fragment.controller;

import java.io.IOException;
import org.openmrs.module.outgoingmessageexceptions.OutgoingMessage;
import org.openmrs.module.outgoingmessageexceptions.api.OutgoingMessageExceptionsService;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.fragment.FragmentRequest;

public class ExceptionDetailsFragmentController {
	
	public void controller(FragmentModel model, FragmentRequest request,
	        @SpringBean OutgoingMessageExceptionsService outgoingMessageExceptionsService,
	        @FragmentParam(value = "messageId") Integer messageId) throws IOException {
		OutgoingMessage outgoingMessage = outgoingMessageExceptionsService.getMessageById(messageId);
		
		model.addAttribute("outgoingMessage", outgoingMessage);
		
		request.setProviderName("outgoing-message-exceptions"); // set the proper path to the fragment view (with '-' chars)
	}
}
