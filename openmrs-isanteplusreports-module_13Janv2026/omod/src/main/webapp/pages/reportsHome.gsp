<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeCss("reportingui", "reportsapp/home.css")

    def appFrameworkService = context.getService(context.loadClass("org.openmrs.module.appframework.service.AppFrameworkService"))
    def overviews = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.overview")
    def monitoringReports = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.monitoring")
    def dataQualityReports = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.dataquality")
    def dataExports = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.dataexport")
    def other = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.other")
    def antenatal = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.antenatal")
    def patientsStatus = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.patientsstatus")
    def alertPrecoce = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.alertprecoce")
    def dashboard = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.dashboard")
    def ptme = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.ptme")
    def psychosocial = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.psychosocial")
    def comorbidity = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.isanteplusreports.reports.comorbidity")
    def contextModel = [:]
    def chargeViraleIds = [
        "isanteplusreports.septJoursLibelle",
        "isanteplusreports.quatorzeJoursLibelle",
        "isanteplusreports.list_patients_charge_virale_plus_mille_by_result_date",
        "isanteplusreports.list_patients_charge_virale_moins_mille_by_result_date",
        "isanteplusreports.number_charge_virale_by_result_date"
    ]
    def chargeVirale = overviews.findAll { chargeViraleIds.contains(it.id) }
    def overviewsSansChargeVirale = overviews.findAll { !chargeViraleIds.contains(it.id) }
    def frequentationIds = [
        "isanteplusreports.consultationByDay",
        "isanteplusreports.numberVisitByMonth",
        "isanteplusreports.firstVisitAge",
        "isanteplusreports.patByAge",
        "isanteplusreports.report.transitionedPatient",
        "isanteplusreports.patientArvExpectedDateInThirtyDays",
        "isanteplusreports.numberPatientBySex",
        "isanteplusreports.number_hiv_patient",
        "isanteplusreports.institution_frequenting_by_user",
        "isanteplusreports.institution_frequenting_by_user_and_date",
        "isanteplusreports.institution_frequenting_by_date",
        "isanteplusreports.monitoring.institution_frequenting",
        "isanteplusreports.monitoring.institution_frequenting_by_date"
    ]
    def frequentationReports = (overviews + dataExports).findAll { frequentationIds.contains(it.id) }
            .sort { (ui.message(it.label) ?: it.id ?: "").toLowerCase() }
    def pharmacyIds = [
        "isanteplusreports.drugsPrescriptionAmount",
        "isanteplusreports.dispensingMedications"
    ]
    def pharmacyReports = (dataExports + patientsStatus).findAll { pharmacyIds.contains(it.id) }
            .sort { (ui.message(it.label) ?: it.id ?: "").toLowerCase() }
    def dataQualityIds = [
        "isanteplusreports.report.vitalStatistics",
        "isanteplusreports.report.patient_with_finger_print",
        "isanteplusreports.fingerprint.indicator.label",
        "isanteplusreports.patient_with_only_register_form",
        "isanteplusreports.hiv_patient_without_first_visit",
        "isanteplusreports.hiv_patient_with_activity_after_discontinuation",
        "isanteplusreports.form_recently_filled",
        "isanteplusreports.patientWithoutPCOrST",
        "isanteplusreports.patientsWithNoGender",
        "isanteplusreports.patientsWithMultipleARTRegimens",
        "isanteplusreports.possibleDuplicateRegistrations",
        "isanteplusreports.activity_after_discontinuation_by_period",
        "isanteplusreports.isanteplusreports.hiv_patients_without_hiv_test"
    ]
    def dataQualitySelectedReports = (dataQualityReports + overviews).findAll { dataQualityIds.contains(it.id) }
            .sort { (ui.message(it.label) ?: it.id ?: "").toLowerCase() }
    def arvCareIds = [
        "isanteplusreports.hiv_transmission_risks_factor",
        "isanteplusreports.patientStartedArv",
        "isanteplusreports.patientArvExpectedDateInThirtyDays",
        "isanteplusreports.patientArvEnd",
        "isanteplusreports.list_patients_receiving_arv_in_community",
        "isanteplusreports.list_patients_receiving_arv_in_hospital",
        "isanteplusreports.art_dispensation_follow_up",
        "isanteplusreports.artdistribution.report.label",
        "isanteplusreports.patientArvByPeriod",
        "isanteplusreports.community_arv_distribution",
        "isanteplusreports.report.ddp",
        "isanteplusreports.pnls_report",
        "isanteplusreports.alertPrecoce_report"
    ]
    def arvCareSelectedReports = (overviews + dataExports + dashboard + alertPrecoce).findAll { arvCareIds.contains(it.id) }
            .sort { (ui.message(it.label) ?: it.id ?: "").toLowerCase() }
    def viralLoadIds = [
        "isanteplusreports.alert",
        "isanteplusreports.number_charge_virale_by_demand_date",
        "isanteplusreports.list_patients_charge_virale_moins_mille_by_demand_date",
        "isanteplusreports.list_patients_charge_virale_plus_mille_by_demand_date",
        "isanteplusreports.list_of_lab_orders",
        "isanteplusreports.list_patients_eligible_for_charge_virale",
        "isanteplusreports.list_patients_eligible_for_charge_virale_controle",
        "isanteplusreports.reports.activePatientsWithViralLoadLastTwelveMonths"
    ]
    def viralLoadSelectedReports = overviews.findAll { viralLoadIds.contains(it.id) }
            .sort { (ui.message(it.label) ?: it.id ?: "").toLowerCase() }
    def ptmeIds = [
        "isanteplusreports.number_pregnancy_women_hiv_tested",
        "isanteplusreports.number_prenancy_women_vih_positive",
        "isanteplusreports.pregnancy_women_on_haart",
        "isanteplusreports.list_patients_eligible_for_charge_virale",
        "isanteplusreports.pregnancy_women_tested_for_syphilis",
        "isanteplusreports.pregnancy_women_diagnosed_with_syphilis",
        "isanteplusreports.exposed_infants_register_in_ptme_program",
        "isanteplusreports.number_infants_from_mother_on_prophylaxis",
        "isanteplusreports.number_exposed_infants_tested_by_pcr",
        "isanteplusreports.number_exposed_infants_confirmed_hiv",
        "isanteplusreports.womenEnrolledBecamePregnant",
        "isanteplusreports.list_of_exposed_infants",
        "isanteplusreports.list_patients_beneficie_pcr",
        "isanteplusreports.number_patients_beneficie_pcr",
        "isanteplusreports.list_eligible_children_for_pcr",
        "isanteplusreports.number_eligible_children_for_pcr"
    ]
    def ptmeSelectedReports = (ptme + antenatal + overviews).findAll { ptmeIds.contains(it.id) }
            .sort { (ui.message(it.label) ?: it.id ?: "").toLowerCase() }
    def tbHivIds = [
        "isanteplusreports.reports.activePatientsScreenedForTb",
        "isanteplusreports.reports.patientWithIncompleteProphylaxisAgainstTb",
        "isanteplusreports.reports.patientScreenedNegativeAgainstTbWithNoProphylaxis"
    ]
    def tbHivSelectedReports = overviews.findAll { tbHivIds.contains(it.id) }
            .sort { (ui.message(it.label) ?: it.id ?: "").toLowerCase() }
    def tbHivPlusIds = [
        "isanteplusreports.patientsCrachatWithoutTbDiagnostic",
        "isanteplusreports.patientsWithTbDiagnosticWithoutTreatment",
        "isanteplusreports.patientsWithTbDiagnosticWithoutCrachat",
        "isanteplusreports.patientsWithCompletedTbTreatment"
    ]
    def tbHivPlusSelectedReports = overviews.findAll { tbHivPlusIds.contains(it.id) }
            .sort { (ui.message(it.label) ?: it.id ?: "").toLowerCase() }
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("reportingui.reportsapp.home.title") }", link: "${ ui.pageLink("reportingui", "reportsapp/home") }" }
    ];
