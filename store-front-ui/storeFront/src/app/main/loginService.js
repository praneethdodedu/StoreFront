(function(){
    'use strict';
    angular.module('storeFront')
        .factory('LoginService', LoginService);

    function LoginService ($http, storeFront_Constants) {
        return {
          signUpUser: function (data) {
              return $http({
                   url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.REGISTER_URL.URI,
                   method: storeFront_Constants.API.REGISTER_URL.METHOD,
                   data: data
              });
          },
          getAccountLists: function() {
              return $http({
                url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_ACCOUNT_LISTS.URI,
                method: storeFront_Constants.API.GET_ACCOUNT_LISTS.METHOD
              });
          },
          getAuthenticationStatus: function() {
              return $http({
                url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_LOGIN_AUTHENTICATION.URI,
                method: storeFront_Constants.API.GET_LOGIN_AUTHENTICATION.METHOD
              });
          }
        }
    }
})();
