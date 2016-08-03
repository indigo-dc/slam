var app = angular.module('indigo');

app.controller('AddAffiliationController', ['$scope', '$http', 'affiliationService', 'profileService', 'institutionService', 'countryService', 'instTypeService', '$route','identityService', 'helperService', 'SpringDataRestAdapter', 
    function ($scope, $http, affiliationService, profileService, institutionService, countryService, instTypeService, $route, identityService, helperService, SpringDataRestAdapter) {
        if(!identityService.identityRegistered()){
            window.location = "#/";
        }
        $scope.doTheBack = function() {
            window.history.back();
        };

        $scope.instTypeList = instTypeService.getInstTypeList();
        $scope.countryList = countryService.getCountryList();
        
    	var httpPromise = $http.get("/users/" + identityService.getIdentity().id);
		SpringDataRestAdapter.process(httpPromise).then(
				function(processedResponse) {
					console.log(processedResponse._links.self.href); 
					
					 $scope.affiliation = { owner: processedResponse._links.self.href };
					 console.log($scope.affiliation); 
				}, helperService.alertError);
        
        $scope.addAffiliationSubmit = function () {
        	var affiliation = $scope.affiliation;
        	console.log($scope.affiliation);
        	var httpPromise = $http.post('/affiliations/', affiliation);
			SpringDataRestAdapter.process(httpPromise).then(
					function(processedResponse) {
						  window.location = "#";
		                  location.reload();
					}, helperService.alertError);
        }
        
    }]);
