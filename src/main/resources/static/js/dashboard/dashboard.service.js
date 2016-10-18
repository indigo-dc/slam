angular.module('indigo.dashboard')
    .service('DashboardResource', function ($resource) {
        return $resource('/query/available?queryCategoryId=userDashboard', {}, {
            query: {method: 'GET'}
        });
    })
    .service('DocumentResource', function ($resource) {
        return $resource('/query/documents?queryId=:query_id', {query_id: '@query_id', id: '@id'}, {
            query: {method: 'GET'},
            metrics: {method: 'GET', url: 'api/metrics?type=computing', isArray: true},
            query_action: {method: 'GET', url: 'api/sla_action/:id', isArray: true},
            perform_action: {method: 'POST', url: 'api/sla_action/:id?action=:action'},
            create: {method: 'POST', url: 'api/sla'},
            update: {method: 'PUT', url: 'api/sla/:id'},
            //sites: {method: 'GET', url: 'http://indigo.cloud.plgrid.pl/cmdb/service/id/4401ac5dc8cfbbb737b0a02575e6f4bc'}
            sites: {method: 'GET', url: 'api/sites'}
        })
    });