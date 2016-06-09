var app = angular.module('ltosApp');

app.controller('AffiliationsListController', ['$scope', '$http', 'affiliationService', '$route','identityService', 'helperService', 'SpringDataRestAdapter',
    function ($scope, $http, affiliationService, $route, identityService, helperService, SpringDataRestAdapter) {
        if(!identityService.getIdentityRegistered()){
            window.location = "#/";
        }
        
        $scope.affiliationsList = null;
        
        $scope.setAffiliation = function (affiliation){
        	affiliationService.setCurrentAffiliation(affiliation);
        }
  
		SpringDataRestAdapter.process($http.get("/affiliations/")).then(_setAffiliations, helperService.alertError);
	
		function _setAffiliations(processedResponse) {
			$scope.affiliationsList = processedResponse._embeddedItems;
		};
        
    }]);