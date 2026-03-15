<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>

<body>
  <jsp:include page="/header.jsp"/>
  <div class="main">
    <h2>Column Statistics</h2>
    <% String errorMessage=(String) request.getAttribute("errorMessage");
    if (errorMessage !=null) { %>
      <p style="color: red;">
        <%= errorMessage %>
      </p>
    <% } else {
      Map<String, Integer> columnStatistics = (Map<String, Integer>) request.getAttribute("columnStatistics"); %>
      <ul>
        <% Set<String> keys = columnStatistics.keySet();
        for(String key : keys){ %>
          <li><%= ((key == null || key.equals(""))?"(empty)":key) + ": " + columnStatistics.get(key).toString() %>
        <% } %>
      </ul>
    <% } %>
    <jsp:include page="/footer.jsp" />
  </div>
</body>

</html>