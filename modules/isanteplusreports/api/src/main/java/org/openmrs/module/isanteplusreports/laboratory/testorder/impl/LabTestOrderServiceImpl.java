package org.openmrs.module.isanteplusreports.laboratory.testorder.impl;

import org.openmrs.Concept;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.isanteplusreports.laboratory.testorder.LabTestOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.openmrs.module.isanteplusreports.util.IsantePlusReportsConstants.LAB_TEST_CONCEPT_ID_FILE;

@Component("isanteplusreports.LabTestOrderService")
public class LabTestOrderServiceImpl extends BaseOpenmrsService implements LabTestOrderService {

    @Autowired
    ConceptService conceptService;

    @Override
    public List<Concept> getAllTestConcepts() throws APIException {
        List<Concept> result = new ArrayList<>();
        List<Integer> conceptIds = getTestConceptIds();
        for (Integer id : conceptIds) {
            Concept concept = conceptService.getConcept(id);
            if (concept != null) {
                result.add(concept);
            }
        }
        return result;
    }

    private List<Integer> getTestConceptIds() {
        List<Integer> result = new ArrayList<>();
        String ids = IsantePlusReportsUtil.getStringFromResource(LAB_TEST_CONCEPT_ID_FILE);
        StringTokenizer tokenizer = new StringTokenizer(ids);
        while (tokenizer.hasMoreElements()) {
            Integer id = Integer.parseInt(tokenizer.nextElement().toString());
            result.add(id);
        }
        return result;
    }
}
