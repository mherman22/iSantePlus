<%
	ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("isanteplusreports.patientSummary") ])
	ui.includeJavascript("isanteplusreports", "print.js")
%>

<meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=cover">

<style>
body {
	background: #f4f7fb;
	font-family: "Segoe UI", Arial, sans-serif;
}

.summary-container {
	max-width: 75%;
	margin: auto;
	padding: 20px;
}

#DivIdToPrint{
	/*width: 100%;*/
	/*border: 1px solid red;*/
}

.summary-header {
	background: white;
	padding: 20px;
	/*border-radius: 2px;*/
	margin-bottom: 25px;
	box-shadow: 0 3px 2px -3px;
}

.summary-header h2 { margin: 0 0 8px 0; }

.print-btn {
	float: right;
	padding: 7px 14px;
	margin-bottom: 10px;
	background: #7a98bd;
	color: white;
	border: none;
	font-size: 13px;
	border-radius: 0px 0px 0px 15px;
	cursor: pointer;
}

.print-btn:hover {
	background: #89aad3;
}

.accordion {
	margin-bottom: 18px;
	border-radius: 5px;
	overflow: hidden;
	background: white;
	box-shadow: 0 3px 2px -3px;
}

.accordion-header {
	padding: 16px;
	cursor: pointer;
	font-weight: 600;
	color: white;
	display: flex;
	justify-content: space-between;
	align-items: center;
	font-size: 15px;
}

.accordion-icon { transition: transform 0.3s ease; }

/* === ACCORDION CONTENT === */
.accordion-content {
	display: none; /* pas affiché par défaut */
	padding: 18px;
	background: #ffffff;
	overflow-x: auto;
}

.accordion.active .accordion-content {
	display: block; /* affiché si actif */
}
.accordion.active .accordion-icon {
	transform: rotate(180deg);
}

