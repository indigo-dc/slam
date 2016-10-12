angular.module('indigo.dashboard', ['ngRoute']).config(function ($routeProvider) {
    $routeProvider.when('/dashboard', {templateUrl: 'js/dashboard/dashboard.view.html', controller: 'dashboardCtrl',
        activetab: 'dashboard',
        data: {
        //authorizedRoles: [USER_ROLES.admin, USER_ROLES.editor]
    }});
}).controller('dashboardCtrl', function ($scope, DashboardResource) {
    $scope.slas = [];

    DashboardResource.query({}, function (data) {
        $scope.slas = data.data;
    }, function (resonse) {

    });
});