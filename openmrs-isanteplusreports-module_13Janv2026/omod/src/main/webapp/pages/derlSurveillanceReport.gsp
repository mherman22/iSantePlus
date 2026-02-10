<%
    ui.decorateWith("appui", "standardEmrPage")
%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>

<script>
    var cohortIndicatorList1 = ${ui.toJson(cohortIndicators1)};
    var cohortIndicatorList2 = ${ui.toJson(cohortIndicators2)};
    var cohortIndicatorList3 = ${ui.toJson(cohortIndicators3)};
</script>

<script type="text/javascript" charset="UTF-8">
    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {icon: "icon-dashboard", label: "${ ui.message("isanteplusreports.derl.surveillance.report") }", link: '${ ui.thisUrl() }'}
    ];

    var jq = jQuery;

    jq(document).ready(function () {

        const tabs = document.querySelectorAll(".tab");
        const contents = document.querySelectorAll(".content");


        for (let i = 0; i < cohortIndicatorList1.length; i++) {

            var indicatorList = cohortIndicatorList1[i].indicatorList;

            if(indicatorList.length>0) {
                for (let j = 0; j < indicatorList.length; j++) {
                    var indicator = indicatorList[j];

                    console.log("indicator ::: ", indicator)

                    var item = indicator.indicatorDate;
                    var jsDate = new Date(item.year, item.monthValue - 1, item.dayOfMonth);

                    // Formater en yyyy-MM-DD
                    var yyyy = jsDate.getFullYear();
                    var mm = String(jsDate.getMonth() + 1).padStart(2, '0'); // mois 2 chiffres
                    var dd = String(jsDate.getDate()).padStart(2, '0');        // jour 2 chiffre

                    indicator.indicatorDate = yyyy + '-' + mm + '-' + dd;
                }
            }
        }

        for (let i = 0; i < cohortIndicatorList2.length; i++) {

            var indicatorList = cohortIndicatorList2[i].indicatorList;

            if(indicatorList.length>0) {
                for (let j = 0; j < indicatorList.length; j++) {
                    var indicator = indicatorList[j];

                    console.log("indicator ::: ", indicator)

                    var item = indicator.indicatorDate;
                    var jsDate = new Date(item.year, item.monthValue - 1, item.dayOfMonth);

                    // Formater en yyyy-MM-DD
                    var yyyy = jsDate.getFullYear();
                    var mm = String(jsDate.getMonth() + 1).padStart(2, '0'); // mois 2 chiffres
                    var dd = String(jsDate.getDate()).padStart(2, '0');        // jour 2 chiffre

                    indicator.indicatorDate = yyyy + '-' + mm + '-' + dd;
                }
            }
        }

        for (let i = 0; i < cohortIndicatorList3.length; i++) {

            var indicatorList = cohortIndicatorList3[i].indicatorList;

            if(indicatorList.length>0) {
                for (let j = 0; j < indicatorList.length; j++) {
                    var indicator = indicatorList[j];

                    console.log("indicator ::: ", indicator)

                    var item = indicator.indicatorDate;
                    var jsDate = new Date(item.year, item.monthValue - 1, item.dayOfMonth);

                    // Formater en yyyy-MM-DD
                    var yyyy = jsDate.getFullYear();
                    var mm = String(jsDate.getMonth() + 1).padStart(2, '0'); // mois 2 chiffres
                    var dd = String(jsDate.getDate()).padStart(2, '0');        // jour 2 chiffre

                    indicator.indicatorDate = yyyy + '-' + mm + '-' + dd;
                }
            }
        }


        function getWeekNumber(date) {
            const d = new Date(Date.UTC(date.getFullYear(), date.getMonth(), date.getDate()));
            const dayNum = d.getUTCDay() || 7; // Dimanche -> 7
            d.setUTCDate(d.getUTCDate() + 4 - dayNum); // Aller au jeudi de la semaine
            const yearStart = new Date(Date.UTC(d.getUTCFullYear(), 0, 1));
            const weekNo = Math.ceil((((d - yearStart) / 86400000) + 1) / 7);
            return weekNo;
        }

        function groupIndicatorsByWeek(indicators) {
            const weekMap = {};

            indicators.forEach(ind => {
                const date = new Date(ind.indicatorDate);
                const week = getWeekNumber(date);

                if (!weekMap[week]) {
                    weekMap[week] = {
                        count: 0,
                        patients: []
                    };
                }

                weekMap[week].count++;
                weekMap[week].patients.push(ind.patientId);
            });

            return weekMap;
        }


        function reportTable(tableHeaderID, tableBodyID, columnCount, indicatorList) {

            const tableHeader = document.getElementById(tableHeaderID);
            const tableBody = document.getElementById(tableBodyID);

            // 🟦 En-tête semaines
            for (let w = 1; w <= columnCount; w++) {
                const th = document.createElement("th");
                th.textContent = "" + w;
                th.style.textAlign = "center"
                tableHeader.appendChild(th);
            }

            // ➕ En-tête Total
            const thTotal = document.createElement("th");
            thTotal.textContent = "Total";
            thTotal.style.width = "20px";
            thTotal.style.backgroundColor = "#718daf";
            thTotal.style.color = "#fff";
            thTotal.style.borderColor = "#fff";
            thTotal.style.borderRadius = "0px 8px 0px 0px";
            tableHeader.appendChild(thTotal);

            // 🟩 Lignes indicateurs
            indicatorList.forEach(indicator => {
                const tr = document.createElement("tr");
                tr.style.backgroundColor = "#fff";

                // Colonne label
                const th = document.createElement("th");
                th.textContent = indicator.label;
                th.classList.add("sticky-col");
                tr.appendChild(th);

                // Regroupement par semaine
                const weekData = groupIndicatorsByWeek(indicator.indicatorList);

                let total = 0;
                let allPatients = [];

                for (let w = 1; w <= columnCount; w++) {
                    const td = document.createElement("td");

                    if (weekData[w]) {
                        total += weekData[w].count;
                        allPatients.push(...weekData[w].patients);

                        const patientIds = weekData[w].patients.join(",");
                        const link = document.createElement("a");

                        link.href = "/" + OPENMRS_CONTEXT_PATH +
                            "/isanteplusreports/derlSurveillanceReportPatientList.page" +
                            "?ids=" + encodeURIComponent(patientIds);

                        link.textContent = weekData[w].count;
                        link.classList.add("openDetached");
                        link.style.fontWeight = "bold";
                        link.style.fontSize = "14px";

                        td.appendChild(link);
                        td.classList.add("clickable");

                    } else {
                        td.textContent = "";
                    }

                    tr.appendChild(td);
                }

                // 🟨 Colonne Totale (MÊME COMPORTEMENT QUE LES SEMAINES)
                const tdTotal = document.createElement("td");

                // 👉 dédoublonnage recommandé
                const allPatientIds = [...new Set(allPatients)].join(",");

                const totalLink = document.createElement("a");
                totalLink.href = "/" + OPENMRS_CONTEXT_PATH +
                    "/isanteplusreports/derlSurveillanceReportPatientList.page" +
                    "?ids=" + encodeURIComponent(allPatientIds);

                totalLink.textContent = total;
                totalLink.classList.add("openDetached");
                totalLink.style.fontWeight = "bold";
                totalLink.style.fontSize = "14px";

                if (!allPatientIds) {
                    totalLink.style.pointerEvents = "none";
                    totalLink.style.color = "#8d8d8d";
                }

                tdTotal.style.backgroundColor = "#f5fbfe";
                tdTotal.style.width = "20px";

                tdTotal.appendChild(totalLink);
                tdTotal.classList.add("clickable");

                tr.appendChild(tdTotal);

                tableBody.appendChild(tr);
            });
        }


        function groupIndicatorsByMonth(indicatorList) {
            const monthData = {};

            indicatorList.forEach(item => {
                const date = new Date(item.indicatorDate); // adapte le champ si besoin
                const month = date.getMonth() + 1; // 1 = JANVIER, 12 = DÉCEMBRE

                if (!monthData[month]) {
                    monthData[month] = {
                        count: 0,
                        patients: []
                    };
                }

                monthData[month].count++;
                monthData[month].patients.push(item.patientId); // ou item.patient
            });

            return monthData;
        }

        function reportTableMonth(tableHeaderID, tableBodyID, indicatorList) {

            const tableHeader = document.getElementById(tableHeaderID);
            const tableBody = document.getElementById(tableBodyID);

            const tableMonth = [
                "JANVIER",
                "FÉVRIER",
                "MARS",
                "AVRIL",
                "MAI",
                "JUIN",
                "JUILLET",
                "AOÛT",
                "SEPTEMBRE",
                "OCTOBRE",
                "NOVEMBRE",
                "DÉCEMBRE"
            ];

            // 🟦 En-tête mois
            for (let w = 0; w < tableMonth.length; w++) {
                const th = document.createElement("th");
                th.textContent = tableMonth[w];
                th.style.textAlign = "center";
                tableHeader.appendChild(th);
            }

            // ➕ En-tête Total
            const thTotal = document.createElement("th");
            thTotal.textContent = "Total";
            thTotal.style.width = "20px";
            thTotal.style.backgroundColor = "#718daf";
            thTotal.style.color = "#fff";
            thTotal.style.borderColor = "#fff";
            thTotal.style.borderRadius = "0px 8px 0px 0px";
            tableHeader.appendChild(thTotal);

            // 🟩 Lignes indicateurs
            indicatorList.forEach(indicator => {
                const tr = document.createElement("tr");
                tr.style.backgroundColor = "#fff";

                // Colonne label
                const th = document.createElement("th");
                th.textContent = indicator.label;
                th.classList.add("sticky-col");
                tr.appendChild(th);

                // Regroupement par mois
                const monthData = groupIndicatorsByMonth(indicator.indicatorList);

                let total = 0;
                let allPatients = [];

                for (let w = 1; w <= tableMonth.length; w++) {
                    const td = document.createElement("td");

                    if (monthData[w]) {
                        total += monthData[w].count;
                        allPatients.push(...monthData[w].patients);

                        const patientIds = monthData[w].patients.join(",");
                        const link = document.createElement("a");

                        link.href = "/" + OPENMRS_CONTEXT_PATH +
                            "/isanteplusreports/derlSurveillanceReportPatientList.page" +
                            "?ids=" + encodeURIComponent(patientIds);

                        link.textContent = monthData[w].count;
                        link.classList.add("openDetached");
                        link.style.fontWeight = "bold";
                        link.style.fontSize = "14px";

                        td.appendChild(link);
                        td.classList.add("clickable");
                    } else {
                        td.textContent = "";
                    }

                    tr.appendChild(td);
                }

                // 🟨 Colonne Totale (MÊME COMPORTEMENT QUE LES MOIS)
                const tdTotal = document.createElement("td");

                // 👉 dédoublonnage
                const allPatientIds = [...new Set(allPatients)].join(",");

                const totalLink = document.createElement("a");
                totalLink.href = "/" + OPENMRS_CONTEXT_PATH +
                    "/isanteplusreports/derlSurveillanceReportPatientList.page" +
                    "?ids=" + encodeURIComponent(allPatientIds);

                totalLink.textContent = total;
                totalLink.classList.add("openDetached");
                totalLink.style.fontWeight = "bold";
                totalLink.style.fontSize = "14px";

                if (!allPatientIds) {
                    totalLink.style.pointerEvents = "none";
                    totalLink.style.color = "#8d8d8d";
                }

                tdTotal.style.backgroundColor = "#f5fbfe";
                tdTotal.style.width = "20px";

                tdTotal.appendChild(totalLink);
                tdTotal.classList.add("clickable");

                tr.appendChild(tdTotal);
                tableBody.appendChild(tr);
            });
        }



        function enableCrossPartialHover(tableId) {
            const table = document.getElementById(tableId);
            const rows = table.tBodies[0].rows;

            Array.from(rows).forEach((row, rowIndex) => {
                Array.from(row.cells).forEach((cell, colIndex) => {

                    // Ignore la colonne label si besoin
                    if (cell.tagName !== "TD") return;

                    cell.addEventListener("mouseenter", () => {

                        // 🔵 Ligne : de 0 → colonne courante
                        for (let c = 0; c <= colIndex; c++) {
                            row.cells[c]?.classList.add("cross-hover");
                        }

                        // 🟢 Colonne : de 0 → ligne courante
                        for (let r = 0; r <= rowIndex; r++) {
                            rows[r].cells[colIndex]?.classList.add("cross-hover");
                        }

                        // ⭐ cellule courante
                        cell.classList.add("current-cell");
                    });

                    cell.addEventListener("mouseleave", () => {

                        // Nettoyage total
                        Array.from(rows).forEach(r =>
                            Array.from(r.cells).forEach(c =>
                                c.classList.remove("cross-hover", "current-cell")
                            )
                        );
                    });
                });
            });
        }

        reportTable("tableHeaderImmediate", "tableBodyImmediate", 53, cohortIndicatorList1);
        reportTable("tableHeaderWeek", "tableBodyWeek", 53, cohortIndicatorList2);
        reportTableMonth("tableHeaderMonth", "tableBodyMonth", cohortIndicatorList3);

        enableCrossPartialHover("indicatorTable1");
        enableCrossPartialHover("indicatorTable2");
        enableCrossPartialHover("indicatorTable3");

        jq(document).on("click", "a.openDetached", function (e) {
            e.preventDefault();

            var url = jq(this).attr("href");

            if (!url) return;

            window.open(
                url,
                "_blank",
                "width=1500,height=700,resizable=yes,scrollbars=yes,toolbar=no,menubar=no,location=no,status=no"
            );
        });


        tabs.forEach(tab => {
            tab.addEventListener("click", () => {
                // Retirer active partout
                tabs.forEach(t => t.classList.remove("active"));
                contents.forEach(c => c.classList.remove("active"));

                // Activer l'onglet cliqué
                tab.classList.add("active");
                const target = tab.getAttribute("data-tab");
                document.getElementById(target).classList.add("active");
            });
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
                '<h1 style="text-align:center;">Rapport de Comorbidité</h1>' +
                '<p style="text-align:center;"><strong>Période :</strong> ' + startDate + ' → ' + endDate + '</p>' +
                '<hr/>' +
                '</div><br/>';

            // 2️⃣ Contenu du rapport (clone pour ne pas toucher au DOM)
            var reportElement = document.getElementById("indicatorTable1");
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
                filename: "rapport_surveillance.pdf",
                image: { type: "jpeg", quality: 1 },
                html2canvas: { scale: 4, logging: true },
                jsPDF: { unit: "in", format: "a4", orientation: "landscape" }
            };

            // 5️⃣ Génération PDF
            html2pdf().set(options).from(pdfContainer).save();
        });


    });


