<% ui.decorateWith("appui", "standardEmrPage") %>
<%

    if (sessionContext.authenticated && !sessionContext.currentProvider) {
        throw new IllegalStateException("Logged-in user is not a Provider")
    }

%>
<meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=cover">
<script>
    var stateProvinces = ${ui.toJson(stateProvinces)};
    var cityVillages = ${ui.toJson(cityVillages)};
    var municipalSections = ${ui.toJson(municipalSections)};
    var localities = ${ui.toJson(localities)};
</script>


<script type="text/javascript">

    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {label: "Fiche d'enregistrement du patient"}
    ];

    jQuery(function (jq) {

        const today = new Date().toISOString().split('T')[0];
        const input = document.getElementById("registrationDate");
        const input1 = document.getElementById("dateOfBirth");

        input.max = today;    // <= aujourd'hui
        input1.max = today;    // <= aujourd'hui
        input.value = today;  // valeur par défaut = aujourd'hui


        function fillSelect(selectId, items, placeholder) {
            const select = document.getElementById(selectId);
            select.innerHTML = '<option value="">' + placeholder + '</option>';

            items.forEach(item => {
                const opt = document.createElement("option");
                opt.value = item.id;              // pour JS
                opt.textContent = item.name;      // affichage
                opt.dataset.name = item.name;     // 🔥 pour le submit
                select.appendChild(opt);
            });
        }

        function QuickAddress(departementId, communeId, sectionId, localiteId) {

            const departementEl = document.getElementById(departementId);
            const communeEl = document.getElementById(communeId);
            const sectionEl = document.getElementById(sectionId);
            const localiteEl = document.getElementById(localiteId);

            if (!departementEl || !communeEl || !sectionEl || !localiteEl) {
                console.warn("QuickAddress: élément manquant");
                return;
            }

            // Département → Commune
            departementEl.addEventListener("change", function () {
                const deptId = parseInt(this.value);

                const communes = cityVillages.filter(c => c.parent === deptId);
                fillSelect(communeId, communes, "-- Sélectionner --");
                fillSelect(sectionId, [], "-- Sélectionner --");
                fillSelect(localiteId, [], "-- Sélectionner --");
            });

            // Commune → Section communale
            communeEl.addEventListener("change", function () {
                const communeIdVal = parseInt(this.value);

                const sections = municipalSections.filter(s => s.parent === communeIdVal);
                fillSelect(sectionId, sections, "-- Sélectionner --");
                fillSelect(localiteId, [], "-- Sélectionner --");
            });

            // Section communale → Localité
            sectionEl.addEventListener("change", function () {
                const sectionIdVal = parseInt(this.value);

                const locs = localities.filter(l => l.parent === sectionIdVal);
                fillSelect(localiteId, locs, "-- Sélectionner --");
            });
        }

        function swapValueToText(selectId) {
            const select = document.getElementById(selectId);
            if (!select) return;

            const opt = select.options[select.selectedIndex];
            if (!opt) return;

            opt.value = opt.textContent.trim();
        }

        function pushAddresses() {
            [
                "departement_birthdate",
                "commune_birthdate",
                "sectionCommunale_birthdate",
                "localite_birthdate",

                "departement_demo",
                "commune_demo",
                "sectionCommunale_demo",
                "localite_demo",

                "departement_pcontact",
                "commune_pcontact",
                "sectionCommunale_pcontact",
                "localite_pcontact",

                "departement_presponsable",
                "commune_presponsable",
                "sectionCommunale_presponsable",
                "localite_presponsable"
            ].forEach(swapValueToText);
        }



        //------------------------empreinte-----------

        const quickFinger = jq("#quickFinger");

        jq("#btnScanFingerprint").on("click", function (e) {
            e.preventDefault(); // ⛔ empêche le reload

            jq("#btnScanFingerprint").addClass("disabled");

            const serverUrl = jq("#serverUrl").val();

            jq("#fingerprint").val("");

            quickFinger.attr("placeholder", "Démarrer la capture d'empreinte...");

            captureFingerprint(serverUrl, "DoubleCapture").then(function (biometricXml) {
                jq("#fingerprint").val(biometricXml.TemplateData);
            }).always(function () {
                if (jq("#fingerprint").val().trim() !== "") {
                    quickFinger.attr("placeholder", "Succès...");
                    setTimeout(function () {
                        quickFinger.attr("placeholder", "Cliquez sur l’icône pour la prise d’empreinte digitale !");
                    }, 2000);
                } else {
                    quickFinger.attr("placeholder", "Cliquez sur l’icône pour la prise d’empreinte digitale !");
                }
                jq("#btnScanFingerprint").removeClass("disabled");
            });
        });

        function captureFingerprint(serverUrl, captureType) {
            captureType = captureType || "SingleCapture";

            return jq.ajax({
                url: serverUrl,
                type: "POST",
                contentType: "application/json",
                dataType: "json",
                timeout: 20000,
                data: JSON.stringify({
                    deviceName: "Secugen",
                    templateFormat: "FP1",
                    engineName: "FP1",
                    captureType: captureType,
                    quickScan: "Enable"
                })
            }).then(function (data) {
                if (!data || !data.CloudScanrStatus.Success || data.CloudScanrStatus.Success !== true) {
                    quickFinger.attr("placeholder", "Capture échouée !");
                    setTimeout(function () {
                        quickFinger.attr("placeholder", "Cliquez sur l’icône pour la prise d’empreinte digitale !");
                    }, 2000);
                    return jq.Deferred().reject("Capture échouée !");
                }
                return data;
            });
        }
        //-----------FIN-------------empreinte-----------



        // ----------------------
        // SUBMIT (CREATE)
        // ----------------------
        jq("#form").on("submit", function (e) {
            e.preventDefault();

            pushAddresses();

            if (!validateCurrentPanel()) {
                alert("Veuillez saisir les valeurs obligatoires !");
                return;
            }

            const formSave = jq(this).serialize(); // x-www-form-urlencoded (attendu par @RequestParam)
            console.log("formSave==", formSave)

            jq.ajax({
                url: "${ ui.pageLink('registration','register',null) }",
                type: "POST",
                data: formSave,
                success: function () {
                    jq().toastmessage('showSuccessToast', "Patient enregistré avec succès !")
                    setTimeout(() => location.reload(), 600);
                },
                error: function (err) {
                    console.error(err);
                    jq().toastmessage('showErrorToast', "Erreur lors de l'enregistrement (" + err.status + ").")
                }
            });
        });



        const form = document.getElementById("form");
        const submitBtn = document.getElementById("btn-submit");

        function checkFormValidity() {
            if (form.checkValidity()) {
                submitBtn.disabled = false;
            } else {
                submitBtn.disabled = true;
            }
        }

        // Écoute tous les champs requis
        form.querySelectorAll("input, select, textarea").forEach(el => {
            el.addEventListener("input", checkFormValidity);
            el.addEventListener("change", checkFormValidity);
        });

        // Vérification initiale
        checkFormValidity();

        // (function () {
        const tabs = Array.from(document.querySelectorAll('[role="tab"]'));
        const panels = Array.from(document.querySelectorAll('[role="tabpanel"]'));
        let currentTabIndex = 0;


        function activateTab(newTab) {
            tabs.forEach(t => {
                t.setAttribute('aria-selected', 'false');
                t.setAttribute('tabindex', '-1');
            });
            panels.forEach(p => {
                p.setAttribute('hidden', '');
                p.classList.remove('active');
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

            // Update button visibility
            updateButtonStates();
        }

        function updateButtonStates() {
            const backBtns = document.querySelectorAll('#btn-back');
            const nextBtns = document.querySelectorAll('#btn-next');
            const submitBtn = document.getElementById('btn-submit');

            // Hide back button on first step
            backBtns.forEach(btn => {
                btn.style.display = currentTabIndex === 0 ? 'none' : 'inline-block';
            });

            // Hide next button and show submit on last step
            nextBtns.forEach(btn => {
                btn.style.display = currentTabIndex === tabs.length - 1 ? 'none' : 'inline-block';
            });

            if (submitBtn) {
                submitBtn.style.display = currentTabIndex === tabs.length - 1 ? 'inline-block' : 'none';
            }
        }

        // Validation function to check required fields
        function validateCurrentPanel() {
            const currentPanel = panels[currentTabIndex];
            if (!currentPanel) return true;

            const requiredInputs = currentPanel.querySelectorAll('input[required], select[required]');
            let isValid = true;

            requiredInputs.forEach(input => {
                const value = input.value ? input.value.trim() : '';
                if (!value) {
                    isValid = false;
                    input.style.borderColor = '#dc3545'; // Red border for empty required fields
                    input.style.backgroundColor = '#ffe6e6'; // Light red background
                } else {
                    input.style.borderColor = '#ced4da'; // Reset to normal
                    input.style.backgroundColor = '#fff'; // Reset to normal
                }
            });

            return isValid;
        }

        // Handle next button
        const nextButtons = document.querySelectorAll('#btn-next');
        nextButtons.forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();
                if (validateCurrentPanel() && currentTabIndex < tabs.length - 1) {
                    activateTab(tabs[currentTabIndex + 1]);
                }
            });
        });

        // Handle back button
        const backButtons = document.querySelectorAll('#btn-back');
        backButtons.forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();
                if (currentTabIndex > 0) {
                    activateTab(tabs[currentTabIndex - 1]);
                }
            });
        });

        tabs.forEach(tab => {
            tab.addEventListener('click', () => activateTab(tab));
            tab.addEventListener('keydown', (e) => {
                const idx = tabs.indexOf(tab);
                let nextIndex = null;
                switch (e.key) {
                    case 'ArrowLeft':
                    case 'Left':
                        nextIndex = (idx - 1 + tabs.length) % tabs.length;
                        break;
                    case 'ArrowRight':
                    case 'Right':
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

        // ---------------- Dynamic identifiers management ---------------------
        function addIdentifierRow() {
            const container = document.getElementById('identifiers-container');
            const newRow = document.createElement('div');
            newRow.className = 'identifier-row div-demo';
            newRow.style.cssText = 'border: 1px solid #e6e9ee; padding: 15px; border-radius: 6px; margin-bottom: 15px; position: relative;';
            newRow.innerHTML = `
                    <div class="form-group demo">
                        <label>Type d'identifiant</label>
                        <select class="form-select" name="idType[]" style="width: 100%;">
                            <option value="">-- Sélectionner --</option>
                            <option value="PASSPORT">Passeport</option>
                            <option value="DRIVING_LICENSE">Permis de conduire</option>
                            <option value="ID_CARD">Carte d'identité</option>
                            <option value="BIRTH_CERT">Acte de naissance</option>
                            <option value="VOTER_ID">Carte d'électeur</option>
                        </select>
                    </div>
                    <div class="form-group demo">
                        <label>Numéro d'identifiant</label>
                        <input type="text" class="form-control" name="idNumber[]" placeholder="Entrez le numéro">
                    </div>
                    <button type="button" class="remove-identifier-btn" style="position: absolute; top: 10px; right: 10px; background: #dc3545; color: white; border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer;">Supprimer</button>
                `;
            container.appendChild(newRow);

            // Add remove listener
            newRow.querySelector('.remove-identifier-btn').addEventListener('click', (e) => {
                e.preventDefault();
                newRow.remove();
            });
        }

        const addIdBtn = document.getElementById('add-identifier-btn');
        if (addIdBtn) {
            addIdBtn.addEventListener('click', (e) => {
                e.preventDefault();
                addIdentifierRow();
            });
        }

        // Add remove listener to initial identifier row
        const initialRemoveBtn = document.querySelector('.identifier-row .remove-identifier-btn');
        if (initialRemoveBtn) {
            initialRemoveBtn.addEventListener('click', (e) => {
                e.preventDefault();
                initialRemoveBtn.closest('.identifier-row').remove();
            });
        }
        // ---------------- FIN Dynamic identifiers ---------------------

        // ---------------- Dynamic Contact Point management ---------------------
        function addIdentifierRow1() {
            const container = document.getElementById('pcontact-container');
            const newRow = document.createElement('div');
            newRow.className = 'pcontact-row div-demo';
            newRow.style.cssText = 'border: 1px solid #e6e9ee; padding: 15px; border-radius: 6px; margin-bottom: 15px; position: relative;';
            newRow.innerHTML = `
                    <div class="form-group demo">
                        <label>Type de réseau</label>
                        <select class="form-select" name="idType[]" style="width: 100%;">
                            <option value="">-- Sélectionner --</option>
                            <option value="Facebook">Facebook</option>
                            <option value="TikTok">TikTok</option>
                            <option value="Instagram">Instagram</option>
                            <option value="Action">Tweeter</option>
                            <option value="VOTER_ID">Linked</option>
                        </select>
                    </div>
                    <div class="form-group demo">
                        <label>Numéro d'identifiant</label>
                        <input type="text" class="form-control" name="idNumber[]" placeholder="Entrez le numéro">
                    </div>
                    <button type="button" class="remove-pcontact-btn" style="position: absolute; top: 10px; right: 10px; background: #dc3545; color: white; border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer;">Supprimer</button>
                `;
            container.appendChild(newRow);

            // Add remove listener
            newRow.querySelector('.remove-pcontact-btn').addEventListener('click', (e) => {
                e.preventDefault();
                newRow.remove();
            });
        }

        const addIdBtn1 = document.getElementById('add-pcontact-btn');
        if (addIdBtn1) {
            addIdBtn1.addEventListener('click', (e) => {
                e.preventDefault();
                addIdentifierRow1();
            });
        }

        // Add remove listener to initial identifier row
        const initialRemoveBtn1 = document.querySelector('.pcontact-row .remove-pcontact-btn');
        if (initialRemoveBtn1) {
            initialRemoveBtn1.addEventListener('click', (e) => {
                e.preventDefault();
                initialRemoveBtn1.closest('.pcontact-row').remove();
            });
        }
        // ---------------- FIN Dynamic Contact Point ---------------------



        // Initialize button states
        updateButtonStates();

        QuickAddress("departement_birthdate", "commune_birthdate", "sectionCommunale_birthdate", "localite_birthdate");
        QuickAddress("departement_demo", "commune_demo", "sectionCommunale_demo", "localite_demo");
        QuickAddress("departement_pcontact", "commune_pcontact", "sectionCommunale_pcontact", "localite_pcontact");
        QuickAddress("departement_presponsable", "commune_presponsable", "sectionCommunale_presponsable", "localite_presponsable");
    });

</script>


<style>

* {
    box-sizing: border-box;
}

body {
    font-family: Inter, system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial;
    background: #f7f8fa;
    color: #0f172a;
    margin: 0;
    padding: 0;
}

.tabs {
    width: 98%;
    max-width: 1350px;
    margin: 0 auto;
    background: white;
    border-radius: 12px;
    box-shadow: 0 3px 5px -5px;
    overflow: hidden;
    margin-top: -5px;
}

.tablist {
    display: flex;
    gap: 4px;
    padding: 8px;
    background: linear-gradient(90deg, #ffffff, #fbfbff);
    border-bottom: 1px solid #e6e9ee;
    overflow-x: auto;
    -webkit-overflow-scrolling: touch;
}

.tab {
    appearance: none;
    border: 0;
    background: transparent;
    padding: 10px 16px;
    border-radius: 10px;
    cursor: pointer;
    font-weight: 600;
    font-size: 14px;
    color: #586674;
    margin-right: 20px;
    white-space: nowrap;
    flex-shrink: 0;
    transition: all 0.2s ease;
}

.tab[aria-selected="true"] {
    color: #0b4a7b;
    background: linear-gradient(180deg, #eef8ff, #e6f1fb);
    box-shadow: 0 2px 6px rgba(11, 74, 123, 0.08) inset;
}

.tab:focus {
    outline: 3px solid rgba(11, 74, 123, 0.14);
    outline-offset: 3px;
}

.panels {
    padding: 20px;
}

.panel {
    display: none;
}

.panel[hidden] {
    display: none;
}

.panel.active {
    display: block;
    animation: fadeIn 0.3s ease-in;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(10px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.panel h2 {
    margin-top: 0;
    font-size: 20px;
    color: #0f172a;
    margin-bottom: 20px;
}

.info-dem,
.info-pers,
.info-sociodem {
    display: grid;
    gap: 16px;
}

.form-control, .form-select {
    border-radius: 5px;
    padding: 12px 10px;
    border: 1px solid #ced4da;
    box-shadow: none;
    transition: all 0.1s ease-in-out;
    font-size: 16px;
    width: 100%;
    font-family: inherit;
    -webkit-appearance: none;
    appearance: none;
    background-color: #fff;
}

.form-select {
    background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16'%3e%3cpath fill='none' stroke='%23343a40' stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='M2 5l6 6 6-6'/%3e%3c/svg%3e");
    background-repeat: no-repeat;
    background-position: right 0.75rem center;
    background-size: 16px 12px;
    padding-right: 2.5rem;
}

.form-control:focus, .form-select:focus {
    border: 2px solid #0b4a7b;
    background-color: #fff;
    outline: none;
    box-shadow: 0 0 0 0.2rem rgba(11, 74, 123, 0.25);
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 6px;
}

.form-group label {
    font-weight: 500;
    font-size: 14px;
    color: #0f172a;
}

.button-group {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 30px;
    gap: 10px;
    flex-wrap: wrap;
}

.button-group button {
    padding: 12px 24px;
    /*border: 1px solid #d0d7de;*/
    /*border-radius: 6px;*/
    background-color: #fff;
    /*color: #24292f;*/
    /*font-weight: 500;*/
    /*font-size: 14px;*/
    /*cursor: pointer;*/
    /*transition: all 0.2s ease;*/
    /*flex: 1;*/
    /*min-width: 120px;*/
}

.button-group button:hover:not(:disabled) {
    background-color: #eaeef2;
    border-color: #c9cfd5;
}

.button-group button.btn-primary {
    background-color: #0b4a7b;
    color: white;
    /*border-color: #0b4a7b;*/
}

.button-group button.btn-primary:hover:not(:disabled) {
    background-color: #083456;
    border-color: #083456;
    color: white;
}

.button-group button:disabled {
    opacity: 0.5;
    cursor: not-allowed;
}

.info-fingerprint {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 10px;
    background: transparent;
    width: 100%;
}

.fingerprint-btn {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 50px;
    height: 60px;
    min-width: 60px;
    border-radius: 60%;
    background: #fff;
    box-shadow: 0px 2px 6px -4px rgba(0, 0, 0, 0.2);
    border: 1px solid #e6e9ee;
    text-decoration: none;
    color: inherit;
    cursor: pointer;
    transition: all 0.2s ease;
    flex-shrink: 0;
    touch-action: manipulation;
}

.fingerprint-btn:hover {
    background: #f6f8fa;
    border-color: #7cc8af;
    box-shadow: 0px 2px 8px -2px rgba(11, 74, 123, 0.15);
}

.fingerprint-btn:active {
    transform: scale(0.95);
}

.fingerprint-btn i {
    font-size: 20px;
}

.info-fingerprint .form-control {
    margin: 0;
    flex: 1;
}

fieldset {
    border: 1px solid #e6e9ee;
    padding: 15px;
    border-radius: 6px;
    margin: 16px 0;
}

legend {
    padding: 0 10px;
    font-weight: 600;
    color: #0f172a;
    font-size: 14px;
}

.identifier-row {
    border: 1px solid #e6e9ee;
    padding: 15px;
    border-radius: 6px;
    margin-bottom: 16px;
    position: relative;
}

.remove-identifier-btn {
    position: absolute;
    top: 10px;
    right: 10px;
    background: #dc3545;
    color: white;
    border: none;
    padding: 6px 10px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 12px;
    transition: all 0.2s ease;
    touch-action: manipulation;
}

.remove-identifier-btn:hover {
    background: #c82333;
}

.remove-identifier-btn:active {
    transform: scale(0.95);
}

#add-identifier-btn, #add-pcontact-btn, #add-presponsable-btn {
    background: #0b4a7b;
    color: white;
    border: none;
    padding: 12px 16px;
    border-radius: 6px;
    cursor: pointer;
    font-weight: 500;
    margin-top: 10px;
    width: 100%;
    font-size: 14px;
    transition: all 0.2s ease;
    touch-action: manipulation;
}

#add-identifier-btn:hover {
    background: #083456;
}
#add-pcontact-btn:hover {
    background: #083456;
}
#add-presponsable-btn:hover {
    background: #083456;
}

#add-identifier-btn:active {
    transform: scale(0.98);
}
#add-pcontact-btn:active {
    transform: scale(0.98);
}
#add-presponsable-btn:active {
    transform: scale(0.98);
}

