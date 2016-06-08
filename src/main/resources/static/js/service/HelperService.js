var app = angular.module('ltosApp');

app.service("helperService", function($http, $q) {
    
	var handleError = function getError(response) {
		console.log(response);
		if(response.status && response.statusText) {
			if (response.data && response.data.errors && response.data.errors.length > 0) {
				return response.data.errors[0].message;
			} 
			return response.status + " " + response.statusText;
		}
		return "Unexpected error";
	}

    return ({
    	handleError:handleError
    });
    
});