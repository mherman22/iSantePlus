<%
    ui.decorateWith("appui", "standardEmrPage")
%>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<style>

body {
    font-family: Arial, sans-serif;
}

/* Container */
.contain {
    display: flex;
    justify-content: center;
    width: 100%;
}

.box {
    width: 56%;
    padding: 20px;
    background: #ffffff;
    box-shadow: 0 3px 2px -3px;
    border-radius: 2px;
    border: 1px solid #f4f5f5;
}

/* Title */
.page-title {
    text-align: center;
    font-size: 22px;
    font-weight: bold;
    color: #2c3e50;
    margin-bottom: 20px;
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
    font-size: 14px;
    color: #888;
    pointer-events: none;
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

/* Visit Card */
.visit-card {
    background: #ffffff;
    border: 1px solid #e3e8ef;
    padding: 10px 13px;
    margin-bottom: 12px;
    cursor: pointer;
    transition: 0.2s ease;
    border-radius: 6px;
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

.visit-meta {
    font-size: 12px;
    color: #555;
    display: flex;
    gap: 15px;
    flex-wrap: wrap;
    margin-bottom: 8px;
}

.visit-type {
    color: white;
    padding: 3px 8px;
    font-size: 11px;
    border-radius: 4px;
}

.visit-info {
    font-size: 12px;
    color: #666;
    margin-bottom: 4px;
}

.no-results {
    text-align: center;
    color: #888;
    margin-top: 20px;
    display: none;
}

/* Pagination */
.pagination {
    display: flex;
    justify-content: center;
    margin-top: 20px;
    flex-wrap: wrap;
    gap: 5px;
}

.page-btn {
    padding: 6px 10px;
    border: 1px solid #dce3ec;
    cursor: pointer;
    font-size: 13px;
    background: #fff;
}

.page-btn:hover {
    background: #f0f7ff;
}

.page-btn.active {
    background: #7e9fc1;
    color: white;
    border-color: #7d9cc1;
}

/* Responsive */
@media (max-width: 768px) {
    .box {
        width: 95%;
    }
    .visit-name {
        font-size: 15px;
    }
    .page-title {
        font-size: 22px;
    }
}

@media (max-width: 1024px) {
    .box {
        width: 85%;
    }
}

</style>

<div class="contain">
    <div class="box">

        <div class="page-title">
            ${ ui.message("coreapps.activeVisits.title") }
        </div>

        <!-- SEARCH -->
        <div class="search-wrapper">
            <span class="search-icon">🔍</span>
            <input type="text"
                   id="visitSearch"
                   class="search-input"
                   placeholder="Rechercher par nom ou par ID">
            <span class="clear-icon" id="clearSearch">✖</span>
        </div>

        <% if (visitSummaries == null || visitSummaries.size() == 0) { %>
        <div style="text-align:center;color:#777;">
            ${ ui.message("coreapps.none") }
        </div>
        <% } %>

        <div id="visitList">

            <% visitSummaries.each { v ->
                def checkIn = v.checkInEncounter
                def latest = v.lastEncounter
            %>

            <div class="visit-card"
                 data-search="${ ui.format(v.visit.patient) } ${ ui.format(v.visit.patient.patientIdentifier) }"
                 onclick="window.location.href='${ ui.urlBind("/" + contextPath + patientPageUrl, v.visit) }'">

                <div class="visit-name">
                    ${ ui.encodeHtmlContent(ui.format(v.visit.patient)) }
                </div>

                <div class="visit-meta">
                    <span>
                        <b>ID:</b>
                        ${ ui.encodeHtmlContent(ui.format(v.visit.patient.patientIdentifier)) }
                    </span>
                </div>

                <% if (checkIn) { %>
                <div class="visit-info">
                    <b>Check-in:</b>
                    ${ ui.encodeHtmlContent(ui.format(checkIn.location)) }
                    @ ${ ui.format(checkIn.encounterDatetime) }
                </div>
                <% } %>

                <% if (latest) { %>
                <div class="visit-info">
                    <b>Dernière visite:</b>
                    ${ ui.encodeHtmlContent(ui.format(latest.encounterType)) }
                    —
                    ${ ui.encodeHtmlContent(ui.format(latest.location)) }
                    @ ${ ui.format(latest.encounterDatetime) }
                </div>
                <% } %>

            </div>

            <% } %>

        </div>

        <div id="pagination" class="pagination"></div>

        <div class="no-results" id="noResults">
            Aucun résultat trouvé.
        </div>

    </div>
</div>

<script>

    let searchInput = document.getElementById("visitSearch");
    let clearBtn = document.getElementById("clearSearch");
    let cards = Array.from(document.querySelectorAll(".visit-card"));
    let paginationContainer = document.getElementById("pagination");

    const itemsPerPage = 4;
    let currentPage = 1;
    let filteredCards = cards;

    /* ---------- Pagination ---------- */

    function renderPagination() {

        paginationContainer.innerHTML = "";

        let totalPages = Math.ceil(filteredCards.length / itemsPerPage);
        if (totalPages <= 1) return;

        // PREVIOUS BUTTON
        let prevBtn = document.createElement("div");
        prevBtn.classList.add("page-btn");
        prevBtn.textContent = "«";
        prevBtn.onclick = function () {
            if (currentPage > 1) {
                currentPage--;
                updateDisplay();
            }
        };
        paginationContainer.appendChild(prevBtn);

        // Dynamic page range (5 max)
        let startPage = Math.max(1, currentPage - 2);
        let endPage = Math.min(totalPages, startPage + 4);

        // Fix range if near end
        if (endPage - startPage < 4) {
            startPage = Math.max(1, endPage - 4);
        }

        for (let i = startPage; i <= endPage; i++) {

            let btn = document.createElement("div");
            btn.classList.add("page-btn");
            btn.textContent = i;

            if (i === currentPage) {
                btn.classList.add("active");
            }

            btn.onclick = function () {
                currentPage = i;
                updateDisplay();
            };

            paginationContainer.appendChild(btn);
        }

        // NEXT BUTTON
        let nextBtn = document.createElement("div");
        nextBtn.classList.add("page-btn");
        nextBtn.textContent = "»";
        nextBtn.onclick = function () {
            if (currentPage < totalPages) {
                currentPage++;
                updateDisplay();
            }
        };
        paginationContainer.appendChild(nextBtn);
    }

    function updateDisplay() {

        cards.forEach(card => card.style.display = "none");

        let start = (currentPage - 1) * itemsPerPage;
        let end = start + itemsPerPage;

        filteredCards.slice(start, end).forEach(card => {
            card.style.display = "block";
        });

        renderPagination();
    }

    /* ---------- Search ---------- */

    searchInput.addEventListener("keyup", function() {

        let filter = this.value.toLowerCase();

        clearBtn.style.display = filter.length > 0 ? "block" : "none";

        filteredCards = cards.filter(card =>
            card.getAttribute("data-search").toLowerCase().includes(filter)
        );

        currentPage = 1;

        document.getElementById("noResults").style.display =
            filteredCards.length === 0 ? "block" : "none";

        updateDisplay();
    });

    clearBtn.addEventListener("click", function() {
        searchInput.value = "";
        clearBtn.style.display = "none";
        filteredCards = cards;
        currentPage = 1;
        document.getElementById("noResults").style.display = "none";
        updateDisplay();
    });

    /* Initial load */
    updateDisplay();


</script>
