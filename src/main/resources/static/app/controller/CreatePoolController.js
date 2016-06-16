var app = angular.module('configurationManager');

app.controller('CreatePoolController', ['$scope', '$routeParams', '$route', 'DocumentService', 'SitesService',
    function ($scope, $routeParams, $route, DocumentService, SitesService) {
        $scope.grantId = "";
        $scope.site = "";
        $scope.displayErrorComunicate = false;
        $scope.isLoading = false;

        $scope.sites;


        init();

        function init() {
            loadUserSites();
        };


        function loadUserSites() {
            $scope.isLoading = true;
            //return sites in which user has an administator role
            SitesService.getAdminSites().then(
                function (result) {
                    $scope.sites = result['data'];
                    $scope.isLoading = false;
                },
                function (result) {
                    $scope.displayErrorComunicate = true;
                    $scope.isLoading = false;
                }
            );
        }


        $scope.createPool = function () {
            $scope.isCreating = true;
            DocumentService.createPool($scope.grantId, $scope.site).then(
                function (result) {
                    window.location = "/#/document/" + result.data.redirectToDocument;
                }, function (error) {
                    $scope.isLoading = false;
                    alert("Wystąpił błąd podczas tworzenia nowej puli.");
                }
            );
        }

    }]);