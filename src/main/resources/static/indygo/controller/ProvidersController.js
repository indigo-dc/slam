var app = angular.module('configurationManager');

app.controller('ProvidersController', ['$scope', '$routeParams', '$route', 'QueryService', 'GetLoggerUser',
    function ($scope, $routeParams, $route, QueryService, GetLoggerUser) {
        $scope.isLoading = true;
        $scope.queries;
        $scope.computing;
        $scope.storage;
        $scope.queryFiltr = [];
        $scope.queryFiltr.push("computingOffers");
        $scope.queryFiltr.push("StorageOffers");

        $scope.init = function() {

        };

        $scope.change = function() {
            console.log("change");
            $scope.queryFiltr = [];
            if($scope.computing==true){
                $scope.queryFiltr.push("computingOffers");
            }
            if($scope.storage==true){
                $scope.queryFiltr.push("StorageOffers");
            }
        };

        setTimeout(function(){
        }, 100);

        _loadRemoteData();

        // I load the remote data from the server.
        function _loadRemoteData() {
            $scope.isLoading = true;

            QueryService.getAvailableQueries("offers")
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
            console.log($scope.queries);
        }

        GetLoggerUser.get().then(function(result) {
            $scope.identity = result;
            if($scope.identity.operator == true){
                $scope.hasProviderRole = true;
            }
            /* $scope.hasProviderRole = $scope.identity['roles'].indexOf("provider") !== -1;*/
        });
    }]);