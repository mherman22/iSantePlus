<%
    ui.decorateWith("appui", "standardEmrPage")
%>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<style>
* {
    box-sizing: border-box;
}

body {
    font-family: Inter, system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial;
    color: #0f172a;
    margin: 0;
    padding: 0;
}

/* CONTAINER */
.tabs {
    width: 98%;
    max-width: 1350px;
    margin: 0 auto;
    background: white;
    border-radius: 5px;
    box-shadow: 0 3px 5px -5px;
    overflow: hidden;
}

/* TAB HEADER */
.tablist {
    display: flex;
    gap: 6px;
    padding: 10px;
    border-bottom: 1px solid #e6e9ee;
    overflow-x: auto;
}

.tab {
    border: 0;
    background: transparent;
    padding: 10px 18px;
    border-radius: 10px;
    cursor: pointer;
    font-weight: 600;
    font-size: 14px;
    color: #586674;
    white-space: nowrap;
    transition: all 0.2s ease;
}

.tab:hover {
    background: #f1f5f9;
}

.tab[aria-selected="true"] {
    color: #0b4a7b;
    background: linear-gradient(180deg, #eef8ff, #e6f1fb);
    box-shadow: inset 0 2px 6px rgba(11, 74, 123, 0.08);
}

.tab:focus {
    outline: 3px solid rgba(11, 74, 123, 0.2);
}

/* PANELS */
.panels {
    padding: 25px;
}

.panel {
    display: none;
}

.panel.active {
    display: block;
    animation: fadeIn 0.3s ease-in;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(8px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* RESPONSIVE */
@media (max-width: 768px) {
    .tab {
        font-size: 13px;
        padding: 8px 12px;
    }

    .panels {
        padding: 15px;
    }
    .tablist {
        flex-direction: column;
        align-items: center;
    }
}
</style>

<script type="text/javascript">
    jQuery(function () {

        const tabs = Array.from(document.querySelectorAll('[role="tab"]'));
        const panels = Array.from(document.querySelectorAll('[role="tabpanel"]'));
        let currentTabIndex = 0;

        function activateTab(newTab) {

            tabs.forEach(t => {
                t.setAttribute('aria-selected', 'false');
                t.setAttribute('tabindex', '-1');
            });

            panels.forEach(p => {
                p.classList.remove('active');
                p.setAttribute('hidden', 'true');
            });

            newTab.setAttribute('aria-selected', 'true');
            newTab.setAttribute('tabindex', '0');
            newTab.focus();

            currentTabIndex = tabs.indexOf(newTab);

            const panelId = newTab.getAttribute('aria-controls');
            const panel = document.getElementById(panelId);

            if (panel) {
                panel.removeAttribute('hidden');
                panel.classList.add('active');
            }
        }

        tabs.forEach(tab => {

            tab.addEventListener('click', () => activateTab(tab));

            tab.addEventListener('keydown', (e) => {

                const idx = tabs.indexOf(tab);
                let nextIndex = null;

                switch (e.key) {
                    case 'ArrowLeft':
                        nextIndex = (idx - 1 + tabs.length) % tabs.length;
                        break;
                    case 'ArrowRight':
                        nextIndex = (idx + 1) % tabs.length;
                        break;
                    case 'Home':
                        nextIndex = 0;
                        break;
                    case 'End':
                        nextIndex = tabs.length - 1;
                        break;
                    case 'Enter':
                    case ' ':
                        e.preventDefault();
                        activateTab(tab);
                        return;
                    default:
                        return;
                }

                e.preventDefault();
                tabs[nextIndex].focus();
            });

        });

        // Initialisation
        activateTab(tabs[0]);

    });
</script>

<div style="width: 100%; display: flex; flex-direction: column; min-height: 100vh;">
    <h2 align="center" class="mb-4 text-center">TRIAGE INITIAL</h2>

    <main class="tabs">

        <div class="tablist" role="tablist" aria-label="Dossier patient">

            <button class="tab"
                    role="tab"
                    id="tab-1"
                    aria-selected="true"
                    aria-controls="panel-1"
                    tabindex="0">
                Informations personnelles
            </button>

            <button class="tab"
                    role="tab"
                    id="tab-2"
                    aria-selected="false"
                    aria-controls="panel-2"
                    tabindex="-1">
                Modificateurs
            </button>

            <button class="tab"
                    role="tab"
                    id="tab-3"
                    aria-selected="false"
                    aria-controls="panel-3"
                    tabindex="-1">
                Examen clinique
            </button>

        </div>

        <form id="form" method="POST" class="panels">

            <div id="panel-1"
                 class="panel"
                 role="tabpanel"
                 aria-labelledby="tab-1">
                <h2>Informations personnelles</h2>

                <div class="info-pers" style="margin-top: -10px">

                    <div class="form-group">
                        <label for="firstName">Prénom <span class="asterix">*</span></label>
                        <input type="text" class="form-control" name="firstName" value="${firstName != null ? firstName : ''}" id="firstName" placeholder=""
                               required>
                    </div>

                    <div class="form-group">
                        <label for="lastName">Nom <span class="asterix">*</span></label>
                        <input type="text" class="form-control" name="lastName" value="${lastName != null ? lastName : ''}" id="nom" placeholder=""
                               required>
                    </div>

                    <div class="form-group">
                        <label for="birthDate">Date de naissance</label>
                        <input type="date" class="form-control" name="birthDate" id="birthDate" value="${dateOfBirth != null ? dateOfBirth : ''}"
                               placeholder="mm/dd/yy">
                        <span class="span-hint" style="font-size: small">mm/dd/yy</span>
                    </div>

                    <div class="form-group">
                        <label for="gender">Sexe <span class="asterix">*</span></label>
                        <select class="form-select" name="gender" id="gender" required>
                            <option value=""></option>
                            <option value="M" ${(gender != null ? gender : '') == 'M' ? 'selected' : ''}>Masculin</option>
                            <option value="F" ${(gender != null ? gender : '') == 'F' ? 'selected' : ''}>Féminin</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="phone">Téléphone</label>
                        <input type="tel" class="form-control" name="phone" value="${phoneNumber != null ? phoneNumber : ''}" id="phone"
                               placeholder="+509 0000-0000">
                    </div>

                    <div class="form-group" style="width: 36%;">
                        <label style="margin-left: 1.4%" for="adresse">Adresse</label>
                        <input style="float: right; width: 95%; margin-right: 0.5%;" type="text" class="form-control" list="locationOptions" value="${address != null ? address : ''}" name="adresse" id="" placeholder="">
                        <datalist id="locationOptions">
                            <option value="">Limothe, 1ere La Plate, Bassin Bleu, Nord-Ouest, Haiti</option>
                            <% locationAddressMirrors.each { location -> %>
                            <option value="${location.getFullAddress()}">${location.getFullAddress()}</option>
                            <% } %>
                        </datalist>
                    </div>

                </div>

                <div class="info-pers" style="margin-top: 10px">

                    <div class="form-group">
                        <label for="arrivalMode">Mode d’arrivée <span class="asterix">*</span></label>
                        <select class="form-select" name="arrivalMode" id="arrivalMode" required>
                            <option value=""></option>
                            <option value="133499">Ambulance</option>
                            <option value="133499">Voiture privée</option>
                            <option value="133499">Transport publique</option>
                            <option value="133499">Motocyclette</option>
                            <option value="133499">A pied</option>
                            <option value="133499">Animal</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="ageGroup">Catégorie <span class="asterix">*</span></label>
                        <select class="form-select" name="ageGroup" id="ageGroup" required>
                            <option value=""></option>
                            <option value="122496">Nourisson</option>
                            <option value="122496">Enfant</option>
                            <option value="122496">Adolescent</option>
                            <option value="122496">Adulte</option>
                        </select>
                    </div>

                    <div class="form-group" style="width: 19%">
                        <label for="contactName"
                               style="font-size: 14px"><strong>Contact</strong>  (Nom complet)
                        </label>
                        <input type="text" name="contactName"
                               class="form-control" id="contactName"
                               placeholder="">
                    </div>

                    <div class="form-group">
                        <label for="relation">Relation</label>
                        <select class="form-select" name="relation" id="relation">
                            <option value=""></option>
                            <% relationshipsList.each { relation -> %>
                            <option value="${relation.answerConcept}" ${relationship == relation.answerConcept ? 'selected' : ''}>${relation.name}</option>
                            <% } %>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="contactPhone"
                               style="font-size: 14px"><strong>Contact</strong>  (Téléphone)
                        </label>
                        <input type="tel" class="form-control" name="contactPhone"
                               id="telephone"
                               placeholder="+509 0000-0000">
                    </div>
                </div>


            </div>

            <div id="panel-2"
                 class="panel"
                 role="tabpanel"
                 aria-labelledby="tab-2"
                 hidden>
                <h2>Modificateurs</h2>
                <p>Contenu du deuxième onglet...</p>
            </div>

            <div id="panel-3"
                 class="panel"
                 role="tabpanel"
                 aria-labelledby="tab-3"
                 hidden>
                <h2>Examen clinique</h2>
                <p>Contenu du troisième onglet...</p>
            </div>

        </form>

    </main>

</div>