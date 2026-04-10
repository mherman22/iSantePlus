package org.openmrs.module.isanteplusreports.library;

import java.util.Map;

import org.openmrs.module.isanteplusreports.IsantePlusReportsUtil;
import org.openmrs.module.reporting.data.encounter.definition.EncounterDataDefinition;
import org.openmrs.module.reporting.data.encounter.definition.SqlEncounterDataDefinition;
import org.openmrs.module.reporting.definition.library.BaseDefinitionLibrary;
import org.openmrs.module.reporting.definition.library.DocumentedDefinition;

public class EncounterDataLibrary extends BaseDefinitionLibrary<EncounterDataDefinition> {
	
	public static final String PREFIX = "iSantePlus.encounterDataCalculation.";
	
	@Override
	public Class<? super EncounterDataDefinition> getDefinitionType() {
		return EncounterDataDefinition.class;
	}
	
	@DocumentedDefinition("visitNextSevenDays")
	public EncounterDataDefinition getEncounterTypeName() {
		return sqlEncounterDataDefinition("visitNextSevenDays.sql", null);
	}
	
	@Override
	public String getKeyPrefix() {
		// TODO Auto-generated method stub
		return PREFIX;
	}
	
	private EncounterDataDefinition sqlEncounterDataDefinition(String resourceName, Replacements replacements) {
		String sql = IsantePlusReportsUtil.getStringFromResource("org/openmrs/module/isanteplusreports/sql/fullDataExports/"
		        + resourceName);
		if (replacements != null) {
			for (Map.Entry<String, String> entry : replacements.entrySet()) {
				sql = sql.replaceAll(":" + entry.getKey(), entry.getValue());
			}
		}
		
		SqlEncounterDataDefinition definition = new SqlEncounterDataDefinition();
		definition.setSql(sql);
		return definition;
	}
	
}
