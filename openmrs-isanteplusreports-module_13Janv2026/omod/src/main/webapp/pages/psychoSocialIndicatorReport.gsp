<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeJavascript("isanteplusreports", "healthQualExportToExcel.js")
%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>

<script type="text/javascript">
    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {
            label: "${ ui.escapeJs(ui.message("reportingui.reportsapp.home.title")) }",
            link: emr.pageLink("reportingui", "reportsapp/home")
        },
        {label: "${ ui.message("isanteplusreports.psychosocial.indicator.label") }", link: '${ ui.thisUrl() }'}
    ];
    var jq = jQuery;

    jq(document).ready(function () {

        // Checkbox principal : select all
        jq('#check').on('change', function () {
            var isChecked = jq(this).is(':checked');

            // Cocher / décocher tous les autres checkbox du formulaire
            jq('#indicatorsForm input[type="checkbox"]')
                .not('#check')
                .prop('checked', isChecked);
        });

        // Récupérer les dates au submit du formulaire
        jq('#indicatorsForm').on('submit', function () {
            e.preventDefault();

            // Champs générés par datetimepicker OpenMRS
            var startDate = jq('input[name="startDate"]').val();
            var endDate = jq('input[name="endDate"]').val();

            console.log("Start Date :", startDate);
            console.log("End Date :", endDate);

            // Récupérer les indicateurs cochés
            var selectedIndicators = [];

            jq('#indicatorsForm input[type="checkbox"]:checked')
                .not('#check')
                .each(function () {
                    selectedIndicators.push(jq(this).attr('name'));
                });

            console.log("Indicateurs sélectionnés :", selectedIndicators);

            // 3️⃣ Payload (CLÉ = nom attendu par le controller)
            var payload = {
                startDate: startDate,
                endDate: endDate,
                selectedIndicators: selectedIndicators
            };

            // 4️⃣ AJAX POST
            jq.ajax({
                url: "${ ui.pageLink('isanteplusreports','psychosocialIndicatorReport',null) }",
                type: "POST",
                data: payload,              // x-www-form-urlencoded
                traditional: true,          // ⭐ TRÈS IMPORTANT pour les List
                success: function () {
                    jq().toastmessage(
                        'showSuccessToast',
                        "Rapport lancé avec succès !"
                    );
                },
                error: function (xhr) {
                    console.error(xhr);
                    jq().toastmessage(
                        'showErrorToast',
                        "Erreur (" + xhr.status + ")"
                    );
                }
            });

            // return false; // <-- seulement si tu veux bloquer l’envoi
        });


        jq("#btnPdf").on("click", function () {

            var startDateRaw = jq('input[name="startDate"]').val();
            var endDateRaw = jq('input[name="endDate"]').val();

            var startDateObj = parseLocalDate(startDateRaw);
            var endDateObj   = parseLocalDate(endDateRaw);

            var startDate = formatDateFr(startDateObj);
            var endDate   = formatDateFr(endDateObj);

            // 1️⃣ Header PDF uniquement
            var pdfOnlyContent =
                '<div style="font-size:12px; margin-bottom:10px;">' +
                '<h1 style="text-align:center;">Rapport Psychosocial</h1>' +
                '<p style="text-align:center;"><strong>Période :</strong> ' + startDate + ' → ' + endDate + '</p>' +
                '<hr/>' +
                '</div><br/>';

            // 2️⃣ Contenu du rapport (clone pour ne pas toucher au DOM)
            var reportElement = document.getElementById("reportContent");
            var reportClone = reportElement.cloneNode(true);
            reportClone.querySelectorAll("table").forEach(function (table) {
                table.style.width = "100%";
                table.style.tableLayout = "fixed";
            });

            // 3️⃣ Conteneur temporaire PDF
            var pdfContainer = document.createElement("div");
            pdfContainer.style.padding = "10px";
            pdfContainer.innerHTML = pdfOnlyContent;
            pdfContainer.appendChild(reportClone);

            // 4️⃣ Options PDF
            var options = {
                margin: 0.5,
                filename: "rapport_psychosocial.pdf",
                image: { type: "jpeg", quality: 1 },
                html2canvas: { scale: 4, logging: true },
                jsPDF: { unit: "in", format: "a4", orientation: "portrait" }
            };

            // 5️⃣ Génération PDF
            html2pdf().set(options).from(pdfContainer).save();
        });


        jq("#btnExcel").on("click", function () {

            var startDateRaw = jq('input[name="startDate"]').val();
            var endDateRaw = jq('input[name="endDate"]').val();

            var startDateObj = parseLocalDate(startDateRaw);
            var endDateObj   = parseLocalDate(endDateRaw);

            var startDate = formatDateFr(startDateObj);
            var endDate   = formatDateFr(endDateObj);

            // 📘 Nouveau classeur
            var wb = XLSX.utils.book_new();

            // ===============================
            // 🟦 FEUILLE 1 : Infos générales
            // ===============================
            var infoData = [
                ["Rapport Psychosocial"],
                [],
                ["Période", startDate + " → " + endDate],
                [],
            ];

            var wsInfo = XLSX.utils.aoa_to_sheet(infoData);
            XLSX.utils.book_append_sheet(wb, wsInfo, "Infos");

            // ===============================
            // 🟩 FEUILLE 2 : Résultats
            // ===============================
            var tables = document.querySelectorAll("#reportContent table");

            var excelRows = [];

            tables.forEach(function (table, index) {

                var title = table.querySelector("thead th")?.innerText || ("Indicateur " + (index + 1));

                // Titre indicateur
                excelRows.push([title]);
                excelRows.push([]);

                // Parcours lignes
                table.querySelectorAll("tr").forEach(function (tr) {
                    var row = [];
                    tr.querySelectorAll("th, td").forEach(function (cell) {
                        row.push(cell.innerText.trim());
                    });
                    excelRows.push(row);
                });

                excelRows.push([]);
                excelRows.push([]);
            });

            var wsData = XLSX.utils.aoa_to_sheet(excelRows);

            // Ajustement largeur colonnes
            wsData["!cols"] = [
                { wch: 20 },
                { wch: 20 },
                { wch: 20 },
                { wch: 20 },
                { wch: 20 },
                { wch: 20 }
            ];

            XLSX.utils.book_append_sheet(wb, wsData, "Résultats");

            // 💾 Téléchargement
            XLSX.writeFile(wb, "rapport_psychosocial.xlsx");
        });


        jq(".openDetached").on("click", function (e) {
            e.preventDefault();

            var url = jq(this).attr("href");

            window.open(
                url,
                "_blank",
                "width=1500,height=700,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,status=no"
            );
        });

        function formatDateFr(dateStr) {
            if (!dateStr) return "";

            const date = new Date(dateStr);

            return date.toLocaleDateString('fr-FR', {
                day: 'numeric',
                month: 'long',
                year: 'numeric'
            });
        }
        function parseLocalDate(dateStr) {
            if (!dateStr) return null;

            const parts = dateStr.split("-"); // YYYY-MM-DD
            return new Date(
                parseInt(parts[0], 10),
                parseInt(parts[1], 10) - 1,
                parseInt(parts[2], 10)
            );
        }

    });

