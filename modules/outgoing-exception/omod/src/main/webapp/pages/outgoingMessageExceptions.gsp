<%
    ui.decorateWith("appui", "standardEmrPage")
    if (context.hasPrivilege("View Outgoing Messages")) {
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("Outgoing Message Exceptions")}" }
    ];
</script>

<div id="apps">
    
    <a class="button app big" href="${ ui.pageLink("outgoing-message-exceptions", "pix") }"
            id="outgoing-message-exceptions.pix">
        <i class="icon-calendar"></i>
        ${ ui.message("outgoing-message-exceptions.pix.label") }
    </a>
    
    <a class="button app big" href="${ ui.pageLink("outgoing-message-exceptions", "pdq") }"
            id="outgoing-message-exceptions.pdq">
        <i class="icon-calendar"></i>
        ${ ui.message("outgoing-message-exceptions.pdq.label") }
    </a>
    
    <a class="button app big" href="${ ui.pageLink("outgoing-message-exceptions", "xdsb") }"
            id="outgoing-message-exceptions.xdsb">
        <i class="icon-calendar"></i>
        ${ ui.message("outgoing-message-exceptions.xdsb.label") }
    </a>
    
    <a class="button app big" href="${ ui.pageLink("outgoing-message-exceptions", "ccd") }"
            id="outgoing-message-exceptions.ccd">
        <i class="icon-calendar"></i>
        ${ ui.message("outgoing-message-exceptions.ccd.label") }
    </a>

</div>

<% } %>