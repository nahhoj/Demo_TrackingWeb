sap.ui.define([ "sap/ui/core/UIComponent", "sap/ui/model/json/JSONModel" ], function(UIComponent,JSONModel) {
	"use strict";
	return UIComponent.extend("com.co.johan.maven.openui5.Component", {
		metadata : {
			manifest : "json"
		},
		init : function() {
			UIComponent.prototype.init.apply(this, arguments);
			var comp=this;
			var url = window.location.href	+ "Client?email=nahhoj@hotmail.com";
			jQuery.ajax({
				url : url,
				method : "GET",
				datatype : "json",
				success : function(response) {
					var oModel = new JSONModel(response);
					comp.setModel(oModel, "client");				
					var idClient=oModel.getProperty("/id");
					var url = window.location.href + "Unit?IdClient=" + idClient;
					jQuery.ajax({
						url : url,
						method : "GET",
						datatype : "json",
						success : function(response) {
							var oModel = new JSONModel(response);
							comp.setModel(oModel, "units");
						},
						error : function() {
	
						}
					});
					var url = window.location.href + "LastEvent?IdClient=" + idClient;
					jQuery.ajax({
						url : url,
						method : "GET",
						datatype : "json",
						success : function(response) {
							var oModel = new JSONModel(response);
							comp.setModel(oModel, "events");
						},
						error : function() {
	
						}
					});
				},
				error : function() {
	
				}
			});		
		}
	});
});