//for some reason in order to make it work with brunch module init must be wrapped into function

angular.module('indigo', ['ngRoute',
    'ngSanitize',
    'ngResource',
    'pascalprecht.translate',
    'spring-data-rest',
    'engine',
    //application modules
    'indigo.components.header',
    'indigo.dashboard',
    'indigo.profile',
    'indigo.constants' //this module is dynamically generated in index.html
]);