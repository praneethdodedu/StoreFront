(function(){
    'use strict';
    angular.module('storeFront')
        .factory('LearnPDetailService', LearnPDetailService);

    function LearnPDetailService ($http, storeFront_Constants) {
      return {
            getLPDetails: function () {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_LEARNING_PROGRAM.URI,
                     method: storeFront_Constants.API.GET_LEARNING_PROGRAM.METHOD
                });
            },
            getLPDetailsByID: function (id) {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_LEARNING_PROGRAM_BYID.URI.replace('{id}', id),
                     method: storeFront_Constants.API.GET_LEARNING_PROGRAM_BYID.METHOD
                });
            },
      }
    }

})();
