angular.module('indigo.profile', ['ngRoute']).config(function ($routeProvider) {
    $routeProvider.when('/profile', {templateUrl: 'js/profile/profile.view.html', controller: 'profileCtrl',
        data: {
        //authorizedRoles: [USER_ROLES.admin, USER_ROLES.editor]
    }});
}).controller('profileCtrl', function ($scope) {

});