</script>


<style type="text/css">

.clearfix::after {
    content: "";
    clear: both;
    display: table;
}

.indicatorsHeader {
    text-align: center;
    background-color: #fefad3;
    font-style: italic;
}

#indicators thead th {
    background-color: #fdf59a;
}

#divWithReportTables th, #divWithReportTables td {
    padding: 5pt;
    border-color: black;
    border-style: solid;
    border-top-width: 1px;
    border-left-width: 1px;
    border-right-width: 0px;
    border-bottom-width: 0px;
}

#divWithReportTables th:last-child, #divWithReportTables td:last-child {
    border-right-width: 1px;
}

#divWithReportTables tr:last-child th, #divWithReportTables tr:last-child td {
    border-bottom-width: 1px;
}

#divWithReportTables th, #divWithReportTables td {
    white-space: normal;
}

#divWithReportTables table {
    border-collapse: separate;
    border-spacing: 0;
    empty-cells: hide;
    margin: 10pt auto;
    width: auto;
}

.centered-table th,
.centered-table td {
    text-align: center;
    vertical-align: middle;
}

@media print {
    body {
        background: white;
    }

    a {
        text-decoration: none;
        color: black;
    }

    table {
        page-break-inside: avoid;
        font-size: 11px;
    }
}

@media print {
    button {
        display: none
    }
}

#btnPdf {
    margin-bottom: 10px;
    background: #efefef;
    font-size: 14px;
    padding: 8px 10px;
}
#btnExcel{
    margin-bottom: 10px;
    margin-left: 8px;
    background: #efefef;
    font-size: 14px;
    padding: 8px 10px;
}

/* ===== PDF FIX TABLE ===== */
#reportContent table {
    width: 100% !important;
    table-layout: fixed !important;
}