</script>

<style type="text/css">
body {
    font-family: Arial, sans-serif;
}

/*body {*/
/*    font-family: Inter, system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial;*/
/*    background: #f7f8fa;*/
/*    color: #0f172a;*/
/*    margin: 0;*/
/*    padding: 0;*/
/*}*/


.tabs {
    width: 100%;
    margin: 15px auto;
}

.tab-list {
    display: flex;
    gap: 8px;
    list-style: none;
    padding: 6px;
    margin: 0 0 10px 0;
    background: #f4f6f8;
    border-radius: 10px;
}

.tab {
    padding: 10px 20px;
    cursor: pointer;
    font-weight: 600;
    color: #555;
    border-radius: 8px;
    transition: all 0.25s ease;
}

.tab:hover {
    background: #e1e6eb;
}

.tab.active {
    background: #7a98bd;
    color: #fff;
    box-shadow: 0 4px 10px rgba(0, 123, 255, 0.20);
    animation: fadeIn 0.3s ease-in;
    display: block;
}


@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(1px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.tab-content {
    padding: 20px;
    border: 1px solid #e2e6ea;
    border-radius: 10px;
    background: #fff;
}

.content {
    display: none;
}

.content.active {
    display: block;
}


/*-----------------------------------------------*/

table {
    border-collapse: collapse;
    width: 100%; /* prend toute la largeur écran */
    table-layout: fixed; /* 🔑 force les colonnes à se partager l’espace */
    height: 70vh;
}

th, td {
    border: 1px solid #999;
    padding: 2px 4px; /* padding réduit */
    font-size: 13px; /* texte compact */
    text-align: center;

}

th {
    background-color: #f5fbfe;
}

/* Colonne indicateurs (gauche) */
.sticky-col {
    width: 160px; /* largeur fixe lisible */
    text-align: left;
    font-weight: bold;
}

/* Colonnes semaines ultra fines */


td.clickable a {
    cursor: pointer;
    color: #0d6efd;
    text-decoration: underline;
}

/* ligne */
.row-hover td,
.row-hover th {
    background-color: #fff3cd;
}

.cross-hover {
    background-color: #fff3cd;
}

.current-cell {
    background-color: #eccf80;
}



</style>


<div style="height: 85vh;">
    <div class="running-reports">

        <div class="tabs">
            <ul class="tab-list">
                <li class="tab active" data-tab="tab1">Déclaration Immédiate</li>
                <li class="tab" data-tab="tab2">Déclaration Hebdomadaire</li>
                <li class="tab" data-tab="tab3">Déclaration mensuelle</li>
            </ul>

            <div class="tab-content">
                <div id="tab1" class="content active">
                    <div style="display: flex; justify-content: space-between; margin-bottom: 10px">
                        <h3>Déclaration immédiate des maladies infectieuses</h3>
                        <div>
                            <button type="button" id="btnPdf">📄 Exporter PDF</button>
                        </div>
                    </div>
                    <table id="indicatorTable1">
                        <thead>
                        <tr>
                            <th style="background: #fff; color: #fff; font-size: 18px; border: 1px solid #fff" class="sticky-col"></th>
                            <th colspan="53" style="text-align: center; background: #3b5674; color: #fff; font-size: 15px; border-color: white; border-radius: 8px 8px 0px 0px;">Liste complète des semaines de l’année 2026, allant de la 1ʳᵉ à la 53ᵉ semaine</th>
                            <th style="background: #fff; color: #fff; font-size: 18px; border: 1px solid #fff; width: 35px" class="sticky-col"></th>

                        </tr>
                        <tr id="tableHeaderImmediate">
                            <th  style="background: #7a98bd; color: #fff; font-size: 18px; border-color: white; border-radius: 8px 0px 0px 0px;" class="sticky-col">Indicateurs</th>
                        </tr>
                        </thead>
                        <tbody id="tableBodyImmediate">
                        </tbody>
                    </table>
                </div>

                <div id="tab2" class="content">
                    <h3>Déclaration Hebdomadaire</h3>

                    <table id="indicatorTable2">
                        <thead>
                        <tr>
                            <th style="background: #fff; color: #fff; font-size: 18px; border: 1px solid #fff" class="sticky-col"></th>
                            <th colspan="53" style="text-align: center; background: #3b5674; color: #fff; font-size: 15px; border-color: white; border-radius: 8px 8px 0px 0px;">Liste complète des semaines de l’année 2026, allant de la 1ʳᵉ à la 53ᵉ semaine</th>
                            <th style="background: #fff; color: #fff; font-size: 18px; border: 1px solid #fff; width: 35px" class="sticky-col"></th>

                        </tr>
                        <tr id="tableHeaderWeek">
                            <th  style="background: #7a98bd; color: #fff; font-size: 18px; border-color: white; border-radius: 8px 0px 0px 0px;" class="sticky-col">Indicateurs</th>
                        </tr>
                        </thead>
                        <tbody id="tableBodyWeek">
                        </tbody>
                    </table>
                </div>

                <div id="tab3" class="content">
                    <h3>Déclaration mensuelle</h3>

                    <table id="indicatorTable3">
                        <thead>
                        <tr>
                            <th style="background: #fff; color: #fff; font-size: 18px; border: 1px solid #fff" class="sticky-col"></th>
                            <th colspan="12" style="text-align: center; background: #3b5674; color: #fff; font-size: 15px; border-color: white; border-radius: 8px 8px 0px 0px;">Répartition mensuelle des déclarations pour l’année 2026 (Janvier–Décembre)</th>
                            <th style="background: #fff; color: #fff; font-size: 18px; border: 1px solid #fff; width: 35px" class="sticky-col"></th>

                        </tr>
                        <tr id="tableHeaderMonth">
                            <th  style="background: #7a98bd; color: #fff; font-size: 18px; border-color: white; border-radius: 8px 0px 0px 0px;" class="sticky-col">Indicateurs</th>
                        </tr>
                        </thead>
                        <tbody id="tableBodyMonth"></tbody>
                    </table>
                </div>
            </div>

        </div>

    </div>

</div>

