var app = angular.module('ltosApp');

app.controller('ProfileController', ['$scope', '$http','profileService','countryService', 'identityService', 'helperService', 'SpringDataRestAdapter', '$route',
    function ($scope, $http, profileService, countryService, identityService, helperService, SpringDataRestAdapter, $route) {
        
		if(!identityService.getIdentityRegistered()){
            window.location = "#/";
        }
		
		$scope.user = {}
        $scope.oldUser = {};
        $scope.countryList = countryService.getCountryList();
        $scope.editProfile = false;
        
        $scope.editOperation = function(){
        	$scope.oldUser = JSON.parse(JSON.stringify($scope.user)); 
            $scope.editProfile = true;
        }
        
        $scope.cancelOperation = function(){
        	$scope.user = JSON.parse(JSON.stringify($scope.oldUser)); 
        	$scope.editProfile = false;
        }
        
        $scope.submit = function () {
        	var httpPromise = $http.put($scope.user._links.self.href, $scope.user);
			SpringDataRestAdapter.process(httpPromise).then(
					function(processedResponse) {
						$scope.user = processedResponse;
						$scope.editProfile = false;
	                    window.location = "#/account";
					}, 
					function(processedResponse) {
						alert(helperService.handleError(processedResponse));
					});
        }

        _doAction();
        
    	function _doAction() {
			var httpPromise = $http.get("/users/" + identityService.getIdentityId());
			SpringDataRestAdapter.process(httpPromise).then(
					function(processedResponse) {
						$scope.user = processedResponse;
					},
					function(processedResponse) {
						alert(helperService.handleError(processedResponse));
						window.location = "#";
						location.reload();
					});
		}
    	

    }]);