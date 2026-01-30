<%
    ui.decorateWith("appui", "standardEmrPage")
%>
<script type="text/javascript">
    <% if (breadcrumbs) { %>
    var breadcrumbs = ${ breadcrumbs };
    <% } else { %>
    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {label: "${ ui.message(label)}"}
    ];
    <% } %>

    jq(function () {
        jq('#patient-search').focus();
    });

</script>

<style>
.form-group {
    width: 50%;
    margin-left: 20px;
}

.form-control, .form-select {
    border-radius: 3px;
    height: 30px;
    padding: 5px 10px;
    border: 1px solid #ced4da;
    box-shadow: none;
    transition: all 0.1s ease-in-out;
    width: 100%;
    font-size: 14px;
}

.form-control:focus, .form-select:focus {
    border: 1px solid #ced4da;
    background: #fff;
    outline-color: #8bafd4;
}

</style>

<div style="width: 100%;">

    <div style="margin-left: 20px;">
        <h1>${ui.message(heading)}</h1>
    </div>

    <div class="form-group">
        <input type="text" class="form-control" name="search" value="" id="search" placeholder="Rechercher par ID ou par nom" required>
    </div>

</div>