/* === COLORS POUR CHAQUE SECTION === */
.accordion-header:nth-of-type(1) { background-color: #234666; } /* Demographics */
.accordion-header:nth-of-type(2) { background-color: #1c2833; } /* Last six forms */
.accordion-header:nth-of-type(3) { background-color: #4b5358; } /* Clinic exams */
.accordion-header:nth-of-type(4) { background-color: #3b3f42; } /* Lab results */
.accordion-header:nth-of-type(5) { background-color: #2f2d3a; } /* Dispensing */
.accordion-header:nth-of-type(6) { background-color: #3e3e3e; } /* Vitals */
.accordion-header:nth-of-type(7) { background-color: #4b3c3c; } /* Motifs */
.accordion-header:nth-of-type(8) { background-color: #3c4b3c; } /* Impressions */
.accordion-header:nth-of-type(9) { background-color: #3c3c4b; } /* Viral load */

.accordion-header:hover { opacity: 0.9; }

/* === TABLE DESIGN === */
.accordion-content table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 10px;
	font-size: 14px;
}

.accordion-content table thead {
	background: #007bff;
	color: #0f172a;
}

.accordion-content table th,
.accordion-content table td {
	padding: 10px;
	border-bottom: 1px solid #eee;
}

.accordion-content table tbody tr:nth-child(even) {
	background-color: #f8f9fc;
}

.accordion-content table tbody tr:hover {
	background-color: #e9f2ff;
}

@media (max-width: 768px) {
	.accordion-content table { font-size: 13px; }
	.summary-container {
		max-width: 100%;
		margin: auto;
		padding: 20px;
	}
}

@media print {
	body { background: white; }
	.print-btn { display: none; }
	.accordion-content { display: block !important; }
	.accordion-header { background: #ddd !important; color: black !important; }
	.accordion-content table thead { background: #ddd !important; color: black !important; }
}
</style>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<div class="summary-container">

	<button class="print-btn" onclick="printDiv()">
		${ ui.message("isanteplusreports.print") }
	</button>

	<div id="DivIdToPrint">

		<div class="summary-header">
			<h2>${ ui.message("isanteplusreports.summaryMedicalData") }</h2>
			<strong>${ui.format(patient.getGivenName())} ${ui.format(patient.getFamilyName())}</strong><br/>
			${ui.format(location)}
		</div>

		<!-- 1 DEMOGRAPHICS -->
		<div class="accordion active">
			<div class="accordion-header">
				${ ui.message("isanteplusreports.demographicsData") } <span class="accordion-icon">▼</span>
			</div>
			<div class="accordion-content">
				<p><strong>${ ui.message("isanteplusreports.familyName") }:</strong> ${ui.format(patient.getFamilyName())}</p>
				<p><strong>${ ui.message("isanteplusreports.name") }:</strong> ${ui.format(patient.getGivenName())}</p>
				<p><strong>${ ui.message("isanteplusreports.sex") }:</strong> ${ui.format(patient.getGender())}</p>
				<p><strong>${ ui.message("isanteplusreports.age") }:</strong> ${ui.format(patient.getAge())}</p>
				<p><strong>${ ui.message("isanteplusreports.address") }:</strong> ${ui.format(patient.getPersonAddress())}</p>
			</div>
		</div>

		<!-- 2 LAST SIX FORMS -->
		<div class="accordion active">
			<div class="accordion-header">
				${ ui.message("isanteplusreports.lastSixForms") } <span class="accordion-icon">▼</span>
			</div>
			<div class="accordion-content">
				${ ui.includeFragment("isanteplusreports", "lastSixForms", [ patientId: patient.patientId ]) }
			</div>
		</div>

		<!-- 7 MOTIFS -->
		<div class="accordion active">
			<div class="accordion-header">
				Motifs Consultation <span class="accordion-icon">▼</span>
			</div>
			<div class="accordion-content">
				${ ui.includeFragment("isanteplusreports", "motifsConsultation", [ patientId: patient.patientId ]) }
			</div>
		</div>

		<!-- 8 IMPRESSIONS -->
		<div class="accordion active">
			<div class="accordion-header">
				Impressions Cliniques <span class="accordion-icon">▼</span>
			</div>
			<div class="accordion-content">
				${ ui.includeFragment("isanteplusreports", "impressionsCliniques", [ patientId: patient.patientId ]) }
			</div>
		</div>

		<!-- 9 VIRAL LOAD -->
		<div class="accordion active">
			<div class="accordion-header">
				Charge Virale <span class="accordion-icon">▼</span>
			</div>
			<div class="accordion-content">
				${ ui.includeFragment("isanteplus", "lastViralLoadTest", [ patientId: patient.patientId ]) }
			</div>
		</div>

		<!-- 3 CLINIC EXAMS -->
		<div class="accordion active">
			<div class="accordion-header">
				Examens Cliniques <span class="accordion-icon">▼</span>
			</div>
			<div class="accordion-content">
				${ ui.includeFragment("isanteplusreports", "clinicExams", [ patientId: patient.patientId ]) }
			</div>
		</div>

		<!-- 4 LAB RESULTS -->
		<div class="accordion active">
			<div class="accordion-header">
				${ ui.message("isanteplusreports.labsResult") } <span class="accordion-icon">▼</span>
			</div>
			<div class="accordion-content">
				${ ui.includeFragment("isanteplusreports", "labsResult", [ patientId: patient.patientId ]) }
			</div>
		</div>

		<!-- 5 DISPENSING -->
		<div class="accordion active">
			<div class="accordion-header">
				Dispensation Médicaments <span class="accordion-icon">▼</span>
			</div>
			<div class="accordion-content">
				${ ui.includeFragment("isanteplusreports", "dispensingDrugs", [ patientId: patient.patientId ]) }
			</div>
		</div>

		<!-- 6 VITALS -->
		<div class="accordion active">
			<div class="accordion-header">
				${ ui.message("isanteplusreports.lastVitals") } <span class="accordion-icon">▼</span>
			</div>
			<div class="accordion-content">
				${ ui.includeFragment("isanteplus", "isantePlusMostRecentVitals", [ patientId: patient.patientId ]) }
			</div>
		</div>

	</div>
</div>

<script>
	document.querySelectorAll('.accordion-header').forEach(header => {
		header.addEventListener('click', function() {
			this.parentElement.classList.toggle('active');
		});
	});
</script>
