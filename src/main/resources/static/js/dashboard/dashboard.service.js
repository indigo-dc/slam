angular.module('indigo.dashboard')
    .service('DashboardResource', function ($resource) {
        return $resource('/query/available?queryCategoryId=userDashboard', {}, {
            query: {method: 'GET'}
        });
    })
    .service('DocumentResource', function ($resource) {
        return $resource('/query/documents?queryId=:query_id', {query_id: '@query_id'}, {
            query: {method: 'GET'}
        })
    });