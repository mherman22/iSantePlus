<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeJavascript("coreapps", "visit/jquery.dataTables.js")
    ui.includeJavascript("coreapps", "visit/filterTable.js")
    ui.includeCss("coreapps", "visit/visits.css")
%>

<script type="text/javascript">

    jQuery(function (jq) {

        // ----------------------
        // SUBMIT (CREATE)
        // ----------------------
        jq("#form").on("submit", function (e) {
            e.preventDefault();

            if (!jq("#signature").val().trim()) {
                alert("Veuillez saisir les initiales de l'infirmier(ère).");
                return;
            }

            const payload = jq(this).serialize(); // x-www-form-urlencoded (attendu par @RequestParam)
            console.log("payload==", payload)

            jq.ajax({
                url: "${ ui.pageLink('isanteplus','triage',null) }",
                type: "POST",
                data: payload,
                success: function () {
                    jq().toastmessage('showSuccessToast', "Patient enregistré avec succès !")
                    setTimeout(() => location.reload(), 600);
                },
                error: function (xhr) {
                    console.error(xhr);
                    jq().toastmessage('showErrorToast', "Erreur lors de l'enregistrement (" + xhr.status + ").")
                }
            });
        });

        // Fonction pour interdire le signe négatif
        function preventNegativeInput(event) {
            const value = event.target.value;
            // Si la valeur contient un signe '-' ou est une chaîne vide, on la remplace par 0
            if (value.includes("-") || value === "") {
                event.target.value = "";
            }
        }
        // Ajouter un événement de saisie pour interdire les signes négatifs
        document.querySelectorAll('input[type="number"]').forEach(input => {
            input.addEventListener('input', preventNegativeInput);
        });


        jq("#locationInput").on("input", function () {
            let crit = jq(this).val();
            if (crit.length >= 2) {
                jq("#locationOptions").load(
                    "${ ui.pageLink('isanteplus', 'triage') }?app=isanteplus.triage&critere=" + encodeURIComponent(crit)
                );
            }
        });


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

        function calculerGlasgow() {
            const e = parseInt(document.getElementById("ge").value) || 0;
            const v = parseInt(document.getElementById("gv").value) || 0;
            const m = parseInt(document.getElementById("gm").value) || 0;

            const total = e + v + m;
            document.getElementById("glasgow").value = total;
        }

        // recalcul chaque fois qu’un champ change
        document.getElementById("ge").addEventListener("input", calculerGlasgow);
        document.getElementById("gv").addEventListener("input", calculerGlasgow);
        document.getElementById("gm").addEventListener("input", calculerGlasgow);

        // au chargement de la page, si les champs sont déjà remplis
        document.addEventListener("DOMContentLoaded", calculerGlasgow);

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

.form-select {
    height: 36px;
}


.form-control:focus, .form-select:focus {
    border: 1px solid #ced4da;
    background: #fff;
    outline-color: #8bafd4;
}

.info-pers {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
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
    /*background: #f4f9ff;*/
}


.form-group {
    width: 14%;
    /*border: 1px solid green;*/
}

.contain-span {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: end;
    /*border: 1px solid red;*/
    margin: 0px 4px;
}

.span {
    display: flex;
    flex-direction: row-reverse;
    align-items: end;
    height: 20px;
    padding: 3px;
    float: left;
}

.form-span {
    display: flex;
    flex-direction: row;
    align-items: end;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 10px;
    float: left;
}

.form-contact {
    display: flex;
    flex-direction: row;
    /*flex-wrap: wrap;*/
    /*border: 1px solid blue;*/
}

.div-evaluation {
    display: flex;
    flex-direction: col;
    justify-content: space-evenly;
    flex-wrap: wrap;
    /*align-items: stretch;*/
    box-sizing: content-box;
    gap: 10px;
}

.evaluation {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    flex-wrap: wrap;
    /*flex-basis: 50%;*/
    margin-top: 5px;
    /*margin-left: 5px;*/
    width: 98%;
}

.form-control {
    width: 90%;
}

.form-select {
    width: 99%;
}

td {
    font-size: 15px;
}

.asterix {
    color: red;
}

.span-hint {
    float: right;
    margin-right: 11%;
    font-size: 10px;
    color: #487b9a;
    margin-top: 2px;
}

.text-center {
    text-align: center;
    background: #deebef;
    color: #215381;
    /*background: #3b5371;*/
    padding: 5px;
    margin-top: -1px;
    border-radius: 5px 5px 0 0;
}

.vitals {
    width: 10%;
}

.glasgow {
    display: flex;
    flex-direction: row;
    justify-content: space-evenly;
    margin-top: -20px;
}

/* Pour Chrome, Edge, Opera */
datalist option {
    display: block;
}

datalist {
    max-height: 150px;
    overflow-y: auto;
}

</style>


<div id="container" style="${(patientId != null || visitId != null) ? 'pointer-events: none;' : ''}">

    <div class="form-container">
        <h2 class="mb-4 text-center">TRIAGE INITIAL</h2>

        <form id="form" method="POST">
            <div class="div-fieldset">
                <fieldset style="border: 1px solid #ced4da">
                    <legend style="color: #215381">Informations personnelles</legend>

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

                        <div class="form-group">
                            <label for="birthDate">Date de naissance</label>
                            <input type="date" class="form-control" name="birthDate" id="birthDate" value="${dateOfBirth != null ? dateOfBirth : ''}"
                                   placeholder="mm/dd/yy">
                            <span class="span-hint" style="font-size: small">mm/dd/yy</span>
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

                </fieldset>

            </div>

            <br/>

            <div class="div-fieldset" style="width: 100%;">
                <fieldset style="border: 1px solid #ced4da">
                    <legend style="color: #215381">Modificateurs</legend>


                    <div class="info-pers">

                        <div class="form-group">
                            <label for="poids">Poids</label>
                            <input type="number" style="" class="form-control vitals" name="poids" id="poids"
                                   placeholder="">
                            <span class="span-hint">(3 – 200 kg)</span>
                        </div>

                        <div class="form-group">
                            <label for="taille">Taille /cm</label>
                            <input type="number" class="form-control vitals" name="taille" id="taille" placeholder="">
                            <span class="span-hint">(40 – 250 cm)</span>
                        </div>

                        <div class="form-group">
                            <label for="imc">IMC</label>
                            <input type="number" class="form-control vitals" id="imc" placeholder=""
                                   disabled>
                        </div>

                        <div class="form-group">
                            <label for="tasys">Systol.</label>
                            <input type="number" class="form-control vitals" name="tasys" id="tasys" placeholder="">
                            <span class="span-hint">(70 – 200 mmHg)</span>
                        </div>

                        <div class="form-group">
                            <label for="tadias">Diastol.</label>
                            <input type="number" class="form-control vitals" name="tadias" id="tadias" placeholder="">
                            <span class="span-hint">(40 – 120 mmHg)</span>
                        </div>

                        <div class="form-group">
                            <label for="fr">Respiration</label>
                            <input type="number" class="form-control vitals" name="fr" id="resp" placeholder="">
                            <span class="span-hint">(12 – 40 /min)</span>
                        </div>

                        <div class="form-group">
                            <label for="fc">Pouls</label>
                            <input type="number" class="form-control vitals" name="fc" id="fc" placeholder="">
                            <span class="span-hint">(40 – 180 bpm)</span>
                        </div>

                    </div>

                    <div class="info-pers">

                        <div class="form-group">
                            <label for="typeDouleur">Type de Douleur</label>
                            <select class="form-select" name="typeDouleur" id="typeDouleur" style="width: 87%">
                                <option value=""></option>
                                <option value="159347">Aiguë</option>
                                <option value="152065">Chronique</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="scoreDouleur">Score de Douleur</label>
                            <input type="number" class="form-control vitals" min="0" name="scoreDouleur"
                                   id="scoreDouleur"
                                   placeholder="">
                            <span class="span-hint">(0 – 10)</span>
                        </div>

                        <div class="form-group">
                            <label for="temp">Température</label>
                            <input type="number" class="form-control vitals" name="temp" id="temp" placeholder="">
                            <span class="span-hint">(35 – 42 °C)</span>
                        </div>

                        <div class="form-group">
                            <label for="glycemie">Glycémie</label>
                            <input type="number" class="form-control vitals" name="glycemie" id="glycemie"
                                   placeholder="">
                            <span class="span-hint">(2 – 25 mmol/L)</span>
                        </div>

                        <div class="form-group" style="">
                            <label for="pc">Périmètre Crânien</label>
                            <input type="number" class="form-control vitals" name="pc" id="pc" placeholder="">
                            <span class="span-hint">(30 – 60 cm)</span>
                        </div>

                        <div class="form-group">
                            <label for="sao2">SpO₂</label>
                            <input type="number" class="form-control vitals" name="sao2" id="sao2" placeholder="">
                            <span class="span-hint">(70 – 100 %)</span>
                        </div>

                        <div class="form-group">
                            <label for="pb">Périmètre brachial</label>
                            <input type="number" class="form-control vitals" name="pb" id="pb" placeholder="">
                            <span class="span-hint">(10 – 40 cm)</span>
                        </div>

                    </div>

                </fieldset>

            </div>

            <br/>

            <div style="border: 1px solid white; " class="glasgow">
                <p style="font-size: 18px; margin-top: 30px;"><strong>Glasgow :</strong></p>

                <div class="form-group">
                    <label for="ge"><strong>E</strong></label>
                    <input type="number" class="form-control" name="ge" id="ge" placeholder="">
                </div>

                <div class="form-group">
                    <label for="gv"><strong>V</strong></label>
                    <input type="number" class="form-control" name="gv" id="gv" placeholder="">
                </div>

                <div class="form-group">
                    <label for="gm"><strong>M</strong></label>
                    <input type="number" class="form-control" name="gm" id="gm" placeholder="">
                </div>

                <strong style="font-size: xx-large; margin-top: 30px; height: 30px">=</strong>

                <div class="form-group">
                    <label for="gm"><strong>Score</strong></label>
                    <div style="display: flex;">
                        <input type="number" class="form-control" name="glasgow" id="glasgow" placeholder="" disabled>
                        <span style="margin-top: 12px; margin-left: 2px"><strong> /</strong></span>
                        <span style="margin-top: 12px; margin-left: 2px"><strong>15</strong></span>
                    </div>
                </div>

            </div>

            <br/>

            <div id="div-fieldset">
                <fieldset id="" style="border: 1px solid #ced4da">
                    <legend style="color: #215381">Examen clinique</legend>

                    <div class="div-evaluation">

                        <div class="evaluation">
                            <div style="width: 55%">
                                <table class="tg"><!-- concepts a remplacer -->
                                    <thead>
                                    <tr>
                                        <th colspan="5">A (Voies aériennes)</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>
                                            <span class="span">
                                                <label for="alerte">Alerte</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="alerte">
                                            </span>
                                        </td>
                                        <td>
                                            <span class="span" style="float: left">
                                                <label for="stimuli">Réagit aux stimuli Verbaux</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="stimuli">
                                            </span>
                                        </span>
                                        </td>
                                        <td>
                                            <span class="span">
                                                <label for="nreagitp">Ne réagit pas</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="nreagitp">
                                            </span>
                                        </td>
                                        <td>
                                            <span class="span">
                                                <label for="irritable">Irritable</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="irritable">
                                            </span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="span">
                                                <label for="agite">Agité</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="agite">
                                            </span>
                                        </td>
                                        <td>
                                            <span class="span">
                                                <label for="stimulid">Réagit aux stimuli douloureux</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="stimulid">
                                            </span>
                                        </td>
                                        <td>
                                            <span class="span">
                                                <label for="consolable">Consolable</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="consolable">
                                            </span>
                                        </td>
                                        <td>
                                            <span class="span">
                                                <label for="confus">Confus</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="confus">
                                            </span>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                            <div style="width: 44%">
                                <table class="tg">
                                    <thead>
                                    <tr>
                                        <th colspan="5">B (Respiration)</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>
                                            <span class="span">
                                                <label for="normale">Normale</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="normale">
                                            </span>
                                        </td>
                                        <td>
                                            <span class="span">
                                                <label for="respiratoire">Détresse Respiratoire</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="respiratoire">
                                            </span>
                                        </span>
                                        </td>
                                        <td>
                                            <span class="span">
                                                <label for="stridor">Stridor</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="stridor">
                                            </span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <span class="span">
                                                <label for="apnee">Apnée</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="apnee">
                                            </span>
                                        </td>
                                        <td>
                                            <span class="span">
                                                <label for="grognement">Grognement</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="grognement">
                                            </span>
                                        </td>
                                        <td>
                                            <span class="span">
                                                <label for="sifflement">Sifflement</label>
                                                <input type="checkbox" name="evaluation" value="120749" class=""
                                                       id="sifflement">
                                            </span>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div style="width: 98%; margin-top: 6px">
                            <table class="tg">
                                <thead>
                                <tr>
                                    <th colspan="5">C (Circulation) - Peau / Muqueuse / Mains et Plantes des pieds</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        <span class="span">
                                            <label for="pale">Pâle</label>
                                            <input type="checkbox" name="evaluation" value="120749" class="" id="pale">
                                        </span>
                                    </td>
                                    <td>
                                        <span class="span">
                                            <label for="cyanosee">Cyanosée</label>
                                            <input type="checkbox" name="evaluation" value="120749" class=""
                                                   id="cyanosee">
                                        </span>
                                    </span>
                                    </td>
                                    <td>
                                        <span class="span">
                                            <label for="marbrure">Marbrure</label>
                                            <input type="checkbox" name="evaluation" value="120749" class=""
                                                   id="marbrure">
                                        </span>
                                    </td>
                                    <td>
                                        <span class="span">
                                            <label for="seche">Sèche</label>
                                            <input type="checkbox" name="evaluation" value="120749" class="" id="seche">
                                        </span>
                                    </td>
                                    <td>
                                        <div class="form-span">
                                            <span>TRC :</span>
                                            <span class="span">
                                                <label for="inf2">≤ 2 secondes</label>
                                                <input type="radio" name="evaluation" value="120749" class="" id="inf2">
                                            </span>
                                            <span class="span">
                                                <label for="supp2">> 2 secondes</label>
                                                <input type="radio" name="evaluation" value="120749" class=""
                                                       id="supp2">
                                            </span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <span class="span">
                                            <label for="humide">Humide</label>
                                            <input type="checkbox" name="evaluation" value="120749" class=""
                                                   id="humide">
                                        </span>
                                    </td>
                                    <td>
                                        <span class="span">
                                            <label for="chaude">Chaude</label>
                                            <input type="checkbox" name="evaluation" value="120749" class=""
                                                   id="chaude">
                                        </span>
                                    </td>
                                    <td>
                                        <span class="span">
                                            <label for="froide ">Froide</label>
                                            <input type="checkbox" name="evaluation" value="120749" class=""
                                                   id="froide">
                                        </span>
                                    </td>
                                    <td>
                                        <span class="span">
                                            <label for="petechie">Pétéchie</label>
                                            <input type="checkbox" name="evaluation" value="120749" class=""
                                                   id="petechie">
                                        </span>
                                    </td>
                                    <td>
                                        <span class="span">
                                            <label for="purpura">Purpura</label>
                                            <input type="checkbox" name="evaluation" value="120749" class=""
                                                   id="purpura">
                                        </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                    </div>

                    <br/>

                </fieldset>
            </div>

            <div>
                <div class="col-md-6 contain-span">
                    <div class="col-md-6" style="width: 40%; float: left;">
                        <div class="form-group" style="width: 100%">
                            <label for="lastName">Intervention</label>
                            <textarea style="width: 95%; height: 55px" type="" class="form-control" name="intervention"
                                      id="nom" placeholder=""></textarea>
                        </div>
                    </div>

                    <div style="top: 0px; float: left; position: relative;">
                        <span style="font-size: 15px;"><strong>Disposition :</strong></span>

                        <div class="form-contact" style="margin-top: 5px;">
                            <span class="span">
                                <label for="ambulance">Réanimation</label>
                                <input type="checkbox" name="disposition" value="137593" class="" id="ambulance">
                            </span>
                            <span class="span">
                                <label for="voiturePrivee">Consultation</label>
                                <input type="checkbox" name="disposition" value="137593" class="" id="voiturePrivee">
                            </span>
                            <span class="span">
                                <label for="transportPublique">Parti sans autorisation</label>
                                <input type="checkbox" name="disposition" value="137593" class=""
                                       id="transportPublique">
                            </span>
                        </div>
                    </div>

                    <div class="col-md-6" style="width: 20%; margin-right: 5px">
                        <div class="form-group" style="width: 100%">
                            <label for="signature">Signature du prestataire : <span class="asterix">*</span></label>
                            <input style="width: 96%; height: 30px; outline-color: #04b6de;" type="text"
                                   class="form-control" name="signature" id="signature" placeholder="" required>
                        </div>
                    </div>
                </div>

                <br/>

                <div class="col-md-6 contain-span" style="justify-content: end; margin-top: 10px; ${(patientId != null || visitId != null) ? 'display: none;' : ''}">
                    <div style="display: flex; gap: 50px">
                        <div style="float: right">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="reset"
                                           style="width: 120px; height: 40px; border: 1px solid #fff; background: #e07a5f; color: #fff; border-radius: 5px"
                                           class="" id="reset" name="E" value="Annuler" placeholder="">
                                </div>
                            </div>
                        </div>

                        <div style="float: right">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <input type="submit"
                                           style="width: 120px; height: 40px; border: 1px solid #fff; background: #3b5371; color: #fff; border-radius: 5px"
                                           class="" id="submit" name="E" value="Enregistrer" placeholder="">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <br/><br/>

            </div>

        </form>

    </div>

</div>

