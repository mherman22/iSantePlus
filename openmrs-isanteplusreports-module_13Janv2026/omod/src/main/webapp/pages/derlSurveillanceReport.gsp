<%
    ui.decorateWith("appui", "standardEmrPage")
    ui.includeJavascript("isanteplusreports", "healthQualExportToExcel.js")
%>

<script type="text/javascript" charset="UTF-8">
    var breadcrumbs = [
        {icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm'},
        {label: "${ ui.message("isanteplusreports.derl.surveillance.report") }", link: '${ ui.thisUrl() }'}
    ];

    var jq = jQuery;

    jq(document).ready(function () {

        // runReport();

        // jq('#indicatorsForm').ready(function(event) {
        //     runReport();
        // });

        jq('#indicatorsForm').submit(function (event) {
            runReport();
        });
        jq('#buttonToPdf').click(function (event) {
            event.preventDefault();
            savePdf();
        });
        jq('#buttonToExcel').click(function (event) {
            event.preventDefault();
            saveExcel();
        });

        var checkBox = document.getElementById("check");
        jq('#check').change(function (event) {

            if (checkBox.checked == true) {
                jq('.opts').prop("checked", true);
            } else {
                jq('.opts').prop("checked", false);
            }
        });

    });

    <% if (pdfResult != null) { %>

    function savePdf() {
        var link = document.createElement('a');
        link.setAttribute('href', 'data:application/pdf;base64, <%= pdfResult %>');
        link.setAttribute('download', generatePdfName());

        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

    <% } %>

    function saveExcel() {
        var worksheet = "";
        jq("#divWithReportTables").find("table").each(function (i, table) {
            worksheet += jq(this).html();
            worksheet += "<tr></tr>"; // table separation
        });

        saveToExcel(worksheet, 'workbenchName', generateExcelName());
    }

    function generateExcelName() {
        return "DerlReport" + formatDate(new Date()) + ".xls";
    }

    function generatePdfName() {
        return "DerlReport" + formatDate(new Date()) + ".pdf";
    }

    function formatDate(date) {
        return jq.datepicker.formatDate('yy-mm-dd', date) + '_' + date.toLocaleTimeString();
    }

    function runReport() {
        var form = jq('#indicatorsForm');
        var indicators = parseIndicators(form);

        jq("#codedIndicators").remove();    // if it exists
        form.append("<input id='codedIndicators' type='hidden' name='indicatorList' value='" + JSON.stringify(indicators) + "'/>");
    }

    function parseIndicators(form) {
        var parsedIndicators = [];
        var indicators = form.find(".indicator");

        indicators.each(function () {
            var indicator = jq(this);
            if (indicator.find("[name=selection]").prop('checked') === true) {
                var map = new Object();
                indicator.find('[name=options]').each(function () {
                    map[indicator.find('[name=options] :selected').attr('name')] = indicator.find('[name=options] :selected').val();
                });
                parsedIndicators.push(
                    createIndicator(
                        indicator.attr('id'),
                        map
                    )
                );
            }
        });
        return parsedIndicators;
    }

    function createIndicator(uuidValue, optionValue) {
        return {
            uuid: uuidValue,
            options: optionValue
        };
    }

    function openCity(evt, cityName) {
        // Declare all variables
        var i, tabcontent, tablinks;

        // Get all elements with class="tabcontent" and hide them
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }

        // Get all elements with class="tablinks" and remove the class "active"
        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }

        // Show the current tab, and add an "active" class to the button that opened the tab
        document.getElementById(cityName).style.display = "block";
        evt.currentTarget.className += " active";
    }

    if (jQuery) {
        jQuery(document).ready(function () {
            openCity(event, 'immediate');
            document.getElementById('')
        });
    }
</script>

<style type="text/css">
.clearfix::after {
    content: "";
    clear: both;
    display: table;
}

.indicatorsHeader {
    text-align: center;
    background-color: #fefad3;
    font-style: italic;
}

#indicators thead th {
    background-color: #fdf59a;
}

#divWithReportTables th, #divWithReportTables td {
    padding: 5pt;
    border-color: #dddddd;
    border-style: solid;
    border-top-width: 1px;
    border-left-width: 1px;
    border-right-width: 0px;
    border-bottom-width: 0px;
    text-align: center;
}

#divWithReportTables th:last-child, #divWithReportTables td:last-child {
    border-right-width: 1px;
}

#divWithReportTables tr:last-child th, #divWithReportTables tr:last-child td {
    border-bottom-width: 1px;
}

#divWithReportTables th, #divWithReportTables td {
    white-space: normal;
}

#divWithReportTables td:hover {
    white-space: normal;
    background-color: #e6ffe9;
}

#divWithReportTables th {
    white-space: normal;
    background: #f5f5f5;
    height: 40px;
    /*width: 100px;*/
    font-size: 14px;
    /*overflow: auto;*/
}

#divWithReportTables td {
    white-space: normal;
    background: #fff;
    width: 100px;
    font-size: 14px;
}

.indicatorLabel {
    text-align: center;
    color: #393683;
    font-weight: bold;
    font-size: large;
}

.label {
    text-align: center;
}

