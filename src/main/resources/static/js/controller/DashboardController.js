var app = angular.module('ltosApp');

app.controller('DashboardController', ['$scope', '$http', 'identityService', 'dashboardService',
    function ($scope, $http, identityService, dashboardService) {
        $scope.userFirstSteps = {};
        if(!identityService.getIdentityRegistered()){
            window.location = "#/";
        }
        _checkUserFirstSteps();
        function _checkUserFirstSteps() {
            dashboardService.getUserFirstSteps().then(
                function (result) {
                    //console.log(result['type']);
                    $scope.userFirstSteps = result['type'];
                }
            );
        }

    }]);