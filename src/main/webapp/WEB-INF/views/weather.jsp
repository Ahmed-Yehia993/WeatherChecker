<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
p {
	font-size: 18px;;
}

.temp {
	font-size: 37px;
	color: orange;
	float: right;
	margin-top: 20px;
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
				<a class="navbar-brand" href="#">Weather Checker</a>
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

	<div class="container">
		<div class="tab-content">
			<div id="home" class="tab-pane fade in active">
				<div class="row">

					<div class="col-md-12">
						<h1>Select the desired location</h1>
						<div id="googleMap" style="width: 100%; height: 380px;"></div>

					</div>
				</div>

				<div class="col-md-6">
					<div class="row">
						<div class="col-md-6">
							<button type="button" class="btn btn-info" data-toggle="collapse"
								data-target="#demo">Click To see note</button>
							<div id="demo" class="collapse">
								<p id='note'></p>
							</div>
						</div>


					</div>
				</div>


			</div>
		</div>
	</div>


	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyArLo-5AqNLpes8rHv0ULsKyhfLAu3qpWk">
		
	</script>

	<script>
		var markers = [];

		var map;
		var myCenter = new google.maps.LatLng(34.27083595165, 22.3681640625);

		function grabMyPosition() {
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(initialize);
			} else {
				alert("You don't support this");
			}
		}

		function initialize(position) {
			myCenter = new google.maps.LatLng(position.coords.latitude,
					position.coords.longitude);
			var mapProp = {
				center : myCenter,
				zoom : 10,
				mapTypeId : google.maps.MapTypeId.ROADMAP
			};

			map = new google.maps.Map(document.getElementById("googleMap"),
					mapProp);

			google.maps.event.addListener(map, 'click', function(event) {
				placeMarker(event.latLng);
			});
			placeMarker(myCenter);
		}

		function placeMarker(location) {

			for (i = 0; i < markers.length; i++) {
				markers[i].setMap(null);
			}

			var marker = new google.maps.Marker({
				position : location,
				map : map,
			});
			markers.push(marker);
			var contentstr = "";
			var key1 = 'ae58586406a5dd26';
			var Weather = "http://api.wunderground.com/api/" + key1
					+ "/forecast/geolookup/conditions/q/" + location.lat()
					+ "," + location.lng() + ".json";
			console.log(Weather);
			$.ajax({
				url : Weather,
				dataType : "jsonp",
				success : function(data) {

					var deg = data['current_observation']['temp_c'];
					var temp = deg + " &deg;C";
					var desc = data['current_observation']['weather']

					var img = data['current_observation']['icon_url'];
					contentstr += "<img src='"+img+"'/><span class='temp'>"
							+ temp + "</span><br><p>" + desc + "</p>";

					var infowindow = new google.maps.InfoWindow({

						content : contentstr

					});
					infowindow.open(map, marker);
					$.ajax({
						url : "getNote?degree=" + deg,
						success : function(data) {
							$("#note").text(data);
						}
					});

				}
			});

		}
		grabMyPosition();
	</script>
</body>
</html>