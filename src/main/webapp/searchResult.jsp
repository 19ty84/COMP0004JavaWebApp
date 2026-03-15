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
      { %>
        <p style="color: red;"><%= errorMessage %></p>
      <% }
      List<String> columnNames  = (List<String>) request.getAttribute("columnNames"); %>
      <form method="GET" action="/runsearch">
      <input type="text" name="searchstring" value="<%= (String)request.getAttribute("searchstring") %>" />
      <br>
      <label for="order">Sort order:</label>
      <select id="order" name="order">
        <option value="default" selected>Default</option>
        <% for(String columnName : columnNames){ %>
          <option value="<%= columnName %>"><%= columnName %> (Ascending)</option>
          <option value="<%= (columnName + "_desc") %>"><%= columnName %> (Descending)</option>
        <% } %>
      </select><br>
      <input type="submit" value="Search" />
    </form>
      <%
      List<Map<String, String>> patients = (List<Map<String, String>>) request.getAttribute("result");
      if (patients != null && patients.size() != 0)
      { %>
        <p>Found <%= request.getAttribute("count") %> patients</p>
        <table border="1">
        <tr>
          <% for (String columnName : columnNames)
          { %>
            <td><%= columnName %></td>
          <% } %>
        </tr>
        <% for (Map<String, String> patient : patients) {
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