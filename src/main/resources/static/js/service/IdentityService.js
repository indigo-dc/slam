/**
 * Created by krzysiek on 30.04.15.
 */
var app = angular.module('ltosApp');

app.service("identityService", function ($http, $q) {
   
	var identityEmail="";
    var identityName="";
    var identityRegistered = false;
    var identityId = -1;
    var user;

    var setIdentityEmail = function(newIdentityEmail) {
        identityEmail = newIdentityEmail;
    }

    var getIdentityEmail = function(){
        return identityEmail;
    }

    var setIdentityName = function(newIdentityName) {
        identityName = newIdentityName;
    }

    var getIdentityName = function(){
        return identityName;
    }

    var setIdentityRegistered = function() {
        identityRegistered = true;
    }

    var getIdentityRegistered = function(){
        return identityRegistered;
    }
    
    var setIdentityId = function(newIdentityId) {
    	identityId = newIdentityId;
    }

    var getIdentityId = function(){
        return identityId;
    }
    
    var setUser = function(newUser) {
    	user = newUser;
    }

    var getUser = function(){
        return user;
    }
    
    // Return public API.
    return ({
        setIdentityEmail: setIdentityEmail,
        getIdentityEmail:getIdentityEmail,
        setIdentityName: setIdentityName,
        getIdentityName:getIdentityName,
        setIdentityRegistered: setIdentityRegistered,
        getIdentityRegistered:getIdentityRegistered,
        setIdentityId:setIdentityId,
        getIdentityId:getIdentityId,
        setUser:setUser,
        getUser:getUser
    });
});