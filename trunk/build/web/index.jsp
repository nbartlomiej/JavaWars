<%-- 
    Document   : login
    Created on : Oct 30, 2008, 1:24:21 PM
    Author     : bartlomiej
--%>

<% if (request.getSession(true).getAttribute("sessionConstants") != null) {%>
<jsp:forward page="javawarsAccount.html" />
<%} else { 
response.sendRedirect("login.jsp");
} %>
