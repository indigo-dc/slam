var app = angular.module('ltosApp');

app.controller('MainController', ['$scope', '$http','identityService','$location',
    function ($scope, $http, identityService,$location) {
		$scope.operator = identityService.getIdentity().operator;
        $scope.identityName = identityService.getIdentity().name;
        $scope.$location = $location;
    }]);