.total {
    color: blue;
}

#divWithReportTables table {
    border-collapse: separate;
    border-spacing: 0;
    empty-cells: hide;
    margin: 30pt auto;
}

#result_container {
    /*height: 500px;*/
    /*overflow: scroll;*/
    /*border: 1px solid silver;*/
    text-align: justify;
    padding: 0px;
    margin: 0px;
    margin-top: 10px;
}

/* Style the tab */
.tab {
    overflow: hidden;
    border: 1px solid #ccc;
    border-bottom: none;
    background-color: #f2f2f2;
}

/* Style the buttons that are used to open the tab content */
.tab button {
    background-color: inherit;
    float: left;
    border: none;
    outline: none;
    cursor: pointer;
    padding: 14px 16px;
    /*transition: 0.1s;*/
    font-size: 15px;
}

/* Change background color of buttons on hover */
.tab button:hover {
    background-color: #fff;
    border-top: 2px solid #393683;
    border-bottom: none;
    height: 50px;
    position: relative;
    height: 50px;
    margin-bottom: -10px;
    /*font-size: 16px;*/
}

/* Create an active/current tablink class */
.tab button.active {
    background-color: #fff;
    border-top: 2px solid #393683;
    border-bottom: none;
    height: 50px;
    position: relative;
    height: 50px;
    margin-bottom: -10px;
    font-size: 16px;
}

/* Style the tab content */
.tabcontent {
    display: none;
    padding: 6px 15px;
    border: 1px solid #ccc;
    border-top: none;
}

.loader {
    float: right;
    border: 3px solid #dddddd; /* Light grey */
    border-top: 3px solid #393683; /* Blue */
    border-right: 3px solid #393683; /* Blue */
    border-bottom: 3px solid #393683; /* Blue */
    border-radius: 50%;
    width: 10px;
    height: 10px;
    animation: spin 0.6s linear infinite;
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
}

</style>


<div style="">
    <h1><i class="icon-dashboard"></i> ${ui.message("isanteplusreports.derl.surveillance.report")}</h1>

    <div class="running-reports">

        <form id="indicatorsForm" name="indicatorsForm" class="clearfix" method="post">
            <div id="div4" class="togglable" style="display: none">

                <fieldset id="run-report" style="float:right; min-width: 450px; margin-left: 10px;">
                    <legend>
                        ${ui.message("reportingui.runReport.run.legend")}
                    </legend>

                    <p><input type="checkbox" id="check"
                              name="check">${ui.message("isanteplusreports.pnls.select.all")}</input></p><br/>

                    <p><input type="checkbox" id="check_submit" name="check_submit"> check </input></p>

                    <p id="parameterSection">
                        ${ui.includeFragment("uicommons", "field/datetimepicker", ["id": "startDateField", "label": "From Date", "formFieldName": "startDate", "defaultDate": startDate, "useTime": false])}
                        ${ui.includeFragment("uicommons", "field/datetimepicker", ["id": "endDateField", "label": "To Date", "formFieldName": "endDate", "defaultDate": endDate, "useTime": false])}
                    <p>

                </fieldset>

                <table id="indicators" style="display:block; width: 100%; padding-top: 11px;">
                    <thead>
                    <tr>
                        <th style="width:100%">${ui.message("isanteplusreports.healthqual.indicator.label")}</th>
                        <th>${ui.message("isanteplusreports.healthqual.selection.label")}</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th class="indicatorsHeader" colspan="2">
                            ${ui.message("isanteplusreports.derl.surveillance.report")}
                        </th>
                    </tr>
                    <% manager.derlIndicators.each { indicator -> %>
                    ${ui.includeFragment("isanteplusreports", "derlIndicatorReport", [indicator: indicator])}
                    <% } %>
                    </tbody>
                </table>
            </div>
            <button id="submit" type="submit" style="font-size: small; padding: 8px 6px;">
                ${ui.message("reportingui.runButtonLabel")}&nbsp;
                <i class="icon-repeat"></i>
            </button>&nbsp;
        </form>

        <% if (htmlResult != null) { %>
        <div style="position: relative; display: inline-block; float: right; margin-top: -37px;">
            <button style="font-size: small; padding: 8px 6px" type='button' id='buttonToPdf' value="PDF"><i
                    class="icon-download-alt"></i> PDF</button>&nbsp;
            <button style="font-size: small; padding: 8px 6px" type='button' id='buttonToExcel' value="Excel"><i
                    class="icon-download-alt"></i> Excel</button>
        </div>

        <div id="result_container" style="margin-top: 10px">
            <div id="divWithReportTables">
                <div id="tab" class="tab">
                    <button id="tab1" class="tablinks active"
                            onclick="openCity(event, 'immediate')">Déclaration Immédiate</button>
                    <button style="border-left: 1px solid silver; border-right: 1px solid silver;" class="tablinks"
                            onclick="openCity(event, 'hebdomadaire')">Déclaration Hebdomadaire</button>
                    <button class="tablinks" onclick="openCity(event, 'mensuel')">Déclaration mensuelle</button>
                </div>
                <%= htmlResult %>
            </div>
        </div>
        <% } %>
    </div>

</div>
