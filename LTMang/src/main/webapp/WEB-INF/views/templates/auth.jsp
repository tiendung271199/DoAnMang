<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/components/taglib.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Grab Từ Thiện | Admin Login</title>
    
    <c:url value="/resources/admin" var="authContextPath" scope="application"></c:url>
    
    <link rel="shortcut icon" type="image/ico" href="${pageContext.request.contextPath}/resources/public/images/logo.png" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="${authContextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- styles -->
    <link href="https://fonts.googleapis.com/css?family=Lobster" rel="stylesheet">
    <link href="${authContextPath}/css/style.css" rel="stylesheet">
    
    <script type="text/javascript" src="${authContextPath}/js/validate.js"></script>
    
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.7.0.min.js"></script>
	<script type="text/javascript" src="${authContextPath}/js/jquery.validate.min.js"></script>
  </head>
  <body>
  	<c:url value="/auth/login" var="urlAdminLogin" scope="application" ></c:url>
  
	<tiles:insertAttribute name="header"></tiles:insertAttribute>
	<div class="page-content container">
		<tiles:insertAttribute name="body"></tiles:insertAttribute>
	</div>

    <script src="${authContextPath}/bootstrap/js/bootstrap.min.js"></script>
    <script src="${authContextPath}/js/custom.js"></script>
  </body>
</html>