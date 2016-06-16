var app = angular.module('configurationManager');

app.controller('MainController', ['$scope', '$routeParams', '$route', 'QueryService', 'GetLoggerUser',
    function ($scope, $routeParams, $route, QueryService, GetLoggerUser) {
        $scope.isLoading = false;
        $scope.documents;
        $scope.queries;
        $scope.identity;
        $scope.hasProviderRole;

        $scope.init = function () {

        }

        _loadRemoteData();

        // I load the remote data from the server.
        function _loadRemoteData() {
            $scope.isLoading = true;

            QueryService.getAvailableQueries()
            .then(function(result) {
                serveResult(result.data);
            })
            .catch(function(error) {
                console.log(error);
                alert('wystąpil błąd...');
            })
            .finally(function() {
                $scope.isLoading = false;
            });
        }

        function serveResult(result) {
            $scope.queries = result['data'];
        }

        GetLoggerUser.get().then(function(result) {
            $scope.identity = result;

           /* $scope.hasProviderRole = $scope.identity['roles'].indexOf("provider") !== -1;*/
        });

    }]);