</script>

<script type="text/javascript">
(function(){
    var m = document.createElement('meta');
    m.setAttribute('name','viewport');
    m.setAttribute('content','width=device-width, initial-scale=1.0');
    document.head.appendChild(m);
})();
</script>

<style type="text/css">
.reportBox {
    margin-top: 20px;
    padding: 0;
    background: transparent;
}
.reportBox .sectionTitle {
    color: #0b7a7a;
    font-weight: 600;
    font-size: 18px;
    padding: 8px 4px;
    margin: 0 0 12px 0;
    border-bottom: 2px solid #e6eaec;
}
.reportBox .subTitle {
    color: #34495e;
    font-weight: 600;
    font-size: 15px;
    padding: 6px 4px;
    margin: 14px 0 8px 0;
    border-left: 4px solid #0b7a7a;
    cursor: pointer;
    user-select: none;
    position: relative;
}
.reportBox .subTitle::after {
    content: "▾";
    color: #7a8691;
    font-size: 13px;
    margin-left: 8px;
}
.reportBox .subTitle.isCollapsed::after {
    content: "▸";
}
.reportBox .listContainer {
    display: flex;
    flex-direction: column;
    gap: 6px;
    margin: 0 0 10px 0;
    padding: 10px;
    background: #f8fafc;
    border: 1px solid #e5e7eb;
    border-radius: 6px;
    counter-reset: item;
}
.reportBox .listItem {
    display: flex;
    align-items: center;
    background: #ffffff;
    border-radius: 0;
    padding: 12px 14px;
    border: 1px solid #e5e7eb;
}
.reportBox .listItem::before {
    counter-increment: item;
    content: counter(item);
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 22px;
    height: 22px;
    min-width: 22px;
    border-radius: 9999px;
    background: #0b7a7a;
    color: #ffffff;
    font-size: 12px;
    font-weight: 600;
    margin-right: 10px;
    line-height: 1;
    font-variant-numeric: tabular-nums;
}
.reportBox .listItem:hover {
    background: #f9fbfc;
}
.reportBox .listItem a {
    display: block;
    color: #1f2937;
    text-decoration: none;
    font-weight: 500;
    width: 100%;
}
.reportBox .listItem a:hover {
    color: #0b7a7a;
}
.searchBar {
    display: flex;
    align-items: center;
    gap: 8px;
    margin: 6px 0 16px 0;
    width: 100%;
}
.searchBar input[type="search"] {
    width: 100%;
    padding: 8px 10px;
    border: 1px solid #e5e7eb;
    border-radius: 0;
    font-size: 14px;
    box-sizing: border-box;
}
.searchBar input[type="search"]:focus {
    outline: none;
    border-color: #0b7a7a;
}
.searchField {
    position: relative;
    display: flex;
    align-items: center;
    width: 100%;
}
.searchIcon {
    position: absolute;
    left: 10px;
    top: 50%;
    transform: translateY(-50%);
    color: #0b7a7a;
    display: inline-flex;
    align-items: center;
    justify-content: center;
}
.searchIcon svg {
    width: 16px;
    height: 16px;
}
.searchField input[type="search"] {
    padding-left: 34px;
    padding-right: 32px;
}
.clearBtn {
    position: absolute;
    right: 8px;
    top: 50%;
    transform: translateY(-50%);
    background: transparent;
    border: 0;
    color: #7a8691;
    font-size: 18px;
    line-height: 1;
    cursor: pointer;
    display: none;
}
.searchField.hasValue .clearBtn {
    display: inline-flex;
}
@media (max-width: 748px) {
    .reportBox {
        padding: 0;
        width:auto;
    }
    .reportBox .sectionTitle {
        padding: 8px 2px;
        font-size: 16px;
    }
    .reportBox .subTitle {
        padding: 6px 2px;
        font-size: 14px;
    }
    .reportBox .listItem {
        padding: 10px 12px;
    }
    .reportBox .listContainer {
        padding: 8px;
        border-radius: 6px;
    }
    .reportBox .listItem::before {
        width: 20px;
        height: 20px;
        min-width: 20px;
        font-size: 11px;
        margin-right: 8px;
    }
}
</style>
<div class="searchBar">
    <div class="searchField" id="reportSearchField">
        <span class="searchIcon" aria-hidden="true">
            <svg viewBox="0 0 24 24" fill="none">
                <path d="M11 4a7 7 0 105.29 12.29l3.7 3.7a1 1 0 001.42-1.42l-3.7-3.7A7 7 0 0011 4zm0 2a5 5 0 110 10A5 5 0 0111 6z" fill="currentColor"/>
            </svg>
        </span>
        <input type="search" id="reportSearchInput" placeholder="Recherche de rapports" aria-label="Search reports">
        <button type="button" id="reportSearchClear" class="clearBtn" aria-label="Clear search">×</button>
    </div>
