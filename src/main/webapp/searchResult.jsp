<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h1>Search Result</h1>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
    List<Map<String, String>> patients = (List<Map<String, String>>) request.getAttribute("result");
    if (patients != null && patients.size() != 0)
    { %>
      <table border="1">
      <tr>
        <% List<String> columnNames = (List<String>) request.getAttribute("columnNames");
        for (String columnName : columnNames)
        {
        %>
          <td><%= columnName %></td>
        <% } %>
      </tr>
      <% for (Map<String, String> patient : patients) {
        String patientID = patient.get("ID");
        String href = "patientInfo.jsp" + "?id=" + patientID;
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
      <% } %>
        </table>
      <%
    } else if (errorMessage == null)
    { %>
      <p>Nothing found</p>
  <% } %>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>