<%
    def patient = config.patient
    def patientNames = config.patientNames

    ui.includeCss("coreapps", "patientHeader.css")
    ui.includeJavascript("coreapps", "patientdashboard/patient.js")

    appContextModel.put("returnUrl", ui.thisUrl())
%>


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
    #container {
        font-family: "OpenSans", Arial, sans-serif;
        color: #363463;
        font-size: small;
    }

    .sticky-header {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        background: rgba(255, 255, 255, 0.98);
        border-bottom: 1px solid #ddd;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        z-index: 9999;
        padding: 10px 20px;
        display: none;
        box-sizing: border-box;
    }

    .patient-header {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        background-color: #f9f9f9;
        padding: 15px;
        border-radius: 5px;
        box-shadow: 0 1px 3px rgba(0,0,0,0.12);
        margin-bottom: 10px;
    }

    .demographics {
        flex: 2;
        min-width: 300px;
        margin-right: 20px;
    }

    .demographics .name {
        font-size: 1.5em;
        font-weight: 600;
        margin-bottom: 10px;
        color: #00463f;
        border-bottom: 1px solid #ddd;
        padding-bottom: 10px;
        display: flex;
        flex-wrap: wrap;
        align-items: baseline;
    }
    
    .demographics .name > span {
        margin-right: 10px;
    }

    .demographics .gender-age {
        display: block;
        margin-top: 5px;
        font-size: 0.8em;
        color: #555;
        font-weight: normal;
    }

    .patient-data-grid {
        width: 100%;
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
        gap: 15px;
        margin-top: 15px;
        /*border: 1px solid green;*/
    }

    .patient-data-item {
        font-size: 0.95em;
        line-height: 1.5;
        background: white;
        padding: 8px;
        /*width: 45%;*/
        border-radius: 4px;
        border: 1px solid #eee;
    }

    .patient-data-item i {
        font-weight: 600;
        color: #566a8b;
        font-style: normal;
        display: block;
        font-size: 0.85em;
        margin-bottom: 2px;
    }

    .patient-data-item b {
        color: #333;
        font-weight: 500;
    }

    .identifiers {
        flex: 1;
        min-width: 200px;
        background: #eef2f5;
        padding: 15px;
        border-radius: 4px;
        border-right: 4px solid #566a8b;
        height: fit-content;
    }

    .identifiers em {
        display: block;
        font-weight: bold;
        color: #566a8b;
        margin-bottom: 3px;
        font-style: normal;
        text-transform: uppercase;
        font-size: 0.75em;
        letter-spacing: 0.5px;
    }

    .identifiers span {
        display: block;
        font-size: 1.1em;
        margin-bottom: 12px;
        font-family: "Courier New", monospace;
        color: #333;
        font-weight: 600;
    }
    
    .identifiers .add-id {
        margin-top: 5px;
    }

    .secondLineFragments {
        width: 100%;
        margin-top: 15px;
        padding-top: 15px;
        border-top: 1px solid #eee;
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
        justify-content: flex-start;
    }

    @media (max-width: 768px) {
        .patient-header {
            flex-direction: column;
            padding: 10px;
        }

        .demographics {
            margin-right: 0;
            margin-bottom: 20px;
            min-width: 100%;
        }
        
        .demographics .name {
            font-size: 1.3em;
        }

        .patient-data-grid {
            width: 99%;
            /*grid-template-columns: 1fr;*/
            gap: 10px;
            /*border: 1px solid red;*/

        }
        
        .identifiers {
            width: 94%;
            box-sizing: border-box;
            border-left: none;
            border-top: 4px solid #566a8b;
        }

        .secondLineFragments {
            flex-direction: column;
        }
    }
</style>

<div id="container">
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
        <div class="death-header">
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
            <div class="patient-data-grid">
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

        <div class="secondLineFragments">
            <% secondLineFragments.each { %>
            ${ ui.includeFragment(it.extensionParams.provider, it.extensionParams.fragment, [patient: config.patient])}
            <% } %>
        </div>

        <div class="close"></div>
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