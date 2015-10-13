<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="/RGACustomerModule/resources/css/bootstrap.min.css"
	type="text/css" media="all">
<link rel="stylesheet" href="/RGACustomerModule/resources/css/reset.css" type="text/css"
	media="all">
<link rel="stylesheet" href="/RGACustomerModule/resources/css/style.css"
	type="text/css" media="all">
<link rel="stylesheet"
	href="<c:url value="https://code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css"/>"
	type="text/css" media="all">
<link rel="stylesheet"
	href="<c:url value="https://cdn.datatables.net/1.10.9/css/dataTables.jqueryui.min.css"/>"
	type="text/css" media="all">
<script src="/RGACustomerModule/resources/scripts/jquery.js"></script>
<script src="/RGACustomerModule/resources/scripts/require.js"></script>
<script src="/RGACustomerModule/resources/scripts/app.js"></script>
<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.9/js/jquery.dataTables.min.js"></script>
<script
	src="https://cdn.datatables.net/1.10.9/js/dataTables.jqueryui.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>RGA Customer Module</title>
</head>
<body>
	<security:authorize access="isAuthenticated()">
		<jsp:include page="views/topMenu.jsp" flush="true" />
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
					<div class="modal-footer">
					</div>
				</div>
			</div>
		</div>
		<button id="modalButton" type="button" class="btn btn-primary btn-lg"
			data-toggle="modal" data-target="#myModal" style="display: none;">
		</button>
		<div class="row well center-block" id="customerInfo">
			<script>
				$(function() {
					$(document).on("ready", function(event) {
						getAllCustomers();
					});
				});
			</script>
		</div>
	</security:authorize>
	<security:authorize access="isAnonymous()">
		<jsp:include page="views/welcomePage.jsp" flush="true" />
	</security:authorize>
</body>
</html>