/* Checkbox groups styling */
.checkbox-group,
.radio-group {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.checkbox-item,
.radio-item {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 8px;
}

.checkbox-item label,
.radio-item label {
    margin-bottom: 0;
    cursor: pointer;
    flex: 1;
}

.radio-group-row {
    display: flex;
    gap: 20px;
    flex-wrap: wrap;
}

#btn-next{
    /*background: #f6ffff;*/
}

.div-demo {
    display: flex;
    flex-direction: row;
    width: 100%;
    gap: 20px;
}
.demo {
    width: 50%;
}

.div-ids {
    display: flex;
    flex-direction: row;
    width: 100%;
    gap: 20px;
}
.ids {
    width: 33%;
}

.div-point-contact {
    display: flex;
    flex-direction: row;
    width: 100%;
    gap: 20px;
}
.pcontact{
    width: 33%;
}

.div-fp {
    display: flex;
    flex-direction: row;
    width: 100%;
    gap: 50px;
}
.fp {
    width: 48%;
}

@media (max-width: 768px) {
    .radio-group-row {
        gap: 16px;
    }
}

@media (max-width: 540px) {
    .checkbox-group,
    .radio-group {
        gap: 10px;
    }

    .radio-group-row {
        flex-direction: column;
        gap: 10px;
    }

    .radio-item {
        gap: 10px;
    }

}

