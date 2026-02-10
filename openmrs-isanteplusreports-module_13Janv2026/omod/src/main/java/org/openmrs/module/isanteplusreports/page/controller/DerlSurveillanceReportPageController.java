package org.openmrs.module.isanteplusreports.page.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.isanteplusreports.derlSurveillance.DerlSurveillanceReportDataset;
import org.openmrs.module.isanteplusreports.derlSurveillance.model.DerlSurveillanceDatasetDefinition;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Transactional
public class DerlSurveillanceReportPageController {

    public String get(@SpringBean DerlSurveillanceReportDataset derlSurveillanceReportDataset,
                      @RequestParam(required = false, value = "startDate") Date startDate,
                      @RequestParam(required = false, value = "endDate") Date endDate,
                      PageModel model) throws IOException {

        // startDate = 1er janvier année courante à 00:00:00
        if (startDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.clear(); // réinitialise tout
            cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            startDate = cal.getTime();
        }

        // endDate = maintenant
        if (endDate == null) {
            endDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            cal.add(Calendar.DATE, 1); // ajoute 1 jour pour l'inclusivité
            endDate = cal.getTime();
        }

        // Sécuriser l'inclusivité
        startDate = DateUtil.getStartOfDay(startDate);
        endDate = DateUtil.getEndOfDay(endDate);

        List<DerlSurveillanceDatasetDefinition> list1 = derlSurveillanceReportDataset.cohortIndicators1(startDate, endDate);
        // 🔥 Traduction des labels ICI
        for (DerlSurveillanceDatasetDefinition indicator : list1) {
            String translatedLabel = Context.getMessageSourceService()
                    .getMessage(indicator.getLabel());
            indicator.setLabel(translatedLabel);
        }
        model.addAttribute("cohortIndicators1", list1);

        List<DerlSurveillanceDatasetDefinition> list2 = derlSurveillanceReportDataset.cohortIndicators2(startDate, endDate);
        // 🔥 Traduction des labels ICI
        for (DerlSurveillanceDatasetDefinition indicator : list2) {
            String translatedLabel = Context.getMessageSourceService()
                    .getMessage(indicator.getLabel());
            indicator.setLabel(translatedLabel);
        }
        model.addAttribute("cohortIndicators2", list2);

        List<DerlSurveillanceDatasetDefinition> list3 = derlSurveillanceReportDataset.cohortIndicators3(startDate, endDate);
        // 🔥 Traduction des labels ICI
        for (DerlSurveillanceDatasetDefinition indicator : list3) {
            String translatedLabel = Context.getMessageSourceService()
                    .getMessage(indicator.getLabel());
            indicator.setLabel(translatedLabel);
        }
        model.addAttribute("cohortIndicators3", list3);

        return null;
    }

}
