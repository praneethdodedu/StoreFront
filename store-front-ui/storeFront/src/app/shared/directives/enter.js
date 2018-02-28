(function() {
  'use strict';

  angular
    .module('storeFront')
    .directive('ngEnter', function () {
        return function (scope, element, attrs) {
          console.log("enter is entered");
            element.bind("keydown keypress", function (event) {
                if (event.which === 13) {
                    scope.$apply(function () {
                        scope.$eval(attrs.ngEnter|| attrs.ngClick);
                    });
                    event.preventDefault();
                }
            });
        };
    })
})
