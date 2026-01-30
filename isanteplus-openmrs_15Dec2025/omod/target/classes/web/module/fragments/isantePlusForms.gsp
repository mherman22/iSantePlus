<style>
.formlinks {
    margin-top: 40px;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: space-evenly;
    align-items: flex-start;
    margin: 0px;
}

h5 {
    padding-left: 10px;
    font-size: 16px;
}

.div {
    border: 1px solid #dfdfdf;
    border-radius: 7px;
    width: 300px;
    margin-bottom: 2%;
    background: #f7f7f7;
}

ul {
    padding: 5px 15px;
}

li {
    font-size: 15px;
    padding: 4px;
}

#primaryCareForms, #labForms, #obygnForms, #hivCareForms, #psychoSocialForms, #otherForms, #emergencyForm, #inPatientForm {
    border-top: 1px solid #eeeeee;
    background: white;
    border-radius: 0px 0px 7px 7px;
}
</style>

<% if (isActiveVisit) { %>

<div class="info-section" style="width: 99%; margin: auto; border: 1px solid #efefef; border-radius: 5px; background: #fff; margin-top: 10px">

    <div align="center" class="info-header">
        <h3 align="center">${ui.message("isanteplus.iSantePlusForms")}</h3>
    </div>

    <div style=""><br/>

        <div class="row formlinks">
            <div class="div">
                <h5 id="togglePrimaryCareForms" style="cursor:pointer;">${
                        ui.message("isanteplus.isanteForms.categories.primaryCare")}
                    <i id="fm1" class="icon-chevron-right" style="float: right; margin: 1px 10px; font-size: 14px"></i>
                </h5>
                <ul id="primaryCareForms">
                    <% primaryCareForms.each { %>
                    <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                    <% } %>
                </ul>
            </div>

            <div class="div">
                <h5 id="toggleLabForms" style="cursor:pointer;">${ui.message("isanteplus.isanteForms.categories.lab")}
                    <i id="fm2" class="icon-chevron-right" style="float: right; margin: 1px 10px; font-size: 14px"></i>
                </h5>
                <ul id="labForms">
                    <% labForms.each { %>
                    <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                    <% } %>
                </ul>
            </div>

            <% if (showObygnForms) { %>
            <div class="div">
                <% if (showObygnForms) { %>
                <h5 id="toggleObygnForms" style="cursor:pointer;">${
                        ui.message("isanteplus.isanteForms.categories.obygn")}
                    <i id="fm3" class="icon-chevron-right" style="float: right; margin: 1px 10px; font-size: 14px"></i>
                </h5>
                <ul id="obygnForms">
                    <% obygnForms.each { %>
                    <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                    <% } %>
                </ul>
                <% } %>
            </div>
            <% } %>

            <div class="div">
                <h5 id="toggleHivCareForms" style="cursor:pointer;">${
                        ui.message("isanteplus.isanteForms.categories.hivCare")}
                    <i id="fm4" class="icon-chevron-right" style="float: right; margin: 1px 10px; font-size: 14px"></i>
                </h5>
                <ul id="hivCareForms">
                    <% hivCareForms.each { %>
                    <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                    <% } %>
                </ul>
            </div>

            <div class="div">
                <h5 id="togglePsychoSocialForms" style="cursor:pointer;">${
                        ui.message("isanteplus.isanteForms.categories.psychoSocial")}
                    <i id="fm5" class="icon-chevron-right" style="float: right; margin: 1px 10px; font-size: 14px"></i>
                </h5>
                <ul id="psychoSocialForms">
                    <% psychoSocialForms.each { %>
                    <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                    <% } %>
                </ul>
            </div>

            <div class="div">
                <h5 id="toggleEmergencyForm" style="cursor:pointer;">${
                        ui.message("isanteplus.isanteForms.categories.emergency")}
                    <i id="fm7" class="icon-chevron-right" style="float: right; margin: 1px 10px; font-size: 14px"></i>
                </h5>
                <ul id="emergencyForm">
                    <% emergencyForms.each { %>
                    <% if(it.name == "Signes Vitaux") { %>
                    <li><a href="/${contextPath}/isanteplus/vitals.page?app=isanteplus.vitals&patientId=${patientId}&visitId=${visitId}">${ui.format(it.name)}</a></li>
                    <% } else { %>
                    <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                    <% } } %>
                </ul>
            </div>

            <div class="div">
                <h5 id="toggleInPatientForm" style="cursor:pointer;">${ui.message("isanteplus.isanteForms.categories.inpatient")}
                    <i id="fm8" class="icon-chevron-right" style="float: right; margin: 1px 10px; font-size: 14px"></i>
                </h5>
                <ul id="inPatientForm">
                    <% inPatientForms.each { %>
                    <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                    <% } %>
                </ul>
            </div>

            <div class="div">
                <h5 id="toggleOtherForms" style="cursor:pointer;">${
                        ui.message("isanteplus.isanteForms.categories.other")}
                    <i id="fm6" class="icon-chevron-right" style="float: right; margin: 1px 10px; font-size: 14px"></i>
                </h5>
                <ul id="otherForms">
                    <% otherForms.each { %>
                    <li><a href="${ui.format(it.url)}">${ui.format(it.name)}</a></li>
                    <% } %>
                </ul>
            </div>

        </div>

    </div><br/>
</div>
<% } %>

