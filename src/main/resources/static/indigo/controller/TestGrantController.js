var app = angular.module('configurationManager');

app.controller('TestGrantController', ['$scope', '$routeParams', '$route', 'QueryService', 'DocumentService', '$q',
    function ($scope, $routeParams, $route, QueryService, DocumentService, $q) {
        $scope.isLoading = false;
        $scope.documents = [];
        $scope.actionsForDocuments = new Object();

        var testGrantsQueryId = 'testGrantsQuery';


        $scope.init = function () {
        }

        _loadRemoteData();

        // I load the remote data from the server.
        function _loadRemoteData() {
            $scope.isLoading = true;

            QueryService.getQueryDocuments(testGrantsQueryId).then(
                function (result) {
                    serveResult(result);
                }, function (result){
                    alert("Błąd");
                    $scope.isLoading = false;
                }
            );
        }

        function serveResult(result) {
            $scope.documents = result['data'];
            var promises = [];
            if ($scope.documents.length > 0) {
                $scope.documents.forEach(function (document) {

                    //odpytanie o akcje dostepne dla dokumentu
                    promises.push(DocumentService.getActionsAvailableForDocument(document.id).then(
                        function (result) {
                            $scope.actionsForDocuments[document.id] = result['data'];
                        }
                    ));
                });
                $q.all(promises).then(function(){
                    $scope.isLoading = false;
                });
            }
            else {
                $scope.isLoading = false;
            }
        }

        $scope.doAction = function (documentId, actionId) {
            $scope.isLoading = true;
            DocumentService.doAction(documentId, actionId, {}).then(
                function (result) {
                    _afterDoActionSuccess(result);
                }, function (result) {
                    _afterDoActionFailed(result)
                }
            );
        }


        function _afterDoActionSuccess(result) {
            $scope.isLoading = false;
            _loadRemoteData();
        }

        function _afterDoActionFailed(result) {
            $scope.isLoading = false;
            alert("Wystąpił błąd!");
            _loadRemoteData();
        }

    }]);