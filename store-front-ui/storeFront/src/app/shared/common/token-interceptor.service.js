(function() {
  'use strict';

  angular
    .module('storeFront')
    .factory('StoreInterceptor', StoreInterceptor);

    function StoreInterceptor($q, storeFront_Constants, $rootScope, $location){
      return {
        // optional method
       'request': function(config) {
         //console.log('request started....', config);
         var access_token = storeFront_Constants.AUTH_KEY;
         //config.headers.AUTH_KEY = localstorage.get('AUTH_KEY');
         if (access_token) {
             config.headers.authorization = access_token;
         }
         return config;
       },

       // optional method
      'requestError': function(response) {
        console.log("request error.......")
         return $q.reject(response);
       },

       // optional method
       'response': function(response) {
         // do something on success
         return response;
       },

       // optional method
     'responseError': function(response) {
        // do something on error
    		console.log("I am here");
    		//console.log(response.status);
        if (response.status ===  -1) {
          var currentPath = $location.path();
          console.log("location",currentPath);
          if (currentPath == '/home') {
            console.log("home page");
          }
          else if (currentPath == '/') {
            console.log("home page");
          }
          else {
            console.log("else part");
            window.location.href=storeFront_Constants.REST_API_BASE+'/login';
          }
            //$rootScope.$broadcast('unauthorized');
            console.log("getting status code 403");
            //console.log("I am here");
        }

        // if (canRecover(rejection)) {
        //   return responseOrNewPromise
        // }
        return $q.reject(response);
      }
    };
  }

})();
