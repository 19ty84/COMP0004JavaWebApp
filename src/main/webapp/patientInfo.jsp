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
    <h2>Patient Information</h2>
    <% String errorMessage=(String) request.getAttribute("errorMessage");
    if (errorMessage !=null) { %>
      <p style="color: red;">
        <%= errorMessage %>
      </p>
    <% } else {
      List<String> columnNames = (List<String>) request.getAttribute("columnNames");
      Map<String, String> patientInfo = (Map<String, String>) request.getAttribute("patientInfo"); %>
      <ul>
        <% for(String columnName : columnNames){ %>
          <li><%= columnName + ": " + patientInfo.get(columnName) %>
        <% } %>
      </ul>
    <% } %>
    <jsp:include page="/footer.jsp" />
  </div>
</body>

</html>