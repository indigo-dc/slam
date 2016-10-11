var app = angular.module('indigo');

app.controller('ManageAffiliationController', ['$scope', '$http', 'affiliationService', 'profileService', 'institutionService', 'countryService', 'instTypeService', '$route','identityService', 'helperService', 'SpringDataRestAdapter', 
    function ($scope, $http, affiliationService, profileService, institutionService, countryService, instTypeService, $route, identityService, helperService, SpringDataRestAdapter) {
        if(!identityService.identityRegistered()){
            window.location = "#/";
        }
        
        $scope.affiliation = affiliationService.getCurrentAffiliation();
        
        if(!$scope.affiliation){
            window.location = "#/";
            return;
        }
        
        $scope.doTheBack = function() {
            window.history.back();
        };

        $scope.affiliation._resources("owner").get(function(owner) {
        	console.log($scope.owner = owner);
        });
        
        $scope.instTypeList = instTypeService.getInstTypeList();
        $scope.countryList = countryService.getCountryList();
        $scope.statusList = ["PENDING", "ACTIVE", "REJECTED"]; 
        
    	$scope.addAffiliationSubmit = function () {
    		var httpPromise = $http.put($scope.affiliation._links.self.href, $scope.affiliation).then(
				function(processedResponse) {
					  window.location = "#";
	                  location.reload();
				}, 
				helperService.alertError);	
        }
    	console.log($scope.affiliation.state);    	
    	
    }]);
