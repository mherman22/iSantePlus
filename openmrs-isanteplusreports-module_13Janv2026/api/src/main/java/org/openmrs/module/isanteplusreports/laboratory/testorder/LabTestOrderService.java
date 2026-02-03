package org.openmrs.module.isanteplusreports.laboratory.testorder;

import org.openmrs.Concept;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LabTestOrderService extends OpenmrsService {
    @Authorized
    @Transactional(readOnly = true)
    List<Concept> getAllTestConcepts() throws APIException;
}
