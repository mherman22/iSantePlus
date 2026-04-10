<%
    ui.decorateWith("appui", "standardEmrPage")

    ui.includeJavascript("uicommons", "angular.min.js");
    ui.includeJavascript("uicommons", "angular-resource.min.js");

    def breadcrumbMiddle = breadcrumbOverride ?: """
        [ { label: '${returnLabel}' , link: '${returnUrl}'} ]
    """
%>

<script type="text/javascript">
    var breadcrumbs = _.flatten([
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {label: "${ ui.escapeJs(ui.format(htmlForm.form)) }"}
    ]);

    jq(function () {
        jq('.cancel').click(function (event) {
            event.preventDefault();
            htmlForm.cancel();
        });
    });
</script>

${ui.includeFragment("htmlformentryui", "htmlform/blankHtmlForm", [
        htmlForm : htmlForm,
        visit    : visit,
        returnUrl: returnUrl
])}


