var app = angular.module('indigo');

app.service("institutionService", function ($http, $q) {
    // Return public API.
    return ({
        getInstitutions:getInstitutions
    });

    // ---
    // PUBLIC METHODS.
    // ---

    // I get all of the friends in the remote collection.

    function getInstitutions() {
//	        console.log("email:"+email);
        var request = $http({
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            method: "get",
            url: "getInstitutionList"
        });

        return (request.then(handleSuccess, handleError));

    }

    // ---
    // PRIVATE METHODS.
    // ---

    // I transform the error response, unwrapping the application dta from
    // the API response payload.
    function handleError(response) {
//	    	console.log(response);
        // The API response from the server should be returned in a
        // nomralized format. However, if the request was not handled by the
        // server (or what not handles properly - ex. server error), then we
        // may have to normalize it on our end, as best we can.
        if (!angular.isObject(response.data) || !response.data.message) {

            return ($q.reject("An unknown error occurred."));

        }

        // Otherwise, use expected error message.
        return ($q.reject(response.data.message));

    }

    // I transform the successful response, unwrapping the application data
    // from the API response payload.
    function handleSuccess(response) {
//	    	console.log(response);

        return (response.data);

    }

});