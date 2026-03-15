<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
  <jsp:include page="/meta.jsp" />
  <title>Patient Data App</title>
</head>

<body>
  <jsp:include page="/header.jsp" />
  <div class="main">
    <h2>Patients:</h2>
    <% String errorMessage=(String) request.getAttribute("errorMessage"); if (errorMessage !=null) { %>
    <p style="color: red;">
      <%= errorMessage %>
    </p>
    <% } %>
    <form action="/patientList" method="GET">
      <label for="order">Sort order:</label>
      <select id="order" name="order">
        <option value="default" selected>Default</option>
        <option value="first">First Name (Ascending)</option>
        <option value="firstdesc">First Name (Decending)</option>
        <option value="last">Last Name (Ascending)</option>
        <option value="lastdesc">Last Name (Decending)</option>
        <option value="birthdate">Birth Date (Ascending)</option>
        <option value="birthdatedesc">Birth Date (Decending)</option>
      </select><br>
      <input type="submit" value="Sort"/>
    </form>
    <table border="1">
      <tr>
        <% List<String> columnNames = List.of("ID", "FIRST", "LAST", "BIRTHDATE", "GENDER");
        for (String columnName : columnNames)
        {
        %>
          <td><%= columnName %></td>
        <% } %>
      </tr>
      <% List<Map<String, String>> patients = (List<Map<String, String>>) request.getAttribute("patientInfos");
        if (patients != null)
        {
          for (Map<String, String> patient : patients)
          {
            String patientID = patient.get("ID");
            String href = "patientInfo" + "?id=" + patientID;
      %>
            <tr>
              <% for (String columnName : columnNames)
              {
                if (columnName.equals("ID")){
                %>
                  <td><a href=<%= href %>><%= patient.get("ID") %></a></td>
                <% } else {
                %>
                  <td><%= patient.get(columnName) %></td>
              <% } } %>
            </tr>
      <% } } %>
    </table>
  </div>
  <jsp:include page="/footer.jsp" />
</body>

</html>