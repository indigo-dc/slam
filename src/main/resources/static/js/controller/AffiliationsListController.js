var app = angular.module('ltosApp');

app.controller('AffiliationsListController', ['$scope', '$http', 'affiliationService', '$route','identityService',
    function ($scope, $http, affiliationService, $route, identityService) {
        if(!identityService.getIdentityRegistered()){
            window.location = "#/";
        }
        $scope.affiliationsList = null;
        
        $scope.setAffiliation = function (affiliation){
        	affiliationService.setAffiliation(affiliation);
        }
        
        _loadAffiliationList();
        // I load the remote data from the server.
        function _loadAffiliationList() {
        	affiliationService.getUserAffiliations().then(
                    function (result) {
                        _afterFetch(result);
                    }
                );
        }

        function _afterFetch(result) {
            if (result['status'] == 'SUCCESS') {
                $scope.affiliationsList  = result['type'];
            }else if( result['status'] == 'UNLOGGED'){
                window.location = "#";
                location.reload();
            }
            
        }
        _refresh = function () {
        	_loadAffiliationList();
        }
    }]);