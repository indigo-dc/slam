var app = angular.module('indigo');

app.controller('DatePickerController', ['$scope',
    function ($scope) {
        $scope.openedDatePopUp = false;


        $scope.today = function () {
            $scope.$parent.metricFormValues[metricId] = $filter('date')(new Date(),'yyyy-MM-dd');
        };

        $scope.openPopUp = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.openedDatePopUp = true;
        };

        $scope.dateOptions = {
            formatYear: 'yy',
            startingDay: 1
        };

    }]);