var app = angular.module('configurationManager', ['ngRoute', 'ngSanitize', "ngResource",'ui.bootstrap', 'pascalprecht.translate', 'spring-data-rest']);


app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/', {
            controller: 'MainController',
            templateUrl: 'app/view/main.html'
        })
        .when('/document/:id', {
            controller: 'DocumentController',
            templateUrl: 'app/view/document.html'
        })
        .when('/test-slas', {
            controller: 'TestGrantController',
            templateUrl: 'app/view/test-grants/list.html'
        })
        .when('/create-pool', {
            controller: 'CreatePoolController',
            templateUrl: 'app/view/sla/create-pool.html'
        })
        .when('/create-request', {
            controller: 'CreateRequestController',
            templateUrl: 'app/view/sla/create-request.html'
        })
        .when('/create-request', {
            controller: 'CreateRequestController',
            templateUrl: 'app/view/sla/create-request.html'
        })
    ;
}]);


app.config(['$translateProvider', function ($translateProvider) {
    // configures staticFilesLoader
    $translateProvider.useStaticFilesLoader({
        prefix: 'data/locale-',
        suffix: '.json'
    });
    // load 'en' table on startup
    $translateProvider.preferredLanguage('pl');
    $translateProvider.useSanitizeValueStrategy('escape');
}]);


app.controller('Ctrl', ['$translate', '$scope', function ($translate, $scope) {

    $scope.changeLanguage = function (langKey) {
        $translate.use(langKey);
    };
}]);
