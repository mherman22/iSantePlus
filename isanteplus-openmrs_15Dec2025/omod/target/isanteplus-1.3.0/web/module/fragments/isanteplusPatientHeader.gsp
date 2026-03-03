<%
    def patient = config.patient
    def patientNames = config.patientNames

    ui.includeCss("coreapps", "patientHeader.css")
    ui.includeJavascript("coreapps", "patientdashboard/patient.js")

    appContextModel.put("returnUrl", ui.thisUrl())
%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<script type="text/javascript">
    var addMessage = "${ ui.message("coreapps.patient.identifier.add") }";
    jq(document).ready(function () {
        jq(window).scroll(function() {
            var scroll = jq(window).scrollTop();
            var headerElement = jq(".patient-header");
            if (headerElement.length) {
                var headerBottom = headerElement.offset().top + headerElement.outerHeight();
                if (scroll > headerBottom) {
                    var stickyHeader = jq("#sticky-patient-header");
                    if (!stickyHeader.is(":visible")) {
                        var mainHeaderHeight = jq("header.isante-header").outerHeight() || 0;
                        stickyHeader.css("top", mainHeaderHeight + "px");
                        stickyHeader.fadeIn(200);
                    }
                } else {
                    jq("#sticky-patient-header").fadeOut(200);
                }
            }
        });

        createEditPatientIdentifierDialog(${patient.id});
        jq("#patientIdentifierValue").keyup(function(event){
            var oldValue = jq("#patientIdentifierValue").val();
            var newValue = jq("#hiddenInitialIdentifierValue").val();
            if(oldValue==newValue){
                jq('.confirm').attr("disabled", "disabled");
                jq('.confirm').addClass("disabled");
            }else{
                jq('.confirm').removeAttr("disabled");
                jq('.confirm').removeClass("disabled");
                if(event.keyCode == 13){
                    //ENTER key has been pressed
                    jq('#confirmIdentifierId').click();
                }
            }

        });

        jq(".editPatientIdentifier").click(function (event) {

            var patientIdentifierId = jq(event.target).attr('data-patient-identifier-id');
            var identifierTypeId = jq(event.target).attr("data-identifier-type-id");
            var identifierTypeName = jq(event.target).attr("data-identifier-type-name");
            var patientIdentifierValue = jq(event.target).attr("data-patient-identifier-value");

            jq("#hiddenIdentifierTypeId").val(identifierTypeId);
            jq("#hiddenInitialIdentifierValue").val(patientIdentifierValue);
            jq("#hiddenPatientIdentifierId").val(patientIdentifierId);
            jq("#identifierTypeNameSpan").text(identifierTypeName);
            jq("#patientIdentifierValue").val(patientIdentifierValue);

            showEditPatientIdentifierDialog();

            jq('.confirm').attr("disabled", "disabled");
            jq('.confirm').addClass("disabled");

        });

        jq(".demographics .name").click(function () {
            emr.navigateTo({
                url: "${ ui.urlBind("/" + contextPath + config.dashboardUrl, [ patientId: patient.patient.id ] ) }"
            });
        })

        jq("#patient-header-contactInfo").click(function (){
            var contactInfoDialogDiv = jq("#contactInfoContent");

            if (contactInfoDialogDiv.hasClass('hidden')) {
                contactInfoDialogDiv.removeClass('hidden');
                jq(this).addClass('expanded');
            } else {
                contactInfoDialogDiv.addClass('hidden');
                jq(this).removeClass('expanded');
            }

            return false;
        });
    })
</script>

<style type="text/css">

/* ================= GLOBAL ================= */
#container-header {
    font-family: "Inter", "OpenSans", Arial, sans-serif;
    width: 100%;
    margin: auto;
    margin-top: -5px;
    margin-bottom: 15px;
    color: #2c3e50;
    font-size: 12px;
}

/* ================= STICKY HEADER ================= */
.sticky-header {
    position: fixed;
    left: 0;
    width: 98%;
    background: rgba(255,255,255,0.97);
    border-bottom: 1px solid #e6e9ef;
    box-shadow: 0 4px 20px rgba(0,0,0,0.05);
    z-index: 9999;
    padding: 14px 30px;
    display: none;
    backdrop-filter: blur(8px);
    /*border: 1px solid darkgreen;*/
}

.sticky-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-weight: 500;
    max-width: 1400px;
    margin: auto;
}

.sticky-name {
    font-weight: 600;
    font-size: 15px;
}

