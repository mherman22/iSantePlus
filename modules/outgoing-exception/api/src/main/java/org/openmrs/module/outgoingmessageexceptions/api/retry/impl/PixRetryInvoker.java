package org.openmrs.module.outgoingmessageexceptions.api.retry.impl;

import java.io.IOException;
import java.util.Date;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.LocationService;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.outgoingmessageexceptions.OutgoingMessage;
import org.openmrs.module.outgoingmessageexceptions.api.OutgoingMessageExceptionsService;
import org.openmrs.module.outgoingmessageexceptions.api.exceptions.OutgoingMessageException;
import org.openmrs.module.outgoingmessageexceptions.api.retry.RetryInvoker;
import org.openmrs.module.registrationcore.RegistrationCoreConstants;
import org.openmrs.module.registrationcore.api.errorhandling.PixErrorHandlingService;
import org.openmrs.module.registrationcore.api.errorhandling.SendingPatientToMpiParameters;
import org.openmrs.module.registrationcore.api.impl.RegistrationCoreProperties;
import org.openmrs.module.registrationcore.api.mpi.common.MpiProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PixRetryInvoker implements RetryInvoker {

	private static final Logger LOGGER = LoggerFactory.getLogger(PixRetryInvoker.class);

	@Autowired
	@Qualifier("adminService")
	private AdministrationService administrationService;

	@Autowired
	private OutgoingMessageExceptionsService outgoingMessagesService;

	@Autowired
	private RegistrationCoreProperties registrationCoreProperties;

	@Autowired
	private LocationService locationService;

	@Autowired
	private PatientService patientService;

	@Override
	public void retry(OutgoingMessage outgoingMessage) {
		boolean isSuccess = false;

		MpiProvider mpiProvider = registrationCoreProperties.getMpiProvider();
		if (mpiProvider != null) {
			try {
				SendingPatientToMpiParameters parameters = getParameters(outgoingMessage);
				Patient patient =
						Context.getPatientService().getPatientByUuid(parameters.getPatientUuid());

				if (patient != null) {
					if (StringUtils.equals(outgoingMessage.getDestination(),
							PixErrorHandlingService.SENDING_PATIENT_AFTER_PATIENT_CREATION_DESTINATION)) {
						String ecid = mpiProvider.exportPatient(patient);
						updatePatient(patient, ecid);
					} else {
						mpiProvider.updatePatient(patient);
					}
				}

				outgoingMessage.setRetryResult("Retried successfully");
				isSuccess = true;
			} catch (Exception e) {
				LOGGER.error("Unsuccessful retry", e);
				outgoingMessage.setRetryResult(ExceptionUtils.getFullStackTrace(e));
			}
		}

		outgoingMessage.setRetried(true);
		outgoingMessage.setFailure(!isSuccess);
		outgoingMessage.setRetryTimestamp(new Date());
		outgoingMessagesService.saveItem(outgoingMessage);
	}

	private void updatePatient(Patient patient, String ecid) {
		patient.addIdentifier(createEcidIdentifier(ecid));
		patientService.savePatient(patient);
	}

	private PatientIdentifier createEcidIdentifier(String personId) {
		return new PatientIdentifier(personId, getEcidIdentifierType(),
				locationService.getDefaultLocation());
	}

	private SendingPatientToMpiParameters getParameters(OutgoingMessage outgoingMessage) {
		try {
			return new ObjectMapper().readValue(outgoingMessage.getMessageBody(),
					SendingPatientToMpiParameters.class);
		} catch (IOException e) {
			throw new RuntimeException("Cannot parse parameters for retrying exception", e);
		}
	}

	private PatientIdentifierType getEcidIdentifierType() {
		String ecidUuid = administrationService.getGlobalProperty(
				RegistrationCoreConstants.GP_MPI_PERSON_IDENTIFIER_TYPE_UUID);
		if (StringUtils.isBlank(ecidUuid)) {
			throw new OutgoingMessageException("ECID uuid is not set in Global Property");
		}
		return patientService.getPatientIdentifierTypeByUuid(ecidUuid);
	}
}
