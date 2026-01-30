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

    def i=0;
    def j=0;
%>

<script type="text/javascript">
    /*var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("reportingui.reportsapp.home.title") }", link: "${ ui.pageLink("reportingui", "reportsapp/home") }" }
    ];*/
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.escapeJs(ui.message("reportingui.reportsapp.home.title")) }", link: emr.pageLink("reportingui", "reportsapp/home") },
        { label: "${ ui.message("reportingui.reportsapp.overviewReports") }", link: '${ ui.thisUrl() }' }
    ];

</script>

<style type="text/css">
.clearfix::after {
    content: "";
    clear: both;
    display: table;
}



.indicatorsHeader {
    text-align: center;
    background-color: #fefad3;
    font-style: italic;
}

#indicators thead th {
    background-color: #fdf59a;
}

#divWithReportTables th, #divWithReportTables td {
    padding: 5pt;
    border-color: black;
    border-style: solid;
    border-top-width: 1px;
    border-left-width: 1px;
    border-right-width: 0px;
    border-bottom-width: 0px;
}

#divWithReportTables th:last-child, #divWithReportTables td:last-child {
    border-right-width: 1px;
}

#divWithReportTables tr:last-child th, #divWithReportTables tr:last-child td {
    border-bottom-width: 1px;
}

#divWithReportTables th, #divWithReportTables td {
    white-space: normal;
}

.div1{
    width: 50%;
    float: left;
}
.div2{
    width: 50%;
    float: right;
    margin-top: 10px;
}
.tdcolor{
    color: #fdf59a;
    padding: 7px;
}

.tdcolor, a{
    color: #363463;
}


</style>


<div class="div1">
    <table id="indicators" style="display: block; width: 100%; padding-top: 11px;">
        <thead>
        <tr>
            <th style="width:100%"><i class="icon-desktop"></i> ${ ui.message("reportingui.reportsapp.overviewReports") }</th>
        </tr>
        </thead>
        <tbody>
        <% overviews.each {  %>
        <% i++; %>
        <tr>
        <% if(i<=23) { if(it.id!="isanteplusreports.septJoursLibelle" && it.id!="isanteplusreports.quatorzeJoursLibelle" && it.id!="isanteplusreports.pnls_report") { %>
        <td class="tdcolor">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </td>
        <% }} %>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<div class="div2">
    <table id="indicators" style="">
        <thead>
        <tr>
            <th style="width:100%"><i class="icon-desktop"></i> ${ ui.message("reportingui.reportsapp.overviewReports") }</th>
        </tr>
        </thead>
        <tbody>
        <% overviews.each { %>
        <% j++; %>
        <tr>
            <% if(j>23) { if(it.id!="isanteplusreports.septJoursLibelle" && it.id!="isanteplusreports.quatorzeJoursLibelle" && it.id!="isanteplusreports.pnls_report") { %>
            <td class="tdcolor">
                ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
            </td>
            <% }} %>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
