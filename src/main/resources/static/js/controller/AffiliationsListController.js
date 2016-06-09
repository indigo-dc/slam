var app = angular.module('ltosApp');

app.controller('AffiliationsListController', ['$scope', '$http', 'affiliationService', '$route','identityService', 'helperService', 'SpringDataRestAdapter',
    function ($scope, $http, affiliationService, $route, identityService, helperService, SpringDataRestAdapter) {
        if(!identityService.getIdentityRegistered()){
            window.location = "#/";
        }
        
        $scope.affiliationsList = null;
        
        _loadAffiliationList();
        
    	function _loadAffiliationList() {
			var httpPromise = $http.get("/affiliations/");
			SpringDataRestAdapter.process(httpPromise).then(
					function(processedResponse) {
						console.log(processedResponse._embeddedItems);
						$scope.affiliationsList = processedResponse._embeddedItems;
					}, helperService.alertError);
		}
        
        $scope.setAffiliation = function (affiliation){
        	affiliationService.setAffiliation(affiliation);
        }
        
    }]);