<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="/RGACustomerModule/resources/css/bootstrap.min.css"
	type="text/css" media="all">
<link rel="stylesheet"
	href="/RGACustomerModule/resources/css/reset.css" type="text/css"
	media="all">
<link rel="stylesheet"
	href="/RGACustomerModule/resources/css/style.css" type="text/css"
	media="all">
<script src="/RGACustomerModule/resources/scripts/jquery.js"></script>
<script src="/RGACustomerModule/resources/scripts/app.js"></script>
<script>
	$(function() {
		$(document).on("ready", function(event) {
			var error = '<%=request.getParameter("error")%>';
			if (error != "null") {
				$('#login_error').html("Invalid username or password!");
				$('#login_error').show();
			} else {
				$('#login_error').hide();
			}
		});
	});
</script>
</head>
<body>
	<div id="info" class="row well center-block" style="width: 50%;">
		<form class="form-horizontal col-sm-12"
			action="<c:url value="/j_spring_security_check"/>" method="post">
			<div id="login_error" class="alert alert-danger fade in">Invalid username or password!</div>
			<div class="form-group">
				<label for="inputEmail" class="col-sm-2 control-label">Username</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="inputEmail"
						placeholder="Username" name="j_username" required="required" >
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" class="col-sm-2 control-label">Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="inputPassword"
						placeholder="Password" name="j_password" required="required" >
				</div>
			</div>
				<div class="col-sm-offset-2 checkbox">
  					<label><input type="checkbox" name="_spring_security_remember_me" checked>Remember me</label>
				</div>
			<div class="form-group">
			<br>
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">Sign in</button>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-11 col-sm-1">
					<a href="/RGACustomerModule"><button id="back" class="btn btn-primary"
							type="button" style="float: right; margin-top: -50px">Back</button></a>
				</div>
			</div>
		</form>
	</div>
</body>
</html>