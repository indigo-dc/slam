var app = angular.module('ltosApp');

app.service("affiliationService", function($http, $q) {
	var affiliation = '';
	var setAffiliation = function(newAffiliation) {
		affiliation = newAffiliation;
	}

	var getAffiliation = function() {
		return affiliation;
	}

	return ({
		setAffiliation : setAffiliation,
		getAffiliation : getAffiliation
	});
});