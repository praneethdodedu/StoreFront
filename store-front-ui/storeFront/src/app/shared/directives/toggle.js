(function() {
  'use strict';

  angular
    .module('storeFront')
    .directive('toggle', function() {
    return function(scope, elem, attrs) {
        scope.$on('event:toggle', function() {
            elem.slideToggle();
        });
    };
  });

})
