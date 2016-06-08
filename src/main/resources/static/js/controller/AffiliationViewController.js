var app = angular.module('ltosApp');

app.controller('AffiliationViewController', ['$scope', '$http', 'affiliationService', '$route','identityService',
    function ($scope, $http, affiliationService, $route, identityService) {
        if(!identityService.getIdentityRegistered()){
            window.location = "#/";
        }
		$scope.affiliation = affiliationService.getAffiliation();
    }]);