<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeCss("coreapps", "clinicianfacing/patient.css")
    ui.includeJavascript("coreapps", "custom/deletePatient.js")
%>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<style>

body {
    font-family: Arial, sans-serif;
    /*background: #f4f7fb;*/
}

/* Container */
.contain {
    display: flex;
    justify-content: center;
}

.box {
    width: 60%;
    background: #fff;
    padding: 25px;
    box-shadow: 0 3px 2px -3px;
    border-radius: 1px;
    border: 1px solid #f4f5f5;
}

.page-title {
    text-align: center;
    font-size: 22px;
    font-weight: bold;
    color: #2c3e50;
    margin-bottom: 25px;
}

/* SEARCH */
.search-wrapper {
    position: relative;
    width: 100%;
    margin-bottom: 20px;
}

.search-input {
    width: 100%;
    box-sizing: border-box;
    padding: 10px 40px 10px 35px;
    border: 1px solid #dce3ec;
    font-size: 14px;
    outline: none;
    border-radius: 5px;
    transition: 0.2s ease;
}

.search-input:focus {
    border-color: #448091;
    box-shadow: 0 0 0 2px rgba(68,128,145,0.1);
}

.search-icon {
    position: absolute;
    left: 12px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 14px;
    color: #888;
}

.clear-icon {
    position: absolute;
    right: 12px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 14px;
    color: #999;
    cursor: pointer;
    display: none;
}

.clear-icon:hover {
    color: #e74c3c;
}

/* Patient Card */
.patient-card {
    background: #ffffff;
    border: 1px solid #e3e8ef;
    padding: 14px 18px;
    margin-bottom: 12px;
    cursor: pointer;
    border-radius: 6px;
    transition: 0.2s ease;
}

.patient-card:hover {
    background: #f0f7ff;
    transform: translateY(-2px);
}

.patient-name {
    font-weight: bold;
    font-size: 16px;
    color: #448091;
    margin-bottom: 6px;
}

.patient-meta {
    font-size: 12px;
    color: #555;
    display: flex;
    gap: 15px;
    flex-wrap: wrap;
}

.patient-address {
    font-size: 12px;
    color: #777;
    margin-top: 6px;
}

/* Pagination */
.pagination {
    display: flex;
    justify-content: center;
    margin-top: 20px;
    gap: 6px;
}

.page-btn {
    padding: 6px 10px;
    border: 1px solid #dce3ec;
    cursor: pointer;
    font-size: 13px;
    background: #fff;
    border-radius: 4px;
}

.page-btn:hover {
    background: #f0f7ff;
}

.page-btn.active {
    background: #448091;
    color: white;
    border-color: #448091;
}

/* Spinner */
@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}

/* Responsive */
@media (max-width: 768px) {
    .box {
        width: 95%;
        padding: 15px;
    }
}
</style>

<div class="contain">
    <div class="box">

        <div class="page-title">
            Rechercher dossier patient
        </div>

        <!-- SEARCH -->
        <div class="search-wrapper">
            <span class="search-icon">🔍</span>
            <input type="text" id="search" class="search-input"
                   placeholder="Rechercher par nom, ID ou critère...">
            <span class="clear-icon" id="clearIcon">✖</span>
        </div>

        <!-- Spinner -->
        <div id="spinner" style="
        display:none;
        position:absolute;
        top:0; left:0;
        width:100%; height:100%;
        background:rgba(255,255,255,0.7);
        justify-content:center;
        align-items:center;
        z-index:10;">
            <div style="
            width:40px;height:40px;
            border:5px solid #ccc;
            border-top:5px solid #448091;
            border-radius:50%;
            animation:spin 0.6s linear infinite;">
            </div>
        </div>

        <!-- Container for patient cards -->
        <div id="patientContainer"></div>

        <!-- Pagination -->
        <div  class="pagination" id="pagination"></div>

        <div style="text-align:center;margin-top:40px; float: left">
            <a href="" id="clearHistory"><u>Effacer l’historique</u></a>
        </div>

    </div>
