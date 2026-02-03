package org.openmrs.module.isanteplusreports.healthqual;

import java.util.List;

import org.openmrs.module.isanteplusreports.library.dimension.CommonDimension;
import org.openmrs.module.isanteplusreports.library.indicator.HealthQualReportIndicatorLibrary;
import org.openmrs.module.isanteplusreports.reporting.utils.ReportUtils;
import org.openmrs.module.reporting.common.MessageUtil;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;


public class HealthQualReportDataset {
		
	public DataSetDefinition constructDataSet(String reportName, String sqlDen, String sqlNum, List<Parameter> parameters) {
	    CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
	    dsd.setName(reportName);
	    dsd.setDescription(MessageUtil.translate(reportName));
	    dsd.setParameters(parameters);
	    
	    dsd.addDimension("gender", ReportUtils.map(new CommonDimension().gender(), ""));
	    	    
	    CohortIndicator numCohortIndicator = HealthQualReportIndicatorLibrary.cohortIndicatorFromSqlResource(sqlNum,reportName + "_num", parameters);
	    CohortIndicator denCohortIndicator = HealthQualReportIndicatorLibrary.cohortIndicatorFromSqlResource(sqlDen,reportName + "_den", parameters);
	    
	    dsd.addColumn(
	    	"femaleNumerator", 
	    	"Female Numerator", 
	    	Mapped.mapStraightThrough(numCohortIndicator),
	    	"gender=F");

	    dsd.addColumn(
	    	"maleNumerator", 
	    	"Male Numerator", 
	    	Mapped.mapStraightThrough(numCohortIndicator),
	    	"gender=M");

	    dsd.addColumn(
	    	"totalNumerator", 
	    	"Total Numerator", 
	    	Mapped.mapStraightThrough(numCohortIndicator),
	    	"");

	    dsd.addColumn(
	    	"maleDenominator", 
	    	"Male Denominator", 
	    	Mapped.mapStraightThrough(denCohortIndicator),
	    	"gender=M");

	    dsd.addColumn(
	    	"femaleDenominator", 
	    	"Female Denominator", 
	    	Mapped.mapStraightThrough(denCohortIndicator),
	    	"gender=F");

	    dsd.addColumn(
	    	"totalDenominator", 
	    	"Total Denominator", 
	    	Mapped.mapStraightThrough(denCohortIndicator),
	    	"");
	    
	    return dsd;
	
	}
	
}