<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeCss("coreapps", "clinicianfacing/patient.css")
    ui.includeJavascript("coreapps", "custom/deletePatient.js")
%>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script type="text/javascript">
    jq(function() {
        jq(".tabs").tabs();

        jq(document).on('sessionLocationChanged', function() {
            window.location.reload();
        });
    });

    var patient = { id: ${ patient.id } };

    function toggleSettingsMenu() {
        var menu = document.getElementById("settingsDropdown");
        if (menu.style.display === "block") {
            menu.style.display = "none";
        } else {
            menu.style.display = "block";
        }
    }

    document.addEventListener("click", function(event) {
        var dropdown = document.getElementById("settingsDropdown");
        var icon = document.querySelector(".settings-icon");

        if (dropdown && icon &&
            !icon.contains(event.target) &&
            !dropdown.contains(event.target)) {
            dropdown.style.display = "none";
        }
    });
</script>

<style>

/* ===== Layout ===== */

.container {
    width: 100%;
    /*padding: 15px;*/
    box-sizing: border-box;
    margin: auto;
}
.info-container{
    width: 100%;
    margin: auto;

}

.dashboard {
    width: 80%;
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    margin: auto;
    box-shadow: 0 3px 2px -3px;
    /*border: 1px solid red;*/
}

.column {
    flex: 1;
    min-width: 375px;
}

/* ===== Fixed Settings Button ===== */

.fixed-settings-wrapper {
    position: fixed;
    top: 340px; /* Ajuster si nécessaire selon hauteur header */
    right: 25px;
    z-index: 2000;
}

.settings-icon {
    font-size: 22px;
    cursor: pointer;
    padding: 14px;
    border-radius: 50%;
    background: #324c6c;
    color: white;
    box-shadow: 0 4px 12px rgba(0,0,0,0.25);
    transition: 0.2s ease;
}

.settings-icon:hover {
    transform: scale(1.1);
}

/* Dropdown menu */

.settings-dropdown {
    display: none;
    position: absolute;
    right: 0;
    top: 38px;
    background: white;
    min-width: 300px;
    box-shadow: 0 4px 15px rgba(0,0,0,0.2);
    border-radius: 8px;
    overflow: hidden;
}

.settings-dropdown a {
    display: flex;
    align-items: center;
    gap: 5px;
    padding: 10px;
    text-decoration: none;
    color: #333;
    font-size: 14px;
}

.settings-dropdown a:hover {
    background: #f5f5f5;
}

/* ===== Responsive ===== */

@media (max-width: 360px) {
    .column {
        flex: 1;
        min-width: 322px;
    }
}

@media (min-width: 361px) and (max-width: 375px) {
    .column {
        flex: 1;
        min-width: 337px; /* force le scroll horizontal si écran trop petit */
    }
}

@media (min-width: 376px) and (max-width: 390px) {
    .column {
        flex: 1;
        min-width: 352px; /* force le scroll horizontal si écran trop petit */
    }

    .settings-dropdown {
        min-width: 250px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.2);
        border-radius: 8px;
        overflow: hidden;
    }

    .settings-dropdown a {
        padding: 8px;
        font-size: 12px;
    }
}

@media (min-width: 391px) and (max-width: 412px) {
    .column {
        flex: 1;
        min-width: 375px; /* force le scroll horizontal si écran trop petit */
    }
}

@media (min-width: 413px) and (max-width: 430px) {
    .column {
        flex: 1;
        min-width: 393px; /* force le scroll horizontal si écran trop petit */
    }
}

@media (min-width: 431px) and (max-width: 540px) {
    .column {
        flex: 1;
        min-width: 393px; /* force le scroll horizontal si écran trop petit */
    }
}

@media (max-width: 992px) {
    .dashboard {
        flex-direction: column;
    }
}

@media (max-width: 992px) {
    .dashboard {
        flex-direction: column;
    }
}

@media (max-width: 768px) {

    .dashboard {
        width: 100%;
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        gap: 15px;
        margin: auto;
        box-shadow: 0 3px 2px -3px;
        /*border: 1px solid red;*/
    }

    .fixed-settings-wrapper {
        top: 130px;
        right: 15px;
    }
}

</style>

<% if(includeFragments){
    includeFragments.each {
        def configs = [:];
        if(it.extensionParams.fragmentConfig != null){
            configs = it.extensionParams.fragmentConfig;
        }
        configs.patient = patient;
%>
${ui.includeFragment(it.extensionParams.provider, it.extensionParams.fragment, configs)}
<% }
} %>

${ ui.includeFragment("coreapps", "patientHeader", [
        patient: patient.patient,
        activeVisit: activeVisit,
        appContextModel: appContextModel
]) }

<!-- ===== Fixed Settings Menu ===== -->

<div class="fixed-settings-wrapper">
    <div class="settings-menu">
        <i class="icon-cog settings-icon" onclick="toggleSettingsMenu()"></i>

        <div id="settingsDropdown" class="settings-dropdown">
            <% if (overallActions && overallActions.size() > 0) {
                overallActions.each { ext -> %>

            <a href="${ ui.escapeJs(ext.url("/" + ui.contextPath(), appContextModel, ui.thisUrl())) }">
                <i class="${ ext.icon }"></i>
                ${ ui.message(ext.label) }
            </a>

            <% } } %>
        </div>
    </div>
</div>

<div class="clear"></div>

<div class="container">
    <div class="dashboard clear">

        <!-- FIRST COLUMN -->
        <div class="info-container column">
            <% if (firstColumnFragments) {
                firstColumnFragments.each {
                    def configs = [:];
                    if(it.extensionParams.fragmentConfig != null){
                        configs = it.extensionParams.fragmentConfig;
                    }
                    configs << [
                            patient: patient,
                            patientId: patient.patient.id,
                            app: it.appId
                    ]
            %>
            ${ ui.includeFragment(it.extensionParams.provider, it.extensionParams.fragment, configs)}
            <%  }
            } %>
        </div>

        <!-- SECOND COLUMN -->
        <div class="info-container column">
            <% if (secondColumnFragments) {
                secondColumnFragments.each {
                    def configs = [:];
                    if(it.extensionParams.fragmentConfig != null) {
                        configs = it.extensionParams.fragmentConfig;
                    }
                    configs << [
                            patient: patient,
                            patientId: patient.patient.id,
                            app: it.appId
                    ]
            %>
            ${ ui.includeFragment(it.extensionParams.provider, it.extensionParams.fragment, configs)}
            <%   }
            } %>
        </div>

    </div>
</div>