</div>

<script>
    jq(function () {

        const input = jq('#search');
        const clearIcon = jq('#clearIcon');
        const container = jq('#patientContainer');
        const spinner = jq('#spinner');

        let currentPage = 1;
        const itemsPerPage = 5;
        let patients = [];

        // Clear icon
        input.on('input', function () {
            clearIcon.toggle(jq(this).val().length > 0);
        });

        clearIcon.on('click', function () {
            input.val('').trigger('input').focus();
            loadHistory();
        });

        // Load history
        function loadHistory() {
            patients = JSON.parse(localStorage.getItem('patientsConsultes')) || [];
            renderPatients();
        }

        // Render patient cards
        function renderPatients() {
            container.empty();

            if (patients.length === 0) {
                container.html('<div style="text-align:center;color:#777;">Aucun patient consulté</div>');
                jq('#pagination').empty();
                return;
            }

            const totalPages = Math.ceil(patients.length / itemsPerPage);
            if (currentPage > totalPages) currentPage = 1;

            const start = (currentPage - 1) * itemsPerPage;
            const end = start + itemsPerPage;

            patients.slice(start, end).forEach(p => {
                const card = jq(
                    "<div class='patient-card' data-patient-id='"+p.patientId+"'>" +
                    "<div class='patient-name'>"+(p.fullName||"")+"</div>" +
                    "<div class='patient-meta'>" +
                    "<span><b>Sexe:</b> "+(p.gender||"")+"</span>" +
                    "<span><b>Âge:</b> "+(p.patientAge||"")+"</span>" +
                    "<span><b>Naissance:</b> "+(p.birthDate||"")+"</span>" +
                    "</div>" +
                    "<div class='patient-address'>"+(p.adresses||"")+"</div>" +
                    "</div>"
                );
                card.click(() => openPatient(p));
                container.append(card);
            });

            renderPagination(totalPages);
        }

        function renderPagination(totalPages) {
            const pagination = jq('#pagination');
            pagination.empty();
            if (totalPages <= 1) return;

            const prev = jq('<div class="page-btn">«</div>');
            prev.click(() => { if (currentPage>1) {currentPage--; renderPatients();} });
            pagination.append(prev);

            let start = Math.max(1, currentPage-2);
            let end = Math.min(totalPages, start+4);
            if (end-start<4) start = Math.max(1, end-4);

            for(let i=start;i<=end;i++){
                const btn = jq('<div class="page-btn">'+i+'</div>');
                if(i===currentPage) btn.addClass('active');
                btn.click(() => {currentPage=i; renderPatients();});
                pagination.append(btn);
            }

            const next = jq('<div class="page-btn">»</div>');
            next.click(() => { if(currentPage<totalPages){currentPage++; renderPatients();} });
            pagination.append(next);
        }

        function openPatient(p) {
            let hist = JSON.parse(localStorage.getItem('patientsConsultes')) || [];
            hist = hist.filter(x => x.patientId!==p.patientId);
            hist.unshift(p);
            if(hist.length>50) hist.pop();
            localStorage.setItem('patientsConsultes', JSON.stringify(hist));
            window.location.href="/openmrs/coreapps/clinicianfacing/patient.page?patientId="+p.patientId;
        }

        // Clear history
        jq('#clearHistory').click(function(e){
            e.preventDefault();
            localStorage.removeItem('patientsConsultes');
            loadHistory();
        });

        // AJAX search
        input.on('keyup', function(){
            const criteria = jq(this).val().trim();
            if(criteria.length<2){
                loadHistory();
                return;
            }
            spinner.show();
            jq.ajax({
                url: "${ ui.actionLink('isanteplus','findPatientCriteria','findPatientByCriteria') }",
                type: "POST",
                data: { criteria: criteria },
                success: function(response){
                    patients = response || [];
                    currentPage = 1;
                    renderPatients();
                },
                complete: function(){ spinner.hide(); }
            });
        });

        loadHistory();

    });
</script>
