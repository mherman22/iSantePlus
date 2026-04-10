<%
    ui.decorateWith("appui", "standardEmrPage")
%>

<% if (patientSummaryList) { %>
<div>
    <table>
        <thead>
        <tr>
            <th>iSantePlus Id</th>
            <th>Code ST</th>
            <th>Prénom</th>
            <th>Nom</th>
            <th>Sexe</th>
            <th>Date de Naissance</th>
        </tr>
        </thead>
        <tbody>
        <% patientSummaryList.each { patientSummary -> %>
        <tr>
            <td>${patientSummary.identifier ?: ""}</td>
            <td>${patientSummary.stId ?: ""}</td>
            <td>${patientSummary.givenName ?: ""}</td>
            <td>${patientSummary.familyName ?: ""}</td>
            <td>${patientSummary.gender ?: ""}</td>
            <td>${patientSummary.birthdate ?: ""}</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<% } %>

