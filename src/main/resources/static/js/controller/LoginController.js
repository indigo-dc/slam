var app = angular.module('ltosApp');

app.controller('LoginController', ['$scope', '$http', '$templateCache','identityService', 'profileService','$route',
    function ($scope, $http, $templateCache, identityService, profileService, $route) {
        profileService.getIdentity().then(
            function (result) {
                //console.log(result);
                _afterFetchIdentity(result);
            }
        )

        function _checkUser() {
            profileService.getUser().then(
                function (result) {
                    //console.log(result);
                    _afterCheckUser(result);
                }
            );
        }

        function _afterCheckUser(result) {

            if (result['status'] != 'FAILED'){
                identityService.setIdentityRegistered();
                window.location = "#/dashboard";
            }
            else {
                window.location = "#/registration";
            }
        }

        function _afterFetchIdentity(result) {
            if (result['status'] == 'SUCCESS') {
                if (result['type']['name'] !=null){
                    identityService.setIdentityName(result['type']['name']);
                }
                if (result['type']['email'] != null){
                    identityService.setIdentityEmail(result['type']['email']);
                    _checkUser();
                }
                if (result['type']['id'] != null){
                    identityService.setIdentityId(result['type']['id']);
                }
                
            }
            //TODO do przemyslenia jak to powinno dzialac
            //else if( result['status'] == 'UNLOGGED'){
            //    window.location = "#";
            //    location.reload();
            //}
        }
//    _loadAffiliationList($scope.identity);
//    // I load the remote data from the server.
//    function _loadAffiliationList(email) {
////    	if()
//    }

//    function _afterFetch(result) {
//        if (result['status'] == 'SUCCESS') {
//            $scope.affiliationsList  = result['type'];
//        }else if( result['status'] == 'UNLOGGED'){
//            window.location = "#";
//            location.reload();
//        }
//        
//    }
//    _refresh = function () {
//    	_loadAffiliationList($scope.identity);
//    }
    }]);