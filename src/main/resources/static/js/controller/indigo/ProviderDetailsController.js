var app = angular.module('indigo');

app.controller('ProviderDetailsController', ['$scope', '$routeParams', '$route', 'DocumentService', 'MetricService', '$q',
    function ($scope, $routeParams, $route, DocumentService, MetricService, $q) {
        // $scope.isLoading = false;
        $scope.providerId = $routeParams.id;
    }]);