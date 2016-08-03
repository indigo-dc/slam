var app = angular.module('indigo');

app.controller('CreateRequestController', ['$scope', '$http','$routeParams', '$route', 'DocumentService', 'DataForGrantCreationService', 'profileService', 'identityService', 'SpringDataRestAdapter',
    function ($scope, $http, $routeParams, $route, DocumentService, DataForGrantCreationService, profileService, identityService,  SpringDataRestAdapter) {
		profileService.loadIdentity().then(function() {
		
			$scope.grantId = "";
	        $scope.affiliationId = "";
	        $scope.team = "";
	        $scope.displayErrorComunicate = false;
	
	        $scope.userTeams;
	        
	        $scope.userAffiliations;
	
	        $scope.isLoading = false;
	        
	//        loadDataForGrantCreation();
	
	
	//        function loadDataForGrantCreation() {
	//            $scope.isLoading = true;
	//            DataForGrantCreationService.getData().then(
	//                function (result) {
	//                    $scope.userTeams = result['data']['teams'];
	//                    $scope.userAffiliations = result['data']['affiliations'];
	//                    $scope.isLoading = false;
	//                },
	//                function (result) {
	//                    $scope.displayErrorComunicate = true;
	//                    $scope.isLoading = false;
	//                }
	//            );
	//        }
	       
	        
	        var httpPromise = $http.get("/users/" + identityService.getIdentity().id);
			SpringDataRestAdapter.process(httpPromise).then(
					function(processedResponse) {
						console.log(processedResponse._links.teams.href); 
						var resources = processedResponse._resources("teams");
						resources.get(function(teamsResponse) {
							$scope.userTeams = teamsResponse._embedded.teams;
							console.log(teamsResponse._embedded.teams);
						});
					}, function (error) {
	                    $scope.isLoading = false;
	                    alert("Wystąpił błąd przy tworzeniu wniosku o grant.");
	                });
	        
			/*
			 * remove this method - find better way 
			 */
			$scope.uglyId = function (team) {
				var str = team._links.self.href;
				var parts = str.split('/');
				var answer = parts[parts.length - 1];
				return answer;
	        }
	
	        $scope.createRequest = function () {
	            $scope.isLoading = true;
	            DocumentService.createRequest($scope.grantId, $scope.team, $scope.affiliationId, $scope.branchOfScienceId).then(
	                function (result) {
	                    window.location = "#/document/" + result.data.redirectToDocument;
	                }, function (error) {
	                    $scope.isLoading = false;
	                    alert("Wystąpił błąd przy tworzeniu wniosku o grant.");
	                }
	            );
	        }
		});
	}]);