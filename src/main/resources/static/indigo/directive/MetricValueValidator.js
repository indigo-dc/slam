var app = angular.module('configurationManager');

app.directive('metricValueValidator', ['$translate', function ($translate) {
    return {
        require: 'ngModel',
        scope: {
            metric:'=',
            message:'='
        },
        link: function (scope, element, attrs, mCtrl) {
            function metricValueValidator(value) {
                var valid = true;
                scope.message = "";

                var maxValue = scope.metric["maxValue"];

                if (maxValue !== null && value > parseFloat(maxValue)) {
                    $translate('validator.maxValue').then(function (translation) {
                        scope.message += translation + maxValue;
                    });
                    valid = false;
                }

                var minValue = scope.metric["minValue"];

                if (minValue !== null && value < parseFloat(minValue)) {
                    $translate('validator.minValue').then(function (translation) {
                        scope.message += translation + minValue;
                    });
                    valid = false;
                }

                var isRequired = scope.metric["required"];

                if (isRequired && (!value || value == '')) {
                    $translate('validator.required').then(function (translation) {
                        scope.message += translation;
                    });
                    valid = false;
                }

                mCtrl.$setValidity('metricValueValidator', valid);
                return value;
            }

            mCtrl.$parsers.push(metricValueValidator);
        }
    };
}]);