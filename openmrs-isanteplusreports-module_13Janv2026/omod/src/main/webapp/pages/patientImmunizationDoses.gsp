<%
	ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("isanteplusreports.patientImmunization") ])
	ui.includeJavascript("isanteplusreports", "print.js")
%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script type="text/javascript">
	var breadcrumbs = [
		{ icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
		{ label: "${ ui.message("isanteplusreports.back") }", link: "/" + OPENMRS_CONTEXT_PATH + "/coreapps/clinicianfacing/patient.page?patientId=${patient.uuid}"},
		{ label: "${ ui.message('isanteplusreports.patientImmunization') }"}
	];
</script>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<style>
.info-section {
	width: 90%;
	max-width: 1000px;
	margin: 20px auto;
	padding: 10px;
}

.info-section h3 {
	text-align: center;
	margin-bottom: 20px;
	color: #2773a6;
}

/* Chaque vaccin devient une ligne */
.immunization-row {
	display: flex;
	flex-wrap: wrap;
	align-items: flex-start;
	border: 1px solid #ddd;
	border-radius: 8px;
	padding: 10px 12px;
	margin-bottom: 12px;
	background: #fff;
	box-shadow: 0 1px 4px rgba(0,0,0,0.05);
}

/* Nom du vaccin */
.vaccine-name {
	font-weight: bold;
	color: #3e5f81;
	margin-right: 15px;
	min-width: 150px;
}

/* Conteneur des doses */
.vaccine-doses {
	display: flex;
	flex-wrap: wrap;
	gap: 8px;
}

/* Chaque dose */
.vaccine-dose {
	width: 100px;
	background: #f0f8ff;
	padding: 4px 8px;
	border-radius: 4px;
	font-size: 13px;
	border: 1px solid #b0c4de;
}

/* Responsive */
@media (max-width: 768px) {
	.immunization-row {
		flex-direction: column;
		gap: 8px;
	}
	.vaccine-name {
		min-width: auto;
	}
}
</style>

<div class="info-section">
	<h3>${ ui.message("isanteplusreports.patientImmunization") }</h3>

	<div class="immunization-list">
		<% columnsvalues.each { row -> %>
		<div class="immunization-row">
			<div class="vaccine-name">
				${ui.format(row.columnValues[columns[0]])} <!-- Nom du vaccin -->
			</div>
			<div class="vaccine-doses">
				<% for (int i = 1; i < columns.size(); i++) { %>
				<div class="vaccine-dose">
					Dose ${i}: ${ui.format(row.columnValues[columns[i]])}
				</div>
				<% } %>
			</div>
		</div>
		<% } %>
	</div>
</div>
