<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("isanteplus.labHistory") ])
%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${patientPropts.name}", link: "/" + OPENMRS_CONTEXT_PATH + "/coreapps/clinicianfacing/patient.page?patientId=${patient.uuid}"},
        { label: "${ ui.message('isanteplus.labHistory') }"}
    ];
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<style>
/* Container */
.info-section {
    width: 90%;
    max-width: 1000px;
    margin: 20px auto;
    padding: 10px;
}

/* Title */
.info-section h3 {
    text-align: center;
    margin-bottom: 20px;
    color: #2773a6;
}

/* Grid container */
.lab-cards {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 15px;
}

/* Individual card */
.lab-card {
    background: #fff;
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 12px 15px;
    box-shadow: 0 2px 5px rgba(0,0,0,0.05);
    transition: transform 0.15s ease, box-shadow 0.15s ease;
}

.lab-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 5px 12px rgba(0,0,0,0.1);
}

/* Card content */
.lab-card div {
    margin-bottom: 6px;
    font-size: 14px;
}

.lab-label {
    font-weight: bold;
    color: #3e5f81;
    margin-right: 5px;
}

.lab-value {
    color: #333;
}

/* Responsive adjustments */
@media (max-width: 768px) {
    .lab-cards {
        grid-template-columns: 1fr;
    }
    .lab-card div {
        font-size: 13px;
    }
}
</style>

<div class="info-section">
    <h3>${ ui.message("isanteplus.labHistory") }</h3>

    <div class="lab-cards">
        <% labresult.each { %>
        <div class="lab-card">
            <div><span class="lab-label">${ ui.message("isanteplus.testName") }:</span>
                <span class="lab-value">${ui.format(it.obs.concept)}</span>
            </div>
            <div><span class="lab-label">Date:</span>
                <span class="lab-value">${ui.format(it.obs.obsDatetime)}</span>
            </div>
            <div><span class="lab-label">${ ui.message("isanteplus.testResult") }:</span>
                <span class="lab-value">
                    ${ui.format(it.obs.valueNumeric)} ${ui.format(it.obs.valueCoded)} ${ui.format(it.obs.valueText)}
                    <% if(it.obs.valueNumeric > 0) { %> ${ui.format(it.conceptNumeric.units)} <% } %>
                </span>
            </div>
            <div><span class="lab-label">${ ui.message("isanteplus.minimumValue") }:</span>
                <span class="lab-value"><% if(it.obs.valueNumeric > 0) { %> ${ui.format(it.conceptNumeric.lowNormal)} <% } %></span>
            </div>
            <div><span class="lab-label">${ ui.message("isanteplus.maximumValue") }:</span>
                <span class="lab-value"><% if(it.obs.valueNumeric > 0) { %> ${ui.format(it.conceptNumeric.hiNormal)} <% } %></span>
            </div>
        </div>
        <% } %>
    </div>
</div>
