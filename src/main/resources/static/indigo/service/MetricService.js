var app = angular.module('configurationManager');

app.service("MetricService", ['$http', function ($http) {

    // Return public API.
    return ({
        getMetrics: getMetrics,
        getMetricCategories: getMetricCategories
    });


    function getMetrics(documentId) {

        return $http.get("metrics",{
            params: {
                documentId: documentId
            }
        }).then(function(result){
            return serveResultMetric(result.data);
        });
    }


    function serveResultMetric(result) {
        var metrics = new Object();

        result['data'].forEach(function (metric) {
            metrics[metric.id] = metric;
        });

        return metrics;
    }


    function getMetricCategories(documentId) {

        return $http.get("metric-categories",{
            params: {
                documentId: documentId
            }
        });
    }

}]);