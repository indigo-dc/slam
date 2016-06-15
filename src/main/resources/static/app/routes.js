var app = angular.module('configurationManager', ['ngRoute', 'ui.bootstrap', 'pascalprecht.translate']);


app.config(function($routeProvider) {
    $routeProvider.when('/', {
        controller : 'MainController',
        templateUrl : 'app/view/main.html'
    })
    .when('/applications', {
        controller : 'ApplicationController',
        templateUrl : 'app/view/application.html'
    })
    .when('/document/:id',
    {
        controller: 'DocumentController',
        templateUrl : 'app/view/document.html'
    })
    ;
});



app.config(['$translateProvider', function ($translateProvider) {
    // configures staticFilesLoader
    $translateProvider.useStaticFilesLoader({
        prefix: 'data/locale-',
        suffix: '.json'
    });
    // load 'en' table on startup
    $translateProvider.preferredLanguage('pl');
}]);




app.controller('Ctrl', ['$translate', '$scope', function ($translate, $scope) {

    $scope.changeLanguage = function (langKey) {
        $translate.use(langKey);
    };
}]);