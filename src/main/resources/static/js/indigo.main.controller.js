angular.module('indigo')
    .controller('MainCtrl', function ($scope, $route, $rootScope, toastr) {
        $rootScope.$on('engine.notification.save.after', function () {
            toastr.success('Document saved');
        });
        $rootScope.$on('engine.notification.action.after', function (event, document, action) {
            if(action.isSave())
                return;

            toastr.success('Action completed');
        });

    });