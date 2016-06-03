var app = angular.module('ltosApp');

app.controller('RegistrationController', ['$scope', '$http', 'identityService', 'registrationService',
            'countryService', 'profileService', '$route',
    function ($scope, $http, identityService, registrationService, countryService, profileService, $route) {
        $scope.countryList = countryService.getCountryList();
        _fillFormFromIdentity();
        _checkRegistration();


        function _fillFormFromIdentity() {
            if (identityService.getIdentityEmail() != null) {
                $scope.email = identityService.getIdentityEmail();
            }

            if (identityService.getIdentityName() != null) {
                $scope.name = identityService.getIdentityName();
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
    }]);