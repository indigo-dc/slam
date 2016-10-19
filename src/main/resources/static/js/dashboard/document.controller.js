angular.module('indigo.dashboard').config(function ($routeProvider) {
    $routeProvider.when('/document/:document_id', {templateUrl: 'js/dashboard/document.view.html', controller: 'documentCtrl',
        activetab: 'dashboard',
        data: {
            //authorizedRoles: [USER_ROLES.admin, USER_ROLES.editor]
        }});
    $routeProvider.when('/document', {templateUrl: 'js/dashboard/document.view.html', controller: 'documentCtrl',
        activetab: 'dashboard',
        data: {
            //authorizedRoles: [USER_ROLES.admin, USER_ROLES.editor]
        }});
}).controller('documentCtrl', function ($scope, $route, $location, DashboardResource, DocumentResource) {
    console.log($route.current.params.document_id);
    $scope.documentId = $route.current.params.document_id;
    $scope.state = 'form';
    $scope.metrics = [];
    $scope.sla = {site: null, name: '', id: $scope.documentId, metrics: {}};
    $scope.sites = [];
    $scope.actions = [];

    $scope.loadDocument = function () {
        if($scope.documentId) {
            DocumentResource.get({id: $scope.documentId}, function (data) {
                $scope.actions = data.actions;
                $scope.sla = data.document;

                $scope.sites.forEach(function (site) {
                    if(site.id == $scope.sla.site){
                        $scope.sla.site = site;
                    }
                });

                //check whether document is editable
                $scope.actions.forEach(function (action) {
                    if(action.id == 'editDraft')
                        //setting it in sla is for possible future compatibility
                        $scope.sla.editable = true;
                })
            }, function (response) {

            })
        }
    };


    $scope.sites = DocumentResource.sites({}, function (data) {
        $scope.sites = data.rows;
        $scope.sla.site = data.rows[0];
        $scope.loadMetrics();
    }, function (response) {

    });

    $scope.loadMetrics = function () {
        DocumentResource.metrics({}, function (data) {
            $scope.metrics = data;
            if(!$scope.documentId)
                $scope.metrics.forEach(function (metric) {
                    $scope.sla.metrics[metric.id] = null;
                });

            $scope.loadDocument();

        }, function (response) {

        });
    };

    $scope.getMetricClasses = function (metric) {
        if(metric.unit && metric.unit != 'none') {
            return 'input-group';
        }
        return '';
    };

    $scope.sendRequest = function (sla) {
        var action = 'create';
        if(sla.id)
            action = 'update';


        DocumentResource[action](sla, function (data) {
            $scope.state = 'success';
        }, function (response) {

        });
    };
    $scope.deleteRequest = function (sla) {
        DocumentResource.delete({id: $scope.documentId}, function (data) {
            $scope.state = 'deleted';
        }, function (response) {

        });
    };
    $scope.performAction = function (action, sla) {
        DocumentResource.perform_action({action: action, id: sla.id}, function (data) {
            $scope.state = 'success';
        }, function (response) {

        });
    };
});