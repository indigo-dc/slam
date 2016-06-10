var app = angular.module('ltosApp');

app.controller('LoginController', ['$scope', '$http', '$templateCache','identityService', 'profileService','$route',
    function ($scope, $http, $templateCache, identityService, profileService, $route) {
        profileService.getIdentity().then(
            function (result) {
                _afterFetchIdentity(result);
            }
        )
        
        function _afterFetchIdentity(result) {
            if (result['name'] !=null){
                identityService.setIdentityName(result['name']);
            }
            if (result['email'] != null){
                identityService.setIdentityEmail(result['email']);
            }
            if (result['id'] != null){
                identityService.setIdentityId(result['id']);
            }
            _checkUser();
        }

        function _checkUser() {
            profileService.getUser().then(
            		 function (result) {
                     	identityService.setIdentityRegistered();
            	 		window.location = "#/dashboard";
                     }, function (result) {
                    	window.location = "#/registration";
                    }
            );
        }

    }]);