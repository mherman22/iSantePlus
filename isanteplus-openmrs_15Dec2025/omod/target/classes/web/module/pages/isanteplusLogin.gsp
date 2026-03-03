<%
    ui.includeFragment("appui", "standardEmrIncludes")
    ui.includeCss("referenceapplication", "login.css")
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>${ui.message("referenceapplication.login.title")}</title>
    <link rel="shortcut icon" type="image/ico" href="/${ui.contextPath()}/images/openmrs-favicon.ico"/>
    <link rel="icon" type="image/png\" href="/${ui.contextPath()}/images/openmrs-favicon.png"/>
    ${ui.resourceLinks()}
</head>

<body>

<style>
* {
    box-sizing: border-box;
}

body {
    margin: 0;
    font-family: "Segoe UI", Arial, sans-serif;
    /*background: #f4f6f9;*/
    min-height: 100vh;
}

/* ================= HEADER ================= */

header {
    background: #566a8b;
    padding: 10px 20px;
    border-radius: 0px;
}

.logo {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

/* ================= TITRE ================= */

h1 {
    font-size: 26px;
    font-weight: 500;
    margin: 25px 0 5px;
    color: #2c3e50;
}

h5 {
    font-weight: 400;
    font-size: 14px;
    color: #6b7280;
}

/* ================= CARD LOGIN ================= */

#body-wrapper {
    max-width: 380px;
    margin: auto;
    padding: 30px 25px;
    background: #ffffff;
    border-radius: 0px;
    border: 1px solid #f4f5f5;
    box-shadow: 0 3px 2px -3px;
}

/* Icône utilisateur plus fine */
.icon-user {
    font-size: 32px !important;
    padding: 10px 14px;
    border-radius: 50%;
    border: 2px solid #3d2763 !important;
}

/* ================= FORM ================= */

form label {
    display: block;
    text-align: left;
    font-size: 13px;
    margin-bottom: 6px;
    color: #374151;
}

input[type="text"],
input[type="password"] {
    width: 100%;
    height: 42px;
    padding: 0 10px;
    border-radius: 6px;
    border: 1px solid #d1d5db;
    font-size: 14px;
    transition: 0.2s ease;
}

input[type="text"]:focus,
input[type="password"]:focus {
    border-color: #566a8b;
    outline: none;
}

/* ================= BUTTON ================= */

#loginButton {
    width: 100%;
    max-width: 300px;
    height: 42px;
    border-radius: 6px;
    border: none;
    font-size: 14px;
    background: #4f617f;
    color: white;
    cursor: pointer;
    transition: background 0.2s ease;
    margin: auto;
    margin-top: 10px;
}

#loginButton:hover {
    background: #404f67;
}

/* ================= RESPONSIVE ================= */

@media (max-width: 768px) {

    #body-wrapper {
        width: 90%;
        padding: 25px 20px;
    }

    #loginButton{
        max-width: 285px;
    }

    h1 {
        font-size: 22px;
    }
}

@media (max-width: 400px) {

    #body-wrapper {
        padding: 20px 15px;
        /*border-radius: 10px;*/
    }

    input[type="text"],
    input[type="password"],
    #loginButton {
        height: 40px;
    }
}
</style>

<script type="text/javascript">
    var OPENMRS_CONTEXT_PATH = '${ ui.contextPath() }';
</script>


${ui.includeFragment("referenceapplication", "infoAndErrorMessages")}

<script type="text/javascript">

    jQuery(function () {

        updateSelectedOption = function () {

            jQuery('#sessionLocationInput').val(jQuery('#sessionLocation li').attr("value"));

            sessionLocationVal = jQuery('#sessionLocationInput').val();

            if (sessionLocationVal != null && sessionLocationVal != "" && sessionLocationVal != 0) {
                jQuery('#sessionLocation li[value|=' + sessionLocationVal + ']').addClass('selected');
            }
        };

        updateSelectedOption();

        jQuery('#sessionLocation li').click(function () {
            jQuery('#sessionLocationInput').val(jQuery(this).attr("value"));
            updateSelectedOption();
        });

        jQuery('#sessionLocation li').focus(function () {
            jQuery('#sessionLocationInput').val(jQuery(this).attr("value"));
            updateSelectedOption();
        });

        // If <Enter> Key is pressed, submit the form
        jQuery('#sessionLocation').keyup(function (e) {
            var key = e.which || e.keyCode;
            if (key === 13) {
                jQuery('#login-form').submit();
            }
        });

        var listItem = Array.from(jQuery('#sessionLocation li'));
        for (var i in listItem) {
            listItem[i].setAttribute('data-key', i);
            listItem[i].addEventListener('keyup', function (event) {
                var keyCode = event.which || event.keyCode;
                switch (keyCode) {
                    case 37: // move left
                        jQuery(this).prev('#sessionLocation li').focus();
                        break;
                    case 39: // move right
                        jQuery(this).next('#sessionLocation li').focus();
                        break;
                    case 38: // move up
                        jQuery('#sessionLocation li[data-key=' + (Number(jQuery(document.activeElement).attr('data-key')) - 3) + ']').focus();
                        break;
                    case 40: //	move down
                        jQuery('#sessionLocation li[data-key=' + (Number(jQuery(document.activeElement).attr('data-key')) + 3) + ']').focus();
                        break;
                    default:
                        break;
                }
            });
        }

        jQuery('#loginButton').click(function (e) {
            var sessionLocationVal = jQuery('#sessionLocationInput').val();

            if (!sessionLocationVal) {
                jQuery('#sessionLocationError').show();
                e.preventDefault();
            }
        });

        var cannotLoginController = emr.setupConfirmationDialog({
            selector: '#cannotLoginPopup',
            actions: {
                confirm: function () {
                    cannotLoginController.close();
                }
            }
        });

        jQuery('#username').focus();
        jQuery('a#cantLogin').click(function () {
            cannotLoginController.show();
        });

        pageReady = true;
    });
