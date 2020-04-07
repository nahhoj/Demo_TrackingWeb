sap.ui.define([
	"sap/ui/core/Control"
], function (Control) {
	"use strict";
	var map;
	return Control.extend("com.co.johan.maven.openui5.control.GoogleMaps", {
		metadata: {
			properties: {
				"key": "string",
				"width": { 
					"type": "sap.ui.core.CSSSize",
					"defaultValue": "100%"
				},
				"height": { 
					"type": "sap.ui.core.CSSSize",
					"defaultValue": "100%"
				},
			},
			aggregations: {},
			events : {
				afterloadMap : {}
			}
		},
		init : function () {},
		renderer : function (oRM, oControl) {
			var sGlobalStyle = "width:" + oControl.getWidth()  + ";height:" + oControl.getHeight() + ";"; // Come on it's ES6 Mr SAP
			var sLoadingStyle = 'color:#A09494;text-align:center;font-size:1rem;padding-top:2rem';		
			oRM.write("<div id='map'");
			oRM.writeAttributeEscaped("style", sGlobalStyle);
			oRM.write(">");
			oRM.write("</div>");
		},
		onAfterRendering:function(){
			var url='https://maps.googleapis.com/maps/api/js?key=' + this.getKey();
			var control=this;
			$.getScript(url).done(function(){
			         	map = new google.maps.Map(document.getElementById('map'), {
			         	center: {lat: -34.397, lng: 150.644},
			         	zoom: 8
			        });
			         control.fireEvent("afterloadMap");
			});
			
		},
		putMark:function(lat,lng,icon,msg){
			var infowindow = new google.maps.InfoWindow({
		          content: msg
		        });
			var uluru = {lat: lat, lng: lng};
		    var latlng = new google.maps.LatLng(lat,lng);
		    map.setCenter(latlng);
		    map.setZoom(16);
			var marker = new google.maps.Marker({position: uluru, map: map,icon:icon});
			 marker.addListener('click', function() {
		          infowindow.open(map, marker);
		        });
		},
		drawPath:function(PlanCoordinates){
			var flightPath = new google.maps.Polyline({
		          path: PlanCoordinates,
		          geodesic: true,
		          strokeColor: '#FF0000',
		          strokeOpacity: 1.0,
		          strokeWeight: 2
		        });
			flightPath.setMap(map);
		},
		getGeolocation:function(){
			var infoWindow = new google.maps.InfoWindow;
			if (navigator.geolocation) {
		          navigator.geolocation.getCurrentPosition(function(position) {
		            var pos = {
		              lat: position.coords.latitude,
		              lng: position.coords.longitude
		            };
		            infoWindow.setPosition(pos);
		            infoWindow.setContent('Location found.');
		            infoWindow.open(map);
		            map.setCenter(pos);
		          }, function() {
		            handleLocationError(true, infoWindow, map.getCenter());
		          });
		        } else {
		          // Browser doesn't support Geolocation
		          handleLocationError(false, infoWindow, map.getCenter());
		        }
		}
	});
});