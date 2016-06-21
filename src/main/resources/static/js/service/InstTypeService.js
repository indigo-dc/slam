var app = angular.module('ltosApp');

app.service("instTypeService", function($http, $q) {
	var instTypeList = [ "Hospital", "Industry", "International Organization",
			"NGO", "Private", "Research Institute", "University", "Other" ];

	var getInstTypeList = function() {
		return instTypeList;
	}
	// Return public API.
	return ({
		getInstTypeList : getInstTypeList
	});
});