</div>

<script type="text/javascript">
document.addEventListener('DOMContentLoaded', function() {
    var input = document.getElementById('reportSearchInput');
    var field = document.getElementById('reportSearchField');
    var clear = document.getElementById('reportSearchClear');
    if (!input) return;
    var boxes = Array.prototype.slice.call(document.querySelectorAll('.reportBox'));
    function updateUI() {
        var has = input.value.trim().length > 0;
        if (field) {
            if (has) { field.classList.add('hasValue'); }
            else { field.classList.remove('hasValue'); }
        }
    }
    function applyFilter() {
        var q = input.value.toLowerCase().trim();
        updateUI();
        boxes.forEach(function(box) {
            var boxVisibleCount = 0;
            var lists = Array.prototype.slice.call(box.querySelectorAll('.listContainer'));
            lists.forEach(function(list) {
                var groupVisibleCount = 0;
                var items = Array.prototype.slice.call(list.querySelectorAll('.listItem'));
                items.forEach(function(item) {
                    var text = item.textContent.toLowerCase();
                    var match = q.length === 0 || text.indexOf(q) !== -1;
                    item.style.display = match ? '' : 'none';
                    if (match) groupVisibleCount++;
                });
                var prev = list.previousElementSibling;
                if (prev && prev.classList && prev.classList.contains('subTitle')) {
                    if (q.length === 0) {
                        prev.style.display = '';
                        list.style.display = prev.classList.contains('isCollapsed') ? 'none' : '';
                    } else {
                        var showGroup = groupVisibleCount > 0;
                        prev.style.display = showGroup ? '' : 'none';
                        if (showGroup) prev.classList.remove('isCollapsed');
                        list.style.display = showGroup ? '' : 'none';
                    }
                } else {
                    list.style.display = (q.length === 0 || groupVisibleCount > 0) ? '' : 'none';
                }
                boxVisibleCount += groupVisibleCount;
            });
            box.style.display = (q.length === 0 || boxVisibleCount > 0) ? '' : 'none';
        });
    }
    (function initCollapse() {
        var subs = Array.prototype.slice.call(document.querySelectorAll('.reportBox .subTitle'));
        subs.forEach(function(sub) {
            sub.classList.add('isCollapsed');
            var next = sub.nextElementSibling;
            if (next && next.classList && next.classList.contains('listContainer')) {
                next.style.display = 'none';
            }
            sub.addEventListener('click', function() {
                var target = sub.nextElementSibling;
                if (!target || !target.classList || !target.classList.contains('listContainer')) return;
                var collapsed = sub.classList.toggle('isCollapsed');
                if (input.value.trim().length === 0) {
                    target.style.display = collapsed ? 'none' : '';
                } else {
                    target.style.display = collapsed ? 'none' : '';
                }
            });
        });
    })();
    input.addEventListener('input', applyFilter);
    if (clear) {
        clear.addEventListener('click', function() {
            input.value = '';
            applyFilter();
        });
    }
    applyFilter();
});
</script>


