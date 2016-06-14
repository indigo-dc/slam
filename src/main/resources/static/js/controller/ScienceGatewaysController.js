var app = angular.module('ltosApp');

app.controller('ScienceGatewaysController', ['$scope', '$http', '$templateCache','identityService',
    function ($scope, $http, $templateCache, identityService) {
        if(!identityService.identityRegistered()){
            window.location = "#/";
            return;
        }
        $scope.scienceGatewaysList = null;
    }]);