/* ================= MAIN CARD ================= */
.patient-header {
    width: 98%;
    display: flex;
    /*flex-wrap: wrap;*/
    gap: 25px;
    background: #ffffff;
    padding: 15px;
    margin: auto;
    border-radius: 10px;
    /*box-shadow: 0 10px 35px rgba(0,0,0,0.05);*/
    box-shadow: 0 3px 10px rgba(0,0,0,0.03);
    /*margin-top: -20px;*/
    transition: all 0.3s ease;
    border: 1px solid #ebf1fa;
}

/* ================= DEMOGRAPHICS ================= */
.demographics {
    width: 78%;
    /*border: 1px solid red;*/
}

.demographics .name {
    /*font-size: 1.9em;*/
    font-weight: 600;
    margin-bottom: 10px;
    color: #1a2b4c;
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
}

.demographics .name em {
    /*font-size: 0.65em;*/
    font-weight: 500;
    text-transform: uppercase;
    /*letter-spacing: 0.5px;*/
    color: #8898aa;
}

.gender-age {
    /*font-size: 0.85em;*/
    color: #6c7a89;
}

/* ================= DATA GRID ================= */
.patient-data-grid {
    width: 100%;
    display: flex;
    /*grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));*/
    gap: 20px;
    margin-top: 20px;
    /*border: 1px solid darkgreen;*/
}

.patient-data-item {
    background: linear-gradient(145deg,#ffffff,#ffffff,#f9fdff);
    padding: 10px 12px;
    border-radius: 8px;
    border: 1px solid #eef1f6;
    /*border-left: 4px solid #566a8b;*/
    transition: 0.25s ease;
    width: 80%;
}

.patient-data-item:hover {
    transform: translateY(-3px);
    box-shadow: 0 6px 18px rgba(0,0,0,0.06);
}

.patient-data-item i {
    font-weight: 600;
    font-size: 11px;
    text-transform: uppercase;
    color: #7b8ca8;
    display: block;
    margin-bottom: 4px;
    font-style: normal;
}

.patient-data-item b {
    font-size: 11px;
    /*font-weight: 600;*/
    color: #2c3e50;
}

/* ================= IDENTIFIERS CARD ================= */
.identifiers {
    flex: 1;
    background: #ffffff;
    padding: 10px 12px;
    border-radius: 8px;
    box-shadow: 0 8px 25px rgba(0,0,0,0.03);
    border: 1px solid #eef1f6;
    border-right: 4px solid #566a8b;
    float: right;
    /*height: fit-content;*/
}

.identifiers em {
    display: block;
    /*font-weight: 600;*/
    color: #5a6c90;
    margin-bottom: 4px;
    text-transform: uppercase;
    font-size: 11px;
    letter-spacing: 0.5px;
    font-style: normal;
}


.identifiers span {
    display: block;
    font-size: 14px;
    margin-bottom: 10px;
    font-family: "Courier New", monospace;
    font-weight: 600;
    color: #1f2d3d;
}

#contactInfoContent{
    font-size: 18px;
}

/* ================= UNKNOWN PATIENT ================= */
.unknown-patient {
    background: #fff3cd;
    border: 1px solid #ffeeba;
    padding: 15px;
    border-radius: 12px;
    margin-top: 18px;
}

/* ================= SECOND LINE FRAGMENTS ================= */
.secondLineFragments {
    width: 100%;
    margin-top: 25px;
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
}

/* ================= RESPONSIVE ================= */
@media (max-width: 992px) {
    .patient-header {
        flex-direction: column;
        /*padding: 20px;*/
    }
    .identifiers {
        width: 80%;
    }
}

@media (max-width: 768px) {
    #container-header {
        width: 100%;
        margin: 0px;
        margin-top: -10px;
        margin-bottom: 10px;
        /*border: 1px solid red;*/
    }
    .sticky-header {
        width: 87%;
    }

    .patient-header{
        width: 90%;
    }
    .demographics{
        width: 100%;
    }

    .demographics .name {
        font-size: 1.5em;
    }
    .patient-data-item{
        width: 92%;
    }


    .patient-data-grid {
        flex-direction: column;
        /*border: 1px solid red;*/
    }
    .identifiers {
        width: 80%;
        /*border: 1px solid red;*/
    }
}

</style>



