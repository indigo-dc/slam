var app = angular.module('ltosApp');

app.controller('ProfileController', ['$scope', '$http','profileService','countryService', 'identityService', '$route',
    function ($scope, $http, profileService, countryService, identityService, $route) {
        if(!identityService.getIdentityRegistered()){
            window.location = "#/";
        }
        $scope.user = {};
        $scope.userEdit = {};
        $scope.countryList = countryService.getCountryList();
        $scope.userEdit.country = $scope.countryList[0];
        $scope.editProfile = false;
        
        $scope.editOperation = function(){
            $scope.editProfile = true;
        }
        $scope.cancelOperation = function(){
            $scope.editProfile = false;
            $scope.userEdit =
            {
                linkedln:$scope.user.linkedln,
                researchGate:$scope.user.researchGate,
                country:$scope.user.country
            };
        }

        _doAction();
        
        // I load the remote data from the server.
        function _doAction() {
        		profileService.getUser().then(
                    function (result) {
                        _afterFetch(result);
                    }
                );
        }

        function _afterFetch(result) {
            if (result['status'] == 'SUCCESS') {
                $scope.user = result['type'];
                $scope.userEdit =
                        {
                        linkedln:$scope.user.linkedln,
                        researchGate:$scope.user.researchGate,
                        country:$scope.user.country
                        };
            }else if( result['status'] == 'UNLOGGED'){
                window.location = "#";
                location.reload();
            }else{
                var message = "Sorry, an error occurred.";
                if (result['message'] != null) {
                    message = result['message'];
                }
                alert(message);
                location.reload();
            }
        }
//================================================
        $scope.submit = function () {
            var user = 
            {
            	name: $scope.user.name,
            	surname:  $scope.user.name,
            	country:  $scope.userEdit.country,
            	email:  $scope.user.email,
            	researchGate: $scope.userEdit.researchGate,
            	linkedln: $scope.userEdit.linkedln,
            	isPolicyAccepted: $scope.user.isPolicyAccepted
            };
//            console.log($scope.userEdit);
            profileService.editUser(user).then(
                function (result) {
                    _redirectTo(result);
                }
            );

            _redirectTo = function (result) {
//            	console.log(result);
                if (result['status'] == 'SUCCESS') {
                	$scope.editProfile = false;
                    $scope.user = result['type'];
                    window.location = "#/account";
                    _refresh();
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
      //================================================
        _refresh = function () {
            _doAction($scope.emailIdentity);
        }
    }]);