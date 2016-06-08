var app = angular.module('ltosApp');

app.controller('AffiliationsListController', ['$scope', '$http', 'affiliationService', '$route','identityService', 'SpringDataRestAdapter',
    function ($scope, $http, affiliationService, $route, identityService, SpringDataRestAdapter) {
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
					},
					function(processedResponse) {
						alert(getError(processedResponse));
						window.location = "#";
						location.reload();
					});
		}
        
        $scope.setAffiliation = function (affiliation){
        	affiliationService.setAffiliation(affiliation);
        }
        
      	function getError(response) {
    		console.log(response);
    		if(response.status && response.statusText) {
    			if (response.data && response.data.errors && response.data.errors.length > 0) {
    				return response.data.errors[0].message;
    			} 
    			return response.status + " " + response.statusText;
    		}
    		return "Unexpected error";
    	}
    }]);