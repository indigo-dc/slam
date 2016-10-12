angular.module('indigo.dashboard')
    .service('DashboardResource', function ($resource) {
        return $resource('/query-category/queries?queryCategoryId=userDashboard', {}, {
            query: {method: 'GET'}
        });
    })
    .service('DocumentResource', function ($resource) {
        return $resource('/queries/documents?queryId=:query_id', {query_id: '@query_id'}, {
            query: {method: 'GET'}
        })
    });