/* Responsive Typography */
@media (max-width: 768px) {
    .tabs {
        width: 100%;
        margin: 0;
        border-radius: 0;
    }

    .panels {
        padding: 16px;
    }

    .panel h2 {
        font-size: 18px;
        margin-bottom: 16px;
    }

    .tablist {
        gap: 2px;
        padding: 6px;
    }

    .tab {
        padding: 8px 12px;
        font-size: 13px;
        margin-right: 10px;
        min-width: auto;
    }

    .button-group {
        flex-direction: column;
        margin-top: 24px;
    }

    .button-group button {
        flex: 1;
        min-width: auto;
        width: 100%;
        padding: 14px 16px;
        font-size: 16px;
    }

    .form-control, .form-select {
        font-size: 16px;
        padding: 14px 12px;
    }

    .form-group {
        margin-bottom: 0;
    }

    .info-dem,
    .info-pers,
    .info-sociodem {
        gap: 14px;
    }

    .info-fingerprint {
        flex-direction: column;
        align-items: stretch;
    }

    .fingerprint-btn {
        width: 100%;
        height: 48px;
        border-radius: 6px;
    }



    fieldset {
        padding: 12px;
        margin: 12px 0;
    }

    legend {
        font-size: 13px;
    }

    .div-demo {
        flex-direction: column;
        width: 100%;
        gap: 20px;
    }
    .demo {
        width: 100%;
    }

    .div-fp {
        display: flex;
        flex-direction: column;
        width: 100%;
        gap: 20px;
    }
    .fp {
        width: 100%;
    }
}

