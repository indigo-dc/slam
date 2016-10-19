var app = angular.module('indigo');

app.controller('AffiliationsListController', ['$scope', '$http', 'affiliationService', '$route','identityService', 'helperService', 'SpringDataRestAdapter',
    function ($scope, $http, affiliationService, $route, identityService, helperService, SpringDataRestAdapter) {
        if(!identityService.identityRegistered()){
            window.location = "#/";
            return; 
        }
        
        $scope.affiliationsList = null;
        $scope.setAffiliation = function (affiliation){
        	affiliationService.setCurrentAffiliation(affiliation);
        }
  
		SpringDataRestAdapter.process($http.get("/users/" + identityService.getIdentity().id + "/affiliations")).then(_setAffiliations, helperService.alertError);
	
		function _setAffiliations(processedResponse) {
			$scope.affiliationsList = processedResponse._embeddedItems;
		};
        
    }]);