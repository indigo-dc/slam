/**
 * Created by krzysiek on 04.05.15.
 */
var app = angular.module('ltosApp');

app.controller('ResourcesController', ['$scope', '$http', '$route','identityService', 'affiliationService',
    function ($scope, $http, $route, identityService, affiliationService) {
        if(!identityService.identityRegistered()){
            window.location = "#/";
            return;
        }

        affiliationService.getUserAffiliations().then(
            function (result) {
                _afterFetch(result);
            }
        );
        $scope.hasAffiliation = false;
        $scope.resourcesList = null;
        $scope.doTheBack = function() {
            window.history.back();
        };
        function _afterFetch(result) {
            if (result['status'] == 'SUCCESS') {
                $scope.hasAffiliation  = true;
            }
        }
    }]);