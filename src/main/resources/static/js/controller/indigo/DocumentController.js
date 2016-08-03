var app = angular.module('indigo');

app.controller('DocumentController', ['$scope', '$routeParams', '$route', 'DocumentService', 'MetricService', '$q', '$stateParams',
    function ($scope, $routeParams, $route, DocumentService, MetricService, $q, $stateParams) {
        $scope.isLoading = false;
        $scope.documentId = $stateParams.id;
        $scope.document;
        $scope.branchOfScience;
        $scope.actions;
        $scope.queries;
        $scope.metrics = new Object();//mapped metric.id => metric
        $scope.metricFormValues = new Array();
        $scope.isInEditMode = false;
        $scope.visibleActions;
        //TODO skoro walidujemy dyrektywa, to dlaczego mamy to pole w scopie controllera?
        $scope.message;
        $scope.metricCategories;
        $scope.visibleMetricCategories = new Array();
        //TODO po zaladowaniu danych aktywowac pierwsza zakladke
        $scope.visibleMetricCategoryId = null;

        $scope.metricCategoryId2MetricId = new Array();
        $scope.displayAddMetricCategoryPopup = false;


        _loadRemoteData();


        // I load the remote data from the server.
        function _loadRemoteData() {
            $scope.isLoading = true;

            var promises = [];

            promises.push(
                DocumentService.getDocument($scope.documentId)
                    .success(function (data) {
                            serveResult(data);
                        }
                    ),
                MetricService.getMetrics($scope.documentId)
                    .then(function (data) {
                            $scope.metrics = data;
                        }
                    ),

                MetricService.getMetricCategories($scope.documentId)
                    .success(function (data) {
                            serveResultMetricCategories(data);
                        }
                    )
            );

            $q.all(promises)
                .then(function (result) {
                    _prepareDataToView();
                })
                .catch(function (error) {
                    console.log(error);
                    alert('wystapil błąd...');
                })
                .finally(function () {
                    $scope.isLoading = false;
                });

        }


        function _prepareDataToView() {
            _prepareCategories2Metrics();
            _prepareVisibleMetricCategories();
        }

        function _prepareCategories2Metrics() {
            var mapping = new Object()

            for (var metricId in $scope.metrics) {
                var metric = $scope.metrics[metricId];
                var categoryId = metric.categoryId;


                var metricsInCategory = new Array();
                if (categoryId in mapping) {
                    metricsInCategory = mapping[categoryId];
                }
                metricsInCategory.push(metricId)
                mapping[categoryId] = metricsInCategory;

            }
            ;

            $scope.metricCategoryId2MetricId = mapping;
        }


        function _prepareVisibleMetricCategories() {
            var checkedCategoriesIds = new Array();

            for (var metricId in $scope.metricFormValues) {

                var metric = $scope.metrics[metricId];

                if (metric == null) {
                    continue;
                }

                var categoryId = metric.categoryId;

                if (categoryId == null) {
                    continue;
                }


                if (checkedCategoriesIds.indexOf(categoryId) == -1) {
                    var metricIds = $scope.metricCategoryId2MetricId[categoryId];

                    for (key in metricIds) {
                        var metricIdToAdd = metricIds[key];
                        var value = $scope.metricFormValues[metricIdToAdd];

                        if (value == null || value == NaN || value == undefined) {
                            $scope.metricFormValues[metricIdToAdd] = $scope.metrics[metricIdToAdd]['defaultValue'];
                        }
                    }

                    checkedCategoriesIds.push(categoryId);
                }

                var mainCategoryId = _getMainCategoryIdForMetric(categoryId);

                _addVisibleMetricCategory(mainCategoryId);

            }

            if ($scope.visibleMetricCategories.length > 0) {
                $scope.visibleMetricCategoryId = $scope.visibleMetricCategories[0];
            }
        }


        function _addVisibleMetricCategory(categoryId) {
            if ($scope.visibleMetricCategories.indexOf(categoryId) == -1) {
                $scope.visibleMetricCategories.push(categoryId);
            }
        }

        function _removeVisibleMetricCategory(categoryId) {
            var key = $scope.visibleMetricCategories.indexOf(categoryId);
            if (key > -1) {
                $scope.visibleMetricCategories.splice(key, 1);
            }

            if (categoryId.localeCompare($scope.visibleMetricCategoryId) === 0) {
                //we are removing active tab, activate lastone
                var lastActiveCategoryId = $scope.visibleMetricCategories[$scope.visibleMetricCategories.length - 1];
                $scope.visibleMetricCategoryId = lastActiveCategoryId;
            }
        }


        $scope.isMainCategoryVisible = function (categoryId) {
            return $scope.visibleMetricCategories.indexOf(categoryId) != -1;
        }


        $scope.addActiveClass = function (categoryId) {
            if ($scope.visibleMetricCategories.indexOf(categoryId) != -1) {
                return "active";
            }
        }

        function _getMainCategoryIdForMetric(categoryId) {
            //TODO this should be recursive

            if (categoryId == null) return null;

            var id = null;
            $scope.metricCategories.forEach(function (metricCategory) {
                if (categoryId.localeCompare(metricCategory.id) === 0) {
                    id = metricCategory.id;
                }
                else {
                    if (metricCategory.children.length > 0) {
                        metricCategory.children.forEach(function (subCategory) {
                            if (categoryId.localeCompare(subCategory.id) === 0) {
                                id = metricCategory.id;
                            }
                        });
                    }
                }
            });
            return id;
        }


        function serveResult(result) {
            $scope.document = result['data'].document;
            if($scope.document.branchOfScienceId!=null){
                DocumentService.getBranchOfScience($scope.document.branchOfScienceId).success(function (result) {
                        $scope.branchOfScience = result['data'];
                    }
                )
            }
            $scope.visibleActions = $scope.actions = result['data'].actions;
            $scope.queries = result['data'].queries;

            for (metricId in $scope.document.metrics) {
                $scope.metricFormValues[metricId] = $scope.document.metrics[metricId];
            }
        }


        function serveResultMetricCategories(result) {
            $scope.metricCategories = result['data'];
        }


        $scope.doAction = function (actionId) {
            $scope.isLoading = true;

            DocumentService.doAction($scope.documentId, actionId, $scope.metricFormValues)
                .then(function (result) {
                    _serverResourcesAfterDoAction(result.data);
                })
                .catch(function (error) {
                    console.log(error);
                    alert('wystapil błąd...');
                })
                .finally(function () {
                    $scope.isLoading = false;
                });
        }


        $scope.showMetricCategory = function (metricCategoryId) {
            $scope.visibleMetricCategoryId = metricCategoryId;
        }


        $scope.addMetricsPopopIsVisible = false;
        $scope.showAddMetricsPopup = function () {
            $scope.addMetricsPopopIsVisible = true;
        }

        $scope.hideAddMetricsPopup = function () {
            $scope.addMetricsPopopIsVisible = false;
        }

        function _serverResourcesAfterDoAction(result) {
            var actionType = result.data.type;

            if ('DISPLAY_FORM'.localeCompare(actionType) === 0) {
                $scope.isInEditMode = true;
                $scope.visibleActions = result.data.actions;
                //$scope.formScope.requestedResourcesForm.
                //TODO uruchomic walidacje aby od razu byly widoczne informacje o bledach w formie
            }
            else if ('REDIRECT'.localeCompare(actionType) === 0) {
                window.location = "#/document/" + result.data.redirectToDocument;
                $route.reload();
            }
            else {
                alert("wystąpił bład, zostaniesz przekierowany na stronę główną")
                window.location = "/#/";
            }
        }

        $scope.addMetricCategory = function (category) {
            var metrics = _getMetricsFromCategory(category);

            metrics.forEach(function (metric) {
                $scope.metricFormValues[metric.id] = metric.defaultValue;
            });


            _addVisibleMetricCategory(category.id);
            //active added category tab
            $scope.visibleMetricCategoryId = category.id;
        }


        $scope.removeMetricCategory = function (category) {
            //remove metrics from document
            var metrics = _getMetricsFromCategory(category);

            metrics.forEach(function (metric) {
                delete $scope.metricFormValues[metric.id];
            });

            _removeVisibleMetricCategory(category.id);
        }


        $scope.cancelEdit = function () {
            $scope.isInEditMode = false;
            $scope.visibleActions = $scope.actions;
        }

        /**
         * Fetch list of metrics belonging to given category, including metrics of subcategories
         *
         * @param category
         * @returns {Array}
         * @private
         */
        function _getMetricsFromCategory(category) {
            /**
             * List of subcategories id of category, including itself
             * @type {Array}
             */
            var categoriesIds = new Array();
            categoriesIds.push(category.id);

            if (category.children.length > 0) {
                category.children.forEach(function (subCategory) {
                    categoriesIds.push(subCategory.id);
                });
            }


            var metrics = new Array();
            for (var metricId in $scope.metrics) {
                var metric = $scope.metrics[metricId];
                if (categoriesIds.indexOf(metric.categoryId) != -1) {
                    metrics.push(metric);
                }
            }

            return metrics;
        }


        /**
         * load metric parameter
         * @param metricId
         * @param paramName
         * @returns {*}
         */
        $scope.getMetricParam = function (metricId, paramName) {
            var metric = $scope.metrics[metricId];

            if (metric != null && metric != undefined && paramName in metric) {
                return metric[paramName];
            }
        }

        $scope.inputTypeVisible = function (metricId, inputType) {
            var metric = $scope.metrics[metricId];
            if (metric == null || metric == undefined) {
                return false;
            }
            if (metric.inputType == inputType) {
                return true;
            }
            return false;
        }

        $scope.formScope = {};
        $scope.setFormScope = function (scope) {
            $scope.formScope = scope;
        }

    }]);

//TODO przeniesc w inne miejsce
app.run(['$rootScope', function ($rootScope) {
    $rootScope.typeOf = function (value) {
        return typeof value;
    };
}])

    .directive('stringToNumber', function () {
        return {
            require: 'ngModel',
            link: function (scope, element, attrs, ngModel) {
                ngModel.$parsers.push(function (value) {
                    return '' + value;
                });
                ngModel.$formatters.push(function (value) {
                    return parseFloat(value);
                });
            }
        };
    });