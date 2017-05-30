angular.module('indigo.dashboard')
    .service('DashboardResource', function ($resource) {
        //tyoe can be either userDashboard or providerDashboard
        return $resource('/query/available?queryCategoryId=:type', {type: '@userDashboard'}, {
            query: {method: 'GET'}
        });
    })
    .service('Sites', function ($resource, $q) {
        var res = $resource('api/sites', {}, {query: {method: 'GET'}});

        var sites = {};
        res.query({}, function (data) {
            data.rows.forEach(function (site) {
                sites[site.id] = site;
            });
        });

        return sites;
    })
    .service('DocumentResource', function ($resource, EngineInterceptor) {


        return $resource('/query/documents?queryId=:query_id', {query_id: '@query_id', id: '@id', action: '@action'}, {
            query: {method: 'GET'},
            get: {method: 'GET', url: '/component/getwithextradata?documentId=:id', interceptor: EngineInterceptor},
            metrics: {method: 'GET', url: 'api/metrics?type=computing', isArray: true},
            query_action: {method: 'GET', url: 'api/sla_action/:id', isArray: true},
            perform_action: {method: 'POST', url: 'api/sla_action/:id', transformRequest: EngineInterceptor.request},
            create: {method: 'POST', url: 'api/sla', transformRequest: EngineInterceptor.request},
            update: {method: 'PUT', url: 'api/sla/:id', transformRequest: EngineInterceptor.request},
            //sites: {method: 'GET', url: 'http://indigo.cloud.plgrid.pl/cmdb/service/list'}
            sites: {method: 'GET', url: 'api/sites'}
        })
    }).service('EngineInterceptor', function () {
    return {
        response: function (response) {
            return response.resource.data;
        },
        responseError: function (response) {
            return response;
        },
        request: function (data, headersGetter) {
            var site = data.site;
            console.log('parsing request');
            if(site && site.id) {
                data.site = site.id;
                data.siteName = site.value.provider_id;
            }

            return angular.toJson(data)
        }
    }
});