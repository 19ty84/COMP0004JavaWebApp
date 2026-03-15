<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>

<body>
  <jsp:include page="/header.jsp"/>
  <div class="main">
    <h2>Please select the column to view the statistics</h2>
    <ul>
      <% List<String> columnNames  = (List<String>) request.getAttribute("columnNames");
      for(String columnName : columnNames){ %>
        <li><a href="<%= "statisticsResult?column=" + columnName %>"><%= columnName %></a></li>
      <% } %>
    </ul>
  </div>
  <jsp:include page="/footer.jsp"/>
</body>

</html>