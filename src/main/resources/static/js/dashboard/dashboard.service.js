angular.module('indigo.dashboard')
    .service('DashboardResource', function ($resource) {
        return $resource('/query-category/queries?queryCategoryId=userDashboard', {}, {
            query: {method: 'GET'},
        });
    });