<div id="container-header">
    <div id="sticky-patient-header" class="sticky-header">
        <div class="sticky-content">
            <span class="sticky-name">
                <% patientNames?.each { %>
                <span>${ ui.encodeHtmlContent(it.value) }</span>
                <% } %>
            </span>
            <span class="sticky-info">
                ${ui.message("coreapps.gender." + ui.encodeHtml(patient.gender))} &bull;
                <% if (patient.birthdate) { %>
                    <% if (patient.age > 0) { %>
                    ${ui.message("coreapps.ageYears", patient.age)}
                    <% } else if (patient.ageInMonths > 0) { %>
                    ${ui.message("coreapps.ageMonths", patient.ageInMonths)}
                    <% } else { %>
                    ${ui.message("coreapps.ageDays", patient.ageInDays)}
                    <% } %>
                <% } %>
            </span>
        </div>
    </div>

    <div class="patient-header <% if (patient.patient.dead) { %>dead<% } %>">

        <% if (patient.patient.dead) { %>
        <div class="death-header" style="">
            <span class="death-message">
                ${ ui.message("coreapps.deadPatient", ui.format(patient.patient.deathDate), ui.format(patient.patient.causeOfDeath)) }
            </span>
            <span class="death-info-extension">
                <%= ui.includeFragment("appui", "extensionPoint", [ id: "patientHeader.deathInfo", contextModel: appContextModel ]) %>
            </span>
        </div>
        <% } %>

        <div class="demographics">
            <h1 class="name">
                <% patientNames?.each { %>
                <span><span class="${ it.key.replace('.', '-') }">${ ui.encodeHtmlContent(it.value) }</span><em>${ui.message(it.key)}</em></span>
                <% } %>
                &nbsp;
                <span class="gender-age">
                    <span>${ui.message("coreapps.gender." + ui.encodeHtml(patient.gender))}&nbsp;</span>
                    <span>
                        <% if (patient.birthdate) { %>
                        <% if (patient.age > 0) { %>
                        ${ui.message("coreapps.ageYears", patient.age)}
                        <% } else if (patient.ageInMonths > 0) { %>
                        ${ui.message("coreapps.ageMonths", patient.ageInMonths)}
                        <% } else { %>
                        ${ui.message("coreapps.ageDays", patient.ageInDays)}
                        <% } %>
                        (<% if (patient.birthdateEstimated) { %>~<% } %>${ ui.formatDatePretty(patient.birthdate) })
                        <% } else { %>
                        ${ui.message("coreapps.unknownAge")}
                        <% } %>
                    </span>
                    <span id="edit-patient-demographics" class="edit-info">
                        <small>
                            <%= ui.includeFragment("appui", "extensionPoint", [ id: "patientHeader.editPatientDemographics", contextModel: appContextModel ]) %>
                        </small>
                    </span>
                    <a href="#" id="patient-header-contactInfo" class="contact-info-label">
                        <span id="coreapps-showContactInfo" class="show">${ui.message("coreapps.patientHeader.showcontactinfo")}</span>
                        <i class="toggle-icon icon-caret-down small"></i>
                        <span class="hide">${ui.message("coreapps.patientHeader.hidecontactinfo")}</span>
                        <i class="toggle-icon icon-caret-up small"></i>
                    </a>
                </span>

                <div class="firstLineFragments">
                    <% firstLineFragments.each { %>
                    ${ ui.includeFragment(it.extensionParams.provider, it.extensionParams.fragment, [patient: config.patient])}
                    <% } %>
                </div>

                <div class="hidden" id="contactInfoContent" class="contact-info-content">
                    ${ ui.includeFragment("coreapps", "patientdashboard/contactInfoInline", [ patient: config.patient, contextModel: appContextModel ]) }
                </div>
            </h1>
            <div class="patient-data-grid" style="">
                <% if (config.patientRecordingDate != null) { %>
                <div class="patient-data-item">
                    <i>${ ui.message("isanteplus.patientRecordingDate") }</i>
                    <b>${config.patientRecordingDate}</b>
                </div>
                <% } %>

                <% if (config.artInitiationDate != null) { %>
                <div class="patient-data-item">
                    <i>${ ui.message("isanteplus.artInitiationDate") }</i>
                    <b>${config.artInitiationDate}</b>
                </div>
                <% } %>

                <% if (config.artInitiationDate == null) { %>
                    <% if (config.columnsArtInitiationDate != null ) { %>
                        <% config.columnsvaluesart.each { %>
                            <% config.columnsArtInitiationDate.each { colNam -> %>
                            <div class="patient-data-item">
                                <i>${ui.format(colNam)}</i>
                                ${ui.format(it.columnValues[colNam])}
                            </div>
                            <% } %>
                        <% } %>
                    <% } %>
                <% } %>

                <% if (config.latestNextVisitDate != null) { %>
                <div class="patient-data-item">
                    <i>${ ui.message("isanteplus.nextVisitDate") }</i>
                    <b>${config.latestNextVisitDate}</b>
                </div>
                <% } %>

                <% if (config.latestNextDispensationDate != null) { %>
                <div class="patient-data-item">
                    <i>${ ui.message("isanteplus.nextDispensationDate") }</i>
                    <b>${config.latestNextDispensationDate}</b>
                </div>
                <% } %>

                <% if (config.columns != null ) { %>
                    <% config.columnsvalues.each { %>
                        <% config.columns.each { colName -> %>
                        <div class="patient-data-item">
                            <i>${ui.format(colName)}</i>
                            ${ui.format(it.columnValues[colName])}
                        </div>
                        <% } %>
                    <% } %>
                <% } %>
            </div>
        </div>

        <div class="identifiers">
            <em>${ui.message("coreapps.patientHeader.patientId")}</em>

            <% patient.primaryIdentifiers.each { %>
            <span>${it.identifier}</span>
            <% } %>
            <br/>
            <% if (config.extraPatientIdentifierTypes) { %>

            <% config.extraPatientIdentifierTypes.each { extraPatientIdentifierType -> %>

            <% def extraPatientIdentifiers = config.extraPatientIdentifiersMappedByType.get(extraPatientIdentifierType.patientIdentifierType) %>

            <% if (extraPatientIdentifiers) { %>
            <em>${ui.format(extraPatientIdentifierType.patientIdentifierType)}</em>

            <% if (extraPatientIdentifierType.editable) { %>
            <% extraPatientIdentifiers.each { extraPatientIdentifier -> %>
            <span><a class="editPatientIdentifier" data-patient-identifier-id="${extraPatientIdentifier.id}" data-identifier-type-id="${extraPatientIdentifierType.patientIdentifierType.id}"
                     data-identifier-type-name="${ui.format(extraPatientIdentifierType.patientIdentifierType)}" data-patient-identifier-value="${extraPatientIdentifier}" href="#${extraPatientIdentifierType.patientIdentifierType.id}">${extraPatientIdentifier}</a></span>
            <% } %>
            <% } else {%>
            <% extraPatientIdentifiers.each { extraPatientIdentifier -> %>
            <span>${extraPatientIdentifier}</span>
            <% } %>
            <% } %>

            <% } else if (extraPatientIdentifierType.editable) { %>
            <em>${ui.format(extraPatientIdentifierType.patientIdentifierType)}</em>
            <span class="add-id"><a class="editPatientIdentifier"  data-patient-identifier-id="" data-identifier-type-id="${extraPatientIdentifierType.patientIdentifierType.id}"
                                    data-identifier-type-name="${ui.format(extraPatientIdentifierType.patientIdentifierType)}" data-patient-identifier-value=""
                                    href="#${extraPatientIdentifierType.patientIdentifierType.id}">${ui.message("coreapps.patient.identifier.add")}</a></span>
            <% } %>

            <br/>
            <% } %>
            <% } %>
        </div>

        <div class="unknown-patient" style= <%=(!patient.unknownPatient) ? "display:none" : ""%>>
            ${ui.message("coreapps.patient.temporaryRecord")} <br/>

            <form action="/${contextPath}/coreapps/datamanagement/mergePatients.page" method="get">
                <input type="hidden" name="app" value="coreapps.mergePatients"/>
                <input type="hidden" name="isUnknownPatient" value="true"/>
                <input type="hidden" name="patient1" value="${patient.patient.id}"/>
                <input type="submit" id="merge-button"
                       value="${ui.message("coreapps.mergePatients.mergeIntoAnotherPatientRecord.button")}"/>
            </form>
        </div>

    </div>

    <div id="edit-patient-identifier-dialog" class="dialog" style="display: none">
        <div class="dialog-header">
            <h3>${ui.message("coreapps.patientDashBoard.editPatientIdentifier.title")}</h3>
        </div>

        <div class="dialog-content">
            <input type="hidden" id="hiddenPatientIdentifierId" value=""/>
            <input type="hidden" id="hiddenIdentifierTypeId" value=""/>
            <input type="hidden" id="hiddenInitialIdentifierValue" value=""/>
            <ul>
                <li class="info">
                    <span>${ui.message("coreapps.patient")}</span>
                    <h5>${ui.escapeJs(ui.encodeHtmlContent(ui.format(patient.patient)))}</h5>
                </li>
                <li class="info">
                    <span id="identifierTypeNameSpan"></span>
                </li>
                <li class="info">
                    <input id="patientIdentifierValue" value=""/>
                </li>
            </ul>

            <button id="confirmIdentifierId" class="confirm right">${ui.message("coreapps.confirm")}</button>
            <button class="cancel">${ui.message("coreapps.cancel")}</button>
        </div>
    </div>
</div>