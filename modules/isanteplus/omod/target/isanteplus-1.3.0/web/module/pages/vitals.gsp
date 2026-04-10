<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeJavascript("coreapps", "visit/jquery.dataTables.js")
    ui.includeJavascript("coreapps", "visit/filterTable.js")
    ui.includeCss("coreapps", "visit/visits.css")
%>

<script type="text/javascript">
    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {label: "${ ui.message("isanteplus.vitals.app.label")}"}
    ];


    jQuery(function (jq) {

        let mode = "create"; // "create" | "update"

        function switchToCreateMode() {
            mode = "create";
            jq("#encounterId").val("");
            jq("#modeId").val("create");
            jq("#submit")
                .val("Enregistrer")
                .css({background: "#3b5371", color: "#fff", border: "1px solid #fff"});
        }

        function switchToUpdateMode() {
            mode = "update";
            jq("#modeId").val("update");
            jq("#submit")
                .val("Mise à jour")
                .css({background: "forestgreen", color: "#fff", border: "1px solid #fff"});
        }

        function switchToDeleteMode() {
            mode = "delete";
            jq("#modeId").val("delete");
        }


        // ----------------------
        // EDIT : on ne sauvegarde pas ici, remonte les donnees dans les champs !
        // ----------------------
        jq(document).on("click", ".edit-btn", function (e) {
            e.preventDefault();
            switchToUpdateMode();

            // Récupérer la ligne parente
            const row = jq(this).closest("tr");

            const encounterId = row.data("encounter-id");

            const obsIdPoids = row.find('td[data-concept="5089"]').data('obs-id');
            const obsIdTaille = row.find('td[data-concept="5090"]').data('obs-id');
            const obsIdTemp = row.find('td[data-concept="5088"]').data('obs-id');
            const obsIdFc = row.find('td[data-concept="5087"]').data('obs-id');
            const obsIdTasys = row.find('td[data-concept="5085"]').data('obs-id');
            const obsIdTadias = row.find('td[data-concept="5086"]').data('obs-id');
            const obsIdResp = row.find('td[data-concept="5242"]').data('obs-id');
            const obsIdSao2 = row.find('td[data-concept="5092"]').data('obs-id');
            const obsIdSignature = row.find('td[data-concept="1473"]').data('obs-id');

            if (!encounterId) {
                alert("Impossible d’éditer : encounterId manquant sur la ligne.");
                return;
            }

            // Remplir les champs depuis les <td data-concept="...">
            const pick = (c) => row.find("td[data-concept='" + c + "']").text().trim();

            jq("#poids").val(pick("5089"));
            jq("#temp").val(pick("5088"));
            jq("#fc").val(pick("5087"));
            jq("#tasys").val(pick("5085"));
            jq("#tadias").val(pick("5086"));
            jq("#resp").val(pick("5242"));
            jq("#sao2").val(pick("5092"));
            jq("#taille").val(pick("5090"));
            jq("#signature").val(pick("1473"));

            jq("#encounterId").val(encounterId);

            jq("#poidsId").val(obsIdPoids);
            jq("#tailleId").val(obsIdTaille);
            jq("#tempId").val(obsIdTemp);
            jq("#fcId").val(obsIdFc);
            jq("#tasysId").val(obsIdTasys);
            jq("#tadiasId").val(obsIdTadias);
            jq("#respId").val(obsIdResp);
            jq("#sao2Id").val(obsIdSao2);
            jq("#signatureId").val(obsIdSignature);

        });

        // ----------------------
        // RESET → retour en mode création
        // ----------------------
        jq("#reset").on("click", function () {
            switchToCreateMode();
        });

        // ----------------------
        // SUBMIT (CREATE ou UPDATE)
        // ----------------------
        jq("#form").on("submit", function (e) {
            e.preventDefault();

            if (!jq("#signature").val().trim()) {
                alert("Veuillez saisir les initiales de l'infirmier(ère).");
                return;
            }

            const payload = jq(this).serialize(); // x-www-form-urlencoded (attendu par @RequestParam)
            console.log("payload==", payload)

            if (mode === "create") {
                // Appelle la méthode post(...) du PageController
                jq.ajax({
                    url: "${ ui.pageLink('isanteplus','vitals',null) }",
                    type: "POST",
                    data: payload,
                    success: function () {
                        alert("Signes vitaux enregistrés avec succès !");
                        location.reload();
                    },
                    error: function (xhr) {
                        console.error(xhr);
                        alert("Erreur lors de l'enregistrement (" + xhr.status + ").");
                    }
                });
            } else if (mode === "update") {
                // Appelle l’action updateVitals(...) (UI Framework action)
                if (!jq("#encounterId").val()) {
                    return;
                }
                jq.ajax({
                    url: "${ ui.pageLink('isanteplus','vitals',null) }",
                    type: "POST",
                    data: payload, // contient encounterId + champs
                    success: function () {
                        alert("Mise à jour réussie !");
                        location.reload();
                    },
                    error: function (xhr) {
                        console.error(xhr);
                        alert("Erreur lors de la mise à jour (" + xhr.status + ").");
                    }
                });
            }
        });



        // ----------------------
        // DELETE : bouton sur la ligne
        // ----------------------
        jq(document).on("click", ".delete-btn", function (e) {
            e.preventDefault();
            switchToDeleteMode()

            const row = jq(this).closest("tr");
            const encounterId = row.data("encounter-id");

            console.log("encounterId,",encounterId)
            console.log("mode,",mode)



            if (!encounterId) {
                alert("Impossible de supprimer : encounterId manquant.");
                return;
            }

            if (!confirm("Voulez-vous vraiment supprimer ces signes vitaux ?")) {
                return;
            }

            const patientId = jq("#patientId").val()
            const visitId = jq("#visitId").val()


            jq.ajax({
                url: "${ ui.pageLink('isanteplus','vitals',null) }",
                type: "POST",
                data: {
                    modeId: "delete",
                    encounterId: encounterId,
                    signature: "none",
                    patientId : patientId,
                    visitId : visitId
                },
                success: function () {
                    alert("Suppression réussie !");
                    row.remove()
                },
                error: function (xhr) {
                    console.error(xhr);
                    alert("Erreur lors de la suppression (" + xhr.status + ").");
                }
            });
        });

        // mode initial
        switchToCreateMode();


        function calculerIMC() {
            const poids = parseFloat(document.getElementById("poids").value);
            const taille = parseFloat(document.getElementById("taille").value);

            if (!isNaN(poids) && !isNaN(taille) && taille > 0) {
                const bmi = poids / ((taille/100) * (taille/100));
                const bmiTronque = Math.floor(bmi * 10) / 10; // troncature à 1 décimale
                document.getElementById("imc").value = bmiTronque;
            } else {
                document.getElementById("imc").value = "";
            }
        }
        document.getElementById("poids").addEventListener("input", calculerIMC);
        document.getElementById("taille").addEventListener("input", calculerIMC);

        // recalcul IMC quand on clique sur "éditer" et que les champs sont remplis
        document.getElementById("icon-edit").addEventListener("click", function() {
            setTimeout(calculerIMC, 50);
            // petit délai pour laisser ton script remplir les champs poids/taille avant le calcul
        });


        jq("DOMContentLoaded", function () {
            document.querySelectorAll("tr").forEach(row => {
                const poidsCell = row.querySelector('td[data-concept="5089"]');
                const tailleCell = row.querySelector('td[data-concept="5090"]');
                const imcCell   = row.querySelector('td[data-concept="imc"]');

                if (poidsCell && tailleCell && imcCell) {

                    const poids = parseFloat(poidsCell.innerText.trim());
                    const taille = parseFloat(tailleCell.innerText.trim());

                    if (!isNaN(poids) && !isNaN(taille) && taille > 0) {
                        const bmi = poids / ((taille/100) * (taille/100));
                        const bmiTronque = Math.floor(bmi * 10) / 10;
                        imcCell.innerText = bmiTronque;
                    } else {
                        imcCell.innerText = "-";
                    }
                }
            });
        });

    });


