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

        function returnQuery(query){
            for(var result in $scope.queries){
                if(result.id==query){
                    return result;
                }
            }
        }

        function serveResult(result) {
            $scope.queries = result['data'];
            console.log($scope.queries);
        }

        GetLoggerUser.get().then(function(result) {
            $scope.identity = result;
            if($scope.identity.operator == true){
                $scope.hasProviderRole = true;
            }
            console.log($scope.identity);
           /* $scope.hasProviderRole = $scope.identity['roles'].indexOf("provider") !== -1;*/
        });

    }]);