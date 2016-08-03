var app = angular.module('indigo');

app.controller('LoginController', ['$scope', '$http', '$templateCache','identityService', 'profileService','$route',
    function ($scope, $http, $templateCache, identityService, profileService, $route) {
        profileService.loadIdentity().then(function() {
    		if (identityService.identityRegistered()) {
            	window.location = "#/dashboard";
            } else {
            	window.location = "#/registration";
            }
        });
    }]);