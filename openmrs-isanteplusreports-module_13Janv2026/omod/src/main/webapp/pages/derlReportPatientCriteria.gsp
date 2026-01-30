<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeJavascript("coreapps", "fragments/datamanagement/codeDiagnosisDialog.js")
    ui.includeJavascript("uicommons", "datatables/jquery.dataTables.min.js")
    ui.includeJavascript("isanteplusreports", "print.js")
    ui.includeJavascript("isanteplusreports", "isanteplusReport.js")
    ui.includeCss("uicommons", "datatables/dataTables_jui.css")
%>

<style>
table, th, td {
    background-color: #fff;
    height: 20px;
}
</style>


<div>
    <div style="min-height: 830px">
        <h1><i class="icon-dashboard"></i> ${ui.message("isanteplusreports.derl.surveillance.report")}</h1>
        <table style="margin: 7px; width: 99.2%; border: 1px solid red;">
            <thead>
            <tr align="center">
                <th colspan="13">
                    <h3 style="margin-left: auto; margin-right: auto; width: max-content"><%= selectedColumn %></h3>
                </th>
            </tr>
            <tr align="center" style="background-color: #fff;">
                <td rowspan="3" style="width: 35%;"><h4>Indicateurs</h4></td>
                <td colspan="12"><h4>Cas</h4></td>
            </tr>
            <tr collapse="collapse" align="center">
                <td colspan="2">0 - 28 Jrs</td>
                <td colspan="2">< 5 ans</td>
                <td colspan="2">5 - 14 ans</td>
                <td colspan="2">15 - 49 ans</td>
                <td colspan="2">50 et plus</td>
                <td colspan="2">Total</td>
            </tr>
            <tr collapse="collapse" align="center">
                <td>H</td>
                <td>F</td>
                <td>H</td>
                <td>F</td>
                <td>H</td>
                <td>F</td>
                <td>H</td>
                <td>F</td>
                <td>H</td>
                <td>F</td>
                <td>H</td>
                <td>F</td>
            </tr>
            </thead>
            <%= tbody %>
        </table>
        <br/>
    </div>
</div>
