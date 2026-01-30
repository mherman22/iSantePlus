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
    padding: 0;
    background: linear-gradient(135deg, #f5f5f5 0%, #e9e9e9 100%);
    min-height: 100vh;
    display: flex;
    flex-direction: column;
}

#username, #password {
    width: 97%;
    height: 40px;
    border-radius: 5px;
    max-width: 100%;
    padding: 8px;
}

#body-wrapper {
    border-radius: 10px;
    box-shadow: 1px 5px 14px -10px;
    width: calc(100% - 20px);
    max-width: 500px;
    height: auto;
    margin: 0px auto 40px;
    padding: 30px 20px;
    background: white;
    flex-shrink: 0;
}

.clear, .select {
    display: none;
}

#loginButton {
    width: 80%;
    max-width: 100%;
    height:auto;
    margin-left: auto;
    margin-right: auto;
}

.confirm {
    width: 100%;
}

.logo {
    display: flex;
    flex-direction: row;
    align-items: center;
}

h1 {
    font-size: clamp(1.5rem, 8vw, 3rem);
}

h5 {
    font-size: clamp(0.875rem, 4vw, 1rem);
}

input[type="text"],
input[type="password"] {
    font-size: 16px;
}

@media (max-width: 768px) {
    body {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
    }

    #body-wrapper {
        width: 90%;
        max-width: 380px;
        margin: 20px auto 40px;
        padding: 20px;
        flex-shrink: 0;
    }

    #username, #password {
        width: 100%;
        height: 44px;
    }

    .loginButton {
        padding: 15px 10px !important;
    }
}

@media (max-width: 360px) {
    #body-wrapper {
        width: 90%;
        max-width: 320px;
        margin: 15px auto 30px;
        padding: 15px;
        border-radius: 10px;
    }

    #username, #password {
        width: 100%;
        height: 44px;
    }

    h1 {
        font-size: 1.75rem;
        margin: 10px 0;
    }

    h5 {
        font-size: 0.9rem;
    }

    .loginButton {
        padding: 12px 10px !important;
        width: 100% !important;
    }

    #loginButton {
        width: 100% !important;
    }

    div[align="center"] > div[style*="padding"] {
        padding: 0 10px !important;
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
<header style="background: #566a8b">
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
        <div style="margin-top:5px; margin-left: -5px"><span style="font-size: small;">v2.8.5</span></div>
    </div>
</header>
<br/><br/>
<div style="width: 100%; padding: 0 10px; display: flex; flex-direction: column; justify-content: flex-start;">

    <h1 align="center" style="font-weight: bold; margin: 20px 0 10px 0;">Bienvenue</h1>

    <h5 align="center" style="margin: 10px 0 20px 0;">
        <% locations.sort { ui.format(it) }.each { %>
        <span align="center" id="${ui.encodeHtml(it.name)}" tabindex="1"
              value="${it.id}">${ui.encodeHtmlContent(ui.format(it))}</span>
        <% } %>
    </h5>
</div>

<br/>
<div id="body-wrapper">
    <div id="content">
        <p align="center" class="clear">
            <label for="sessionLocation">
                ${ui.message("referenceapplication.login.sessionLocation")}:
                <span class="location-error" id="sessionLocationError"
                      style="display: none">${ui.message("referenceapplication.login.error.locationRequired")}</span>
            </label>
        <ul align="center" id="sessionLocation" class="select">
            <% locations.sort { ui.format(it) }.each { %>
            <li id="${ui.encodeHtml(it.name)}" tabindex="0" value="${it.id}">${ui.encodeHtmlContent(ui.format(it))}</li>
            <% } %>
        </ul>
    </p>

        <form id="login-form" method="post" autocomplete="off">

            <div align="center" style="padding: 0 15px;">

                <div><br/><br/>
                    <i style="font-size: xxx-large; padding: 5px 11px; border-radius: 60px; margin-top: 30px; border: 6px solid #3d2763;"
                       class="icon-user small"><br/></i>

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

            <div class="loginButton" style="width: 91.5%; margin-left: auto; margin-right: auto;">
                <input style="border-radius: 5px; padding: 12px" id="loginButton" class="confirm" type="submit"
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