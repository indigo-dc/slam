/**
 * Created by krzysiek on 30.04.15.
 */
var app = angular.module('ltosApp');

app.service("identityService", function ($http, $q) {
    var identityEmail="";
    var identityName="";
    var identityRegistered = false;


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
    // Return public API.
    return ({
        setIdentityEmail: setIdentityEmail,
        getIdentityEmail:getIdentityEmail,
        setIdentityName: setIdentityName,
        getIdentityName:getIdentityName,
        setIdentityRegistered: setIdentityRegistered,
        getIdentityRegistered:getIdentityRegistered
    });
});