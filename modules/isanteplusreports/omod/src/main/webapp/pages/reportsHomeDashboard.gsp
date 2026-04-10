<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeCss("reportingui", "reportsapp/home.css")

    def appFrameworkService = context.getService(context.loadClass("org.openmrs.module.appframework.service.AppFrameworkService"))
    def overviews = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.overview")
    def monitoringReports = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.monitoring")
    def dataQualityReports = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.dataquality")
    def dataExports = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.dataexport")
    def other = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.other")
    def antenatal = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.antenatal")
    def patientsStatus = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.patientsstatus")
    def alertPrecoce = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.alertprecoce")
    def dashboard = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.dashboard")
    def ptme = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.ptme")
    def psychosocial = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.psychosocial")
    def comorbidity = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.comorbidity")
    def contextModel = [:]

    def htmlSafeId = { extension ->
        "${extension.id.replace(".", "-")}-${extension.id.replace(".", "-")}-extension"
    }
%>

<script type="text/javascript">
    /*var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("reportingui.reportsapp.home.title") }", link: "${ ui.pageLink("reportingui", "reportsapp/home") }" }
    ];*/
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.escapeJs(ui.message("reportingui.reportsapp.home.title")) }", link: emr.pageLink("reportingui", "reportsapp/home") }
    ];
</script>

<script type="text/javascript">
(function(){
    var m = document.createElement('meta');
    m.setAttribute('name','viewport');
    m.setAttribute('content','width=device-width, initial-scale=1.0');
    document.head.appendChild(m);
})();
</script>

<style type="text/css">
#home-container #apps {
    display: grid;
    grid-template-columns: repeat(4, minmax(240px, 1fr));
    grid-gap: 12px;
}
#home-container #apps a.button.app.big {
    display: flex;
    align-items: center;
    gap: 8px;
    min-height: 56px;
    width: 100%;
    box-sizing: border-box;
    float: none !important;
}
@media (max-width: 1200px) {
    #home-container #apps {
        grid-template-columns: repeat(3, minmax(240px, 1fr));
    }
}
@media (max-width: 900px) {
    #home-container #apps {
        grid-template-columns: repeat(2, minmax(240px, 1fr));
    }
}
@media (max-width: 640px) {
    #home-container #apps {
        grid-template-columns: 1fr;
    }
    #home-container #apps a.button.app.big {
        width: 100% !important;
    }
}
</style>



<div id="home-container">

    <div id="apps">
        <% if (overviews) { %>
        <a id="${ui.message("reportingui.reportsapp.overviewReports")}" href="/${contextPath}/isanteplusreports/reportsHomeOverview.page" class="button app big">
            <i class="icon-desktop"></i>
            ${ui.message("reportingui.reportsapp.overviewReports")}
        </a>
        <% } %>

        <% if (antenatal) { %>
        <a id="${ui.message("isanteplusreports.reportsapp.antenatal")}" href="/${contextPath}/isanteplusreports/reportsHomePrenatal.page" class="button app big">
            <i class="icon-magic"></i>
            ${ui.message("isanteplusreports.reportsapp.antenatal")}
        </a>
        <% } %>

        <% if (dataQualityReports) { %>
        <a id="${ui.message("reportingui.reportsapp.dataQualityReports")}" href="/${contextPath}/isanteplusreports/reportsHomeDataQuality.page" class="button app big">
            <i class="icon-align-right"></i>
            ${ui.message("reportingui.reportsapp.dataQualityReports")}
        </a>
        <% } %>

        <% if (patientsStatus) { %>
        <a id="${ui.message("isanteplusreports.reportsapp.patientsStatus")}" href="/${contextPath}/isanteplusreports/patientsStatus.page" class="button app big">
            <i class="icon-spinner"></i>
            ${ui.message("isanteplusreports.reportsapp.patientsStatus")}
        </a>
        <% } %>

        <% if (alertPrecoce) { %>
        <a id="${ui.message("isanteplusreports.reportsapp.alertprecoce")}" href="/${contextPath}/isanteplusreports/alertPrecoceReport.page" class="button app big">
            <i class="icon-exclamation-sign"></i>
            ${ui.message("isanteplusreports.reportsapp.alertprecoce")}
        </a>
        <% } %>

        <% if (comorbidity) { %>
        <a id="${ui.message("isanteplusreports.reportsapp.comorbidity")}" href="/${contextPath}/isanteplusreports/comorbidityIndicatorReport.page" class="button app big">
            <i class="icon-bar-chart"></i>
            ${ui.message("isanteplusreports.reportsapp.comorbidity")}
        </a>
        <% } %>

        <% if (dataExports) { %>
        <a id="${ui.message("reportingui.reportsapp.dataExports")}" href="/${contextPath}" class="button app big">
            <i class="icon-share"></i>
            ${ui.message("reportingui.reportsapp.dataExports")}
        </a>
        <% } %>

        <% if (dashboard) { %>
        <a id="${ui.message("isanteplusreports.reportsapp.dashboard")}" href="/${contextPath}" class="button app big">
            <i class="icon-reorder"></i>
            ${ui.message("isanteplusreports.reportsapp.dashboard")}
        </a>
        <% } %>

        <% if (ptme) { %>
        <a id="${ui.message("isanteplusreports.reportsapp.ptme")}" href="/${contextPath}/isanteplusreports/reportsHomePTME.page" class="button app big">
            <i class="icon-tint"></i>
            ${ui.message("isanteplusreports.reportsapp.ptme")}
        </a>
        <% } %>

        <% if (psychosocial) { %>
        <a id="${ui.message("isanteplusreports.reportsapp.psychosocial")}" href="/${contextPath}/isanteplusreports/psychoSocialIndicatorReport.page" class="button app big">
            <i class="icon-eye-open"></i>
            ${ui.message("isanteplusreports.reportsapp.psychosocial")}
        </a>
        <% } %>

        <% if (other) { %>
        <a id="${ui.message("isanteplusreports.reportsapp.other")}" href="/${contextPath}" class="button app big">
            <i class="icon-folder-close"></i>
            ${ui.message("isanteplusreports.reportsapp.other")}
        </a>
        <% } %>

    </div>

</div>


<!--
<div class="reportBox">
    <% if (monitoringReports) { %>
    <p>${ ui.message("reportingui.reportsapp.monitoringReports") }</p>
    <ul>
        <% monitoringReports.each { %>
        <li>
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </li>
        <% } %>
    </ul>
    <% } %>
</div>
-->

<div class="reportBox">


    <% if (dataExports) { %>
        <p>${ ui.message("reportingui.reportsapp.dataExports") }</p>
        <ul>
            <% dataExports.each { %>
            <li>
                ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
            </li>
            <% } %>
        </ul>
    <% } %>

    <% if (dashboard) { %>
        <p>${ ui.message("isanteplusreports.reportsapp.dashboard") }</p>
        <ul>
            <% dashboard.each { %>
            <li>
                ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
            </li>
            <% } %>
        </ul>
    <% } %>


 <!--
     <% if (other) { %>
        <p>${ ui.message("isanteplusreports.reportsapp.other") }</p>
        <ul>
            <% other.each { %>
            <li>
                ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
            </li>
            <% } %>
        </ul>
    <% } %>
-->

</div>