@media (max-width: 540px) {
    .tabs {
        box-shadow: none;
        border-radius: 0;
    }

    .tablist {
        flex-wrap: wrap;
        gap: 1px;
        padding: 4px;
        background: linear-gradient(90deg, #f0f0f0, #f8f8f8);
    }

    .tab {
        flex: 1 1 calc(50% - 2px);
        padding: 10px 8px;
        font-size: 12px;
        margin-right: 0;
        text-align: center;
    }

    .tab[aria-selected="true"] {
        flex: 1 1 100%;
        margin-bottom: 4px;
    }

    .panels {
        padding: 12px;
    }

    .panel h2 {
        font-size: 16px;
        margin-bottom: 12px;
    }

    .form-control, .form-select {
        font-size: 16px;
        padding: 12px 10px;
    }

    .form-group label {
        font-size: 13px;
    }

    .button-group {
        flex-direction: column;
        margin-top: 20px;
        gap: 8px;
    }

    .button-group button {
        width: 100%;
        padding: 12px 16px;
        font-size: 15px;
    }

    .info-dem,
    .info-pers,
    .info-sociodem {
        gap: 12px;
    }

    .fingerprint-btn {
        width: 100%;
        height: 44px;
        border-radius: 4px;
    }

    .info-fingerprint {
        flex-direction: column;
    }

    fieldset {
        padding: 10px;
        margin: 10px 0;
    }

    .div-demo {
        display: flex;
        flex-direction: column;
        width: 100%;
        gap: 20px;
    }

    .demo {
        width: 100%;
    }
}

@media (max-width: 360px) {
    .tab {
        flex: 1 1 100%;
        font-size: 11px;
    }

    .panels {
        padding: 10px;
    }

    .form-control, .form-select {
        font-size: 15px;
        padding: 11px 8px;
    }

    .button-group button {
        padding: 11px 12px;
        font-size: 14px;
    }

    .div-demo {
        display: flex;
        flex-direction: column;
        width: 100%;
        gap: 20px;
    }

    .demo {
        width: 100%;
    }

}

#btn-submit:hover {
    background: #083456;
}


