<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeCss("coreapps", "clinicianfacing/patient.css")
    ui.includeJavascript("coreapps", "custom/deletePatient.js")
%>
<meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=cover">



<style>

.form-group {
    width: 100%;
    margin: auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
}
.form-control {
    border-radius: 10px;
    height: 30px;
    padding: 8px 15px;
    border: 1px solid #ced4da;
    box-shadow: none;
    /*transition: all 0.1s ease-in-out;*/
    width: 95%;
    font-size: 14px;
    margin-bottom: -10px;
}

.form-control:focus {
    border: 1px solid #ced4da;
    background: #fff;
    outline-color: #8bafd4;
}

.contain {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.box {
    height: 80vh;
    display: flex;
    flex-direction: column;
    width: 56%;
    padding: 20px;
    border: 1px solid #f1f4f8;
    /*border: 1px solid red;*/
    background: white;
    border-radius: 10px;
    box-shadow: 0 3px 5px -5px;

}

.results-container {
    flex: 1;
}


/*--------------pagination--------------------------------------*/
.pagination {
    display: flex;
    gap: 6px;
    justify-content: end;
    align-items: end;
    margin-top: 15px;
}

.pagination button {
    background: #fff;
    border: 1px solid #ccc;
    color: #333;
    padding: 5px 10px;
    border-radius: 5px;
    cursor: pointer;
    font-size: 14px;
}

.pagination button.active {
    background: #3498db;
    color: white;
    font-weight: bold;
}

.pagination button:hover {
    background: #f2f2f2;
}

.pagination button:disabled {
    opacity: 0.5;
    cursor: not-allowed;
}
/*------------------------------------------------*/

/*----------spinner--------------------*/
@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}



/*---------------------------------------*/
/* ===== Google Style Results ===== */

#myTable {
    width: 100%;
    border-collapse: collapse;
}

#myTable thead {
    display: none;
    border-color: #fff;
}
#myTable tr {
    border-color: #fff;
}
#myTable td {
    background: #fff;
    border-color: #fff;
    /*border: 1px solid green;*/
    padding: 5px 0px;
    display: flex;
    justify-content: center;
}

#myTable tr {
    border-bottom: 1px solid #fff;
}

.patient-card {
    width: 100%;
    padding: 6px 12px;
    background: #fff;
    border: 1px solid #f6fafd;
    /*border: 1px solid red;*/
    border-radius: 10px;
    box-shadow: 0 3px 2px -3px;
}
.patient-card:hover {
    background: #f7fbff;
}

.patient-meta {
    font-size: 12px;
    color: #555;
    display: flex;
    gap: 10px;
}
.patient-meta span {
    display: inline-block;
    margin-right: 15px;
}


.patient-address {
    font-size: 12px;
    color: #777;
}

.patient-name {
    font-weight: bold;
    font-size: 16px;
    margin-bottom: 5px;
    color: #3c5a81;
}

.patient-meta span {
    display: inline-block;
    margin-right: 15px;
}

.patient-address {
    margin-top: 4px;
    color: #666;
}

.icon-map-marker {
    margin-right: 5px;
    color: #66a7aa;
}
#clearHistory {
    font-size: 12px;
}

@media (max-width: 768px) {
    .box {
        width: 90%;
        padding: 0px 12px;
    }
    .form-control {
        height: 28px;
        border: 1px solid #ced4da;
        font-size: 13px;
        /*margin-bottom: -20px;*/
    }
    .patient-name {
        font-weight: bold;
        font-size: 13px;
    }
    .patient-meta span {
        margin-right: 5px;
    }
    .patient-address {
        margin-top: 2px;
        font-size: 11px;
    }
    #clearHistory{
        font-size: 10px;
    }
}

@media (min-width: 769px) and (max-width: 1024px) {
    .box {
        width: 80%;
        padding: 0px 12px;
    }
}


</style>

<div class="contain" style="width: 100%;">

    <div class="box results-container">

        <div style="margin-left: 20px; margin: auto;">
            <h2>Rechercher dossier patient</h2>
        </div>
        <br/>

        <div class="form-group">

            <div>
                ${ ui.includeFragment("isanteplus", "findPatientCriteria") }
            </div>

            <div id="spinner" style="
            display: none;
            position: absolute;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background: rgba(255, 255, 255, 0.8);
            z-index: 10;
            justify-content: center;
            align-items: center;
            ">
                <div style="
                width: 50px;
                height: 50px;
                border: 6px solid #ccc;
                border-top: 6px solid #007bff;
                border-radius: 50%;
                animation: spin 0.6s linear infinite;
                "></div>
            </div>
            <table id="myTable" style="margin-top: 30px; width:100%;">
                <tbody style="background: #fff">
                </tbody>
            </table>
        </div>
        <br/>
        <div class="pagination" id="pagination" style="text-align:center; margin:20px; border: 1px solid white"></div>
        <a href="" style="margin-bottom: 5px" id="clearHistory"><u>Effacer l’historique</u></a>
    </div>

