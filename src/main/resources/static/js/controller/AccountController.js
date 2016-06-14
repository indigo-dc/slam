var app = angular.module('ltosApp');

app.controller('AccountController', ['$scope', '$http','identityService',
    function ($scope, $http, identityService) {
        if(!identityService.identityRegistered()){
            window.location = "#/";
            location.reload();
        }
    }]);