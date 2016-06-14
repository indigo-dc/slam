var app = angular.module('ltosApp');

app.service("affiliationService", function($http, $q) {
	
	var currentAffiliation;
	
	var setCurrentAffiliation = function(newAffiliation) {
		currentAffiliation = newAffiliation;
	}

	var getCurrentAffiliation = function() {
		return currentAffiliation;
	}

	return ({
		setCurrentAffiliation : setCurrentAffiliation,
		getCurrentAffiliation : getCurrentAffiliation
	});
});