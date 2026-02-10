<%  if (enableIsantePlusUI) {  %>
	<!-- isantePlus added the next line (css styling) and 13th line (changes logo) -->
	<link href="/${contextPath}/ms/uiframework/resource/isanteplus/styles/isanteplus.css"  rel="stylesheet" type="text/css" />
	<link rel="shortcut icon" type="image/ico" href="${ui.resourceLink("isanteplus", "images/favicon.ico")}"/>
    <link rel="icon" type="image/png\" href="${ui.resourceLink("isanteplus", "images/favicon.png")}"/>

<% } %>

<%
    def addContextPath = {
        if (!it)
            return null
        if (it.startsWith("/")) {
            it = "/" + org.openmrs.ui.framework.WebConstants.CONTEXT_PATH + it
        }
        return it
    }
    def logoIconUrl;
    if (enableIsantePlusUI) {
    	logoIconUrl = addContextPath(configSettings?."logo-icon-url") ?: ui.resourceLink("isanteplus", "images/isanteplus_logo_120x42.png")
    } else {
    	logoIconUrl = addContextPath(configSettings?."logo-icon-url") ?: ui.resourceLink("referenceapplication", "images/openMrsLogo.png")
    }
    def logoLinkUrl = addContextPath(configSettings?."logo-link-url") ?: "/${ org.openmrs.ui.framework.WebConstants.CONTEXT_PATH }"

    def multipleLoginLocations = (loginLocations.size > 1);

    def enableUserAccountExt = userAccountMenuItems.size > 0;

%>
<script type="text/javascript">

    var sessionLocationModel = {
        id: ko.observable(),
        text: ko.observable()
    };

    jq(function () {

        // Toggle hamburger menu on mobile
        jq('.hamburger-menu').click(function() {
            jq('.user-options').toggleClass('open');
        });

        ko.applyBindings(sessionLocationModel, jq('.change-location').get(0));
        sessionLocationModel.id(${ sessionContext.sessionLocationId });
        sessionLocationModel.text("${ ui.format(sessionContext.sessionLocation) }");

        // we only want to activate the functionality to change location if there are actually multiple login locations
        <% if (multipleLoginLocations) { %>

            jq(".change-location a").click(function () {
                jq('#session-location').show();
                jq(this).addClass('focus');
                jq(".change-location a i:nth-child(3)").removeClass("icon-caret-down");
                jq(".change-location a i:nth-child(3)").addClass("icon-caret-up");
            });

            jq('#session-location').mouseleave(function () {
                jq('#session-location').hide();
                jq(".change-location a").removeClass('focus');
                jq(".change-location a i:nth-child(3)").addClass("icon-caret-down");
                jq(".change-location a i:nth-child(3)").removeClass("icon-caret-up");
            });

            jq("#session-location ul.select li").click(function (event) {
                var element = jq(event.target);
                var locationId = element.attr("locationId");
                var locationName = element.attr("locationName");

                var data = { locationId: locationId };

                jq("#spinner").show();

                jq.post(emr.fragmentActionLink("appui", "session", "setLocation", data), function (data) {
                    sessionLocationModel.id(locationId);
                    sessionLocationModel.text(locationName);
                    jq('#session-location li').removeClass('selected');
                    element.addClass('selected');
                    jq("#spinner").hide();
                    jq(document).trigger("sessionLocationChanged");
                })

                jq('#session-location').hide();
                jq(".change-location a").removeClass('focus');
                jq(".change-location a i:nth-child(3)").addClass("icon-caret-down");
                jq(".change-location a i:nth-child(3)").removeClass("icon-caret-up");
            });

            <% if (enableUserAccountExt) { %>
            jq('.identifier').hover(
                function(){
                    jq('.appui-toggle').show();
                    jq('.appui-icon-caret-down').hide();
                },
                 function(){
                    jq('.appui-toggle').hide();
                    jq('.appui-icon-caret-down').show();
                 }
            );
            jq('.identifier').css('cursor', 'pointer');
            <% } %>
        <% } %>
    });

</script>

