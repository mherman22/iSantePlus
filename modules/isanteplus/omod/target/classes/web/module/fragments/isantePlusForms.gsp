<% if (isActiveVisit) { %>

<style>

/* SECTION PRINCIPALE */
.info-section-form {
    width: 72.2%;
    margin: auto;
    /*background: #f9fbfd;*/
    padding: 20px;
    /*border-radius: 12px;*/
    /*border: 1px solid green;*/
}

/* TITRE */
.info-header h3 {
    text-align: center;
    margin-bottom: 25px;
    color: #2a567c;
    font-weight: 600;
    font-size: 20px;
}

/* GRID */
.formlinks {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(380px, 1fr));
    gap: 20px;
    position: relative;
    z-index: 1;
    /*border: 1px solid red;*/
}

/* CARD */
.form-card-menu {
    position: relative;
    z-index: 1;
    background: #ffffff;
    /*width: 92%;*/
    box-shadow: 0 4px 12px rgba(0,0,0,0.06);
    overflow: visible;
    margin: 5px;
    transition: transform 0.2s ease, box-shadow 0.2s ease;
    border: 1px solid #f2f5f5;
    border-radius: 5px;
}

/* Quand actif → passe au-dessus */
.form-card-menu.active {
    z-index: 9999;
}

.form-card-menu:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 20px rgba(0,0,0,0.12);
}

/* HEADER */
.form-card-menu h5 {
    margin: 0;
    padding: 15px 18px;
    font-size: 15px;
    background: #fff;
    color: #2a567c;
    display: flex;
    justify-content: space-between;
    align-items: center;
    cursor: pointer;
    border-radius: 5px;
    /*border: 1px solid red;*/
}

/* Partie gauche (icone + texte) */
.form-title {
    display: flex;
    align-items: center;
    gap: 10px;
    font-weight: bold;
}

/* Emoji circulaire coloré */
.category-icon {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 50px;
    height: 50px;
    padding: 0px;
    border-radius: 50%;
    font-size: 20px;
    color: white;
    border: 1px solid #dbe6eb;
}



/* Flèche rotation */
.form-card-menu h5 i.icon-chevron-right {
    transition: transform 0.3s ease;
}

.form-card-menu h5.active i.icon-caret-right {
    transform: rotate(90deg);
}
.icon-caret-right {
    font-size: 20px;
}

/* DROPDOWN */
.form-card-menu ul {
    list-style: none;
    margin: 0;
    padding: 12px 18px;
    position: absolute;
    top: 95%;
    left: 0;
    width: 92%;
    background: white;
    border-radius: 0 0 12px 12px;
    box-shadow: 0 10px 25px rgba(0,0,0,0.15);
    display: none;
    z-index: 10000;
    animation: fadeIn 0.2s ease-in-out;
}

.form-card-menu ul li {
    padding: 6px 0;
}

.form-card-menu ul li a {
    text-decoration: none;
    color: #2a567c;
    font-size: 14px;
    transition: all 0.2s ease;
}

.form-card-menu ul li a:hover {
    color: #1b3d5a;
    font-weight: 500;
    padding-left: 4px;
    background: #5a6c90;
}

/* Animation */
@keyframes fadeIn {
    from { opacity: 0; transform: translateY(-5px); }
    to { opacity: 1; transform: translateY(0); }
}

/* MOBILE */
@media (max-width: 768px) {
    .formlinks {
        grid-template-columns: 1fr;
    }
    .info-section-form {
        width: 90%;
    }
}

</style>

<div class="info-section-form">

    <div class="info-header">
        <h3>${ui.message("isanteplus.iSantePlusForms")}</h3>
    </div>
