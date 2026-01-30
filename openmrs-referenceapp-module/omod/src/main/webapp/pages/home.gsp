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

<script type="text/javascript">
    jQuery(function () {

        const user = "<%= user %>";
        const location = "<%= location %>";
        const session = <%= session %>;
        const locale = "<%= locale %>";

        const buttonIds = ['sessionContextSave', 'sessionContextSearch'];

        buttonIds.forEach(id => {
            document.getElementById(id).addEventListener('click', function () {
                const data = {
                    user: localStorage.getItem('user'),
                    location: localStorage.getItem('location'),
                    session: localStorage.getItem('session'),
                    locale: localStorage.getItem('locale')
                };
            });
        });
    });
</script>

<div id="home-container">

    ${ui.includeFragment("coreapps", "administrativenotification/notifications")}

    <div id="apps">
        <% extensions.each { ext -> %>
        <% if (ext.label != 'outgoing-message-exceptions.label' && ext.label != 'referenceapplication.app.capturevitals.title' && ext.label != 'isanteplus.vitals.app.label') { %>
        <a id="${htmlSafeId(ext)}" href="/${contextPath}/${ext.url}" class="button app big">
            <% if (ext.icon) { %>
            <i style="margin-bottom: 8px" class="${ext.icon}"></i>
            <% } %>
            ${ui.message(ext.label)}
        </a>
        <% }
        } %>
    </div>
</div>