</script>

<style>

#container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    flex-wrap: wrap;
    gap: 10px;
    background: #fff;
    margin: -10px 5px 0px 5px;
    border-radius: 5px;
    padding: 10px;
}

#form {
    margin: 5px;
    margin-left: 5px;
    padding: 10px;
    margin-bottom: -1%;
}


.form-container {
    background: white;
    border-radius: 5px;
    border: 1px solid #d9edf7;
    margin-bottom: 5px;
}


.form-control, .form-select {
    border-radius: 5px;
    height: 22px;
    padding: 5px 10px;
    border: 1px solid #ced4da;
    box-shadow: none;
    transition: all 0.1s ease-in-out;
    /*background: #ddd;*/
    /*width: 220px;*/
    font-size: 14px;
}


.form-control:focus, .form-select:focus {
    border: 1px solid #ced4da;
    background: #fff;
    outline-color: #8bafd4;
}

.div-fieldset {
    display: flex;
    gap: 10px;
    /*border: 1px solid red;*/
    width: 100%;
}

fieldset {
    border: none;
    margin-top: -8px;
    width: 97.5%;
    border-radius: 5px;
}

.info-pers {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    flex-wrap: wrap;
    width: 100%;
    /*border: 1px solid red;*/
    /*margin: 10px;*/
}

