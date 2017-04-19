<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New User</title>
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet"></link>
<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
<link rel="stylesheet" type="text/css"
	href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />
</head>

<body>
	<div id="mainWrapper">
		<div class="login-container">
			<div class="login-card">
				<div class="login-form">
				
					<c:if test="${ErrorMsg != null}">
						<div class="alert alert-danger">
							<p>Email Already Exists.</p>
						</div>
					</c:if>
					
					<c:url var="newUserUrl" value="/newuser/process" />
					<form action="${newUserUrl}" method="post" class="form-horizontal">

						<div class="input-group input-sm">
							<label class="input-group-addon" for="firstName"><i
								class="fa fa-user"></i></label> <input type="text" class="form-control"
								id="firstName" name="firstName" placeholder="Enter First Name"
								required>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="lastName"><i
								class="fa fa-user"></i></label> <input type="text" class="form-control"
								id="lastName" name="lastName" placeholder="Enter Last Name"
								required>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="email"><i
								class="fa fa-envelope"></i></label> <input type="email"
								class="form-control" id="email" name="email"
								placeholder="Enter Email" required>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="phone"><i
								class="fa fa-phone"></i></label> <input type="text" class="form-control"
								id="phone" name="phone" placeholder="Enter Phone Number"
								required>
						</div>
						<div class="input-group input-sm">
							<label class="input-group-addon" for="password"><i
								class="fa fa-lock"></i></label> <input type="password"
								class="form-control" id="password" name="password"
								placeholder="Enter Password" required>
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />

						<div class="form-actions">
							<input type="submit"
								class="btn btn-block btn-primary btn-default" value="Register">
							<p>
								<a href="<c:url value='/login' />">already have account ?</a>
							</p>

						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>