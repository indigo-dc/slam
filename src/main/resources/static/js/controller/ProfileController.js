var app = angular.module('ltosApp');

app.controller('ProfileController', ['$scope', '$http','profileService','countryService', 'identityService', 'SpringDataRestAdapter', '$route',
    function ($scope, $http, profileService, countryService, identityService, SpringDataRestAdapter, $route) {
        if(!identityService.getIdentityRegistered()){
            window.location = "#/";
        }
        $scope.user = {};
        $scope.userEdit = {};
        $scope.countryList = countryService.getCountryList();
        $scope.userEdit.country = $scope.countryList[0];
        $scope.editProfile = false;
        
        $scope.editOperation = function(){
            $scope.editProfile = true;
        }
        $scope.cancelOperation = function(){
            $scope.editProfile = false;
            $scope.userEdit =
            {
                linkedln:$scope.user.linkedln,
                researchGate:$scope.user.researchGate,
                country:$scope.user.country
            };
        }

        _doAction();
        
        // I load the remote data from the server.
        function _doAction() {
			
			var httpPromise = $http.get('/users/1').error(function(response) {
			      var message = "Sorry, an error occurred.";
	              alert(message);
	               window.location = "#";
                   location.reload();
			});

			SpringDataRestAdapter.process(httpPromise).then(
					function(processedResponse) {
						$scope.user = processedResponse;
					});
        }
        
      
//================================================
        $scope.submit = function () {
        	
			var httpPromise = $http.put('/users/1', $scope.user).error(function(response) {
			      var message = "Sorry, an error occurred.";
	              alert(message);
			});
	
			SpringDataRestAdapter.process(httpPromise).then(
					function(processedResponse) {
						$scope.user = processedResponse;
						$scope.editProfile = false;
	                    window.location = "#/account";
					});
            	
        }
      //================================================
        _refresh = function () {
            _doAction($scope.emailIdentity);
        }
    }]);