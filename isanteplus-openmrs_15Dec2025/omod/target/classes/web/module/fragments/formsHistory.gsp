<style>

#forms_history_section {
	display: flex;
	justify-content: center;
	width: 100%;
}

.forms-box {
	width: 70%;
	background: #ffffff;
	padding: 20px;
	border-radius: 6px;
	box-shadow: 0 3px 8px rgba(0,0,0,0.05);
}

.forms-title {
	font-size: 20px;
	font-weight: bold;
	margin-bottom: 20px;
	color: #2c3e50;
}

.form-card {
	border: 1px solid #e3e8ef;
	border-radius: 6px;
	padding: 15px;
	margin-bottom: 12px;
	transition: 0.2s ease;
	background: #fff;
}

.form-card:hover {
	background: #f7fbff;
	transform: translateY(-0.2px);
}

.form-row {
	display: flex;
	flex-wrap: wrap;
	gap: 15px;
	font-size: 13px;
	margin-bottom: 6px;
}

.form-label {
	font-weight: bold;
	color: #3e5f81;
}

.form-name {
	font-weight: bold;
	font-size: 17px;
	color: #2773a6;
	margin-bottom: 8px;
}

/* Actions container */
.form-actions {
	display: flex;
	justify-content: flex-end;
	gap: 15px;
	margin-top: 10px;
}

/* Icons */
.action-icon {
	cursor: pointer;
	font-size: 17px;
	transition: 0.1s ease;
	padding: 6px;
	margin: 3px;
	border-radius: 3px;
}

.edit-icon {
	color: #3498db;
	border: 1px solid #3498db;
}

.edit-icon:hover {
	color: #21618c;
	transform: scale(1.1);
}

.delete-icon {
	color: #e74c3c;
	border: 1px solid #e74c3c;
}

.delete-icon:hover {
	color: #c0392b;
	transform: scale(1.1);
}

/* Responsive */
@media (max-width: 1024px) {
	.forms-box {
		width: 90%;
	}
}

@media (max-width: 768px) {
	.forms-box {
		width: 95%;
	}

	.form-row {
		flex-direction: column;
		gap: 4px;
	}
}

</style>


<script type="text/javascript">

	function deleteForm(uuid, iconElement) {

		var question = "${ ui.message("coreapps.patientDashBoard.deleteEncounter.message") }";

		if (confirm(question)) {

			jQuery.ajax({
				type: "POST",
				url: "${ ui.actionLink('deleteSelectedFormHistory') }",
				data: { selectedFormHistory: [uuid] },
				dataType: "json",
				success: function() {

					jQuery(iconElement)
							.closest(".form-card")
							.fadeOut(300, function(){
								jQuery(this).remove();
							});

				}
			});

		}
	}

</script>


${ ui.includeFragment("isanteplus", "isantePlusForms") }

<br/>

<div id="forms_history_section">
	<div class="forms-box">

		<div class="forms-title">
			${ ui.message("isanteplus.formsHistory") }
			(${ ui.message("isanteplus.formsHistory.filled") })
		</div>

		<% allFormHistory.each { %>

		<div class="form-card">

			<div class="form-name">
				${ ui.format(it.encounter.form.name) }
			</div>

			<div class="form-row">
				<div><span class="form-label">${ ui.message("isanteplus.formsHistory.visitDate") }:</span>
					${ ui.format(it.encounter.visit.startDatetime) }</div>

				<div><span class="form-label">${ ui.message("isanteplus.formsHistory.formStatus") }:</span>
					${ ui.format(it.formStatus) }</div>

				<div><span class="form-label">${ ui.message("isanteplus.formsHistory.provider") }:</span>
					${ ui.format(it.enteredBy) }</div>
			</div>

			<div class="form-row">
				<div><span class="form-label">${ ui.message("isanteplus.formsHistory.creationDate") }:</span>
					${ ui.format(it.encounter.dateCreated) }</div>
				<% if(ui.format(it.dateChanged)) {%>
				<div><span class="form-label">${ ui.message("isanteplus.formsHistory.lastModification") }:</span>
					${ ui.format(it.dateChanged) }</div>
				<% } %>
			</div>

			<div class="form-actions">

				<!-- EDIT -->
				<a href="/${appName}/htmlformentryui/htmlform/editHtmlFormWithStandardUi.page?patientId=${ ui.format(it.encounter.patient.uuid) }&encounterId=${ ui.format(it.encounter.uuid) }">
					<i class="icon-pencil action-icon edit-icon"
					   title="${ ui.message("general.edit") }"></i>
				</a>

				<!-- DELETE -->
				<i class="icon-trash action-icon delete-icon"
				   title="${ ui.message("general.remove") }"
				   onclick="deleteForm('${ ui.format(it.uuid) }', this)">
				</i>

			</div>

		</div>

		<% } %>

	</div>
</div>
