var app = angular.module('configurationManager');

app.controller('DocumentController', ['$scope', '$routeParams','$route', 'DocumentService', 'MetricService',
function ($scope, $routeParams, $route, DocumentService, MetricService) {
    $scope.isLoading = true;
    $scope.documentId = $routeParams.id;
    $scope.document;
    $scope.actions;
    $scope.queries;
    $scope.messages;
    $scope.metrics;
    $scope.metricFormValues = new Array();
    $scope.isInEditMode = false;
    $scope.visibleActions;


    $scope.init = function() {


    }

    _loadRemoteData();
    _loadMetrics();


    // I load the remote data from the server.
    function _loadRemoteData() {
        $scope.isLoading = true;

        DocumentService.getDocument($scope.documentId).then(
            function(result) {
                serveResult(result);
            }
        );
    }

    function _loadMetrics() {
        $scope.isLoading = true;

        MetricService.getMetrics($scope.documentId).then(
            function(result) {
                serveResultMetric(result);
            }
        );
    }

    function serveResult(result) {
        //if(result['status'] == 'SUCCESS') {
        $scope.document = result['data'].document;
        $scope.visibleActions = $scope.actions = result['data'].actions;
        $scope.queries = result['data'].queries;
        $scope.messages = result['data'].messages;

        for (metricId in $scope.document.requestedResouces) {
            $scope.metricFormValues[metricId] = $scope.document.requestedResouces[metricId];
        }

        //} else {
        //    alert("blad");
        //}
    }

    function serveResultMetric(result) {
        //if(result['status'] == 'SUCCESS') {
        $scope.metrics = result['data'];
        //} else {
        //    alert("blad");
        //}
    }

    $scope.doAction = function(actionId) {

        //if (_isFirstStepAction(actionId)) {
        //    $scope.isInEditMode = true;
        //}
        //else {
        //    alert('request');
        //}



        DocumentService.doAction($scope.documentId, actionId, $scope.metricFormValues).then(
            function(result) {
                _serverResourcesAfterDoAction(result);
            }
        );
    }

    function _serverResourcesAfterDoAction(result) {
        if (result.data.type == 'DISPLAY_FORM') {
            $scope.isInEditMode = true;
            $scope.visibleActions = result.data.actions;
        }
        else if (result.data.type == 'REDIRECT') {
            if (result.data.redirectToDocument != null)  {
                window.location = "#/document/"+result.data.redirectToDocument;
            }
        }
        else {
            alert("sth go wrong. please contact with Tadeusz Szymocha, he will write procedure for this scenario");
        }
    }

    $scope.addMetricToForm = function (metricId) {
        $scope.metricFormValues[metricId] = '';
        console.log($scope.metricFormValues);
    }

    $scope.cancelEdit = function() {
        $scope.isInEditMode = false;
        $scope.visibleActions = $scope.actions;
    }



    //function _isFirstStepAction(actionId) {
    //    var action = _getActionById(actionId);
    //    console.log(action);
    //
    //
    //    if (action == undefined) {
    //        return false;
    //    }
    //    else {
    //        if (action.secondStepActions.length > 0) {
    //            return true;
    //        }
    //        return false;
    //    }
    //}

    //function _getActionById(actionId) {
    //    var actionToReturn = undefined;
    //    $scope.actions.forEach(function(action) {
    //        console.log(action.id);
    //        if (action.id == actionId) {
    //            actionToReturn = action;
    //        }
    //
    //    });
    //    return actionToReturn;
    //
    //}

    $scope.isMetricVisible = function(metricId) {
        return $scope.metricFormValues[metricId] !== undefined;
    }

    $scope.removeMetric = function(metricId) {
        delete $scope.metricFormValues[metricId];
    }

    /**
     *
     * @param containerType (data/form)
     */
    $scope.isVisibleContainer = function(containerType) {
        if (containerType == 'form') {
            return $scope.isInEditMode;
        }
        else {
            return true;
        }
    }

    $scope.getMetricParam = function(metricId, paramName) {
        var metric = _getMetricById(metricId);

        if (metric != null && metric != undefined) {
            return metric[paramName];
        }

    }


    function _getMetricById(metricId) {
        var metric;
        if ($scope.metrics != null) {
            $scope.metrics.forEach(function (m) {
                if (m.id == metricId) {
                    metric = m;
                }

            });
        }
        return metric;
    }

  }]);