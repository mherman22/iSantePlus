package org.openmrs.module.isanteplusreports.derlSurveillance;


import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.isanteplusreports.derlSurveillance.model.DerlSurveillanceDatasetDefinition;
import org.openmrs.module.isanteplusreports.derlSurveillance.model.Indicator;
import org.openmrs.module.isanteplusreports.derlSurveillance.model.PatientSummary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.isanteplusreports.derlSurveillance.DerlSurveillanceReportsConstants.DERL_REPORTS_PATIENT_LIST_SQL;
import static org.openmrs.module.isanteplusreports.derlSurveillance.DerlSurveillanceReportsConstants.DERL_REPORTS_RESOURCE_PATH;


@Component
public class DerlSurveillanceReportDataset {

    private List<DerlSurveillanceDatasetDefinition> getAllDerlSurveilanceReports1() {

        List<DerlSurveillanceDatasetDefinition> list = new ArrayList<>();

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.ANIMAL_SUSPECTED_RABBIES_MESSAGE,
                DerlSurveillanceReportsConstants.ANIMAL_SUSPECTED_RABBIES_UUID,
                DerlSurveillanceReportsConstants.ANIMAL_SUSPECTED_RABBIES_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.CHOLERA_SUSPECT_MESSAGE,
                DerlSurveillanceReportsConstants.CHOLERA_SUSPECT_UUID,
                DerlSurveillanceReportsConstants.CHOLERA_SUSPECT_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.COVID_SUSPECTED_MESSAGE,
                DerlSurveillanceReportsConstants.COVID_SUSPECTED_UUID,
                DerlSurveillanceReportsConstants.COVID_SUSPECTED_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.COVID_CONFIRM_MESSAGE,
                DerlSurveillanceReportsConstants.COVID_CONFIRM_UUID,
                DerlSurveillanceReportsConstants.COVID_CONFIRM_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.COQUELUCHE_SUSPECTE_MESSAGE,
                DerlSurveillanceReportsConstants.COQUELUCHE_SUSPECTE_UUID,
                DerlSurveillanceReportsConstants.COQUELUCHE_SUSPECTE_SQl));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.DIPHTERIE_SUSPECT_MESSAGE,
                DerlSurveillanceReportsConstants.DIPHTERIE_SUSPECT_UUID,
                DerlSurveillanceReportsConstants.DIPHTERIE_SUSPECT_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.MENINGITE_SUSPECTE_MESSAGE,
                DerlSurveillanceReportsConstants.MENINGITE_SUSPECTE_UUID,
                DerlSurveillanceReportsConstants.MENINGITE_SUSPECTE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.PALUDISME_CONFIRME_MESSAGE,
                DerlSurveillanceReportsConstants.PALUDISME_CONFIRME_UUID,
                DerlSurveillanceReportsConstants.PALUDISME_CONFIRME_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.PARALYSIE_FLASQUE_AIGUE_MESSAGE,
                DerlSurveillanceReportsConstants.PARALYSIE_FLASQUE_AIGUE_UUID,
                DerlSurveillanceReportsConstants.PARALYSIE_FLASQUE_AIGUE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.PESTE_SUSPECTE_MESSAGE,
                DerlSurveillanceReportsConstants.PESTE_SUSPECTE_UUID,
                DerlSurveillanceReportsConstants.PESTE_SUSPECTE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.ROUGEOLE_RUBEOLE_SUSPECTE_MESSAGE,
                DerlSurveillanceReportsConstants.ROUGEOLE_RUBEOLE_SUSPECTE_UUID,
                DerlSurveillanceReportsConstants.ROUGEOLE_RUBEOLE_SUSPECTE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.SYNDROME_FIEVRE_HEMORRAGIQUE_AIGUE_MESSAGE,
                DerlSurveillanceReportsConstants.SYNDROME_FIEVRE_HEMORRAGIQUE_AIGUE_UUID,
                DerlSurveillanceReportsConstants.SYNDROME_FIEVRE_HEMORRAGIQUE_AIGUE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.SYNDROME_RUBEOLE_CONGENITALE_MESSAGE,
                DerlSurveillanceReportsConstants.SYNDROME_RUBEOLE_CONGENITALE_UUID,
                DerlSurveillanceReportsConstants.SYNDROME_RUBEOLE_CONGENITALE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.ESAVI_MAJEUR_MESSAGE,
                DerlSurveillanceReportsConstants.ESAVI_MAJEUR_UUID,
                DerlSurveillanceReportsConstants.ESAVI_MAJEUR_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.MORTALITE_NEONATALE_MESSAGE,
                DerlSurveillanceReportsConstants.MORTALITE_NEONATALE_UUID,
                DerlSurveillanceReportsConstants.MORTALITE_NEONATALE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.TETANOS_NEONATAL_MESSAGE,
                DerlSurveillanceReportsConstants.TETANOS_NEONATAL_UUID,
                DerlSurveillanceReportsConstants.TETANOS_NEONATAL_SQL));

        return list;
    }

    public List<DerlSurveillanceDatasetDefinition> cohortIndicators1(Date startDate, Date endDate) {

        IsantePlusReportsService isantePlusReportsService = Context.getService(IsantePlusReportsService.class);
        List<DerlSurveillanceDatasetDefinition> list = new ArrayList<>();

        for (DerlSurveillanceDatasetDefinition datasetDefinition : getAllDerlSurveilanceReports1()) {
            List<Indicator> indicatorList = isantePlusReportsService.getDerlSurveillanceReport(DERL_REPORTS_RESOURCE_PATH + datasetDefinition.getSqlFile(), startDate, endDate);
            datasetDefinition.setIndicatorList(indicatorList);
            list.add(datasetDefinition);
        }

        return list;
    }


    private List<DerlSurveillanceDatasetDefinition> getAllDerlSurveilanceReports2() {

        List<DerlSurveillanceDatasetDefinition> list = new ArrayList<>();

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.AUTRE_FIEVRE_ORIGINE_INDETERMINE_MESSAGE,
                DerlSurveillanceReportsConstants.AUTRE_FIEVRE_ORIGINE_INDETERMINE_UUID,
                DerlSurveillanceReportsConstants.AUTRE_FIEVRE_ORIGINE_INDETERMINE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.CHARBON_CUTANE_SUSPECT_MESSAGE,
                DerlSurveillanceReportsConstants.CHARBON_CUTANE_SUSPECT_UUID,
                DerlSurveillanceReportsConstants.CHARBON_CUTANE_SUSPECT_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.DECES_MATERNEL_MESAAGE,
                DerlSurveillanceReportsConstants.DECES_MATERNEL_UUID,
                DerlSurveillanceReportsConstants.DECES_MATERNEL_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.DIARRHEE_AIGUE_MESSAGE,
                DerlSurveillanceReportsConstants.DIARRHEE_AIGUE_UUID,
                DerlSurveillanceReportsConstants.DIARRHEE_AIGUE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.DIARRHEE_AIGUE_SANGLANTE_MESSAGE,
                DerlSurveillanceReportsConstants.DIARRHEE_AIGUE_SANGLANTE_UUID,
                DerlSurveillanceReportsConstants.DIARRHEE_AIGUE_SANGLANTE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.ESAVI_MINEURE_MESSAGE,
                DerlSurveillanceReportsConstants.ESAVI_MINEURE_UUID,
                DerlSurveillanceReportsConstants.ESAVI_MINEURE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.FIEVRE_TYPHOIDE_SUSPECT_MESSAGE,
                DerlSurveillanceReportsConstants.FIEVRE_TYPHOIDE_SUSPECT_UUID,
                DerlSurveillanceReportsConstants.FIEVRE_TYPHOIDE_SUSPECT_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.INFECTION_RESPIRATOIRE_AIGUE_MESSAGE,
                DerlSurveillanceReportsConstants.INFECTION_RESPIRATOIRE_AIGUE_UUID,
                DerlSurveillanceReportsConstants.INFECTION_RESPIRATOIRE_AIGUE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.TETANOS_MESSAGE,
                DerlSurveillanceReportsConstants.TETANOS_UUID,
                DerlSurveillanceReportsConstants.TETANOS_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.DENGUE_SUSTECTE_MESSAGE,
                DerlSurveillanceReportsConstants.DENGUE_SUSTECTE_UUID,
                DerlSurveillanceReportsConstants.DENGUE_SUSTECTE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.FILARIOSE_PROBABLE_MESSAGE,
                DerlSurveillanceReportsConstants.FILARIOSE_PROBABLE_UUID,
                DerlSurveillanceReportsConstants.FILARIOSE_PROBABLE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.RAGE_HUMAINE_MESSAGE,
                DerlSurveillanceReportsConstants.RAGE_HUMAINE_UUID,
                DerlSurveillanceReportsConstants.RAGE_HUMAINE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.SYNDROME_ICTERIQUE_FEBRILE_MESSAGE,
                DerlSurveillanceReportsConstants.SYNDROME_ICTERIQUE_FEBRILE_UUID,
                DerlSurveillanceReportsConstants.SYNDROME_ICTERIQUE_FEBRILE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.VIOLENCES_SEXUELLES_MESSAGE,
                DerlSurveillanceReportsConstants.VIOLENCES_SEXUELLES_UUID,
                DerlSurveillanceReportsConstants.VIOLENCES_SEXUELLES_SQL));

        return list;
    }

    public List<DerlSurveillanceDatasetDefinition> cohortIndicators2(Date startDate, Date endDate) {

        IsantePlusReportsService isantePlusReportsService = Context.getService(IsantePlusReportsService.class);
        List<DerlSurveillanceDatasetDefinition> list = new ArrayList<>();

        for (DerlSurveillanceDatasetDefinition datasetDefinition : getAllDerlSurveilanceReports2()) {
            List<Indicator> indicatorList = isantePlusReportsService.getDerlSurveillanceReport(DERL_REPORTS_RESOURCE_PATH + datasetDefinition.getSqlFile(), startDate, endDate);
            datasetDefinition.setIndicatorList(indicatorList);
            list.add(datasetDefinition);
        }

        return list;
    }


    private List<DerlSurveillanceDatasetDefinition> getAllDerlSurveilanceReports3() {

        List<DerlSurveillanceDatasetDefinition> list = new ArrayList<>();

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.PUBLIC_ACCIDENT_MESSAGE,
                DerlSurveillanceReportsConstants.PUBLIC_ACCIDENT_UUID,
                DerlSurveillanceReportsConstants.PUBLIC_ACCIDENT_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.DOMESTIC_ACCIDENT_MESSAGE,
                DerlSurveillanceReportsConstants.DOMESTIC_ACCIDENT_UUID,
                DerlSurveillanceReportsConstants.DOMESTIC_ACCIDENT_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.BREAST_CANCER_MESSAGE,
                DerlSurveillanceReportsConstants.BREAST_CANCER_UUID,
                DerlSurveillanceReportsConstants.BREAST_CANCER_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.PROSTATE_CANCER_MESSAGE,
                DerlSurveillanceReportsConstants.PROSTATE_CANCER_UUID,
                DerlSurveillanceReportsConstants.PROSTATE_CANCER_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.CANCER_COL_MESSAGE,
                DerlSurveillanceReportsConstants.CANCER_COL_UUID,
                DerlSurveillanceReportsConstants.CANCER_COL_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.DIABETE_MESSAGE,
                DerlSurveillanceReportsConstants.DIABETE_UUID,
                DerlSurveillanceReportsConstants.DIABETE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.EPILEPSIE_MESSAGE,
                DerlSurveillanceReportsConstants.EPILEPSIE_UUID,
                DerlSurveillanceReportsConstants.EPILEPSIE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.HYPERTENSION_ARTERIELLE_MESSAGE,
                DerlSurveillanceReportsConstants.HYPERTENSION_ARTERIELLE_UUID,
                DerlSurveillanceReportsConstants.HYPERTENSION_ARTERIELLE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.LEPRE_MESSAGE,
                DerlSurveillanceReportsConstants.LEPRE_UUID,
                DerlSurveillanceReportsConstants.LEPRE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.MALNUTRITION_MESSAGE,
                DerlSurveillanceReportsConstants.MALNUTRITION_UUID,
                DerlSurveillanceReportsConstants.MALNUTRITION_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.SYPHILLIS_CONGENITALE_MESSAGE,
                DerlSurveillanceReportsConstants.SYPHILLIS_CONGENITALE_UUID,
                DerlSurveillanceReportsConstants.SYPHILLIS_CONGENITALE_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.IST_MESSAGE,
                DerlSurveillanceReportsConstants.IST_UUID,
                DerlSurveillanceReportsConstants.IST_SQL));

        list.add(new DerlSurveillanceDatasetDefinition(
                DerlSurveillanceReportsConstants.VIOLENCES_PHYSIQUES_MESSAGE,
                DerlSurveillanceReportsConstants.VIOLENCES_PHYSIQUES_UUID,
                DerlSurveillanceReportsConstants.VIOLENCES_PHYSIQUES_SQL));

        return list;
    }

    public List<DerlSurveillanceDatasetDefinition> cohortIndicators3(Date startDate, Date endDate) {

        IsantePlusReportsService isantePlusReportsService = Context.getService(IsantePlusReportsService.class);
        List<DerlSurveillanceDatasetDefinition> list = new ArrayList<>();

        for (DerlSurveillanceDatasetDefinition datasetDefinition : getAllDerlSurveilanceReports3()) {
            List<Indicator> indicatorList = isantePlusReportsService.getDerlSurveillanceReport(DERL_REPORTS_RESOURCE_PATH + datasetDefinition.getSqlFile(), startDate, endDate);
            datasetDefinition.setIndicatorList(indicatorList);
            list.add(datasetDefinition);
        }

        return list;
    }

    public List<PatientSummary> getAllPatientSummaries(String identifiers) {
        IsantePlusReportsService isantePlusReportsService = Context.getService(IsantePlusReportsService.class);
        return isantePlusReportsService.getPatientsByIdentifiersDerlSurveillance(DERL_REPORTS_RESOURCE_PATH + DERL_REPORTS_PATIENT_LIST_SQL, identifiers);
    }
}