.contain-span {
    display: flex;
    flex-direction: row;
    align-items: end;
    gap: 10px;
}


td {
    font-size: 15px;
}


.form-group {
    width: 9%;
    /*border: 1px solid green;*/
}

label {
    font-size: 14px;
}

.form-control {
    width: 90%;
}

th {
    background: #fff;
    font-size: 13px;
    text-align: center;
}

td {
    text-align: center;
    font-weight: 18px;
    /*background: white;*/
}


.asterix {
    color: red;
}

.span-hint {
    float: right;
    margin-right: 5px;
    font-size: 10px;
    color: #487b9a;
    margin-top: 2px;
}


</style>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }


<div id="container" >

    <div class="form-container">
        <form id="form" method="POST">

            <div>
                <div class="col-md-6 contain-span" style="justify-content: space-between">

                    <div class="col-md-6" style="border: 1px solid #fff; width: 20%; float: left">
                        <div class="form-group" style="width: 60%">
                            <label for="signature">Initiales de l’infirmier(ère) <span class="asterix">*</span></label>
                            <input type="text" class="form-control" name="signature" id="signature" placeholder=""
                                   required>
                        </div>
                    </div>

                    <div style="display: flex; gap: 50px; margin-right: 5px">
                        <div style="float: right">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="reset"
                                           style="width: 110px; height: 40px; font-size: 13px; border: 1px solid #fff; background: #e07a5f; color: #fff; border-radius: 5px"
                                           class="" id="reset" name="E" value="Annuler" placeholder="">
                                </div>
                            </div>
                        </div>

                        <div style="float: right">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="submit"
                                           style="width: 110px; height: 40px; font-size: 13px; border: 1px solid #fff; background: #3b5371; color: #fff; border-radius: 5px"
                                           class="" id="submit" name="E" value="Enregistrer" placeholder="">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br/>

            <div class="div-fieldset">
                <fieldset style="border: 1px solid #ced4da">
                    <legend>Signes Vitaux et Anthropometrie</legend>


                    <input type="hidden" id="encounterId" name="encounterId"/>
                    <input type="hidden" id="obsId" name="obsId"/>
                    <input type="hidden" id="modeId" name="modeId"/>
                    <input type="hidden" id="patientId" name="patientId" value="${patientId}"/>
                    <input type="hidden" id="visitId" name="visitId" value="${visitId}"/>

                    <input type="hidden" id="poidsId" name="poidsId"/>
                    <input type="hidden" id="tempId" name="tempId"/>
                    <input type="hidden" id="fcId" name="fcId"/>
                    <input type="hidden" id="tasysId" name="tasysId"/>
                    <input type="hidden" id="tadiasId" name="tadiasId"/>
                    <input type="hidden" id="respId" name="respId"/>
                    <input type="hidden" id="tailleId" name="tailleId"/>
                    <input type="hidden" id="sao2Id" name="sao2Id"/>
                    <input type="hidden" id="signatureId" name="signatureId"/>




                    <div class="info-pers" style="border: 1px solid white">
                        <div class="form-group">
                            <label for="poids">Poids</label>
                            <input type="number" style="" class="form-control" name="poids" id="poids"
                                   placeholder="" required>
                            <span class="span-hint">(3 – 200 kg)</span>
                        </div>

                        <div class="form-group">
                            <label for="taille">Taille /cm</label>
                            <input type="number" class="form-control" name="taille" id="taille" placeholder="">
                            <span class="span-hint">(40 – 250 cm)</span>
                        </div>

                        <div class="form-group">
                            <label for="imc">IMC</label>
                            <input type="number" class="form-control" name="imc" id="imc" placeholder="" disabled>
                            <span class="span-hint">(Pds kg/T² m)</span>
                        </div>

                        <div class="form-group">
                            <label for="fc">Pouls</label>
                            <input type="number" class="form-control" name="fc" id="fc" placeholder="">
                            <span class="span-hint">(40 – 180 bpm)</span>
                        </div>

                        <div class="form-group" style="border: 1px solid white">
                            <label for="tasys">Systol.</label>
                            <input type="number" class="form-control" name="tasys" id="tasys" placeholder="">
                            <span class="span-hint">(70 – 200 mmHg)</span>
                        </div>

                        <div class="form-group">
                            <label for="tadias">Diastol.</label>
                            <input type="number" class="form-control" name="tadias" id="tadias" placeholder="">
                            <span class="span-hint">(40 – 120 mmHg)</span>
                        </div>

                        <div class="form-group">
                            <label for="temp">Temp.</label>
                            <input type="number" class="form-control" name="temp" id="temp" placeholder="">
                            <span class="span-hint">(35 – 42 °C)</span>
                        </div>

                        <div class="form-group">
                            <label for="resp">FR</label>
                            <input type="number" class="form-control" name="resp" id="resp" placeholder="">
                            <span class="span-hint">(12 – 40 /min)</span>
                        </div>

                        <div class="form-group">
                            <label for="sao2">SpO₂</label>
                            <input type="number" class="form-control" name="sao2" id="sao2" placeholder="">
                            <span class="span-hint">(70 – 100 %)</span>
                        </div>
                    </div>

                </fieldset>

            </div>

            <br/>

            <div>
                <table id="vitalsTable" border="1">
                    <thead>
                    <tr>
                        <th colspan="2"
                            style="background:#4c80a1; color:#f3f6f9; width:14%; text-align:center;">Date et Heure</th>
                        <th style="background:#f3f6f9; color:#4c80a1; width:6%; text-align:center;">Poids</th>
                        <th style="background:#f3f6f9; color:#4c80a1; width:6%; text-align:center;">Taille /cm</th>
                        <th style="background:#f3f6f9; color:#4c80a1; width:6%; text-align:center;">IMC |kg/m²</th>
                        <th style="background:#f3f6f9; color:#4c80a1; width:6%; text-align:center;">Pouls</th>
                        <th style="background:#f3f6f9; color:#4c80a1; width:9%; text-align:center;"
                            colspan="2">TA (syst. / dias.)</th>
                        <th style="background:#f3f6f9; color:#4c80a1; width:6%; text-align:center;">Temp.</th>
                        <th style="background:#f3f6f9; color:#4c80a1; width:6%; text-align:center;">Resp.</th>
                        <th style="background:#f3f6f9; color:#4c80a1; width:6%; text-align:center;">SpO₂</th>
                        <th style="background:#4c80a1; color:#f3f6f9; width:8%; text-align:center;">Init. Inf</th>
                        <th style="background:#4c80a1; color:#f3f6f9; width:8%; text-align:center;">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        def sdfDate = new java.text.SimpleDateFormat("dd MMMM yyyy", new Locale("fr"))
                        def sdfHeure = new java.text.SimpleDateFormat("HH")
                        def sdfMinute = new java.text.SimpleDateFormat("mm")

                        hashSetObs.each { encounterObs ->
                            if (encounterObs.size()) {
                                def firstObs = encounterObs[0]   // pour récupérer obsDatetime et obsId
                    %>
                    <tr data-encounter-id="${firstObs?.encounter?.encounterId}">

                        <td style="border-right: 1px solid white">
                            ${firstObs?.obsDatetime ? sdfDate.format(firstObs.obsDatetime) : ''}
                        </td>
                        <td>
                            ${firstObs?.obsDatetime ? sdfHeure.format(firstObs.obsDatetime) : ''}h :
                            ${firstObs?.obsDatetime ? sdfMinute.format(firstObs.obsDatetime) : ''} mm
                        </td>

                        <!-- Poids (5089) -->
                        <td data-concept="5089"
                            data-obs-id="${encounterObs.find { it?.concept.conceptId == 5089 }?.obsId}">
                            ${encounterObs.find { it?.concept.conceptId == 5089 }?.valueNumeric ?: ''}
                        </td>

                        <!-- Taille (5090) -->
                        <td data-concept="5090"
                            data-obs-id="${encounterObs.find { it?.concept.conceptId == 5090 }?.obsId}">
                            ${encounterObs.find { it?.concept.conceptId == 5090 }?.valueNumeric ?: ''}
                        </td>

                        <!-- IMC -->
                        <td data-concept="imc"></td>

                        <!-- Pouls (5087) -->
                        <td data-concept="5087"
                            data-obs-id="${encounterObs.find { it?.concept.conceptId == 5087 }?.obsId}">
                            ${encounterObs.find { it?.concept.conceptId == 5087 }?.valueNumeric ?: ''}
                        </td>

                        <!-- TA Systolique (5085) -->
                        <td data-concept="5085"
                            data-obs-id="${encounterObs.find { it?.concept.conceptId == 5085 }?.obsId}">
                            ${encounterObs.find { it?.concept.conceptId == 5085 }?.valueNumeric ?: ''}
                        </td>

                        <!-- TA Diastolique (5086) -->
                        <td data-concept="5086"
                            data-obs-id="${encounterObs.find { it?.concept.conceptId == 5086 }?.obsId}">
                            ${encounterObs.find { it?.concept.conceptId == 5086 }?.valueNumeric ?: ''}
                        </td>

                        <!-- Température (5088) -->
                        <td data-concept="5088"
                            data-obs-id="${encounterObs.find { it?.concept.conceptId == 5088 }?.obsId}">
                            ${encounterObs.find { it?.concept.conceptId == 5088 }?.valueNumeric ?: ''}
                        </td>

                        <!-- Respiration (5242) -->
                        <td data-concept="5242"
                            data-obs-id="${encounterObs.find { it?.concept.conceptId == 5242 }?.obsId}">
                            ${encounterObs.find { it?.concept.conceptId == 5242 }?.valueNumeric ?: ''}
                        </td>

                        <!-- SaO₂ (5092) -->
                        <td data-concept="5092"
                            data-obs-id="${encounterObs.find { it?.concept.conceptId == 5092 }?.obsId}">
                            ${encounterObs.find { it?.concept.conceptId == 5092 }?.valueNumeric ?: ''}
                        </td>

                        <!-- Infirmier initial (1473) -->
                        <td data-concept="1473"
                            data-obs-id="${encounterObs.find { it?.concept.conceptId == 1473 }?.obsId}">
                            ${encounterObs.find { it?.concept.conceptId == 1473 }?.valueText ?: ''}
                        </td>

                        <!-- Actions -->
                        <td>
                            <div style="display:flex; justify-content:space-evenly;">
                                <a href="#" class="delete-btn" data-obs-id="${firstObs?.obsId}">
                                    <i class="icon-remove-sign" style="color:red; font-size:18px;"></i>
                                </a>
                                <a href="#" id="icon-edit" class="edit-btn" data-obs-id="${firstObs?.obsId}">
                                    <i class="icon-edit" style="color:#c5bb32; font-size:18px;"></i>
                                </a>
                            </div>
                        </td>
                    </tr>
                    <% }
                    } %>
                    </tbody>
                </table>
            </div>
            <br/><br/>
        </form>

    </div>

</div>

