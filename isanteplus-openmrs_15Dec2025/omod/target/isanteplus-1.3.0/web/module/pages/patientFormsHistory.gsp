<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("isanteplus.formsHistory") ])
%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

${ ui.includeFragment("isanteplus", "formsHistory") }