</style>


<div style="width: 100%; display: flex; flex-direction: column; min-height: 100vh;">

    <main class="tabs">

        <div class="tablist" role="tablist" aria-label="Exemple d'onglets">
            <button class="tab" role="tab" aria-selected="true" aria-controls="panel-1" id="tab-1"
                    tabindex="0">Empreintes digitales</button>
            <button class="tab" role="tab" aria-selected="false" aria-controls="panel-2" id="tab-2"
                    tabindex="-1">Données démographiques</button>
            <button class="tab" role="tab" aria-selected="false" aria-controls="panel-3" id="tab-3"
                    tabindex="-1">Identifiants</button>
            <button class="tab" role="tab" aria-selected="false" aria-controls="panel-4" id="tab-4"
                    tabindex="-1">Point de Contacts</button>
            <button class="tab" role="tab" aria-selected="false" aria-controls="panel-5" id="tab-5"
                    tabindex="-1">Contact d'urgence</button>
            <button class="tab" role="tab" aria-selected="false" aria-controls="panel-6" id="tab-6"
                    tabindex="-1">Personne responsable</button>
            <button class="tab" role="tab" aria-selected="false" aria-controls="panel-7" id="tab-7"
                    tabindex="-1">Vie Socio-economique</button>
        </div>

        <form id="form" method="POST" class="panels">
            <div id="panel-1" class="panel active" role="tabpanel" aria-labelledby="tab-1">
                <h2>Veuillez prendre les empreintes digitales du patient</h2>

                <div class="info-pers div-fp">
                    <div class="form-group fp">
                        <label for="registrationDate">Date de l'enregistrement</label>
                        <input type="date"
                               class="form-control"
                               name="registrationDate"
                               id="registrationDate" required>
                    </div>

                    <div class="form-group fp">
                        <label for="nom">Empreintes du patient</label>

                        <div class="info-fingerprint">
                            <a id="btnScanFingerprint" class="fingerprint-btn" href="" title="Prendre les empreintes">
                                <i class="icon-hand-up" style="color: green; font-size: 40px"></i>
                            </a>
                            <input type="text" class="form-control" name="" value="" id="quickFinger" placeholder="Cliquer sur l’icône pour la prise d’empreinte digitale !" disabled>
                            <input hidden="hidden" type="text" class="form-control" name="fingerprint" value="" id="fingerprint"/>
                            <input hidden="hidden" type="text" class="form-control" name="deviceName" value="" id="deviceName"/>
                            <input hidden="hidden" type="text" class="form-control" name="templateFormat" value="" id="templateFormat"/>
                            <input hidden="hidden" type="text" class="form-control" name="captureType" value="" id="captureType"/>
                            <input hidden="hidden" type="text" class="form-control" name="serverUrl" value="${serverUrl}" id="serverUrl"/>
                        </div>
                    </div>
                </div>

                <div class="button-group">
                    <button id="btn-back" type="button" style="display: none;">← Retour</button>
                    <button id="btn-next" type="button">Suivant <i class="icon-chevron-right"
                                                                   style="font-size: 15px"></i></button>
                </div>
            </div>

            <div id="panel-2" class="panel" role="tabpanel" aria-labelledby="tab-2">
                <h2>Informations démographiques</h2>

                <div class="info-dem">
                    <div class="div-demo">
                        <div class="form-group demo">
                            <label for="firstName">Prénom</label>
                            <input type="text" class="form-control" name="firstName" id="firstName" value="" required>
                        </div>

                        <div class="form-group demo">
                            <label for="lastName">Nom de famille</label>
                            <input type="text" class="form-control" name="lastName" id="lastName" value="" required>
                        </div>
                    </div>

                    <div class="div-demo">
                        <div class="form-group demo">
                            <label for="dateOfBirth">Date de naissance</label>
                            <input type="date" class="form-control" name="birthDate" id="dateOfBirth" value="" required>
                        </div>

                        <div class="form-group demo">
                            <label for="gender">Sexe</label>
                            <select class="form-select" name="gender" id="gender" required>
                                <option value="">-- Sélectionner --</option>
                                <option value="M">Masculin</option>
                                <option value="F">Féminin</option>
                            </select>
                        </div>
                    </div>

                    <fieldset>
                        <legend>Lieu de naissance</legend>

                        <div class="form-group">
                            <label for="departement_birthdate">Département</label>
                            <select class="form-select" name="departement_birthdate" id="departement_birthdate" required>
                                <option value="">-- Sélectionner --</option>
                                <% stateProvinces.each { stateProvince -> %>
                                <option value="${stateProvince.id}">
                                    ${stateProvince.name}
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="commune_birthdate">Commune</label>
                            <select class="form-select" name="commune_birthdate" id="commune_birthdate" required>
                                <option value="">-- Sélectionner --</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="sectionCommunale_birthdate">Section Communale</label>
                            <select class="form-select" name="sectionCommunale_birthdate" id="sectionCommunale_birthdate" required>
                                <option value="">-- Sélectionner --</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="localite_birthdate">Localité</label>
                            <select class="form-select" name="localite_birthdate" id="localite_birthdate" required>
                                <option value="">-- Sélectionner --</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="additionalAdresses_birthdate">Adresse fixe</label>
                            <input type="text" class="form-control" name="additionalAdresses_birthdate" id="additionalAdresses_birthdate" placeholder="">
                        </div>
                    </fieldset>
                </div>

                <div class="button-group">
                    <button id="btn-back" type="button"><i class="icon-chevron-left" style="font-size: 15px"></i> Retour
                    </button>
                    <button id="btn-next" type="button">Suivant <i class="icon-chevron-right"
                                                                   style="font-size: 15px"></i></button>
                </div>
            </div>

            <div id="panel-3" class="panel" role="tabpanel" aria-labelledby="tab-3" hidden>
                <h2>Identifications</h2>

                <div class="info-dem div-ids">
                    <div class="form-group ids">
                        <label for="codeST">St Code</label>
                        <input type="number" class="form-control" name="codeST" id="codeST" value="">
                    </div>

                    <div class="form-group ids">
                        <label for="codeNational">Code National</label>
                        <input type="text" class="form-control" name="codeNational" id="codeNational" value="" required>
                    </div>

                    <div class="form-group ids">
                        <label for="codePC">Code PC</label>
                        <input type="text" class="form-control" name="codePC" id="codePC" value="">
                    </div>
                </div>
                <br/>

                <div class="info-dem">
                    <div id="identifiers-container">
                        <!-- First identifier row (default) -->
                    </div>

                    <button id="add-identifier-btn" type="button">+ Ajouter un identifiant</button>
                </div><br/>

                <div class="button-group">
                    <button id="btn-back" type="button"><i class="icon-chevron-left" style="font-size: 15px"></i> Retour
                    </button>
                    <button id="btn-next" type="button">Suivant <i class="icon-chevron-right"
                                                                   style="font-size: 15px"></i></button>
                </div>
            </div>

            <div id="panel-4" class="panel" role="tabpanel" aria-labelledby="tab-4" hidden>
                <h2>Point de Contacts</h2>

                <div class="info-dem">
                    <div class="div-point-contact">
                        <div class="form-group pcontact">
                            <label for="contactPhone">Téléphone</label>
                            <input type="tel" class="form-control" name="contactPhone" id="contactPhone" value="">
                        </div>

                        <div class="form-group pcontact">
                            <label for="contactEmail">Email</label>
                            <input type="email" class="form-control" name="contactEmail" id="contactEmail" value="">
                        </div>

                        <div class="form-group pcontact">
                            <label for="contactWhatsapp">Whatsapp</label>
                            <input type="tel" class="form-control" name="contactWhatsapp" id="contactWhatsapp" value="">
                        </div>
                    </div>

                    <div class="info-dem">
                        <div id="pcontact-container">
                        </div>
                        <button id="add-pcontact-btn" type="button"><span style="font-size: x-large">+</span> Ajouter un Réseau Social</button>
                    </div><br/>

                    <fieldset>
                        <legend>Adresse du Patient</legend>

                        <div class="form-group">
                            <label for="departement_demo">Département</label>
                            <select class="form-select" name="departement_demo" id="departement_demo" required>
                                <option value="">-- Sélectionner --</option>
                                <% stateProvinces.each { stateProvince -> %>
                                <option value="${stateProvince.id}">
                                    ${stateProvince.name}
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="commune_demo">Commune</label>
                            <select class="form-select" name="commune_demo" id="commune_demo" required>
                                <option value="">-- Sélectionner --</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="sectionCommunale_demo">Section Communale</label>
                            <select class="form-select" name="sectionCommunale_demo" id="sectionCommunale_demo" required>
                                <option value="">-- Sélectionner --</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="localite_demo">Localité</label>
                            <select class="form-select" name="localite_demo" id="localite_demo" required>
                                <option value="">-- Sélectionner --</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="additionalAdresses_demo">Adresse fixe</label>
                            <input type="text" class="form-control" name="additionalAdresses_demo" id="additionalAdresses_demo" placeholder="">
                        </div>
                    </fieldset>

                </div>

                <div class="button-group">
                    <button id="btn-back" type="button"><i class="icon-chevron-left" style="font-size: 15px"></i> Retour
                    </button>
                    <button id="btn-next" type="button">Suivant <i class="icon-chevron-right"
                                                                   style="font-size: 15px"></i></button>
                </div>
            </div>

            <div id="panel-5" class="panel" role="tabpanel" aria-labelledby="tab-5" hidden>
                <h2>Contact en cas d'urgence</h2>

                <div class="info-dem">
                    <div class="div-point-contact">
                        <div class="form-group pcontact">
                            <label for="contactName">Nom et Prénom</label>
                            <input type="text" class="form-control" name="pcontactName" id="pcontactName" value="">
                        </div>

                        <div class="form-group pcontact">
                            <label for="contactPhone">Numéro de téléphone</label>
                            <input type="tel" class="form-control" name="pcontactPhone" id="pcontactPhone" value="">
                        </div>

                        <div class="form-group pcontact">
                            <label for="relation">Relation</label>
                            <select class="form-select" name="prelation" id="prelation">
                                <option value="">-- Sélectionner --</option>
                                <% relationshipsList.each { relation -> %>
                                <option value="${relation.answerConcept}">${relation.name}</option>
                                <% } %>
                            </select>
                        </div>
                    </div>

                    <fieldset>
                        <legend>Adresse du contact</legend>

                        <div class="form-group">
                            <label for="departement_pcontact">Département</label>
                            <select class="form-select" name="departement_pcontact" id="departement_pcontact" required>
                                <option value="">-- Sélectionner --</option>
                                <% stateProvinces.each { stateProvince -> %>
                                <option value="${stateProvince.id}">
                                    ${stateProvince.name}
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="commune_pcontact">Commune</label>
                            <select class="form-select" name="commune_pcontact" id="commune_pcontact" required>
                                <option value="">-- Sélectionner --</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="sectionCommunale_pcontact">Section Communale</label>
                            <select class="form-select" name="sectionCommunale_pcontact" id="sectionCommunale_pcontact" required>
                                <option value="">-- Sélectionner --</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="localite_pcontact">Localité</label>
                            <select class="form-select" name="localite_pcontact" id="localite_pcontact" required>
                                <option value="">-- Sélectionner --</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="additionalAdresses_pcontact">Adresse fixe</label>
                            <input type="text" class="form-control" name="additionalAdresses_pcontact" id="additionalAdresses_pcontact" placeholder="">
                        </div>
                    </fieldset>
                </div>

                <div class="button-group">
                    <button id="btn-back" type="button"><i class="icon-chevron-left" style="font-size: 15px"></i> Retour
                    </button>
                    <button id="btn-next" type="button">Suivant <i class="icon-chevron-right"
                                                                   style="font-size: 15px"></i></button>
                </div>
            </div>

            <div id="panel-6" class="panel" role="tabpanel" aria-labelledby="tab-6" hidden>
                <h2>Personne responsable</h2>

                <div class="info-dem">
                    <div class="div-point-contact">
                        <div class="form-group pcontact">
                            <label for="contactName_presp">Nom et Prénom</label>
                            <input type="text" class="form-control" name="contactName_presp" id="contactName_presp" value="">
                        </div>

                        <div class="form-group pcontact">
                            <label for="contactPhone_presp">Numéro de téléphone</label>
                            <input type="tel" class="form-control" name="contactPhone_presp" id="contactPhone_presp" value="">
                        </div>

                        <div class="form-group pcontact">
                            <label for="relation_presp">Relation</label>
                            <select class="form-select" name="relation_presp" id="relation_presp">
                                <option value="">-- Sélectionner --</option>
                                <% relationshipsList.each { relation -> %>
                                <option value="${relation.answerConcept}">${relation.name}</option>
                                <% } %>
                            </select>
                        </div>
                    </div>

                    <fieldset>
                        <legend>Adresse du contact</legend>

                        <div class="form-group">
                            <label for="departement_presponsable">Département</label>
                            <select class="form-select" name="departement_presponsable" id="departement_presponsable" required>
                                <option value="">-- Sélectionner --</option>
                                <% stateProvinces.each { stateProvince -> %>
                                <option value="${stateProvince.id}">
                                    ${stateProvince.name}
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="commune_presponsable">Commune</label>
                            <select class="form-select" name="commune_presponsable" id="commune_presponsable" required>
                                <option value="">-- Sélectionner --</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="sectionCommunale_presponsable">Section Communale</label>
                            <select class="form-select" name="sectionCommunale_presponsable" id="sectionCommunale_presponsable" required>
                                <option value="">-- Sélectionner --</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="localite_presponsable">Localité</label>
                            <select class="form-select" name="localite_presponsable" id="localite_presponsable" required>
                                <option value="">-- Sélectionner --</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="additionalAdresses_presponsable">Adresse fixe</label>
                            <input type="text" class="form-control" name="additionalAdresses_presponsable" id="additionalAdresses_presponsable" placeholder="">
                        </div>
                    </fieldset>
                </div>

                <div class="button-group">
                    <button id="btn-back" type="button"><i class="icon-chevron-left" style="font-size: 15px"></i> Retour
                    </button>
                    <button id="btn-next" type="button">Suivant <i class="icon-chevron-right"
                                                                   style="font-size: 15px"></i></button>
                </div>
            </div>

            <div id="panel-7" class="panel" role="tabpanel" aria-labelledby="tab-7">
                <h2>Informations socio-économiques</h2>

                <div class="info-sociodem">
                    <div class="div-demo">
                        <div class="form-group demo">
                            <label for="maritalStatus">Statut Marital</label>
                            <select class="form-select" name="maritalStatus" id="maritalStatus">
                                <option value="">-- Sélectionner --</option>
                                <option value="MARIED">Marié(e)</option>
                                <option value="COHABITATION">Concubinage</option>
                                <option value="WIDOWER">Veuf(ve)</option>
                                <option value="SEPARATED">Séparé(e)</option>
                                <option value="DIVORCED">Divorcé(e)</option>
                                <option value="SINGLE">Célibataire</option>
                                <option value="UNKNOWN">Inconnu</option>
                            </select>
                        </div>
                        <div class="form-group demo">
                            <label for="maritalStatus">Éducation</label>
                            <select class="form-select" name="maritalStatus" id="maritalStatus">
                                <option value="">-- Sélectionner --</option>
                                <option value="MARIED">Marié(e)</option>
                                <option value="COHABITATION">Concubinage</option>
                                <option value="WIDOWER">Veuf(ve)</option>
                                <option value="SEPARATED">Séparé(e)</option>
                                <option value="DIVORCED">Divorcé(e)</option>
                                <option value="SINGLE">Célibataire</option>
                                <option value="UNKNOWN">Inconnu</option>
                            </select>
                        </div>
                    </div>

                    <div class="div-demo">
                        <div class="form-group demo">
                            <label for="maritalStatus">Profession</label>
                            <select class="form-select" name="maritalStatus" id="maritalStatus">
                                <option value="">-- Sélectionner --</option>
                                <option value="MARIED">Marié(e)</option>
                                <option value="COHABITATION">Concubinage</option>
                                <option value="WIDOWER">Veuf(ve)</option>
                                <option value="SEPARATED">Séparé(e)</option>
                                <option value="DIVORCED">Divorcé(e)</option>
                                <option value="SINGLE">Célibataire</option>
                                <option value="UNKNOWN">Inconnu</option>
                            </select>
                        </div>
                        <div class="form-group demo">
                            <label for="maritalStatus">Religion</label>
                            <select class="form-select" name="maritalStatus" id="maritalStatus">
                                <option value="">-- Sélectionner --</option>
                                <option value="MARIED">Marié(e)</option>
                                <option value="COHABITATION">Concubinage</option>
                                <option value="WIDOWER">Veuf(ve)</option>
                                <option value="SEPARATED">Séparé(e)</option>
                                <option value="DIVORCED">Divorcé(e)</option>
                                <option value="SINGLE">Célibataire</option>
                                <option value="UNKNOWN">Inconnu</option>
                            </select>
                        </div>
                    </div>

                    <div class="div-demo">
                        <div class="demo">
                            <fieldset style="width: 100%; height: 93%">
                                <legend>Plaintes de nature socio-économique</legend>

                                <div class="checkbox-group">
                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="socioeconomic" id="unemployed"
                                               value="Chômeur/sans emploi">
                                        <label for="unemployed">Chômeur/sans emploi</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="socioeconomic" id="hunger"
                                               value="Faim">
                                        <label for="hunger">Faim</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="socioeconomic" id="water"
                                               value="Inaccessibilité d'eau potable">
                                        <label for="water">Inaccessibilité d'eau potable</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="socioeconomic" id="burial"
                                               value="Frais d'enterrement">
                                        <label for="burial">Frais d'enterrement</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="socioeconomic"
                                               id="housing_problem" value="Problème avec le domicile">
                                        <label for="housing_problem">Problème avec le domicile</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="socioeconomic" id="school_fees"
                                               value="Frais scolaires">
                                        <label for="school_fees">Frais scolaires</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="socioeconomic" id="transport"
                                               value="Frais de transport">
                                        <label for="transport">Frais de transport</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="socioeconomic" id="accommodation"
                                               value="Frais de logement">
                                        <label for="accommodation">Frais de logement</label>
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                        <div class="demo">
                            <fieldset style="width: 100%; height: 93%">
                                <legend>Confort Ménager</legend>

                                <div class="checkbox-group">
                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="comfort" id="computer"
                                               value="Ordinateur">
                                        <label for="computer">Ordinateur</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="comfort" id="radio" value="Radio">
                                        <label for="radio">Radio</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="comfort" id="television"
                                               value="Téléviseur">
                                        <label for="television">Téléviseur</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="comfort" id="refrigerator"
                                               value="Réfrigérateur">
                                        <label for="refrigerator">Réfrigérateur</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="comfort" id="internet"
                                               value="Connexion internet">
                                        <label for="internet">Connexion internet</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="comfort" id="stove"
                                               value="Cuisinière à gaz ou à pétrole">
                                        <label for="stove">Cuisinière à gaz ou à pétrole</label>
                                    </div>

                                    <div class="checkbox-item">
                                        <input type="checkbox" class="form-check-input" name="comfort" id="lamp"
                                               value="Lampe à gaz ou à pétrole">
                                        <label for="lamp">Lampe à gaz ou à pétrole</label>
                                    </div>
                                </div>

                                <hr style="
                                width: 100%;
                                border: none;
                                border-top: 1px solid #e6e9ee;
                                margin: 16px 0;
                                flex-basis: 100%;
                                ">


                                <div class="div-demo demo">
                                    <div class="form-group">
                                        <label>Douche</label>
                                        <div class="radio-group-row">
                                            <div class="radio-item">
                                                <input type="radio" class="form-check-input" name="shower" id="shower_yes"
                                                       value="Oui">
                                                <label for="shower_yes">Oui</label>
                                            </div>

                                            <div class="radio-item">
                                                <input type="radio" class="form-check-input" name="shower" id="shower_no"
                                                       value="Non">
                                                <label for="shower_no">Non</label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group demo">
                                        <label>Toilettes</label>

                                        <div class="radio-group-row">
                                            <div class="radio-item">
                                                <input type="radio" class="form-check-input" name="toilets" id="toilets_yes"
                                                       value="Oui">
                                                <label for="toilets_yes">Oui</label>
                                            </div>

                                            <div class="radio-item">
                                                <input type="radio" class="form-check-input" name="toilets" id="toilets_no"
                                                       value="Non">
                                                <label for="toilets_no">Non</label>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group demo">
                                        <label>Électricité</label>

                                        <div class="radio-group-row">
                                            <div class="radio-item">
                                                <input type="radio" class="form-check-input" name="electricity" id="electricity_yes"
                                                       value="Oui">
                                                <label for="electricity_yes">Oui</label>
                                            </div>

                                            <div class="radio-item">
                                                <input type="radio" class="form-check-input" name="electricity" id="electricity_no"
                                                       value="Non">
                                                <label for="electricity_no">Non</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </div>
                    </div>


                    <div class="div-demo">
                        <div class="form-group demo">
                            <label for="floorCovering">Revêtement de sol</label>
                            <select class="form-select" name="floorCovering" id="floorCovering">
                                <option value="">-- Sélectionner --</option>
                                <option value="CONCRETE">Béton / ciment</option>
                                <option value="EARTH">Terre battue</option>
                                <option value="CARPET">Tapis</option>
                                <option value="WOOD">Parquet bois</option>
                                <option value="CERAMIC">Tuile en céramique</option>
                            </select>
                        </div>

                        <div class="form-group demo">
                            <label for="roofMaterial">Matériau de couverture</label>
                            <select class="form-select" name="roofMaterial" id="roofMaterial">
                                <option value="">-- Sélectionner --</option>
                                <option value="BAMBOO">Bambou</option>
                                <option value="CONCRETE">Béton / ciment</option>
                                <option value="TARP">Bâche</option>
                                <option value="STRAW">Pailles</option>
                                <option value="CORRUGATED">Tôle ondulée</option>
                                <option value="NONE">Aucun</option>
                            </select>
                        </div>

                        <div class="form-group demo">
                            <label for="transport">Transport</label>
                            <select class="form-select" name="transport" id="transport">
                                <option value="">-- Sélectionner --</option>
                                <option value="MOTORCYCLE">Moto</option>
                                <option value="BICYCLE">Vélo</option>
                                <option value="CAR">Voiture</option>
                                <option value="ANIMAL">Animal</option>
                            </select>
                        </div>
                    </div>
                </div>
                <br/>

                <div class="button-group">
                    <button id="btn-back" type="button"><i class="icon-chevron-left" style="font-size: 15px"></i> Retour
                    </button>
                    <button id="btn-submit" type="submit" class="btn-primary"
                            style="display: none; background: #0b4a7b;">Enregistrer le patient</button>
                </div>
            </div>
        </form>
    </main>
</div>











