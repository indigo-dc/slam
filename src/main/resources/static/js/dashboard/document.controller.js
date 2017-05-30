angular.module('indigo.dashboard').config(function ($routeProvider) {
    $routeProvider.when('/component/:document_id', {templateUrl: 'js/dashboard/component.view.html', controller: 'documentCtrl',
        activetab: 'dashboard',
        data: {
            //authorizedRoles: [USER_ROLES.admin, USER_ROLES.editor]
        }});
    $routeProvider.when('/component', {templateUrl: 'js/dashboard/component.view.html', controller: 'documentCtrl',
        activetab: 'dashboard',
        data: {
            //authorizedRoles: [USER_ROLES.admin, USER_ROLES.editor]
        }});
}).controller('documentCtrl', function ($scope, $route, $location, DashboardResource, DocumentResource) {
    console.log($route.current.params.document_id);
    $scope.documentId = $route.current.params.document_id;
    $scope.state = 'form';
    $scope.metrics = [];
    $scope.metrics_d = {};
    $scope.sla = {site: null, name: '', id: $scope.documentId, metrics: {}, editable: false};
    $scope.sites = [];
    $scope.actions = [];

    $scope.loadDocument = function () {
        var sla = null;

        if($scope.documentId) {
            DocumentResource.get({id: $scope.documentId}, function (data) {
                $scope.actions = data.actions;
                sla = data.document;

                for (var metric in sla.metrics) {
                    if (sla.metrics.hasOwnProperty(metric) && metric != 'weightComputing') {
                        if ($scope.metrics_d[metric].inputType == 'DATE' && sla.metrics[metric] != null) {
                            sla.metrics[metric] = new Date(sla.metrics[metric]);
                        }

                    }
                }

                $scope.sla = sla;

                $scope.sites.forEach(function (site) {
                    if(site.id == sla.site){
                        sla.site = site;
                    }
                });

                //check whether component is editable
                $scope.actions.forEach(function (action) {
                    if(action.id == 'editDraft')
                        //setting it in sla is for possible future compatibility
                        sla.editable = true;
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
        $location.url('/error?message=sitesUnavailable');
    });

    $scope.loadMetrics = function () {
        DocumentResource.metrics({}, function (data) {
            $scope.metrics = data;



            $scope.metrics.forEach(function (metric) {
                if(!$scope.documentId)
                    $scope.sla.metrics[metric.id] = null;
                $scope.metrics_d[metric.id] = metric;
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

    $scope.sendRequest = function (form, sla) {
        if(!form.$valid)
            return;
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
    $scope.performAction = function (form, action, sla) {
        if(!form.$valid)
            return;
        $scope.sla.action = action;
        DocumentResource.perform_action(sla, function (data) {
            $scope.state = 'success';
        }, function (response) {

        });
    };
});