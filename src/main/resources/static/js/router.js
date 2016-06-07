var app = angular.module('ltosApp', [ "ui.router", 'ngRoute', 'ngSanitize', "ngResource", "spring-data-rest"]);

app.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.when('', '/login');
	$urlRouterProvider.when('#', '/login');
	$urlRouterProvider.when('/', '/login');
	$stateProvider.state('login', {
		url : '/login',
		views : {
			"viewContent" : {
				controller : 'LoginController',
				templateUrl : 'js/view/login.html'
			}
		}
	}).state('registration', {
		url : "/registration",
		views : {
			"viewContent" : {
				controller : "RegistrationController",
				templateUrl : 'js/view/registration.html'
			}
		}
	}).state('dashboard', {
		url : "/dashboard",
		views : {
			"header" : {
				controller : "MainController",
				templateUrl : 'js/view/header.html'
			},
			"viewContent" : {
				controller : "DashboardController",
				templateUrl : 'js/view/dashboard.html'
			},
			"footer" : {
				templateUrl : 'js/view/footer.html'
			}
		}
	}).state('add-affiliation', {
		url : "/add-affiliation",
		views : {
			"header" : {
				controller : "MainController",
				templateUrl : 'js/view/header.html'
			},
			"viewContent" : {
				controller : "AddAffiliationController",
				templateUrl : 'js/view/add-affiliation.html'
			},
			"footer" : {
				templateUrl : 'js/view/footer.html'
			}
		}
	}).state('account', {
		url : "/account",
		views : {
			"header" : {
				controller : "MainController",
				templateUrl : 'js/view/header.html'
			},
			"viewContent" : {
				controller : "AccountController",
				templateUrl : 'js/view/account.html'
			},
			"footer" : {
				templateUrl : 'js/view/footer.html'
			}
		}
	}).state('affiliation-view', {
		url : "/affiliation-view",
		views : {
			"header" : {
				controller : "MainController",
				templateUrl : 'js/view/header.html'
			},
			"viewContent" : {
				controller : "AffiliationViewController",
				templateUrl : 'js/view/affiliation-view.html'
			},
			"footer" : {
				templateUrl : 'js/view/footer.html'
			}
		}
	}).state('resources', {
		url : "/resources",
		views : {
			"header" : {
				controller : "MainController",
				templateUrl : 'js/view/header.html'
			},
			"viewContent" : {
				controller : "ResourcesController",
				templateUrl : 'js/view/resources.html'
			},
			"footer" : {
				templateUrl : 'js/view/footer.html'
			}
		}
	}).state('resources-apply', {
		url : "/resources-apply",
		views : {
			"header" : {
				controller : "MainController",
				templateUrl : 'js/view/header.html'
			},
			"viewContent" : {
				controller : "ResourcesController",
				templateUrl : 'js/view/resources-apply.html'
			},
			"footer" : {
				templateUrl : 'js/view/footer.html'
			}
		}
	});
});