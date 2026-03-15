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
    <h1>Search</h1>
    <form method="GET" action="/runsearch">
      <input type="text" name="searchstring" placeholder="Enter search keyword here" />
      <br>
      <label for="order">Sort order:</label>
      <select id="order" name="order">
        <option value="default" selected>Default</option>
        <% List<String> columnNames  = (List<String>) request.getAttribute("columnNames");
        for(String columnName : columnNames){ %>
          <option value="<%= columnName %>"><%= columnName %> (Ascending)</option>
          <option value="<%= (columnName + "_desc") %>"><%= columnName %> (Descending)</option>
        <% } %>
      </select><br>
      <input type="submit" value="Search" />
    </form>
  </div>
  <jsp:include page="/footer.jsp"/>
</body>

</html>