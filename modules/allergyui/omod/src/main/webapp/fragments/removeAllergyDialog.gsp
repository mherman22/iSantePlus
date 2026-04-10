<div id="allergyui-remove-allergy-dialog" class="dialog" style="display: none">
    <div class="dialog-header">
        <h3>${ ui.message("allergyui.removeAllergy") }</h3>
    </div>
    <div class="dialog-content">
        <ul>
            <li class="info">
                <span id="removeAllergyMessage">${ ui.message("allergyui.removeAllergy.message") }</span>
            </li>
        </ul>
        <form method="POST" action="allergies.page">
            <input type="" name="patientId" value="${patient.id}"/>
            <input type="" name="returnUrl" value="${ ui.encodeHtml(returnUrl)}"/>
            <input type="" id="allergyId" name="allergyId" value=""/>
            <input type="" name="action" value="removeAllergy"/>
            <input type="hidden" id="removeAllergyMessageTemplate" value="${ ui.message("allergyui.removeAllergy.message") }"/>
            <button class="confirm right" type="submit">${ ui.message("general.yes") }</button>
            <button class="cancel">${ ui.message("general.no") }</button>
        </form>
    </div>
</div>