<div class="reportBox">
    <p class="sectionTitle">Gestion de programme</p>
    <% if (frequentationReports) { %>
    <p class="subTitle">Fréquentation de l’institution</p>
    <div class="listContainer">
        <% frequentationReports.each { %>
        <div class="listItem">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </div>
        <% } %>
    </div>
    <% } %>

    <% if (patientsStatus) { %>
    <p class="subTitle">${ ui.message("isanteplusreports.reportsapp.patientsStatus") }</p>
    <div class="listContainer">
        <% patientsStatus.each { %>
        <div class="listItem">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </div>
        <% } %>
    </div>
    <% if (pharmacyReports) { %>
    <p class="subTitle">Pharmacie</p>
    <div class="listContainer">
        <% pharmacyReports.each { %>
        <div class="listItem">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </div>
        <% } %>
    </div>
    <% } %>
    <% } %>

    <% if (dataQualitySelectedReports) { %>
    <p class="subTitle">${ ui.message("reportingui.reportsapp.dataQualityReports") }</p>
    <div class="listContainer">
        <% dataQualitySelectedReports.each { %>
        <div class="listItem">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </div>
        <% } %>
    </div>
    <% } %>
</div>

<div class="reportBox">
    <p class="sectionTitle">VIH</p>
    <% if (arvCareSelectedReports) { %>
    <p class="subTitle">Soins ARV</p>
    <div class="listContainer">
        <% arvCareSelectedReports.each { %>
        <div class="listItem">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </div>
        <% } %>
    </div>
    <% } %>

    <% if (viralLoadSelectedReports) { %>
    <p class="subTitle">Suivi de la charge virale</p>
    <div class="listContainer">
        <% viralLoadSelectedReports.each { %>
        <div class="listItem">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </div>
        <% } %>
    </div>
    <% } %>

    <% if (ptmeSelectedReports) { %>
    <p class="subTitle">${ ui.message("isanteplusreports.reportsapp.ptme") }</p>
    <div class="listContainer">
        <% ptmeSelectedReports.each { %>
        <div class="listItem">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </div>
        <% } %>
    </div>
    <% } %>

    <% if (tbHivSelectedReports) { %>
    <p class="subTitle">TB/VIH</p>
    <div class="listContainer">
        <% tbHivSelectedReports.each { %>
        <div class="listItem">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </div>
        <% } %>
    </div>
    <% } %>
    <% if (tbHivPlusSelectedReports) { %>
    <p class="subTitle">Tuberculose</p>
    <div class="listContainer">
        <% tbHivPlusSelectedReports.each { %>
        <div class="listItem">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </div>
        <% } %>
    </div>
    <% } %>
</div>

<div class="reportBox">
    <% if (comorbidity) { %>
    <p class="sectionTitle">Maladies non transmissibles</p>
    <div class="listContainer">
        <% comorbidity.each { %>
        <div class="listItem">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </div>
        <% } %>
    </div>
    <% } %>

    <% if (psychosocial) { %>
    <p class="subTitle">Santé mentale</p>
    <div class="listContainer">
        <% psychosocial.each { %>
        <div class="listItem">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </div>
        <% } %>
    </div>
    <% } %>

    <% if (antenatal) { %>
    <p class="subTitle">Santé reproductive, maternelle, du nouveau-né, de l’enfant et de l’adolescent</p>
    <div class="listContainer">
        <% antenatal.each { %>
        <div class="listItem">
            ${ ui.includeFragment("uicommons", "extension", [ extension: it, contextModel: contextModel ]) }
        </div>
        <% } %>
    </div>
    <% } %>
</div>
