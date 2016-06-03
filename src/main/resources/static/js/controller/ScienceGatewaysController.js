var app = angular.module('ltosApp');

app.controller('ScienceGatewaysController', ['$scope', '$http', '$templateCache','identityService',
    function ($scope, $http, $templateCache, identityService) {
        if(!identityService.getIdentityRegistered()){
            window.location = "#/";
        }
        $scope.scienceGatewaysList = null;
    }]);