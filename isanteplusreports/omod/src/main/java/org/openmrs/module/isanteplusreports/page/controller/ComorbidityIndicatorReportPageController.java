package org.openmrs.module.isanteplusreports.page.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.openmrs.module.isanteplusreports.comorbidity.ComorbidityReportDataset;
import org.openmrs.module.isanteplusreports.comorbidity.model.ComorbidityReportResult;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Transactional
public class ComorbidityIndicatorReportPageController {

    public String get(@SpringBean ComorbidityReportDataset comorbidityReportDataset,
                      @RequestParam(required = false, value = "startDate") Date startDate,
                      @RequestParam(required = false, value = "endDate") Date endDate,
                      @RequestParam(value = "selectedIndicators", required = false) List<String> uuids,
                      PageModel model) throws IOException {

        if (startDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            startDate = cal.getTime();
        }
        if (endDate == null) {
            endDate = new Date();
        }

        startDate = DateUtil.getStartOfDay(startDate);
        endDate = DateUtil.getEndOfDay(endDate);

        List<ComorbidityReportResult> comorbidityReportResults = comorbidityReportDataset
                .cohortIndicators(uuids, startDate, endDate);

        // 4️⃣ Recharger la page correctement
        model.addAttribute("indicatorList", comorbidityReportDataset.getAllComorbidityReports());
        model.addAttribute("comorbidityReportResults", comorbidityReportResults);

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return null;
    }

    public String post(@SpringBean ComorbidityReportDataset comorbidityReportDataset,
                       @RequestParam(value = "startDate", required = false) Date startDate,
                       @RequestParam(value = "endDate", required = false) Date endDate,
                       @RequestParam(value = "selectedIndicators", required = false) List<String> uuids,
                       PageModel model) throws IOException {

        // 1️⃣ Dates par défaut
        if (startDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            startDate = cal.getTime();
        }
        if (endDate == null) {
            endDate = new Date();
        }

        // 2️⃣ Normalisation OpenMRS
        startDate = DateUtil.getStartOfDay(startDate);
        endDate = DateUtil.getEndOfDay(endDate);

        List<ComorbidityReportResult> comorbidityReportResults = comorbidityReportDataset
                .cohortIndicators(uuids, startDate, endDate);

        // 4️⃣ Recharger la page correctement
        model.addAttribute("indicatorList", comorbidityReportDataset.getAllComorbidityReports());
        model.addAttribute("comorbidityReportResults", comorbidityReportResults);

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return null;
    }

}