<script type="text/javascript">
    jQuery("#psychoSocialForms, #labForms, #obygnForms, #hivCareForms, #otherForms, #emergencyForm, #inPatientForm").hide();
    jQuery('#fm1').removeClass('icon-chevron-right').addClass('icon-chevron-down');

    jQuery(function () {
        jQuery("#togglePrimaryCareForms").click(function (event) {
            jQuery("#primaryCareForms").toggle();
            var fm = jQuery(this).find("#fm1").attr("class");
            if (fm == 'icon-chevron-right') {
                jQuery('#fm1').removeClass('icon-chevron-right').addClass('icon-chevron-down');
            } else if (fm == 'icon-chevron-down') {
                jQuery('#fm1').removeClass('icon-chevron-down').addClass('icon-chevron-right');
            }
        });

        jQuery("#toggleLabForms").click(function (event) {
            jQuery("#labForms").toggle();
            var fm = jQuery(this).find("#fm2").attr("class");
            if (fm == 'icon-chevron-right') {
                jQuery('#fm2').removeClass('icon-chevron-right').addClass('icon-chevron-down');
            } else if (fm == 'icon-chevron-down') {
                jQuery('#fm2').removeClass('icon-chevron-down').addClass('icon-chevron-right');
            }
        });

        jQuery("#toggleObygnForms").click(function (event) {
            jQuery("#obygnForms").toggle();
            var fm = jQuery(this).find("#fm3").attr("class");
            if (fm == 'icon-chevron-right') {
                jQuery('#fm3').removeClass('icon-chevron-right').addClass('icon-chevron-down');
            } else if (fm == 'icon-chevron-down') {
                jQuery('#fm3').removeClass('icon-chevron-down').addClass('icon-chevron-right');
            }
        });

        jQuery("#toggleHivCareForms").click(function (event) {
            jQuery("#hivCareForms").toggle();
            var fm = jQuery(this).find("#fm4").attr("class");
            if (fm == 'icon-chevron-right') {
                jQuery('#fm4').removeClass('icon-chevron-right').addClass('icon-chevron-down');
            } else if (fm == 'icon-chevron-down') {
                jQuery('#fm4').removeClass('icon-chevron-down').addClass('icon-chevron-right');
            }
        });

        jQuery("#togglePsychoSocialForms").click(function (event) {
            jQuery("#psychoSocialForms").toggle();
            var fm = jQuery(this).find("#fm5").attr("class");
            if (fm == 'icon-chevron-right') {
                jQuery('#fm5').removeClass('icon-chevron-right').addClass('icon-chevron-down');
            } else if (fm == 'icon-chevron-down') {
                jQuery('#fm5').removeClass('icon-chevron-down').addClass('icon-chevron-right');
            }
        });

        jQuery("#toggleOtherForms").click(function (event) {
            jQuery("#otherForms").toggle();
            var fm = jQuery(this).find("#fm6").attr("class");
            if (fm == 'icon-chevron-right') {
                jQuery('#fm6').removeClass('icon-chevron-right').addClass('icon-chevron-down');
            } else if (fm == 'icon-chevron-down') {
                jQuery('#fm6').removeClass('icon-chevron-down').addClass('icon-chevron-right');
            }
        });

        jQuery("#toggleEmergencyForm").click(function (event) {
            jQuery("#emergencyForm").toggle();
            var fm = jQuery(this).find("#fm7").attr("class");
            if (fm == 'icon-chevron-right') {
                jQuery('#fm7').removeClass('icon-chevron-right').addClass('icon-chevron-down');
            } else if (fm == 'icon-chevron-down') {
                jQuery('#fm7').removeClass('icon-chevron-down').addClass('icon-chevron-right');
            }
        });

        jQuery("#toggleInPatientForm").click(function (event) {
            jQuery("#inPatientForm").toggle();
            var fm = jQuery(this).find("#fm8").attr("class");
            if (fm == 'icon-chevron-right') {
                jQuery('#fm8').removeClass('icon-chevron-right').addClass('icon-chevron-down');
            } else if (fm == 'icon-chevron-down') {
                jQuery('#fm8').removeClass('icon-chevron-down').addClass('icon-chevron-right');
            }
        });
    });
</script>
