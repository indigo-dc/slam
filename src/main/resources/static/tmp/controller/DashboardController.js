var app = angular.module('indigo');

app.controller('DashboardController', ['$scope', '$http', 'identityService', 'dashboardService',
    function ($scope, $http, identityService, dashboardService) {
        $scope.userFirstSteps = {};
        if(!identityService.identityRegistered()){
            window.location = "#/";
            return;
        }
        _checkUserFirstSteps();
        function _checkUserFirstSteps() {
            dashboardService.getUserFirstSteps().then(
                function (result) {
                    $scope.userFirstSteps = result;
                }
            );
        }

    }]);