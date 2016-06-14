var app = angular.module('ltosApp');

app.controller('MainController', ['$scope', '$http','identityService','$location',
    function ($scope, $http, identityService,$location) {
        $scope.identityName = identityService.getIdentity().name;
        $scope.$location = $location;
    }]);