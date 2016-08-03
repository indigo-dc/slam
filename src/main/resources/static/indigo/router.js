var app = angular.module('configurationManager', ['ngRoute', 'ngSanitize', "ngResource",'ui.bootstrap', 'pascalprecht.translate', 'spring-data-rest']);


app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/', {
            controller: 'MainController',
            templateUrl: 'indigo/view/main.html'
        })
        .when('/add-resource', {
            controller: 'ProvidersController',
            templateUrl: 'indigo/view/indigo/providers.html'
        })
        .when('/view-provider/:id', {
            controller: 'ProviderDetailsController',
            templateUrl: 'indigo/view/indigo/provider.html'
        })
        .when('/document/:id', {
            controller: 'DocumentController',
            templateUrl: 'indigo/view/document.html'
        })
        .when('/test-slas', {
            controller: 'TestGrantController',
            templateUrl: 'indigo/view/test-grants/list.html'
        })
        .when('/create-pool', {
            controller: 'CreatePoolController',
            templateUrl: 'indigo/view/sla/create-pool.html'
        })
        .when('/create-request', {
            controller: 'CreateRequestController',
            templateUrl: 'indigo/view/sla/create-request.html'
        })
        .when('/create-request', {
            controller: 'CreateRequestController',
            templateUrl: 'indigo/view/sla/create-request.html'
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
