angular.module('indigo.dashboard', ['ngRoute']).config(function ($routeProvider) {
    $routeProvider.when('/dashboard', {templateUrl: 'js/dashboard/dashboard.view.html', controller: 'dashboardCtrl',
        activetab: 'dashboard',
        data: {
       //authorizedRoles: [USER_ROLES.admin, USER_ROLES.editor]
    }});
}).controller('dashboardCtrl', function ($scope, $location, $routeParams, DashboardResource, DocumentResource, SESSION) {
    $scope.slas = [];

    var type = 'userDashboard';

    if($routeParams.type == undefined) {
        if(SESSION.roles.indexOf('ROLE_PROVIDER') != -1)
            type = 'providerDashboard';
    } else {
        type = $routeParams.type;
    }




    DashboardResource.query({type: type}, function (data) {
        $scope.slas = data.data;

        data.data.forEach(function (sla_type) {
            sla_type.slas = [];
            DocumentResource.query({query_id: sla_type.id}, function (data) {
                sla_type.documents = data.data;
            });
        })
    }, function (resonse) {

    });

    $scope.addDocument = function () {
        $location.path('/document');
    };

    $scope.editDocument = function (documentId) {
        $location.path('/document/'+documentId);
    };

    $scope.removeDocument = function (documentId) {

    };
    
    $scope.acceptDocument = function (documentId) {

    }
});