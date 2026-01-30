<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeJavascript("coreapps", "visit/jquery.dataTables.js")
    ui.includeJavascript("coreapps", "visit/filterTable.js")
    ui.includeCss("coreapps", "visit/visits.css")
%>
<script type="text/javascript">
    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {label: "${ ui.message("coreapps.triage.app.label")}"}
    ];


    jQuery(function (jq) {
        jq(document).on("click", ".reload", function (e) {
            location.reload();
        });
    });


</script>

<style>
.container-data {
    width: 99.4%;
    margin: 5px;
    background: #fff;
    border: 1px solid #f0f0f0;
    display: flex;
    justify-content: center;
    border-radius: 3px;
}

.filters {
    margin-right: 8px;
}

#active-visits {
    width: 99%;
    margin: 10px;
}

</style>


<h2 style="margin-left: 10px">${ui.message("coreapps.triage.app.description")}</h2>
<p id="filter-tags" class="filters">

<a class="reload" style="margin-right: 30px; font-size: 20px; height: 20px; color: #4c80a1; border: 1px solid #90b4c7; border-radius: 25px">
     <i style="margin-left: 1px; margin-right: -3px" class="icon-repeat"></i>
</a>

${ui.message("Filters")}
<% visitTypesWithAttr.each { type, attr -> %>
<span class="filter disabled" value="${type}"/>
<script type="text/javascript">
    jq(document).ready(function () {
        if ('${attr.color}' != null) {
            jq("#visittype-tag-${type}.tag").css("background", '${attr.color}');
        }
    })
</script>
<span id="visittype-tag-${type}" class="tag ${attr.shortName}" style="cursor:pointer;">
    ${ui.format(attr.name)}
</span>
</span>
<% } %>
</p>

<div class="container-data">
    <table id="active-visits" border="1" cellspacing="0" cellpadding="2">
        <thead>
        <tr>
            <th style="background:#f3f6f9; color:#4c80a1; width:6%; text-align:center;">Date d'arrivée</th>
            <th style="background:#f3f6f9; color:#4c80a1; width:6%; text-align:center;">${ui.message("coreapps.patient.identifier")}</th>
            <th style="background:#f3f6f9; color:#4c80a1; width:6%; text-align:center;">${ui.message("coreapps.person.name")}</th>
            <th style="background:#f3f6f9; color:#4c80a1; width:6%; text-align:center;">Code couleur</th>
            <th style="background:#f3f6f9; color:#4c80a1; width:6%; text-align:center;">${ui.message("coreapps.activeVisits.lastSeen")}</th>
        </tr>
        </thead>
        <tbody>
        <% if (triageList == null || (triageList != null && triageList.size() == 0)) { %>
        <tr>
            <td colspan="4">${ui.message("coreapps.none")}</td>
        </tr>
        <% } %>
        <% triageList.each { triage ->
            def latest = triage.vitals.encounter
        %>
        <tr id="visit-${latest?.visit?.visitId}">
            <td>${ui.format(latest?.encounterDatetime)}</td>
            <td>${ui.encodeHtmlContent(ui.format(triage.patient.patientIdentifier))}</td>
            <td>
                <% if (canViewVisits) { %>
                <a style="color: #5594be"
                   href="${ui.pageLink('coreapps', 'clinicianfacing/patient', [patientId: triage?.patient?.patientId])}">
                    ${ui.encodeHtmlContent(ui.format(triage?.patient))}
                </a>
                <% } else { %>
                ${ui.encodeHtmlContent(ui.format(triage?.patient))}
                <% } %>
            </td>

            <td align="center" style="width: 50px">
                <% if (triage.triageLevel == 'resuscitation') { %>
                <i style="font-size: 45px; border: 1px solid #d3d6d8; border-radius: 25px; padding: 1px 4px; color: #40a6d1;" class="icon-circle"></i>
                <% } else if (triage.triageLevel == 'critical') { %>
                <i style="font-size: 45px; border: 1px solid #d3d6d8; border-radius: 25px; padding: 1px 4px; color: #cc0000" class="icon-circle"></i>
                <% } else if (triage.triageLevel == 'potential') { %>
                <i style="font-size: 45px; border: 1px solid #d3d6d8; border-radius: 25px; padding: 1px 4px; color: #f0f000" class="icon-circle"></i>
                <% } else if (triage.triageLevel == 'semi') { %>
               <!-- <div style="background: #3fbf4e; height: 50px; width: 95%"></div> -->
                <i style="font-size: 45px; border: 1px solid #d3d6d8; border-radius: 25px; padding: 1px 4px; color: #3fbf4e" class="icon-circle"></i>
                <% } else { %>
                <i style="font-size: 45px; border: 1px solid #d3d6d8; border-radius: 25px; padding: 1px 4px; color: #ffffff;" class="icon-circle"></i>
                <% } %>
            </td>
            <td>
                <% if (latest) { %>
                ${ui.encodeHtmlContent(ui.format(latest.encounterType))}
                <br/>
                <small>
                    ${ui.encodeHtmlContent(ui.format(latest.location))} @ ${ui.format(latest.encounterDatetime)}
                </small>
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>

</div>



