var app = angular.module('ltosApp');

app.controller('AddAffiliationController', ['$scope', '$http', 'affiliationService', 'profileService', 'institutionService', 'countryService', '$route','identityService',
    function ($scope, $http, affiliationService, profileService, institutionService, countryService, $route, identityService) {
        if(!identityService.getIdentityRegistered()){
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

        $scope.emailIdentity = profileService.getUser();
        $scope.countryList = countryService.getCountryList();

        $scope.addAffiliationSubmit = function () {
            var affiliation =
            {
                country: $scope.country,
                institution: $scope.institution,
                department: $scope.department,
                webPage: $scope.webPage,
                supervisorName: $scope.supervisorName,
                supervisorEmail: $scope.supervisorEmail,
                status: "",
                lastUpdateDate: new Date(),
                userEmail: ""
            };
            //console.log(affiliation);
            affiliationService.addAffiliation(affiliation).then(
                function (result) {
                    _redirectTo(result);
                }
            );

            _redirectTo = function (result) {
                if (result['status'] == 'SUCCESS') {
                    window.location = "#/account";
                } else if (result['status'] == 'UNLOGGED') {
                    window.location = "#";
                    location.reload();
                }else{
                    var message = "Sorry, an error occurred.";
                    if (result['message'] != null) {
                        message = result['message'];
                    }
                    alert(message);
                }
            }
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
