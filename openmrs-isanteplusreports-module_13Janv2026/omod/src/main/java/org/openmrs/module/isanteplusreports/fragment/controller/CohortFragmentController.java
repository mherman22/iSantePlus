package org.openmrs.module.isanteplusreports.fragment.controller;

import org.openmrs.Cohort;
import org.openmrs.module.reporting.data.patient.definition.PatientDataDefinition;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.service.DataSetDefinitionService;
import org.openmrs.module.reporting.definition.library.AllDefinitionLibraries;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class CohortFragmentController {
	
	public SimpleObject getCohort(@RequestParam("memberIds") Cohort cohort, @SpringBean AllDefinitionLibraries libraries,
	        @SpringBean DataSetDefinitionService dsdService) throws EvaluationException {
		
		PatientDataSetDefinition dsd = new PatientDataSetDefinition();
		dsd.addColumn("patientId", libraries.getDefinition(PatientDataDefinition.class,
		    "reporting.library.patientDataDefinition.builtIn.patientId"), "");
		dsd.addColumn("familyName", libraries.getDefinition(PatientDataDefinition.class,
		    "reporting.library.patientDataDefinition.builtIn.preferredName.familyName"), "");
		dsd.addColumn("givenName", libraries.getDefinition(PatientDataDefinition.class,
		    "reporting.library.patientDataDefinition.builtIn.preferredName.givenName"), "");
		
		EvaluationContext context = new EvaluationContext();
		context.setBaseCohort(cohort);
		DataSet result = dsdService.evaluate(dsd, context);
		return SimpleObject.create("members", simplify(result));
	}
	
	private List<Map<String, Object>> simplify(DataSet dataSet) {
		List<Map<String, Object>> simplified = new ArrayList<Map<String, Object>>();
		for (DataSetRow row : dataSet) {
			simplified.add(row.getColumnValuesByKey());
		}
		return simplified;
	}
	
}
