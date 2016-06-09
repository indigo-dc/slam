var app = angular.module('ltosApp');

app.service("helperService", function($http, $q) {
    
	var handleError = function handleError(response, callback) {
		console.log(response);
		if(response.status) {
			if (response.data && response.data.errors && response.data.errors.length > 0) {
				callback(response.status, response.data.errors[0].message);
				return; 
			}
			callback(response.status, response.statusText);
			return;
		}
		callback(0, "Unexpected error");
		return;
	}
	
	 var alertError = function(processedResponse) {
			handleError(processedResponse, function(status, message) {
				switch (status) {
					case 401:
					case 403:
						alert(message);
						window.location = "#/";
			            location.reload();
			            break;
			        default:
			        	alert(message);
				}
			});
		};

    return ({
    	handleError:handleError,
    	alertError:alertError
    });
    
});