#reportContent th,
#reportContent td {
    word-break: break-word !important;
    overflow-wrap: break-word !important;
    white-space: normal !important;
    font-size: 14px;
    padding: 4px;
}

#reportContent th[colspan] {
    text-align: center;
}

</style>

<div>
    <h1>${ui.message("isanteplusreports.psychosocial.indicator.label")}</h1>

    <div class="running-reports">

        <form id="indicatorsForm" class="clearfix" method="post">
            <fieldset id="run-report" style="float:right; min-width: 450px; margin-left: 10px;">
                <legend>
                    ${ui.message("reportingui.runReport.run.legend")}
                </legend>

                <p><input type="checkbox" id="check" name="check">${ui.message("isanteplusreports.pnls.select.all")}</p>

                <p id="parameterSection">
                    ${ui.includeFragment("uicommons", "field/datetimepicker", ["id": "startDateField", "label": "From Date", "formFieldName": "startDate", "defaultDate": startDate, "useTime": false])}
                    ${ui.includeFragment("uicommons", "field/datetimepicker", ["id": "endDateField", "label": "To Date", "formFieldName": "endDate", "defaultDate": endDate, "useTime": false])}

                <p>
                    <button id="submit" type="submit">${ui.message("reportingui.runButtonLabel")}</button>
            </fieldset>

            <table id="indicators" style="display: block; width: 100%; padding-top: 11px;">
                <thead>
                <tr>
                    <th style="width:100%">${ui.message("isanteplusreports.psychosocial.indicator.label")}</th>
                    <th>${ui.message("isanteplusreports.healthqual.selection.label")}</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th class="indicatorsHeader" colspan="2">
                        VIH
                    </th>
                </tr>
                <% indicatorList.each { indicator -> %>
                <tr>
                    <td>${ui.message(indicator.label)}</td>
                    <td>
                        <input type="checkbox"
                               class="indicator-check"
                               name="selectedIndicators"
                               value="${indicator.uuid}"/>

                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </form>
    </div>
</div>

<br/>
<br/>


<% if (psychosocialReportResults) { %>

<div id="" style="width: 70%; margin-left: auto; margin-right: auto">

    <button type="button" id="btnPdf">📄 Exporter PDF</button>
    <button type="button" id="btnExcel">📊 Exporter Excel</button>

    <div id="reportContent" style="width: 100%; margin-left: auto; margin-right: auto">
        <% psychosocialReportResults.each { indicator -> %>
        <table class="centered-table">
            <thead>
            <tr>
                <th style="background: #3f5874; color: #fff" colspan="6">${ui.message(indicator.indicatorLabel)}</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td colspan="3" style="font-weight: bold">SSP</td>
                <td colspan="3" style="font-weight: bold">VIH</td>
            </tr>
            <tr>
                <td>Numérateur</td>
                <td>Dénominateur</td>
                <td>Pourcentage %</td>
                <td>Numérateur</td>
                <td>Dénominateur</td>
                <td>Pourcentage %</td>
            </tr>
            <tr>
                <td>
                    <a href="${ui.pageLink('isanteplusreports', 'psychosocialIndicatorReportPatientList',
                            [ids: indicator.patientListNume.collect { it.id }.join(',')])}"
                       class="openDetached">
                        ${indicator.patientListNume.size()}
                    </a>
                </td>
                <td>
                    <a href="${ui.pageLink('isanteplusreports','psychosocialIndicatorReportPatientList',
                            [ids: indicator.patientListDeno.collect { it.id }.join(',')])}"
                       class="openDetached">
                        ${indicator.patientListDeno.size()}
                    </a>
                </td>
                <td>${indicator.indicatorPercent}</td>

                <td>
                    <a href="${ui.pageLink('isanteplusreports','psychosocialIndicatorReportPatientList',
                            [ids: indicator.patientListNume.collect { it.id }.join(',')])}"
                       class="openDetached">
                        ${indicator.patientListNume.size()}
                    </a>
                </td>
                <td>
                    <a href="${ui.pageLink('isanteplusreports','psychosocialIndicatorReportPatientList',
                            [ids: indicator.patientListDeno.collect { it.id }.join(',')])}"
                       class="openDetached">
                        ${indicator.patientListDeno.size()}
                    </a>
                </td>
                <td>${indicator.indicatorPercent}</td>
            </tr>
            </tbody>
        </table>
        <br/>
        <% } %>
    </div>
</div>
<% } %>





