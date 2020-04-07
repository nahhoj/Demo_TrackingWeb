sap.ui.define([ "sap/ui/core/mvc/Controller","sap/ui/model/json/JSONModel" ], function(Controller,JSONModel) {
	"use strict";
	return Controller.extend("com.co.johan.maven.openui5.controller.App", {
		onInit : function() {			
			sap.ui.getCore().getEventBus().subscribe("model","loadModel",this.loadModels,this);
		},		
		onBeforeRendering:function(){},
		loadModels:function(){
			var url = window.origin + "/TrackingDemo/"	+  "Session";
			var session=false;
			jQuery.ajax({
				url : url,
				method : "GET",
				datatype : "json",
				async:false,
				success:function(response){
					if (response=="true")
					session=true;
				},
				error : function() {
					session=false;
				}	
			});		
			if (!session){				
				var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
				oRouter.navTo("login");				
			}
			else{
				var oView=this.getView();
				var cntr=this;
				var url = window.origin + "/TrackingDemo/"	+  "LastEvent";				
				jQuery.ajax({
					url : url,
					method : "GET",
					datatype : "json",
					async:true,
					success : function(response) {
						var oModel = new JSONModel(response);
						oView.setModel(oModel, "events");
						var url = window.origin + "/TrackingDemo/"	+  "Unit";
						jQuery.ajax({
							url : url,
							method : "GET",
							datatype : "json",
							async:true,
							success : function(response) {
								var oModel = new JSONModel(response);
								oModel.oData.push({IdUnit: "All", idClient: 0, type: "U", Brand: "", Reference: "", icon: "", Status: false});
								oView.setModel(oModel, "units");
								cntr.putOnMap();
							},
							error : function() {

							}
						});						
					},
					error : function() {

					}
				});
				var url = window.origin + "/TrackingDemo/"	+  "Client";
				jQuery.ajax({
					url : url,
					method : "GET",
					datatype : "json",
					async:true,
					success : function(response) {
						var oModel = new JSONModel(response);
						oView.setModel(oModel, "client");						
					},
					error : function() {
		
					}
				});				
			}
		},
		putOnMap:function(){
			var oMap=this.getView().byId("map");
			var oModelEvents=this.getView().getModel("events");
			var oModelUnits=this.getView().getModel("units");
			var cont=this;
			oModelEvents.oData.forEach(function(value){
				var msg="<p>ID Event = " + value.IdEvent + "</p>";
				var icon=cont.getIcon(oModelUnits,value.IdUnit);
				msg+="<p>ID Unit = " + value.IdUnit + "</p>";
				msg+="<p>Latitude = " + value.Lat + "</p>";
				msg+="<p>Longitude = " + value.Lon + "</p>";
				msg+="<p>Speed = " + value.Speed + "</p>";
				msg+="<p>Date = " + value.dateTime + "</p>";
				msg+="<p>Event = " + value.Event + "</p>";
				oMap.putMark(cont.convertLatlon(value.Lat), cont.convertLatlon(value.Lon),icon,msg);
			});
		},
		onPutOnMap : function(oEvent) {
			var lat=oEvent.getSource().getCells()[2].getText();
			var lon=oEvent.getSource().getCells()[3].getText();
			var oModelUnits=this.getView().getModel("units");
			var icon=this.getIcon(oModelUnits,oEvent.getSource().getCells()[1].getText());
			var msg="<p>ID Event = " + oEvent.getSource().getCells()[0].getText() + "</p>";
			msg+="<p>ID Unit = " + oEvent.getSource().getCells()[1].getText() + "</p>";
			msg+="<p>Latitude = " + lat + "</p>";
			msg+="<p>Longitude = " + lon + "</p>";
			msg+="<p>Speed = " + oEvent.getSource().getCells()[4].getText() + "</p>";
			msg+="<p>Date = " + oEvent.getSource().getCells()[5].getText() + "</p>";
			msg+="<p>Event = " + oEvent.getSource().getCells()[6].getText() + "</p>";
			var oMap = this.getView().byId("map");
			oMap.putMark(this.convertLatlon(lat), this.convertLatlon(lon),icon,msg);
		},
		reloadMap:function(){
			var oMap=this.getView().byId("map");
			oMap.reloadMap();
		},
		drawPath:function(){
			var cntrl=this;
			var oMap = this.getView().byId("map");
			var oModelEvent=this.getView().getModel("events");			
			var oModelPlanCoordinates = new JSONModel([]);
			oModelEvent.oData.forEach(function(value){
				var latlon={lat: cntrl.convertLatlon(value.Lat),lng: cntrl.convertLatlon(value.Lon)};
				oModelPlanCoordinates.oData.push(latlon);								
			});			
			oMap.drawPath(oModelPlanCoordinates.oData);
			this.putOnMap();
		},
		convertLatlon : function(latlon) {
			if (latlon.indexOf("S") > 0 || latlon.indexOf("W") > 0)
				latlon = '-' + latlon;
			latlon = latlon.substring(0, latlon.length - 1);
			latlon = parseFloat(latlon) / 100;
			var temp = parseInt(latlon);
			latlon = latlon - temp;
			latlon = latlon * 100 / 60;
			latlon = latlon + temp;
			return latlon;
		},
		eventsUnit:function(oControlEvent){
			var oView=this.getView();
			var IdUnit=oView.byId("cbUnit")._lastValue;
			var dStart=oView.byId("dtStart").getProperty("value");
			var dEnd=oView.byId("dtEnd").getProperty("value");
			var Speed=oView.byId("iSpeed")._lastValue;
			var Event=oView.byId("cbEvent")._lastValue;
			var filter="Event";
			var flagFirst=false;
			if (IdUnit!='' && IdUnit!='All'){
				flagFirst=true;
				filter+="?IdUnit=" + IdUnit;
			}
			if (dStart!=''){
				if (!flagFirst)
					filter+="?";
				else
					filter+="&";
				flagFirst=true;
				filter+="dStart=" + dStart;
			}
			if (dEnd!=''){
				if (!flagFirst)
					filter+="?";
				else
					filter+="&";
				flagFirst=true;
				filter+="dEnd=" + dEnd;
			}
			if (Speed!=''){
				if (!flagFirst)
					filter+="?";
				else
					filter+="&";
				flagFirst=true;
				filter+="Speed=" + Speed;
			}
			if (Event!=''){
				if (!flagFirst)
					filter+="?";
				else
					filter+="&";
				flagFirst=true;
				filter+="Event=" + Event;
			}
			var url = window.origin + "/TrackingDemo/"+  filter;
			var oModel = new JSONModel(url);
			this.getView().setModel(oModel, "events");
		},
		getIcon:function(oModel,b){
			var icon;
			oModel.oData.forEach(function(value){
				if (value.IdUnit==b)
					icon=value.icon;
			});
			return icon;
		},
		onLogout:function(){			
			 sap.ui.getCore().getEventBus().publish("login","logout");
		}
	});
});
