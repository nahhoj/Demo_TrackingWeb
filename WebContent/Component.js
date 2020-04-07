sap.ui.define([ "sap/ui/core/UIComponent"], function(UIComponent) {
	"use strict";
	return UIComponent.extend("com.co.johan.maven.openui5.Component", {
		metadata : {
			manifest : "json"
		},
		init : function() {
			UIComponent.prototype.init.apply(this, arguments);				
			this.getRouter().initialize();
		}
	});
});