var app = angular.module('configurationManager');

/*get rid of this code - it is simly a copy of ltosApp*/

app.service("identityService", ['$http', '$q', function ($http, $q) {
   
    var identity = '';
    
    var identityRegistered = function(){
        return identity && identity.id;
    }

    var setIdentity = function(newIdentity) {
    	identity = newIdentity;
    }

    var getIdentity = function(){
        return identity;
    }
    
    // Return public API.
    return ({
    	identityRegistered:identityRegistered,
        setIdentity:setIdentity,
        getIdentity:getIdentity
    });
    
}]);