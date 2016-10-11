angular.module('indigo')

    .config(['$routeProvider', function ($routeProvider) {

        //$routeProvider.when('/registration', {templateUrl: 'js/view/registration.html', controller: 'RegistrationController', data: {
        //    authorizedRoles: [USER_ROLES.admin, USER_ROLES.editor]
        //}});

        //$routeProvider.when('/dashboard', {templateUrl: 'js/view/dashboard.html', controller: 'DashboardController', data: {
        //    authorizedRoles: [USER_ROLES.admin, USER_ROLES.editor]
        //}});

        //    .state('add-affiliation', {
        //    url: "/add-affiliation",
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: "AddAffiliationController",
        //            templateUrl: 'js/view/add-affiliation.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('account', {
        //    url: "/account",
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: "AccountController",
        //            templateUrl: 'js/view/account.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('profile', {
        //    url: "/profile",
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: "ProfileController",
        //            templateUrl: 'js/view/profile.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('affiliations-management', {
        //    url: "/affiliations-management",
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: "AffiliationsManagementListController",
        //            templateUrl: 'js/view/affiliations-management-list.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('manage-affiliation', {
        //    url: "/manage-affiliation",
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: "ManageAffiliationController",
        //            templateUrl: 'js/view/manage-affiliation.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('affiliation-view', {
        //    url: "/affiliation-view",
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: "AffiliationViewController",
        //            templateUrl: 'js/view/affiliation-view.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('resources', {
        //    url: "/resources",
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: "ResourcesController",
        //            templateUrl: 'js/view/resources.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('resources-apply', {
        //    url: "/resources-apply",
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: "ResourcesController",
        //            templateUrl: 'js/view/resources-apply.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('indigo', {
        //    url: '/indigo',
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: 'MainController',
        //            templateUrl: 'js/view/indigo/main.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('document', {
        //    url: '/document/:id',
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: 'DocumentController',
        //            templateUrl: 'js/view/indigo/document.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('test-slas', {
        //    url: '/test-slas',
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: 'TestGrantController',
        //            templateUrl: 'js/view/indigo/test-grants/list.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('create-pool', {
        //    url: '/create-pool',
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: 'CreatePoolController',
        //            templateUrl: 'js/view/indigo/sla/create-pool.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('create-request', {
        //    url: '/create-request',
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: 'CreateRequestController',
        //            templateUrl: 'js/view/indigo/sla/create-request.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('view-provider', {
        //    url: '/view-provider/:id',
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: 'ProviderDetailsController',
        //            templateUrl: 'js/view/indigo/indigo/provider.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //}).state('add-resource', {
        //    url: '/add-resource',
        //    views: {
        //        "header": {
        //            controller: "HeadController",
        //            templateUrl: 'js/view/header.html'
        //        },
        //        "viewContent": {
        //            controller: 'ProvidersController',
        //            templateUrl: 'js/view/indigo/indigo/providers.html'
        //        },
        //        "footer": {
        //            templateUrl: 'js/view/footer.html'
        //        }
        //    }
        //});
    }])
    .config(['$translateProvider', function ($translateProvider) {
        // configures staticFilesLoader
        $translateProvider.useStaticFilesLoader({
            prefix: 'data/locale-',
            suffix: '.json'
        });
        // load 'en' table on startup
        $translateProvider.preferredLanguage('pl');
        $translateProvider.useSanitizeValueStrategy('escape');
    }])
    .controller('Ctrl', ['$translate', '$scope', function ($translate, $scope) {

        $scope.changeLanguage = function (langKey) {
            $translate.use(langKey);
        };
    }]);