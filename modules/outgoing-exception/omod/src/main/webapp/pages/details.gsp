<%
    ui.decorateWith("appui", "standardEmrPage")
    def artifactId = "outgoing-message-exceptions"
    if (context.hasPrivilege("View Outgoing Messages")) {
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message(artifactId + '.' + param.backPage[0] + '.label') }",
            link: "${ ui.pageLink(artifactId, param.backPage[0], [pageIndex: param.backPageIndex]) }"
        },
        { label: "${ ui.message(artifactId + '.details.label') }" }
    ];
</script>

<div id="apps">
    ${ ui.includeFragment("outgoingmessageexceptions", "exceptionDetails", [messageId: param.messageId, pageIndex: param.backPageIndex]) }
</div>

<% } %>