<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="/RGACustomerModule/resources/css/bootstrap.min.css"
	type="text/css" media="all">
<link rel="stylesheet" href="/RGACustomerModule/resources/css/style.css" type="text/css"
	media="all">
</head>
<body>
	<div class="container-fluid">
		<div class="row" style="padding:50px">
			<div id="errorMessage" class="alert-danger well center-block">
				<h4>Error-403: ACCESS DENIED</h4>
				<hr>
				Ooops, you don't have access to this page. Click <a href="/RGACustomerModule/">here</a> to go back.
			</div>
		</div>
	</div>

</body>

</html>
