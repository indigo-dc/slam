var app = angular.module('ltosApp');

app.controller('RegistrationController', ['$scope', '$http', 'identityService', 'registrationService',
            'countryService', 'profileService', '$route',
    function ($scope, $http, identityService, registrationService, countryService, profileService, $route) {
		profileService.loadIdentity().then(function() {
	        $scope.countryList = countryService.getCountryList();
				 
	        _fillFormFromIdentity();
	        _checkRegistration();

	        function _fillFormFromIdentity() {
	        	
	            if (identityService.getIdentity().email != null) {
	                $scope.email = identityService.getIdentity().email;
	            }
	
	            if (identityService.getIdentity().name != null) {
	                $scope.name = identityService.getIdentity().name
	            }
	        }
	
	        // I load the remote data from the server.
	        function _checkRegistration() {
	        	profileService.getUser().then(
	                function (result) {
	                    _afterFetch(result);
	                }
	            );
	        }
	
	        function _afterFetch(result) {
	            console.log(result);
	            if (result['status'] == 'SUCCESS') {
	                window.location = "#/dashboard";
	                location.reload();
	            }
	        }
	        _refresh = function () {
	            _checkRegistration();
	        }
	        //======
	        $scope.submit = function () {
	            var user = 
	            {
	            	name: $scope.name,
	            	surname:  $scope.surname,
	            	country:  $scope.country,
	            	email:  $scope.email,
	            	researchGate: $scope.researchGate,
	            	linkedln: $scope.linkedln,
	            	isPolicyAccepted: true
	            };
	            
	            registrationService.registerUser(user).then(
	                function (result) {
	                    _redirectTo(result);
	                }
	            );
	
	            _redirectTo = function (result) {
	                if (result['status'] == 'SUCCESS') {
	                    identityService.setIdentityRegistered();
	                    window.location = "#/";
	                    //zalogowanie zarejestrowanego swiezo usera
	                } else if( result['status'] == 'UNLOGGED'){
	                    window.location = "#";
	                    location.reload();
	                }else{
	                    var message = "Sorry, an error occurred.";
	                    if (result['message'] != null) {
	                        message = result['message'];
	                    }
	                    alert(message);
	                }
	            }
	        }
		 })
    }]);