var app = angular.module('configurationManager');

app.controller('MainController', ['$scope', '$routeParams','$route', 'QueryService',
function ($scope, $routeParams, $route, QueryService) {
    $scope.isLoading = true;
    $scope.documents;
    $scope.queries;

    $scope.init = function() {

    }

    setTimeout(function(){
    }, 100);
        _loadRemoteData();

    // I load the remote data from the server.
    function _loadRemoteData() {
        $scope.isLoading = true;

        QueryService.getAvailableQueries().then(
            function(result) {
                serveResult(result);
            }
        );
    }

    function serveResult(result) {
        //if(result['status'] == 'SUCCESS') {
            $scope.queries = result['data'];
        //} else {
        //    alert("blad");
        //}
    }

  }]);