var app = angular.module('configurationManager');

app.service("GetLoggerUser", ['$http', '$q',function ($http, $q) {

    // Return public API.
    return ({
        get: get,
    });

    // ---
    // PUBLIC METHODS.
    // ---

    // I get all of the friends in the remote collection.
    function get() {

        var request = $http({
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            method: "get",
            url: "identity/get"
        });

        return (request.then(handleSuccess, handleError));

    }

    function handleError(response) {
    	console.log(response);
        if (!response.statusText || !response.status) {
            return ($q.reject({ message: "An unknown error occurred.", status: null }));
        }
        return ($q.reject({ message: response.statusText, status: response.status }));

    }

    function handleSuccess(response) {
        return (response.data);
    }


}]);