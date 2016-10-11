/**
 * Created by krzysiek on 13.05.15.
 */
var app = angular.module('indigo');

app.service("dashboardService", function ($http, $q) {

    // Return public API.
    return ({
        getUserFirstSteps: getUserFirstSteps
    });

    // ---
    // PUBLIC METHODS.
    // ---

    // I get all of the friends in the remote collection.
    function getUserFirstSteps() {

        var request = $http({
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            method: "get",
            url: "user/getSteps"
        });

        return (request.then(handleSuccess, handleError));

    }

    // ---
    // PRIVATE METHODS.
    // ---

    // I transform the error response, unwrapping the application dta from
    // the API response payload.
    function handleError(response) {
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

    append('file', $scope.newAttachment);
    // I transform the successful response, unwrapping the application data
    // from the API response payload.
    function handleSuccess(response) {
        return (response.data);
    }

});