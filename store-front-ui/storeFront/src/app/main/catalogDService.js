(function(){
    'use strict';
    angular.module('storeFront')
        .factory('CatalogDetailService', CatalogDetailService);

    function CatalogDetailService ($http, storeFront_Constants) {
      return {
            getCatalogByID: function (id) {
              console.log("id inside services", id);
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_CATALOG_DETAILS.URI.replace('{id}', id),
                     method: storeFront_Constants.API.GET_CATALOG_DETAILS.METHOD
                });
            },
      }
    }

})();
