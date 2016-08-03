var app = angular.module('indigo');
//TODO isNameUnique byloby troche celniejsza nazwa, prawda?
app.directive('uniqueGrant', ['$http', function ($http) {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, mCtrl) {
            function uniqueGrant(value) {
                //TODO ten request nie powinien sie wysylac kiedy wartosc jest pusta, parametr mozna ladniej przekazac
                $http.get("/is-document-name-unique?name=" + value).success(function (data) {
                    mCtrl.$setValidity('uniqueGrant', data == true);
                });
                return value;
            }
            mCtrl.$parsers.push(uniqueGrant);
        }
    };
}]);