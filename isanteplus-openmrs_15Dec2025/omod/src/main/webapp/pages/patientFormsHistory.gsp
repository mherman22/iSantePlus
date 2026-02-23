<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("isanteplus.formsHistory") ])
%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script type="text/javascript">
	var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${patientPropts.name}", link: "/" + OPENMRS_CONTEXT_PATH + "/coreapps/clinicianfacing/patient.page?patientId=${patient.uuid}"},
        { label: "${ ui.message('isanteplus.formsHistory') }"}
    ];
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

${ ui.includeFragment("isanteplus", "formsHistory") }
