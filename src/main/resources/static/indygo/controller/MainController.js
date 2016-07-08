var app = angular.module('configurationManager');

app.controller('MainController', ['$scope', '$routeParams', '$route', 'QueryService', 'GetLoggerUser',
    function ($scope, $routeParams, $route, QueryService, GetLoggerUser) {
        $scope.isLoading = true;
        $scope.queries;


        $scope.init = function() {
            _loadRemoteData();
        };

        _loadRemoteData();

        // I load the remote data from the server.
        function _loadRemoteData() {
            $scope.isLoading = true;

            QueryService.getAvailableQueries("default")
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

        $scope.returnQuery = function (query) {
            for(var result in $scope.queries){
                if(result.id==query){
                    return result;
                }
            }
        }

        function serveResult(result) {
            $scope.queries = result['data'];
        }

        GetLoggerUser.get().then(function(result) {
            $scope.identity = result;
            if($scope.identity.operator == true){
                $scope.hasProviderRole = true;
            }
           /* $scope.hasProviderRole = $scope.identity['roles'].indexOf("provider") !== -1;*/
        });

    }]);