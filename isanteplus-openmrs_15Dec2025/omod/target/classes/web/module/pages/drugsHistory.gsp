<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("Drugs History") ])
%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${patientPropts.name}", link: "/" + OPENMRS_CONTEXT_PATH + "/coreapps/clinicianfacing/patient.page?patientId=${patient.uuid}"},
        { label: "${ ui.message('drugs history') }"}
    ];
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<style>
/* Container */
.info-section {
    width: 80%;
    margin: 20px auto;
    border: 1px solid #ddd;
    border-radius: 6px;
    background: #fff;
    padding: 15px;
    overflow-x: auto;
}

/* Title */
.info-section h3 {
    text-align: center;
    margin-bottom: 15px;
    color: #333;
    font-weight: 500;
}

/* Table basic style */
table {
    width: 100%;
    border-collapse: collapse;
    font-size: 14px;
}

th, td {
    padding: 10px 12px;
    border: 1px solid #eee;
    text-align: left;
}

th {
    background-color: #f5f5f5;
    font-weight: 600;
}

/* Zebra stripes très léger */
tbody tr:nth-child(even) {
    background-color: #fafafa;
}

/* Hover léger */
tbody tr:hover {
    background-color: #f0f0f0;
}

/* Responsive */
@media (max-width: 768px) {
    .info-section {
        width: 95%;
    }
    th, td {
        padding: 8px 6px;
        font-size: 13px;
    }
}
</style>

<div class="info-section">
    <div class="info-header">
        <h3>${ ui.message("isanteplus.drugsHistory") }</h3>
    </div>

    <table>
        <thead>
        <tr>
            <th>${ ui.message("Medicaments") }</th>
            <th>${ ui.message("Date") }</th>
        </tr>
        </thead>
        <tbody>
        <% drugname.each { %>
        <tr>
            <td>${ ui.format(it.valueCoded) }</td>
            <td>${ ui.format(it.obsDatetime) }</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
