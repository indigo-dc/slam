var app = angular.module('ltosApp');

app.controller('AddAffiliationController', ['$scope', '$http', 'affiliationService', 'profileService', 'institutionService', 'countryService', '$route','identityService', 'helperService', 'SpringDataRestAdapter', 
    function ($scope, $http, affiliationService, profileService, institutionService, countryService, $route, identityService, helperService, SpringDataRestAdapter) {
        if(!identityService.identityRegistered()){
            window.location = "#/";
        }
        $scope.doTheBack = function() {
            window.history.back();
        };
        //$scope.result1 = '';
        //$scope.options1 = null;
        //$scope.details1 = '';
        $scope.institutionsList = [];
        $scope.institutionsListUpdated = [];
        $scope.departmentList = [];
        $scope.updatedDepartmentList = [];
        $scope.updatedList=[];

        institutionService.getInstitutions().then(
            function (result) {
                if(result['type'] != null){
                    $scope.institutionsList = result['type'];
                }
                //console.log($scope.institutionsList);
            }
        );

        $scope.countryList = countryService.getCountryList();
        var user = null;
        
    	var httpPromise = $http.get("/users/" + identityService.getIdentity().id);
		SpringDataRestAdapter.process(httpPromise).then(
				function(processedResponse) {
					user = processedResponse;
				}, helperService.alertError);
        
        $scope.addAffiliationSubmit = function () {
        	 var affiliation =
             {
                 country: $scope.country,
                 institution: $scope.institution,
                 department: $scope.department,
                 webPage: $scope.webPage,
                 supervisorName: $scope.supervisorName,
                 supervisorEmail: $scope.supervisorEmail,
                 lastUpdateDate: new Date(),
                 owner: user._links.self.href
             };
        	
        	var httpPromise = $http.post('/affiliations/', affiliation);
			SpringDataRestAdapter.process(httpPromise).then(
					function(processedResponse) {
						  window.location = "#";
		                    location.reload();
					}, helperService.alertError);
        }
        
        

        $scope.updateListInstitution = function(country){
            $scope.institutionsListUpdated = [];
            for (var i = 0; i < $scope.institutionsList.length; i++) {
                if ($scope.institutionsList[i].country === country)
                    $scope.institutionsListUpdated.push($scope.institutionsList[i]);
            }
            //console.log($scope.institutionsListUpdated.length);
            //return results;
        }

        $scope.updateListDepartment = function(institution){
            $scope.departmentList = [];
            for (var i = 0; i < $scope.institutionsListUpdated.length; i++) {
                var state = $scope.institutionsListUpdated[i].name;
                if ($scope.institutionsListUpdated[i].name === institution){
                    for (var j = 0; j < $scope.institutionsListUpdated[i].departments.length; j++){
                        $scope.departmentList.push($scope.institutionsListUpdated[i].departments[j]);
                    }
                }
            }
            //console.log($scope.departmentList);
            //return results;
        }

        $scope.searchInstitution = function(term) {
            //Geolocator.searchFlight(term).then(function(countries){
            //    $scope.countries = countries;
            //});
            $scope.updatedList = [];
            var q = term.toLowerCase().trim();
            for (var i = 0; i < $scope.institutionsListUpdated.length; i++) {
                var state = $scope.institutionsListUpdated[i].name;
                if (state.toLowerCase().indexOf(q) === 0){
                    $scope.updatedList.push(state);
                }
            }
        }

        $scope.searchDepartment = function(term) {
            $scope.updatedDepartmentList = [];
            var q = term.toLowerCase().trim();
            for (var i = 0; i < $scope.departmentList.length; i++) {
                var state = $scope.departmentList[i];
                //console.log(state);
                if (state.toLowerCase().indexOf(q) === 0){
                    //console.log("uwaga"+state);
                    $scope.updatedDepartmentList.push(state);
                }
                //console.log($scope.departmentList);;
                    //$scope.departmentList = $scope.institutionsList[i].name;
            }
            //console.log($scope.departmentList);
        }

    }]);