<style type="text/css">
    header.isante-header {
        background: #566a8b;
        margin-top: -11px;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        align-items: center;
        padding: 5px 15px;
        position: sticky;
        top: 0;
        z-index: 1001;
        width: 100%;
        box-sizing: border-box;
        border-radius:0px;
    }

    header.isante-header .logo {
        display: flex;
        align-items: center;
    }
    
    header.isante-header .logo div {
        float: none !important;
    }

    header.isante-header .user-options {
        display: flex;
        align-items: center;
        margin: 0;
        padding: 0;
    }
    
    header.isante-header .user-options li {
        list-style: none;
        display: flex;
        align-items: center;
        margin-left: 10px;
    }

    header.isante-header .user-options li.logout {
        margin-left: 30px;
    }

    header.isante-header .hamburger-menu {
        display: none;
        font-size: 24px;
        cursor: pointer;
        color: white;
        margin-left: auto;
    }

    @media (max-width: 768px) {
        header.isante-header {
            flex-direction: column;
            padding-bottom: 10px;
        }

        header.isante-header .logo {
            width: 100%;
            justify-content: space-between;
            margin-bottom: 0;
            display: flex;
            align-items: center;
        }

        header.isante-header .hamburger-menu {
            display: block;
        }

        header.isante-header .user-options {
            display: none; /* Hide by default on mobile */
            width: 100%;
            flex-direction: column;
            background: rgba(86, 106, 139, 0.95);
            margin-top: 10px;
            padding: 10px 0;
            border-top: 1px solid #4a5c7a;
            border-radius: 0 !important;
            box-shadow: none !important;
        }

        header.isante-header .user-options.open {
            display: flex;
        }
        
        header.isante-header .user-options li {
            margin: 10px 0;
            width: 100%;
            justify-content: center;
            border-radius: 0 !important;
        }

        header.isante-header .user-options li.logout {
            margin: 10px 0;
            margin-left: 0;
        }
    }
</style>

<header class="container isante-header">
    <div class="logo" >
       <div><a href="${ logoLinkUrl }" >
           <img src="${ logoIconUrl }" />
       </a></div>
        <div style="margin-left: 10px; display: flex; align-items: center;"><span style="font-size:small;">v2.8.5</span></div>
        <div class="hamburger-menu">
            &#9776; <!-- Hamburger Icon -->
        </div>
    </div>
    <% if (context.authenticated) { %>
    <ul class="user-options">
        <li class="identifier">
            <i class="icon-user small"></i>
            ${context.authenticatedUser.username ?: context.authenticatedUser.systemId}
            <% if (enableUserAccountExt) { %>
            <i class="icon-caret-down appui-icon-caret-down link"></i><i class="icon-caret-up link appui-toggle" style="display: none;"></i>
                <ul id="user-account-menu" class="appui-toggle">
                    <% userAccountMenuItems.each { menuItem  -> %>
                    <li>
                        <a id="" href="/${ contextPath }/${ menuItem.url }">
                            ${ ui.message(menuItem.label) }
                        </a>
                    </li>
                    <% } %>
                </ul>
            <% } %>
        </li>
        <li class="change-location">
            <a href="javascript:void(0);">
                <i class="icon-map-marker small"></i>
                <span data-bind="text: text"></span>
                <% if (multipleLoginLocations) { %>
                    <i class="icon-caret-down link"></i>
                <% } %>
            </a>
        </li>
        <li class="logout">
            <a href="/${contextPath}/logout">
                ${ui.message("emr.logout")}
                <i class="icon-signout small"></i>
            </a>
        </li>
    </ul>

    <div id="session-location">
        <div id="spinner" style="position:absolute; display:none">
            <img src="${ui.resourceLink("uicommons", "images/spinner.gif")}">
        </div>
        <ul class="select">
            <% loginLocations.sort { ui.format(it) }.each {
                def selected = (it == sessionContext.sessionLocation) ? "selected" : ""
            %>
            <li class="${selected}" locationId="${it.id}" locationName="${ui.format(it)}">${ui.format(it)}</li>
            <% } %>
        </ul>
    </div>
    <% } %>
</header>
<br/>
