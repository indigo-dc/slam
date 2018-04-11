angular.module('indigo.components.header', ['ngRoute'])
    .component('indigoHeader', {
        templateUrl: 'js/components/header/header.view.html',
        controller: function ($scope, $route, SESSION) {
            this.$route = $route
            if(SESSION.roles.indexOf("ROLE_PROVIDER") != -1) {
                this.is_provider = true;
            }
            if(SESSION.roles.indexOf("ROLE_ADMIN") != -1) {
                this.is_provider = true;
                this.is_admin = true;
            }
        }
    });