<br/>
    <div class="formlinks">

        <!-- PRIMARY CARE -->
        <div class="form-card-menu">
            <h5>
                <span class="form-title">
                    <span class="category-icon icon-primary">🩺</span>
                    ${ui.message("isanteplus.isanteForms.categories.primaryCare")}
                </span>
                <i class="icon-caret-right"></i>
            </h5>
            <ul>
                <% primaryCareForms.each { %>
                <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                <% } %>
            </ul>
        </div>

        <!-- LAB -->
        <div class="form-card-menu">
            <h5>
                <span class="form-title">
                    <span class="category-icon icon-lab">🧪</span>
                    ${ui.message("isanteplus.isanteForms.categories.lab")}
                </span>
                <i class="icon-caret-right"></i>
            </h5>
            <ul>
                <% labForms.each { %>
                <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                <% } %>
            </ul>
        </div>

        <% if (showObygnForms) { %>
        <div class="form-card-menu">
            <h5>
                <span class="form-title">
                    <span class="category-icon icon-obygn">🤰</span>
                    ${ui.message("isanteplus.isanteForms.categories.obygn")}
                </span>
                <i class="icon-caret-right"></i>
            </h5>
            <ul>
                <% obygnForms.each { %>
                <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                <% } %>
            </ul>
        </div>
        <% } %>

        <!-- HIV -->
        <div class="form-card-menu">
            <h5>
                <span class="form-title">
                    <span class="category-icon icon-hiv">🦠</span>
                    ${ui.message("isanteplus.isanteForms.categories.hivCare")}
                </span>
                <i class="icon-caret-right"></i>
            </h5>
            <ul>
                <% hivCareForms.each { %>
                <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                <% } %>
            </ul>
        </div>

        <!-- PSYCHO -->
        <div class="form-card-menu">
            <h5>
                <span class="form-title">
                    <span class="category-icon icon-psycho">🧠</span>
                    ${ui.message("isanteplus.isanteForms.categories.psychoSocial")}
                </span>
                <i class="icon-caret-right"></i>
            </h5>
            <ul>
                <% psychoSocialForms.each { %>
                <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                <% } %>
            </ul>
        </div>

        <!-- EMERGENCY -->
        <div class="form-card-menu">
            <h5>
                <span class="form-title">
                    <span class="category-icon icon-emergency">🚑</span>
                    ${ui.message("isanteplus.isanteForms.categories.emergency")}
                </span>
                <i class="icon-caret-right"></i>
            </h5>
            <ul>
                <% emergencyForms.each { %>
                <% if (it.name == "Signes Vitaux") { %>
                <li>
                    <a href="/${contextPath}/isanteplus/vitals.page?app=isanteplus.vitals&patientId=${patientId}&visitId=${visitId}">
                        ${ui.format(it.name)}
                    </a>
                </li>
                <% } else { %>
                <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                <% } %>
                <% } %>
            </ul>
        </div>

        <!-- INPATIENT -->
        <div class="form-card-menu">
            <h5>
                <span class="form-title">
                    <span class="category-icon icon-inpatient">🏥</span>
                    ${ui.message("isanteplus.isanteForms.categories.inpatient")}
                </span>
                <i class="icon-caret-right"></i>
            </h5>
            <ul>
                <% inPatientForms.each { %>
                <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                <% } %>
            </ul>
        </div>

        <!-- OTHER -->
        <div class="form-card-menu">
            <h5>
                <span class="form-title">
                    <span class="category-icon icon-other">📁</span>
                    ${ui.message("isanteplus.isanteForms.categories.other")}
                </span>
                <i class="icon-caret-right"></i>
            </h5>
            <ul>
                <% otherForms.each { %>
                <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                <% } %>
            </ul>
        </div>

    </div>
<br/>
</div>

<script>
    jQuery(function () {

        jQuery(".form-card-menu ul").hide();

        jQuery(".form-card-menu h5").click(function (e) {

            e.stopPropagation();

            const card = jQuery(this).closest(".form-card-menu");
            const content = jQuery(this).next("ul");

            // Fermer les autres
            jQuery(".form-card-menu").not(card).removeClass("active");
            jQuery(".form-card-menu ul").not(content).hide();
            jQuery(".form-card-menu h5").not(this).removeClass("active");

            // Toggle actuel
            card.toggleClass("active");
            content.toggle();
            jQuery(this).toggleClass("active");
        });

        // Cliquer ailleurs ferme tout
        jQuery(document).click(function () {
            jQuery(".form-card-menu").removeClass("active");
            jQuery(".form-card-menu ul").hide();
            jQuery(".form-card-menu h5").removeClass("active");
        });

    });
</script>

<% } %>
