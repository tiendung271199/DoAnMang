<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<button onclick="abc()">Click me</button>

	<form action="${pageContext.request.contextPath}/demo" method="post">
		<input type="text" id="date" name="date" value="${date}" placeholder="date" />
		<input type="text" name="datePresent" value="${datePresent}" placeholder="datePresent" />
		<input type="submit" value="Submit" />
	</form>
	
	<script type="text/javascript">
		function demo(id) {
			id = 5;
			return id;
		}
	
		function abc() {
			var arr = new Array();
			var id = 10;
			console.log(id);
			id = demo(id);
			console.log(id);
		}
	</script>
	
</body>
</html>