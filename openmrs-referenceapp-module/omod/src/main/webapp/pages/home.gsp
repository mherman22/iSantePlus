<%
    ui.decorateWith("appui", "standardEmrPage", [title: ui.message("referenceapplication.home.title")])

    def htmlSafeId = { extension ->
        "${extension.id.replace(".", "-")}-${extension.id.replace(".", "-")}-extension"
    }

    def user = authenticatedUser;
    def location = location;
    def session = isSessionOpen;
    def locale = getLocale;
    def base_url = "http://charess.fortiddns.com:9797/search";
%>

<style type="text/css">
    #apps {
        display: flex;
        flex-wrap: wrap;
        gap: 16px;
        padding: 12px 16px;
        width: 100%;
        box-sizing: border-box;
        justify-content: center;
        align-items: stretch;
    }

    .col {
        flex: 0 0 100%;
        max-width: 100%;
    }

    .button.app.big {
        display: inline-flex;
        align-items: center;
        gap: 12px;
        padding: 14px 16px;
        border-radius: 12px;
        text-decoration: none;
        transition: all 0.3s ease;
        -webkit-tap-highlight-color: transparent;
        cursor: pointer;
        font-size: 14px;
        background: #ffffff;
        border: 1px solid #e5e7eb;
        box-shadow: none;
        color: #1f2937;
        width: 100%;
        height: 96px;
        overflow: hidden;
    }

    .button.app.big i {
        font-size: 24px;
        display: inline-block;
        color: #333e62;
    }

    .app-icon {
        width: 48px;
        height: 48px;
        border-radius: 12px;
        background: #eef2ff;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .app-text {
        display: flex;
        align-items: center;
        gap: 6px;
        min-width: 0;
    }

    .app-title {
        font-size: 16px;
        line-height: 1.3;
        color: #333e62;
        white-space: normal;
        overflow-wrap: break-word;
        word-break: break-word;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }

    @media (min-width: 768px) {
        #apps { gap: 14px; padding: 20px; }
        .col {
            flex: 0 0 48%;
            max-width: 48%;
        }

        .button.app.big {
            font-size: 15px;
            padding: 16px 18px;
            height: 100px;
        }

        .button.app.big i {
            font-size: 26px;
        }
    }

    @media (min-width: 480px) and (max-width: 767px) {
        .col {
            flex: 0 0 33%;
            max-width: 33%;
        }
        .button.app.big {
            display: flex;
            flex-direction: column;
            align-items: center;
            min-width: 38%;
            gap: 12px;
            padding: 14px 16px;
            border-radius: 12px;
            text-decoration: none;
            transition: all 0.3s ease;
            -webkit-tap-highlight-color: transparent;
            cursor: pointer;
            font-size: 14px;
            background: #ffffff;
            border: 1px solid #e5e7eb;
            box-shadow: none;
            color: #1f2937;
            height: 96px;
            overflow: hidden;
        }
        .app-text {
            justify-content: center;
            text-align: center;
        }
    }

    @media (min-width: 1024px) {
        #apps { gap: 16px; padding: 24px; }
        .col {
            flex: 0 0 48%;
            max-width: 48%;
        }

        .button.app.big {
            font-size: 16px;
            padding: 18px 20px;
            height: 104px;
        }

        .button.app.big i {
            font-size: 28px;
        }
    }

    .button.app.big:active {
        transform: scale(0.95);
        opacity: 0.9;
    }

    @media (hover: hover) {
        .button.app.big:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
        }
    }

    .button.app.big:focus-visible {
        outline: 3px solid #93c5fd;
        outline-offset: 2px;
    }
</style>

<script type="text/javascript">
    jQuery(function () {
        var vp = document.querySelector('meta[name="viewport"]');
        var content = 'width=device-width, initial-scale=1, viewport-fit=cover';
        if (!vp) {
            vp = document.createElement('meta');
            vp.setAttribute('name', 'viewport');
            vp.setAttribute('content', content);
            document.head.appendChild(vp);
        } else {
            vp.setAttribute('content', content);
        }

        const user = "<%= user %>";
        const locationName = "<%= location %>";
        const sessionOpen = <%= session %>;
        const localeCode = "<%= locale %>";

        try {
            localStorage.setItem('user', user || '');
            localStorage.setItem('location', locationName || '');
            localStorage.setItem('session', String(sessionOpen));
            localStorage.setItem('locale', localeCode || '');
        } catch (e) {}

        const buttonIds = ['sessionContextSave', 'sessionContextSearch'];

        buttonIds.forEach(id => {
            const el = document.getElementById(id);
            if (el) {
                el.addEventListener('click', function () {
                    const data = {
                        user: localStorage.getItem('user'),
                        location: localStorage.getItem('location'),
                        session: localStorage.getItem('session'),
                        locale: localStorage.getItem('locale')
                    };
                });
            }
        });
    });
</script>

<div id="home-container">

    ${ui.includeFragment("coreapps", "administrativenotification/notifications")}

    <div id="apps">
        <% extensions.each { ext -> %>
        <% if(ext.url!="isanteplus/vitals.page?app=isanteplus.vitals&patientId={{patientId}}&visitId={{visitId}}"
                && ext.url!="coreapps/findpatient/findPatient.page?app=referenceapplication.vitals"
                && ext.url!="isanteplus/triage.page?app=isanteplus.triage"
                && ext.url!="coreapps/activePatientVisitsTriaged.page?app=coreapps.activePatientVisitsTriaged") { %>
        <a id="${htmlSafeId(ext)}" href="/${contextPath}/${ext.url}" class="button app big col">
            <% if (ext.icon) { %>
            <div class="app-icon">
                <i class="${ext.icon}"></i>
            </div>
            <% } %>
            <div class="app-text">
                <span class="app-title">${ui.message(ext.label)}</span>
            </div>
        </a>
        <% }} %>
    </div>

</div>
