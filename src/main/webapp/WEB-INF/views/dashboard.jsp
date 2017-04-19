<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Weather APP</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
textarea {
	resize: none;
}
</style>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Weather Checker dashboard</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">

				<ul class="nav navbar-nav navbar-right">
					<li><a href="<c:url value="/logout" />">Logout</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>

	<div class="row">
		<div class="col-md-4">
			<ul class="nav nav-tabs nav-stacked">
				<li class="active"><a data-toggle="tab" href="#home">System
						Users (${NumberOfusers})</a></li>
				<li><a data-toggle="tab" href="#menu2">System Notes
						(${fn:length(notes)})</a></li>
				<li><a data-toggle="tab" href="#menu3">Predefine Notes</a></li>
			</ul>
		</div>
		<div class="col-md-8">
			<div class="tab-content">
				<div id="home" class="tab-pane fade in active">
					<h3>Users</h3>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Firstname</th>
								<th>Lastname</th>
								<th>Email</th>
								<th>Phone Number</th>
								<sec:authorize access="hasRole('ADMIN')">
									<th width="100"></th>
								</sec:authorize>

							</tr>
						</thead>
						<tbody>
							<c:forEach items="${users}" var="user">
								<tr>
									<td>${user.firstName}</td>
									<td>${user.lastName}</td>
									<td>${user.email}</td>
									<td>${user.phone}</td>
									<sec:authorize access="hasRole('ADMIN')">
										<td><a
											href="<c:url value='/delete-user-${user.ssoId}' />"
											class="btn btn-danger custom-width">delete</a></td>
									</sec:authorize>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div id="menu2" class="tab-pane fade">
					<h3>Notes</h3>
					<table class="table table-hover">
						<thead>
							<tr>
								<th style="width: 80%">Note</th>
								<th style="width: 10%">Date</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${todayNote ne true}">
								<tr>
									<td id="todayNoteColumn"><textarea id="todayNote"
											class="form-control" rows="5" cols="100"
											placeholder="Write Today Note"></textarea></td>
									<td id="todayDate"><button type="button"
											class="btn btn-success" onclick="saveTodayNote()">Save</button></td>
								</tr>
							</c:if>
							<c:forEach items="${notes}" var="note">
								<tr>
									<td>${note.description}</td>
									<td>${note.date}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>


				</div>
				<div id="menu3" class="tab-pane fade">
					<h3>PreDefine Notes</h3>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>From</th>
								<th>To</th>
								<th>description</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${preNotes}" var="note">
								<tr>
									<td>${note.from}&deg;</td>
									<td>${note.to}&deg;</td>
									<td id="${note.id}">${note.description}</td>
									<td id="editButton${note.id}">
										<button type="button" class="btn btn-success"
											onclick="editFild(${note.id})">Edit</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

	</div>

	<script type="text/javascript">
		function editFild(id) {
			var description = $("#"+id).text();
		$("#"+id).html("<input class='form-control' type='text' id='input"+id+"' value='"+description+"' >")
		$("#editButton"+id).html("<button type='button' class='btn btn-success' onclick='saveNote("+id+")'>Save</button>")
		}
		function saveNote(id) {
			var newDescription = $("#input"+id).val();
			if(newDescription.length > 0){
			$("#"+id).html(newDescription);
			$("#editButton"+id).html("<button type='button' class='btn btn-success' onclick='editFild("+id+")'>Edit</button>");
			$.ajax({
				url : "editPreNote?id=" + id +"&newDescription="+newDescription,
				success : function(data) {
				}
			});}else{
				alert("Write somthing !")
			}
		}
		function saveTodayNote() {
			var todayNote = $("#todayNote").val();
			if(todayNote.length > 0){
			$.ajax({
				url : "setTodayNote?note="+todayNote,
				success : function(data) {
					$("#todayNoteColumn").text(todayNote);
					$("#todayDate").text(data);


				}
			});
			}
		}
	</script>





</body>
</html>