</div>

<script type="text/javascript">

    jq(function () {

        jq('#patient-search').focus();

        const input = jq('#search');
        const clearIcon = jq('#clearIcon');
        const tbody = jq('#myTable tbody');

        /* ===========================
           ICON CLEAR
        =========================== */

        input.on('input', function () {
            clearIcon.toggle(jq(this).val().length > 0);
        });

        clearIcon.on('click', function () {
            input.val('').trigger('input').focus();
            tbody.empty();
        });

        /* ===========================
           HISTORIQUE (AFFICHAGE)
        =========================== */

        function loadHistory() {

            let historique = JSON.parse(localStorage.getItem('patientsConsultes')) || [];
            tbody.empty();

            if (historique.length === 0) {
                tbody.html('<tr class="no-data"><td style="text-align:center;color:#777;">Aucun patient consulté</td></tr>');
                return;
            }

            historique.forEach(p => {

                const row =
                    "<tr class='patient-row' data-patient-id='" + p.patientId + "'>" +
                    "<td>" +
                    "<div class='patient-card'>" +

                    "<div class='patient-name'>" + (p.fullName || "") + "</div>" +

                    "<div class='patient-meta'>" +
                    "<span><b>Sexe:</b> " + (p.gender || "") + "</span> " +
                    "<span><b>Âge:</b> " + (p.patientAge || "") + "</span> " +
                    "<span><b>Naissance:</b> " + (p.birthDate || "") + "</span>" +
                    "</div>" +

                    "<div class='patient-address'><i class='icon-map-marker'></i>" + (p.adresses || "") + "</div>" +

                    "</div>" +
                    "</td>" +
                    "</tr>";

                tbody.append(row);
            });
        }

        loadHistory();

        /* ===========================
           CLICK SUR LIGNE (UN SEUL HANDLER)
        =========================== */

        jq('#myTable').off('click', 'tr').on('click', 'tr', function () {

            const patientId = jq(this).data('patient-id');
            if (!patientId) return;

            // 🔥 On récupère les infos propres depuis les div internes
            const patient = {
                patientId: patientId,
                fullName: jq(this).find('.patient-name').text().trim(),
                gender: jq(this).find('.patient-meta span').eq(0).text().replace('Sexe:', '').trim(),
                patientAge: jq(this).find('.patient-meta span').eq(1).text().replace('Âge:', '').trim(),
                birthDate: jq(this).find('.patient-meta span').eq(2).text().replace('Naissance:', '').trim(),
                adresses: jq(this).find('.patient-address').text().trim()
            };

            let historique = JSON.parse(localStorage.getItem('patientsConsultes')) || [];

            // Supprimer si déjà existant
            historique = historique.filter(p => p.patientId !== patient.patientId);

            // Ajouter en premier
            historique.unshift(patient);

            // Limite à 50
            if (historique.length > 50) historique.pop();

            localStorage.setItem('patientsConsultes', JSON.stringify(historique));

            window.location.href =
                "/openmrs/coreapps/clinicianfacing/patient.page?patientId=" + patientId;
        });


        /* ===========================
           CLEAR HISTORY
        =========================== */

        jq('#clearHistory').click(function () {
            localStorage.removeItem('patientsConsultes');
            loadHistory();
        });

        /* ===========================
           RECHERCHE AJAX
        =========================== */

        jq("#search").on("keyup", function () {

            const criteria = jq(this).val().trim();
            const spinner = jq("#spinner");

            if (criteria.length < 2) {
                loadHistory();
                return;
            }

            spinner.show();

            jq.ajax({
                url: "${ ui.actionLink('isanteplus', 'findPatientCriteria', 'findPatientByCriteria') }",
                type: "POST",
                data: { criteria: criteria },

                success: function (response) {

                    tbody.empty();

                    if (!response || response.length === 0) {
                        tbody.append("<tr class='no-data'><td>Aucun résultat</td></tr>");
                        return;
                    }

                    jq.each(response, function (i, p) {

                        console.log("p======",p)
                        const row =
                            "<tr class='patient-row' data-patient-id='" + p.patientId + "'>" +
                            "<td>" +
                            "<div class='patient-card'>" +

                            "<div class='patient-name'>" + (p.fullName || "") + "</div>" +

                            "<div class='patient-meta'>" +
                            "<span><b>Sexe:</b> " + (p.gender || "") + "</span> " +
                            "<span><b>Âge:</b> " + (p.patientAge || "") + "</span> " +
                            "<span><b>Naissance:</b> " + (p.birthDate || "") + "</span>" +
                            "</div>" +

                            "<div class='patient-address'><i class='icon-map-marker'></i>" + (p.adresses || "") + "</div>" +

                            "</div>" +
                            "</td>" +
                            "</tr>";

                        tbody.append(row);
                    });
                },

                complete: function () {
                    spinner.hide();
                },

                error: function (xhr, status, error) {
                    console.error("Erreur AJAX:", error);
                }
            });
        });

    });


    function displayDataTable() {
        document.addEventListener("DOMContentLoaded", function () {
            const rowsPerPage = 15;
            let currentPage = 1;

            const table = document.getElementById('myTable');
            const tbody = table.querySelector('tbody');
            const rows = Array.from(tbody.querySelectorAll('tr'));

            function displayTable(page) {
                const start = (page - 1) * rowsPerPage;
                const end = start + rowsPerPage;

                rows.forEach((row, index) => {
                    row.style.display = (index >= start && index < end) ? '' : 'none';
                });

                displayPagination();
                addRowClickListeners();
            }

            function displayPagination() {
                const pagination = document.getElementById('pagination');
                pagination.innerHTML = '';
                const totalPages = Math.ceil(rows.length / rowsPerPage);

                // === Bouton précédent ===
                const prevBtn = document.createElement('button');
                prevBtn.textContent = '<<';
                styleButton(prevBtn);
                prevBtn.disabled = currentPage === 1;
                prevBtn.onclick = () => {
                    if (currentPage > 1) {
                        currentPage--;
                        displayTable(currentPage);
                    }
                };
                pagination.appendChild(prevBtn);

                // === Les 5 premières pages ===
                const maxPagesToShow = Math.min(15, totalPages);
                for (let i = 1; i <= maxPagesToShow; i++) {
                    const btn = document.createElement('button');
                    btn.textContent = i;
                    styleButton(btn);
                    if (i === currentPage) btn.style.background = '#bcced8';
                    btn.onclick = () => {
                        currentPage = i;
                        displayTable(currentPage);
                    };
                    pagination.appendChild(btn);
                }

                // === Bouton Dernière page ===
                if (totalPages > 15) {
                    const lastBtn = document.createElement('button');
                    lastBtn.textContent = 'Dernière (' + totalPages + ')';
                    styleButton(lastBtn);
                    if (currentPage === totalPages) lastBtn.style.background = '#bcced8';
                    lastBtn.onclick = () => {
                        currentPage = totalPages;
                        displayTable(currentPage);
                    };
                    pagination.appendChild(lastBtn);
                }

                // === Bouton suivant ===
                const nextBtn = document.createElement('button');
                nextBtn.textContent = '>>';
                styleButton(nextBtn);
                nextBtn.disabled = currentPage === totalPages;
                nextBtn.onclick = () => {
                    if (currentPage < totalPages) {
                        currentPage++;
                        displayTable(currentPage);
                    }
                };
                pagination.appendChild(nextBtn);
            }

            function styleButton(btn) {
                btn.style.background = '#fff';
                btn.style.border = '1px solid #ccc';
                btn.style.padding = '2px 10px';
                btn.style.fontSize = '13px';
                btn.style.cursor = 'pointer';
                btn.style.height = '26px';
                btn.style.borderRadius = '5px';
                btn.style.margin = '0 2px';
            }

            function addRowClickListeners() {
                rows.forEach(row => {
                    if (row.style.display !== 'none') {
                        row.style.cursor = 'pointer';
                        row.onclick = () => {
                            const patientId = row.getAttribute('data-patient-id');
                            window.location.href = "/openmrs/coreapps/clinicianfacing/patient.page?patientId=" + patientId;
                        }
                    } else {
                        row.onclick = null;
                    }
                });
            }

            displayTable(currentPage);
        });
    }

    displayDataTable();
</script>

