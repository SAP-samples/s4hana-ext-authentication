sap.ui.define([
		"ba/authenticationUI/controller/BaseController"
	], function (BaseController) {
		"use strict";

		return BaseController.extend("ba.authenticationUI.controller.NotFound", {

			/**
			 * Navigates to the worklist when the link is pressed
			 * @public
			 */
			onLinkPressed : function () {
				this.getRouter().navTo("worklist");
			}

		});

	}
);