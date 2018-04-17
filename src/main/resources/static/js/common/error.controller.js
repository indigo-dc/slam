angular.module('indigo.common.error', ['ngRoute']).config(function ($routeProvider) {
    $routeProvider.when('/error', {templateUrl: 'js/common/error.view.html', controller: 'errorCtrl',
        activetab: null,
        data: {
            //authorizedRoles: [USER_ROLES.admin, USER_ROLES.editor]
        }});
}).controller('errorCtrl', function ($scope, $routeParams, $location) {
    $scope.errorMessage = $routeParams.message;
    $scope.backLink = $routeParams.back;
});
