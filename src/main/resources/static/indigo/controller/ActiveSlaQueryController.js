var app = angular.module('configurationManager');

app.controller('ActiveSlaQueryController', ['$scope', '$routeParams', '$route', 'QueryService',
    function ($scope, $routeParams, $route, QueryService) {
        $scope.isLoading = false;
        $scope.query;
        $scope.gueryId;
        $scope.documents = [];

        $scope.init = function (query,gueryId) {
            $scope.gueryId = gueryId;

            QueryService.getAvailableQueries(query)
                .then(function(result) {
                    serveQuery(result);
                })
                .catch(function(error) {
                    console.log(error);
                    alert('wystąpil błąd2...');
                })
                .finally(function() {
                    _loadRemoteData();
            });

        }

        // I load the remote data from the server.
        function _loadRemoteData() {
            $scope.isLoading = true;

            QueryService.getQueryDocuments($scope.query.id).then(
                function (result) {
                    serveResult(result);
                    $scope.isLoading = false;
                }
            );
        }
        function serveQuery(result) {
            var queries = result['data'];
            for(var query in queries){
                if(queries[query].id==$scope.gueryId){
                    $scope.query = queries[query];
                }
            }
        }

        function serveResult(result) {
            //if(result['status'] == 'SUCCESS') {
            $scope.documents = result['data'];
            $scope.totalItems = $scope.documents.length;
            $scope.paginateData();
            //} else {
            //    alert("blad");
            //}
        }


        $scope.paginateData = function () {
            var begin = (($scope.currentPage - 1) * $scope.itemsPerPage);
            var end = begin + $scope.itemsPerPage;

            $scope.filteredDocuments = $scope.documents.slice(begin, end);
        }


        $scope.filteredDocuments = [];

        $scope.currentPage = 1;
        $scope.itemsPerPage = 10;
        $scope.totalItems = 0;


        $scope.pageChanged = function (sth) {
            //$log.log('Page changed to: ' + $scope.currentPage);
            $scope.currentPage = sth;
            $scope.paginateData();
        };

        $scope.maxSize = 5;


    }]);