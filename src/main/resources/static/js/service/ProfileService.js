var app = angular.module('ltosApp');

app.service("profileService", ['$http', '$q', 'identityService', function ($http, $q, identityService) {

    return ({
        getUser: getUser,
        getIdentity: getIdentity, 
        loadIdentity, loadIdentity
    });
    
    function getUser() {
        var request = $http({
            headers: {'Content-Type': 'application/json; charset=UTF-8'},
            method: "get",
            url: "user/get"
        });
        return (request.then(handleSuccess, handleError));
    }
    
    function loadIdentity() {
    	return getIdentity().then(function (response){
    		identityService.setIdentity(response);
    	});	
    }

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

}]);