package org.openmrs.module.outgoingmessageexceptions.api.retry.impl;

import java.io.IOException;
import java.util.Date;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.outgoingmessageexceptions.OutgoingMessage;
import org.openmrs.module.outgoingmessageexceptions.api.OutgoingMessageExceptionsService;
import org.openmrs.module.outgoingmessageexceptions.api.retry.RetryInvoker;
import org.openmrs.module.xdssender.api.errorhandling.ExportProvideAndRegisterParameters;
import org.openmrs.module.xdssender.api.service.XdsExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XdsBRetryInvoker implements RetryInvoker {

	private static final Logger LOGGER = LoggerFactory.getLogger(XdsBRetryInvoker.class);

	@Autowired
	private OutgoingMessageExceptionsService outgoingMessagesService;

	@Autowired
	private XdsExportService xdsExportService;

	@Override
	public void retry(OutgoingMessage outgoingMessage) {
		boolean isSuccess = false;

		try {
			ExportProvideAndRegisterParameters parameters = getParameters(outgoingMessage);
			Encounter encounter =
					Context.getEncounterService().getEncounterByUuid(parameters.getEncounterUuid());
			Patient patient =
					Context.getPatientService().getPatientByUuid(parameters.getPatientUuid());

			xdsExportService.exportProvideAndRegister(encounter, patient);
			outgoingMessage.setRetryResult("Retried successfully");
			isSuccess = true;
		} catch (Exception e) {
			LOGGER.error("Unsuccessful retry", e);
			outgoingMessage.setRetryResult(ExceptionUtils.getFullStackTrace(e));
		}

		outgoingMessage.setRetried(true);
		outgoingMessage.setFailure(!isSuccess);
		outgoingMessage.setRetryTimestamp(new Date());
		outgoingMessagesService.saveItem(outgoingMessage);
	}

	private ExportProvideAndRegisterParameters getParameters(OutgoingMessage outgoingMessage) {
		try {
			return new ObjectMapper().readValue(outgoingMessage.getMessageBody(),
					ExportProvideAndRegisterParameters.class);
		} catch (IOException e) {
			throw new RuntimeException("Cannot parse parameters for retrying exception", e);
		}
	}
}
