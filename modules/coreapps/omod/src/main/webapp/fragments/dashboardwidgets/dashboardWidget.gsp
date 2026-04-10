<%
    ui.includeJavascript("coreapps", "web/coreapps.vendor.js")
    ui.includeJavascript("coreapps", "web/coreapps.dashboardwidgets.js")
    ui.includeJavascript("uicommons", "handlebars/handlebars.js")
%>

<div id="coreapps-${config.id}" class="info-section openmrs-contrib-dashboardwidgets">
    <div class="info-header">
        <i class="${config.icon}"></i>
        <h3>${ ui.message(config.label) }</h3>
    </div>

    <div class="info-body" style="display: flex; flex-direction: column">
        <% if (visitListMap) {
            visitListMap.each { %>
        <div style="display:flex; flex-direction:row; width:100%; padding:4px; border-bottom:1px solid #e3eaeb">
            <a href="/${contextPath}/coreapps/patientdashboard/patientDashboard.page?patientId=${it.key.patient.uuid}&visitId=${it.key.uuid}"
               style="font-size:14px; float:left">
                ${it.key.startDatetime.format("dd.MMM.yyyy")}
            </a>
            <div style="display:flex; flex-direction:row; width:100%; justify-content:end; flex-wrap:wrap">
                <% it.value.eachWithIndex { enc, i -> %>
                <span style="background:#58a44f; color:#fff; font-size:12px; padding:4px;">
                    ${enc.encounterType.name} <% if (i < it.value.size()-1) { %>,<% } %>
                </span>
                <% } %>
            </div>
        </div>
        <% }
        } %>
    </div>
</div>