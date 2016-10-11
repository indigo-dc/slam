/**
 * Created by krzysiek on 30.04.15.
 */
var app = angular.module('indigo');

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