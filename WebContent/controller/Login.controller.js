sap.ui.define(["sap/ui/core/mvc/Controller"], function(Controller) {
	"use strict";
	return Controller.extend("com.co.johan.maven.openui5.controller.Login", {
		onInit:function(){
			sap.ui.getCore().getEventBus().subscribe("login","logout",this.onlogOut,this);
			var url = window.origin + "/TrackingDemo/"	+ "Session";
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
			if (session){
				sap.ui.getCore().getEventBus().publish("model","loadModel");
				var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
				oRouter.navTo("app");				
			}
				
		},
		onLogin:function(){
			var oView=this.getView();
			var user=oView.byId("username").getValue();
			var passwd=oView.byId("password").getValue();
			var cntr=this;
			if (user!='' && passwd!=''){
				var login=btoa(user + ":" + passwd);
				var url = window.location.origin + "/TrackingDemo/"	+  "Session";
				var cntr=this;
				jQuery.ajax({
					url : url,
					method : "POST",		
					headers:{"login":login}, 
					success : function(response) {
						if (response=='Success'){
							sap.ui.getCore().getEventBus().publish("model","loadModel");
							var oRouter = sap.ui.core.UIComponent.getRouterFor(cntr);
							oRouter.navTo("app");
						}	
					},				
					error : function() {
						
					}
				});	
			}
			
	},
	onlogOut:function(){
		var url = window.location.origin + "/TrackingDemo/"	+  "Session";
		var cntr=this;
		jQuery.ajax({
			url : url,
			method : "POST",		
			headers:{"logout":true}, 
			success : function(response) {
				if (response=='Success'){
					sap.ui.getCore().getEventBus().publish("model","loadModel");
					var oRouter = sap.ui.core.UIComponent.getRouterFor(cntr);
					oRouter.navTo("login");
				}	
			},				
			error : function() {
				
			}
		});	
	}
	});
});
	
	