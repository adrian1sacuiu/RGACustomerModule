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
<link rel="stylesheet" href="/RGACustomerModule/resources/css/reset.css"
	type="text/css" media="all">
<link rel="stylesheet" href="/RGACustomerModule/resources/css/style.css"
	type="text/css" media="all">
<script src="/RGACustomerModule/resources/scripts/jquery.js"></script>
<script src="/RGACustomerModule/resources/scripts/app.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Customer</title>
</head>
<body>
	<jsp:include page="topMenu.jsp" flush="true" />
	<c:set var="val" value="${customer.idCustomer}" />
	<c:choose>
		<c:when test="${val == null}">
			<c:set var="function" value="addCustomer()" />
			<c:set var="buttonText" value="Add Customer" />
		</c:when>
		<c:otherwise>
			<c:set var="function" value="updateCustomer()" />
			<c:set var="buttonText" value="Update Customer" />
		</c:otherwise>
	</c:choose>
	<button id="modalButton" type="button" class="btn btn-primary"
		data-toggle="modal" data-target="#myModal" style="display: none;"></button>
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title text-capitalize" id="myModalLabel"></h4>
				</div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>
	<div class="main">
		<section id="content">
			<div id="info" class="row well center-block" style="width: 50%;">
				<div id="customer_error" class="alert alert-danger fade in"
					style="display: none;"></div>
				<sf:form class="form-horizontal" role="form" id="customerForm"
					modelAttribute="customer"
					onsubmit="event.preventDefault(); ${function};">
					<sf:input type="hidden" value="${customer.idCustomer}"
						path="idCustomer" />
					<div class="form-group">
						<label for="inputEmail" class="col-sm-3 control-label">Email
							<span class="red">*</span>
						</label>
						<div class="col-sm-9">
							<sf:input type="email" class="form-control" id="inputEmail"
								value="${customer.email}" placeholder="Email" path="email"
								required="required" />
							<br />
						</div>
					</div>
					<div class="form-group">
						<label for="inputFirstName" class="col-sm-3 control-label">First
							Name </label>
						<div class="col-sm-9">
							<sf:input type="text" class="form-control" id="inputFirstName"
								value="${customer.firstName}" placeholder="First Name"
								path="firstName" />
							<br />
						</div>
					</div>
					<div class="form-group">
						<label for="inputLastName" class="col-sm-3 control-label">Last
							Name </label>
						<div class="col-sm-9">
							<sf:input type="text" class="form-control" id="inputLastName"
								value="${customer.lastName}" placeholder="Last Name"
								path="lastName" />
							<br />
						</div>
					</div>
					<div class="form-group">
						<label for="inputAdress" class="col-sm-3 control-label">Address
						</label>
						<div class="col-sm-9">
							<sf:input type="text" class="form-control" id="inputAdress"
								value="${customer.address}" placeholder="Address" path="address" />
							<br />
						</div>
					</div>
					<div class="form-group">
						<label for="inputPhone" class="col-sm-3 control-label">Phone
						</label>
						<div class="col-sm-9">
							<sf:input type="text" class="form-control" id="inputPhone"
								value="${customer.phone}" placeholder="Phone" path="phone" />
							<br />
						</div>
					</div>
					<div class="col-sm-12">
						<div class="form-group">
							<div class="col-sm-2 pull-left" style="margin-top: 60px">
								<button class="btn btn-primary btn-lg" id="submitButton"
									type="submit">${buttonText}</button>
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
