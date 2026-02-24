<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeJavascript("coreapps", "visit/jquery.dataTables.js")
    ui.includeJavascript("coreapps", "visit/filterTable.js")
    ui.includeCss("coreapps", "visit/visits.css")
%>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<style>
body {
    font-family: Arial, sans-serif;
    background: #f9fbfd;
}

/* Container général */
.container {
    display: flex;
    justify-content: center;
    width: 100%;
}

/* Box centrale */


.box {
    width: 56%;
    padding: 20px;
    background: #ffffff;
    box-shadow: 0 3px 2px -3px;
    border-radius: 2px;
    border: 1px solid #f4f5f5;
}

/* Titre */
.page-title {
    text-align: center;
    font-size: 22px;
    font-weight: bold;
    color: #2c3e50;
    margin-bottom: 15px;
}

/* Search */
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
    transition: 0.2s ease;
    border-radius: 5px;
}

.search-input:focus {
    border-color: #3c5a81;
    box-shadow: 0 0 0 2px rgba(60,90,129,0.1);
}

.search-icon {
    position: absolute;
    left: 12px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 16px;
    color: #888;
    pointer-events: none;
}

.clear-icon {
    position: absolute;
    right: 12px;
    top: 50%;
    transform: translateY(-50%);
    font-size: 16px;
    color: #999;
    cursor: pointer;
    display: none;
}

/* Cards pour visites */
.visit-card {
    background: #ffffff;
    border: 1px solid #e3e8ef;
    border-radius: 6px;
    padding: 12px 15px;
    margin-bottom: 12px;
    cursor: pointer;
    transition: 0.2s ease;
}

.visit-card:hover {
    background: #f0f7ff;
    transform: translateY(-2px);
}

.visit-name {
    font-weight: bold;
    font-size: 16px;
    color: #3e5f81;
    margin-bottom: 6px;
}

.visit-meta, .visit-info {
    font-size: 13px;
    color: #555;
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin-bottom: 5px;
}

.visit-type {
    color: white;
    padding: 2px 8px;
    font-size: 11px;
    border-radius: 4px;
}

.no-results {
    text-align: center;
    color: #888;
    margin-top: 20px;
    display: none;
}

/* Responsive */
@media (max-width: 768px) {
    .box {
        width: 95%;
        padding: 10px;
    }
    .visit-name {
        font-size: 15px;
    }
    .page-title {
        font-size: 20px;
    }
}

@media (max-width: 480px) {
    .visit-meta, .visit-info {
        flex-direction: column;
        gap: 3px;
    }
}
</style>

<div class="container">
    <div class="box">

        <div class="page-title">
            ${ ui.message("coreapps.triage.app.label") }
        </div>

        <!-- SEARCH -->
        <div class="search-wrapper">
            <span class="search-icon">🔍</span>
            <input type="text" id="visitSearch" class="search-input" placeholder="Rechercher par nom ou ID">
            <span class="clear-icon" id="clearSearch">✖</span>
        </div>

        <% if (triageList == null || triageList.size() == 0) { %>
        <div class="no-results">${ ui.message("coreapps.none") }</div>
        <% } %>

        <div id="visitList">

            <% triageList.each { triage ->
                def latest = triage.vitals.encounter
            %>

            <div class="visit-card"
                 data-search="${ ui.format(triage.patient) } ${ ui.format(triage.patient.patientIdentifier) }"
                 onclick="window.location.href='${ui.pageLink('coreapps', 'clinicianfacing/patient', [patientId: triage?.patient?.patientId])}'">

                <div class="visit-name">
                    ${ ui.encodeHtmlContent(ui.format(triage.patient)) }
                </div>

                <div class="visit-meta">
                    <span><b>ID:</b> ${ ui.encodeHtmlContent(ui.format(triage.patient.patientIdentifier)) }</span>
                    <span><b>Date arrivée:</b> ${ ui.format(latest?.encounterDatetime) }</span>
                </div>

                <div class="visit-info">
                    <% if (triage.triageLevel == 'resuscitation') { %>
                    <span class="visit-type" style="background:#40a6d1;">Resuscitation</span>
                    <% } else if (triage.triageLevel == 'critical') { %>
                    <span class="visit-type" style="background:#cc0000;">Critical</span>
                    <% } else if (triage.triageLevel == 'potential') { %>
                    <span class="visit-type" style="background:#f0f000; color:#000;">Potential</span>
                    <% } else if (triage.triageLevel == 'semi') { %>
                    <span class="visit-type" style="background:#3fbf4e;">Semi</span>
                    <% } else { %>
                    <span class="visit-type" style="background:#999;">Normal</span>
                    <% } %>
                </div>

                <% if (latest) { %>
                <div class="visit-info">
                    <b>Dernière visite:</b> ${ ui.encodeHtmlContent(ui.format(latest.encounterType)) } —
                ${ ui.encodeHtmlContent(ui.format(latest.location)) }
                @ ${ ui.format(latest.encounterDatetime) }
                </div>
                <% } %>

            </div>

            <% } %>

        </div>

        <div class="no-results" id="noResults">Aucun résultat trouvé.</div>

    </div>
</div>

<script>
    let searchInput = document.getElementById("visitSearch");
    let clearBtn = document.getElementById("clearSearch");
    let cards = Array.from(document.querySelectorAll(".visit-card"));
    let filteredCards = cards;

    searchInput.addEventListener("keyup", function() {
        let filter = this.value.toLowerCase();
        clearBtn.style.display = filter.length > 0 ? "block" : "none";
        filteredCards = cards.filter(card => card.getAttribute("data-search").toLowerCase().includes(filter));
        cards.forEach(c => c.style.display = "none");
        filteredCards.forEach(c => c.style.display = "block");
        document.getElementById("noResults").style.display = filteredCards.length === 0 ? "block" : "none";
    });

    clearBtn.addEventListener("click", function() {
        searchInput.value = "";
        clearBtn.style.display = "none";
        filteredCards = cards;
        cards.forEach(c => c.style.display = "block");
        document.getElementById("noResults").style.display = "none";
    });
</script>
