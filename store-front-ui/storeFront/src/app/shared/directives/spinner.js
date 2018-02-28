(function() {
  'use strict';

  angular
   .module('storeFront')
   .directive('loading',   ['$http' ,function ($http)
   {
       return {
           restrict: 'A',
           template: '<div class="loading-spiner"><img src="http://www.nasa.gov/multimedia/videogallery/ajax-loader.gif" /> </div>',
           link: function (scope, elm, attrs)
           {
               scope.isLoading = function () {
                   return $http.pendingRequests.length > 0;
               };

               scope.$watch(scope.isLoading, function (v)
               {
                   if(v){
                       elm.show();
                   }else{
                       elm.hide();
                   }
               });
           }
       };
   }])

})
