var app = angular.module('ltosApp');

app.service("profileService", function ($http, $q) {

    return ({
        getUser: getUser,
        getIdentity: getIdentity
    });
    
    function getUser() {

        var request = $http({
            headers: {'Content-Type': 'application/json; charset=UTF-8'},
            method: "get",
            url: "user/get"
        });
        return (request.then(handleSuccess, handleError));

    }

    // I get all of the friends in the remote collection.
    function getIdentity() {

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

    append('file', $scope.newAttachment);

    function handleSuccess(response) {
        return (response.data);

    }

});