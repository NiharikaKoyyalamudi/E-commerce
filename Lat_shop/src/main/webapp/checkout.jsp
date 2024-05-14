<%@ page import="java.util.List" %>
<%@ page import="model.Products" %>

<%
double totalBill = (double) request.getSession().getAttribute("totalBill");
%>

<h2>Total Bill: $<%= totalBill %></h2>