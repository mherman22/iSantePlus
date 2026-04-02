package org.openmrs.module.isanteplus.fragment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
                           @RequestParam("patientId") Patient patient,
//                           @InjectBeans PatientDomainWrapper wrapper,
                           @SpringBean("adtService") AdtService adtService,
                           UiSessionContext sessionContext,
                           @SpringBean("coreResourceFactory") ResourceFactory resourceFactory,
                           @SpringBean("htmlFormEntryService") HtmlFormEntryService htmlFormEntryService,
                           @SpringBean("formService") FormService formService,
//                           HttpServletRequest request,
                           @RequestParam(value = "visitId", required = false) Visit visit) {

        VisitDomainWrapper activeVisit = adtService.getActiveVisit(patient,
                adtService.getLocationThatSupportsVisits(sessionContext.getSessionLocation()));

        // Visit sécurisé (LE POINT CLÉ)
        Visit visitToUse = visit != null
                ? visit
                : (activeVisit != null ? activeVisit.getVisit() : null);

        model.put("isActiveVisit", visitToUse != null);
        model.put("showObygnForms", StringUtils.isNotBlank(patient.getGender()) && "F".equals(patient.getGender()));

        // Si aucun visit → STOP (évite NPE)
        if (visitToUse == null) {
            model.put("primaryCareForms", new ArrayList<>());
            model.put("labForms", new ArrayList<>());
            model.put("obygnForms", new ArrayList<>());
            model.put("hivCareForms", new ArrayList<>());
            model.put("psychoSocialForms", new ArrayList<>());
            model.put("otherForms", new ArrayList<>());
            model.put("emergencyForms", new ArrayList<>());
            model.put("inPatientForms", new ArrayList<>());
            model.put("patientId", patient.getPatientId());
            return;
        }

        Integer patientAge = patient.getAge();
        String patientSex = patient.getGender();

        // Helper pour éviter répétition
        java.util.function.Function<String, IsantePlusHtmlForm> form = file ->
                new IsantePlusHtmlForm(file, resourceFactory, formService, htmlFormEntryService, patient, visitToUse);

        // ================= FORMS =================
        IsantePlusHtmlForm ficheDeConsultationOBGYN = form.apply("OBGYN.xml");
        IsantePlusHtmlForm ficheDePremiereConsultationOBGYN = form.apply("POBGYN.xml");
        IsantePlusHtmlForm ficheDeTravailEtDaccouchement = form.apply("Tacc.xml");

        IsantePlusHtmlForm analyseDeLaboratoire = form.apply("Lab.xml");
        IsantePlusHtmlForm ordonnanceMedicale = form.apply("OrdM.xml");
        IsantePlusHtmlForm ordonnancepediatrique = form.apply("Ordpd.xml");

        IsantePlusHtmlForm saisiePremiereVisiteAdult = form.apply("PVisitAdult.xml");
        IsantePlusHtmlForm saisiePremiereVisitePediatrique = form.apply("PVisitPed.xml");
        IsantePlusHtmlForm visiteDeSuivi = form.apply("VisitSuivi.xml");
        IsantePlusHtmlForm visiteDeSuiviPediatrique = form.apply("VisitSuiviPed.xml");

        IsantePlusHtmlForm soinsDeSantePrimaireConsultation = form.apply("Cons.xml");
        IsantePlusHtmlForm soinsDeSantePrimaireConsultationPediatrique = form.apply("ConsPed.xml");
        IsantePlusHtmlForm soinsDeSantePrimairePremiereConsultation = form.apply("PCons.xml");
        IsantePlusHtmlForm soinsDeSantePrimairePremiereConsultationPediatrique = form.apply("PConsPed.xml");

        IsantePlusHtmlForm vaccination = form.apply("Vacc.xml");
        IsantePlusHtmlForm imagerie = form.apply("Imagerie.xml");
        IsantePlusHtmlForm adherence = form.apply("Adh.xml");
        IsantePlusHtmlForm vitals = form.apply("Vitals.xml");

        IsantePlusHtmlForm emergency = form.apply("Emergency.xml");
        IsantePlusHtmlForm emergencyPed = form.apply("EmergencyPed.xml");
        IsantePlusHtmlForm reevaluation = form.apply("Reevaluation.xml");
        IsantePlusHtmlForm nurseEvaluation = form.apply("NurseEvaluation.xml");
        IsantePlusHtmlForm exeat = form.apply("Exeat.xml");
        IsantePlusHtmlForm nurseNote = form.apply("NurseNote.xml");
        IsantePlusHtmlForm inPatient = form.apply("Inpatient.xml");
        IsantePlusHtmlForm inPatientPed = form.apply("InpatientPed.xml");

        IsantePlusHtmlForm fichePsychosocialeAdulte = form.apply("PsyA.xml");
        IsantePlusHtmlForm fichePsychosocialePediatrique = form.apply("PsyP.xml");
        IsantePlusHtmlForm visitComm = form.apply("VisitComm.xml");
        IsantePlusHtmlForm visitRetAdh = form.apply("VisitRetAdh.xml");

        // ================= LISTES =================
        List<IsantePlusHtmlForm> primaryCareForms = new ArrayList<>();
        List<IsantePlusHtmlForm> labForms = new ArrayList<>();
        List<IsantePlusHtmlForm> obygnForms = new ArrayList<>();
        List<IsantePlusHtmlForm> hivCareForms = new ArrayList<>();
        List<IsantePlusHtmlForm> psychoSocialForms = new ArrayList<>();
        List<IsantePlusHtmlForm> otherForms = new ArrayList<>();
        List<IsantePlusHtmlForm> emergencyForms = new ArrayList<>();
        List<IsantePlusHtmlForm> inPatientForms = new ArrayList<>();

        Integer adultStartingAge = Integer.parseInt(
                Context.getAdministrationService().getGlobalProperty(
                        ConfigurableGlobalProperties.ADULTSTARTINGAGE));

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
            emergencyForms.add(nurseEvaluation);
            inPatientForms.add(inPatient);
        }

        if (patientAge != null && patientAge <= adultStartingAge) {
            primaryCareForms.add(soinsDeSantePrimairePremiereConsultationPediatrique);
            primaryCareForms.add(soinsDeSantePrimaireConsultationPediatrique);
            hivCareForms.add(saisiePremiereVisitePediatrique);
            hivCareForms.add(visiteDeSuiviPediatrique);
            labForms.add(ordonnancepediatrique);
            psychoSocialForms.add(fichePsychosocialePediatrique);
            emergencyForms.add(emergencyPed);
            emergencyForms.add(reevaluation);
            emergencyForms.add(nurseEvaluation);
            inPatientForms.add(inPatientPed);
        }

        labForms.add(analyseDeLaboratoire);
        hivCareForms.add(adherence);

        if (StringUtils.isNotBlank(patientSex) && "F".equals(patientSex)) {
            obygnForms.add(ficheDePremiereConsultationOBGYN);
            obygnForms.add(ficheDeConsultationOBGYN);
            obygnForms.add(ficheDeTravailEtDaccouchement);
        }

        otherForms.add(vaccination);
        otherForms.add(imagerie);
        otherForms.add(exeat);
        otherForms.add(nurseNote);

        psychoSocialForms.add(visitRetAdh);
        psychoSocialForms.add(visitComm);

        // ================= MODEL =================
        model.put("primaryCareForms", primaryCareForms);
        model.put("labForms", labForms);
        model.put("obygnForms", obygnForms);
        model.put("hivCareForms", hivCareForms);
        model.put("psychoSocialForms", psychoSocialForms);
        model.put("otherForms", otherForms);
        model.put("emergencyForms", emergencyForms);
        model.put("inPatientForms", inPatientForms);

        model.put("patientId", patient.getPatientId());
        model.put("visitId", visitToUse.getVisitId());
    }

}