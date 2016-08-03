var app = angular.module('indigo');

app.controller('QueryController', ['$scope', '$routeParams', '$route', 'QueryService',
    function ($scope, $routeParams, $route, QueryService) {
        $scope.isLoading = false;
        $scope.query;
        $scope.documents = [];

        $scope.init = function (query) {
            $scope.query = query;
            _loadRemoteData();
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