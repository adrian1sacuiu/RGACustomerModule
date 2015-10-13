<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
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
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.14.0/jquery.validate.min.js"></script>
<script src="/RGACustomerModule/resources/scripts/app.js"></script>
<title>Register</title>
</head>
<body>
	<div class="main">
		<section id="content">
			<div id="info" class="row well center-block" style="width: 50%;">
				<div id="register_error" class="alert alert-danger fade in" style="display: none;">
				</div>
				<sf:form class="form-horizontal" role="form" id="register_form"
					modelAttribute="user" onsubmit="event.preventDefault(); registerUser();">
					<div class="form-group">
						<label for="inputName" class="col-sm-3 control-label">Username
							<span class="red">*</span>
						</label>
						<div class="col-sm-9">
							<sf:input type="text" class="form-control" id="inputName"
								placeholder="Username" path="username" required="required" />
							<br />
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-sm-3 control-label">Password
							<span class="red">*</span>
						</label>
						<div class="col-sm-9">
							<sf:input type="password" class="form-control"
								id="inputPassword" placeholder="Password" path="password"
								required="required" />
							<br />
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-sm-3 control-label">Email
							<span class="red">*</span>
						</label>
						<div class="col-sm-9">
							<sf:input type="email" class="form-control" id="inputEmail"
								placeholder="Email" path="email" required="required" />
							<br />
						</div>
					</div>
					<div class="col-sm-12">
						<div class="form-group">
							<div class="col-sm-2 pull-left" style="margin-top: 60px">
								<button id="register" class="btn btn-primary btn-lg"
									type="submit">Register</button>
							</div>
							<a href="/RGACustomerModule"><button id="back"
									class="btn btn-primary btn-lg" type="button"
									style="float: right; margin-top: 59px">Back</button></a>
						</div>
					</div>
				</sf:form>
			</div>
		</section>
	</div>
	<!-- END PAGE SOURCE -->
</body>
</html>
