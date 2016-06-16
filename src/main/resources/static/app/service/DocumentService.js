var app = angular.module('configurationManager');

app.service("DocumentService", ['$http', '$q', function ($http, $q) {

    // Return public API.
    return ({
        getDocument: getDocument,
        doAction: doAction,
        getActionsAvailableForDocument: getActionsAvailableForDocument,
        createRequest: createRequest,
        createPool: createPool,
        getBranchOfScience: getBranchOfScience
    });

    // ---
    // PUBLIC METHODS.
    // ---

    // I get all of the friends in the remote collection.
    function getDocument(documentId) {

        return $http.get("document/getwithextradata",{
            params: {
                documentId: documentId
            }
        });

        return request;
    }

    function getBranchOfScience(code) {

        var request = $http({
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            method: "get",
            url: "getBranch",
            params: {
                code : code
            }
        });

        return request;

    }

    function doAction(documentId, actionId, metricValues) {

        var postData = {};
        var metrics = {};
        for (key in metricValues) {
            metrics[key] = metricValues[key];
        }
        postData['metrics'] = metrics;
        console.log(postData);

        return $http({
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            method: "post",
            url: "action/invoke",
            data: $.param(postData),
            //data: JSON.stringify(metricValues),
            params: {
                actionId: actionId,
                documentId: documentId
            }
        });
    }

    function createRequest(grantId, team, affiliationId, branchOfScienceId) {

        var postData = {};
        postData['grantId'] = grantId;
        postData['team'] = team;
        postData['affiliationId'] = affiliationId;
        postData['branchOfScienceId'] = branchOfScienceId;

        var request = $http({
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            method: "put",
            url: "grant/create",
            data: $.param(postData),
            params: {}
        });

        return (request.then(handleSuccess, handleError));
    }

    function createPool(grantId, site) {

        var postData = {};
        postData['grantId'] = grantId;
        postData['site'] = site;

        var request = $http({
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            method: "put",
            url: "pool/create",
            data: $.param(postData),
            params: {}
        });

        return (request.then(handleSuccess, handleError));
    }


    function getActionsAvailableForDocument(documentId) {

        var request = $http({
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            method: "get",
            url: "action/available",
            params: {
                documentId: documentId
            }
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

            return ("An unknown error occurred.");

        }

        // Otherwise, use expected error message.
        return ($q.reject(response.data));

    }

    // I transform the successful response, unwrapping the application data
    // from the API response payload.
    function handleSuccess(response) {

        return (response.data);

    }

}]);