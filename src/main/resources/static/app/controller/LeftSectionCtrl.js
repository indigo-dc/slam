var app = angular.module('configurationManager');

app.controller('LeftSectionCtrl', ['$scope', '$location',
    function ($scope, $location) {

        $scope.isActive = function (viewLocations) {
            var key;
            for (key in viewLocations) {
                if (viewLocations[key] === $location.path()) {
                    return true;
                }
            }
            return false;
        };

    }]);