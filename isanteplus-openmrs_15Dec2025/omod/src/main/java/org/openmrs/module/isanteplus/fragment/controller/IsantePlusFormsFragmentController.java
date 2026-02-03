package org.openmrs.module.isanteplus.fragment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.FormService;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.emrapi.adt.AdtService;
import org.openmrs.module.emrapi.patient.PatientDomainWrapper;
import org.openmrs.module.emrapi.visit.VisitDomainWrapper;
import org.openmrs.module.htmlformentry.HtmlFormEntryService;
import org.openmrs.module.isanteplus.ConfigurableGlobalProperties;
import org.openmrs.module.isanteplus.IsantePlusHtmlForm;
import org.openmrs.ui.framework.annotation.InjectBeans;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.resource.ResourceFactory;
import org.springframework.web.bind.annotation.RequestParam;

public class IsantePlusFormsFragmentController {

    public void controller(FragmentConfiguration config, FragmentModel model,
                           @RequestParam("patientId") Patient patient, @InjectBeans PatientDomainWrapper wrapper,
                           @SpringBean("adtService") AdtService adtService, UiSessionContext sessionContext,
                           @SpringBean("coreResourceFactory") ResourceFactory resourceFactory,
                           @SpringBean("htmlFormEntryService") HtmlFormEntryService htmlFormEntryService,
                           @SpringBean("formService") FormService formService, HttpServletRequest request,
                           @RequestParam(value = "visitId", required = false) Visit visit) {

        VisitDomainWrapper activeVisit = (VisitDomainWrapper) config.getAttribute("activeVisit");
        Location visitLocation = adtService.getLocationThatSupportsVisits(sessionContext.getSessionLocation());
        Integer patientAge = patient.getAge();
        String patientSex = patient.getGender();
        Boolean isActiveVisit = false;

        activeVisit = adtService.getActiveVisit(patient, visitLocation);
        isActiveVisit = activeVisit != null || request.getRequestURL().toString().endsWith("patientDashboard.page")
                ? true : false;
        model.put("isActiveVisit", isActiveVisit);

        model.put("showObygnForms", StringUtils.isNotBlank(patientSex) && patientSex.equals("F"));
        if (isActiveVisit) {
            IsantePlusHtmlForm adherence = new IsantePlusHtmlForm("Adh.xml", resourceFactory, formService,
                    htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm analyseDeLaboratoire = new IsantePlusHtmlForm("Lab.xml",
                    resourceFactory, formService, htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm ficheDeConsultationOBGYN = new IsantePlusHtmlForm("OBGYN.xml",
                    resourceFactory, formService, htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm ficheDePremiereConsultationOBGYN = new IsantePlusHtmlForm(
                    "FOBGYN.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm ficheDeTravailEtDaccouchement = new IsantePlusHtmlForm(
                    "Tacc.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm fichePsychosocialeAdulte = new IsantePlusHtmlForm("PsyA.xml",
                    resourceFactory, formService, htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm fichePsychosocialePediatrique = new IsantePlusHtmlForm(
                    "PsyP.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);

            IsantePlusHtmlForm ficheEvalsocialePediatrique = new IsantePlusHtmlForm(
                    "EvSocialPed.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm ordonnanceMedicale = new IsantePlusHtmlForm("OrdM.xml", resourceFactory,
                    formService, htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm ordonnancepediatrique = new IsantePlusHtmlForm("Ordpd.xml",
                    resourceFactory, formService, htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm rapportDarretDuProgrammeSoinsEtTraitementVIHOrSIDA = new IsantePlusHtmlForm(
                    "ArretVIH.xml", resourceFactory, formService,
                    htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm saisiePremiereVisiteAdult = new IsantePlusHtmlForm("PVisitAdult.xml",
                    resourceFactory, formService, htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm saisiePremiereVisitePediatrique = new IsantePlusHtmlForm(
                    "PVisitPed.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm soinsDeSantePrimaireConsultationPediatrique = new IsantePlusHtmlForm(
                    "ConsPed.xml", resourceFactory, formService,
                    htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm soinsDeSantePrimairePremiereConsultation = new IsantePlusHtmlForm(
                    "PCons.xml", resourceFactory, formService, htmlFormEntryService,
                    patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm soinsDeSantePrimairePremiereConsultationPediatrique = new IsantePlusHtmlForm(
                    "PConsPed.xml", resourceFactory, formService,
                    htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm soinsDeSantePrimaireConsultation = new IsantePlusHtmlForm(
                    "Cons.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm vaccination = new IsantePlusHtmlForm("Vacc.xml", resourceFactory, formService,
                    htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm visiteDeSuivi = new IsantePlusHtmlForm("VisitSuivi.xml", resourceFactory, formService,
                    htmlFormEntryService, patient, visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm visiteDeSuiviPediatrique = new IsantePlusHtmlForm("VisitSuiviPed.xml",
                    resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm imagerie = new IsantePlusHtmlForm("Imagerie.xml",
                    resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm homeVisit = new IsantePlusHtmlForm("HomeVisit.xml",
                    resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);

            IsantePlusHtmlForm emergency =  new IsantePlusHtmlForm("Emergency.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm emergencyPed =  new IsantePlusHtmlForm("EmergencyPed.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm reevaluation =  new IsantePlusHtmlForm("Reevaluation.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm nurseEvaluation =  new IsantePlusHtmlForm("NurseEvaluation.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm exeat =  new IsantePlusHtmlForm("Exeat.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm nurseNote =  new IsantePlusHtmlForm("NurseNote.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);

            IsantePlusHtmlForm inPatient =  new IsantePlusHtmlForm("Inpatient.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm inPatientPed =  new IsantePlusHtmlForm("InpatientPed.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);


            IsantePlusHtmlForm visitComm =  new IsantePlusHtmlForm("VisitComm.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);
            IsantePlusHtmlForm visitRetAdh =  new IsantePlusHtmlForm("VisitRetAdh.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);

            IsantePlusHtmlForm vitals =  new IsantePlusHtmlForm("Vitals.xml", resourceFactory, formService, htmlFormEntryService, patient,
                    visit != null ? visit : activeVisit != null ? activeVisit.getVisit() : null);


            List<IsantePlusHtmlForm> primaryCareForms = new ArrayList<>();
            List<IsantePlusHtmlForm> labForms = new ArrayList<>();
            List<IsantePlusHtmlForm> obygnForms = new ArrayList<>();
            List<IsantePlusHtmlForm> hivCareForms = new ArrayList<>();
            List<IsantePlusHtmlForm> psychoSocialForms = new ArrayList<>();
            List<IsantePlusHtmlForm> otherForms = new ArrayList<>();
            List<IsantePlusHtmlForm> emergencyForms = new ArrayList<>();
            List<IsantePlusHtmlForm> inPatientForms = new ArrayList<>();

            Integer adultStartingAge = Integer.parseInt(Context.getAdministrationService().getGlobalProperty(ConfigurableGlobalProperties.ADULTSTARTINGAGE));

            emergencyForms.add(vitals);

            if (patientAge != null && patientAge > adultStartingAge) {
                primaryCareForms.add(soinsDeSantePrimairePremiereConsultation);
                primaryCareForms.add(soinsDeSantePrimaireConsultation);
                hivCareForms.add(saisiePremiereVisiteAdult);
                hivCareForms.add(visiteDeSuivi);
                labForms.add(ordonnanceMedicale);
                psychoSocialForms.add(fichePsychosocialeAdulte);
                emergencyForms.add(emergency);
                emergencyForms.add(reevaluation);
                inPatientForms.add(inPatient);
                emergencyForms.add(nurseEvaluation);
            }

            if (patientAge != null && patientAge <= adultStartingAge) {
                primaryCareForms.add(soinsDeSantePrimairePremiereConsultationPediatrique);
                primaryCareForms.add(soinsDeSantePrimaireConsultationPediatrique);
                hivCareForms.add(saisiePremiereVisitePediatrique);
                hivCareForms.add(visiteDeSuiviPediatrique);
                labForms.add(ordonnancepediatrique);
                psychoSocialForms.add(fichePsychosocialePediatrique);
//                psychoSocialForms.add(ficheEvalsocialePediatrique);
                emergencyForms.add(emergencyPed);
                emergencyForms.add(reevaluation);
                inPatientForms.add(inPatientPed);
                emergencyForms.add(nurseEvaluation);
            }

            labForms.add(analyseDeLaboratoire);
            hivCareForms.add(adherence);

            if (StringUtils.isNotBlank(patientSex) && patientAge != null && "F".equals(patientSex)) {
                obygnForms.add(ficheDePremiereConsultationOBGYN);
                obygnForms.add(ficheDeConsultationOBGYN);
                obygnForms.add(ficheDeTravailEtDaccouchement);
            }

            otherForms.add(vaccination);
            otherForms.add(rapportDarretDuProgrammeSoinsEtTraitementVIHOrSIDA);
            otherForms.add(imagerie);
            otherForms.add(exeat);
            otherForms.add(nurseNote);
            psychoSocialForms.add(visitRetAdh);
            psychoSocialForms.add(visitComm);
//            psychoSocialForms.add(homeVisit);


            if(patientAge == null) {
                primaryCareForms.add(soinsDeSantePrimairePremiereConsultation);
                primaryCareForms.add(soinsDeSantePrimaireConsultation);
                primaryCareForms.add(soinsDeSantePrimairePremiereConsultationPediatrique);
                primaryCareForms.add(soinsDeSantePrimaireConsultationPediatrique);
                labForms.add(ordonnanceMedicale);
                labForms.add(ordonnancepediatrique);
                hivCareForms.add(saisiePremiereVisiteAdult);
                hivCareForms.add(visiteDeSuivi);
                hivCareForms.add(saisiePremiereVisitePediatrique);
                hivCareForms.add(visiteDeSuiviPediatrique);
                psychoSocialForms.add(fichePsychosocialeAdulte);
                psychoSocialForms.add(fichePsychosocialePediatrique);
                if (StringUtils.isNotBlank(patientSex) && "F".equals(patientSex)) {
                    obygnForms.add(ficheDePremiereConsultationOBGYN);
                    obygnForms.add(ficheDeConsultationOBGYN);
                    obygnForms.add(ficheDeTravailEtDaccouchement);
                }
                emergencyForms.add(emergency);
                emergencyForms.add(emergencyPed);
                emergencyForms.add(reevaluation);
                inPatientForms.add(inPatient);
                inPatientForms.add(inPatientPed);
                emergencyForms.add(nurseEvaluation);
            }

            model.put("primaryCareForms", primaryCareForms);
            model.put("labForms", labForms);
            model.put("obygnForms", obygnForms);
            model.put("hivCareForms", hivCareForms);
            model.put("psychoSocialForms", psychoSocialForms);
            model.put("otherForms", otherForms);
            model.put("emergencyForms", emergencyForms);
            model.put("inPatientForms", inPatientForms);

            model.put("patientId", patient.getPatientId());

            assert activeVisit != null;
            model.put("visitId", activeVisit.getVisit().getId());
        }
    }
}
