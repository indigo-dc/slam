//for some reason in order to make it work with brunch module init must be wrapped into function

angular.module('indigo', ['ngRoute',
                          'ngSanitize',
                          'ngResource',
                          'pascalprecht.translate',
                          'spring-data-rest',
                          //application modules
                          'indigo.components.header',
                          'indigo.dashboard',
                          'indigo.profile'
]);