

var app = angular.module('configurationManager');

app.controller('MainController', ['$scope', '$routeParams','$route', 'QueryService', 'GetLoggerUser',
    function ($scope, $routeParams, $route, QueryService, GetLoggerUser) {
        $scope.isLoading = true;
        $scope.queries;

        $scope.init = function() {
        };

        setTimeout(function(){
        }, 100);
        _loadRemoteData();

        // I load the remote data from the server.
        function _loadRemoteData() {
            $scope.isLoading = true;

            QueryService.getAvailableQueries()
            .then(function(result) {
                serveResult(result);
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

        });

    }]);
