var app = angular.module('indigo');

app.directive('grantNameValidator', function() {
    return {
        require: 'ngModel',
        link: function(scope, element, attr, mCtrl) {
            function grantNameValidator(value) {
                var pattern = new RegExp('^[0-9a-zA-Z]+$');
                mCtrl.$setValidity('incorrectGrantName', pattern.test(value));
                return value;
            }
            mCtrl.$parsers.push(grantNameValidator);
        }
    };
});