</script>

<% if (enableIsantePlusUI) { %>
<!-- isantePlus added the next 6 lines to re-style ui -->
<link href="/${ui.contextPath()}/ms/uiframework/resource/isanteplus/styles/isanteplus.css" rel="stylesheet"
      type="text/css"/>
<link rel="shortcut icon" type="image/ico" href="${ui.resourceLink("isanteplus", "images/favicon.ico")}"/>
<link rel="icon" type="image/png\" href="${ui.resourceLink("isanteplus", "images/favicon.png")}"/>
<style>
form, .form {
    text-align: center;
}
</style>
<% } %>
<header style="background: #566a8b;">
    <div class="logo">
        <div style="display: flex; margin: 0px 5px; flex-direction: row; justify-content: center;">
            <a href="${ui.pageLink("referenceapplication", "home")}">
            <% if (enableIsantePlusUI) { %>
            <!-- isantePlus changed logo in the next line -->
            <img src="${ui.resourceLink("isanteplus", "images/isanteplus_logo_120x30.png")}" style="max-width: 120px; height: auto;"/>
            <% } else { %>
            <img src="${ui.resourceLink("referenceapplication", "images/openMrsLogo.png")}" style="max-width: 120px; height: auto;"/>
            <% } %>
            </a>
        </div>
        <div style="margin-top:5px; margin-left: -5px"><span style="font-size: small;">v2.8.8</span></div>
    </div>
</header>
<br/><br/>
<div class="page-intro">
    <h5 align="center" style="margin: 10px 0 20px 0;">
        <% locations.sort { ui.format(it) }.each { %>
        <span align="center" id="${ui.encodeHtml(it.name)}" tabindex="1"
              value="${it.id}">${ui.encodeHtmlContent(ui.format(it))}</span>
        <% } %>
    </h5>
</div>

<br/>
<div id="body-wrapper">
    <div id="content" >
        <p align="center" class="clear" style="display: none">
            <label for="sessionLocation">
                ${ui.message("referenceapplication.login.sessionLocation")}:
                <span class="location-error" id="sessionLocationError"
                      style="display: none">${ui.message("referenceapplication.login.error.locationRequired")}</span>
            </label>
        <ul align="center" id="sessionLocation" class="select" style="display: none">
            <% locations.sort { ui.format(it) }.each { %>
            <li id="${ui.encodeHtml(it.name)}" tabindex="0" value="${it.id}">${ui.encodeHtmlContent(ui.format(it))}</li>
            <% } %>
        </ul>
    </p>

        <form id="login-form" method="post" autocomplete="off">

            <div align="center" style="padding: 0 15px;">

                <div><br/><br/>
                    <i class="icon-user small"><br/></i>

                    <h1 style="font-size: x-large; margin-top: 10px"><br/>${ui.message("referenceapplication.login.loginHeading")}
                    </h1>
                </div>

                <div class="center">
                    <label for="username">
                        ${ui.message("referenceapplication.login.username")}
                    </label>
                    <input id="username" type="text" name="username"
                           placeholder="${ui.message("referenceapplication.login.username.placeholder")}"/>
                </div>

                <div class="center" style="padding-top: 10px">
                    <label for="password">
                        ${ui.message("referenceapplication.login.password")}
                    </label>
                    <input id="password" type="password" name="password"
                           placeholder="${ui.message("referenceapplication.login.password.placeholder")}"/>
                </div>


                <input type="hidden" id="sessionLocationInput" name="sessionLocation"
                    <% if (lastSessionLocation != null) { %> value="${lastSessionLocation.id}" <% } %>/>

                <br/>

            </div>

            <div class="loginButton">
                <input id="loginButton" class="confirm" type="submit"
                       value="${ui.message("isanteplus.loginButton")}"/>
            </div>

            <input type="hidden" name="redirectUrl" value="${redirectUrl}"/>

        </form>

    </div>
</div>

<div id="cannotLoginPopup" class="dialog" style="display: none;">
    <div class="dialog-header">
        <i class="icon-info-sign"></i>

        <h3>${ui.message("referenceapplication.login.cannotLogin")}</h3>
    </div>

    <div class="dialog-content">
        <p class="dialog-instructions">${ui.message("referenceapplication.login.cannotLoginInstructions")}</p>

        <button class="confirm">${ui.message("referenceapplication.okay")}</button>
    </div>
</div>

</body>
</html>