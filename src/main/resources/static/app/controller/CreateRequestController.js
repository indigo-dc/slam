var app = angular.module('configurationManager');

app.controller('CreateRequestController', ['$scope', '$routeParams', '$route', 'DocumentService', 'DataForGrantCreationService',
    function ($scope, $routeParams, $route, DocumentService, DataForGrantCreationService) {
        $scope.grantId = "";
        $scope.affiliationId = "";
        $scope.team = "";
        $scope.displayErrorComunicate = false;

        $scope.userTeams;
        $scope.branchesOfScience;
        $scope.userAffiliations;

        $scope.isLoading = false;

        loadDataForGrantCreation();


        function loadDataForGrantCreation() {
            $scope.isLoading = true;
            DataForGrantCreationService.getData().then(
                function (result) {
                    $scope.userTeams = result['data']['teams'];
                    $scope.userAffiliations = result['data']['affiliations'];
                    $scope.isLoading = false;
                },
                function (result) {
                    $scope.displayErrorComunicate = true;
                    $scope.isLoading = false;
                }
            );
        }


        $scope.createRequest = function () {
            $scope.isLoading = true;
            DocumentService.createRequest($scope.grantId, $scope.team, $scope.affiliationId, $scope.branchOfScienceId).then(
                function (result) {
                    window.location = "#/document/" + result.data.redirectToDocument;
                }, function (error) {
                    $scope.isLoading = false;
                    alert("Wystąpił błąd przy tworzeniu wniosku o grant.");
                }
            );
        }

    }]);