(function() {
  'use strict';

  angular
    .module('storeFront')
    .controller('CatalogDtailController', CatalogDtailController);

  /** @ngInject */
  function CatalogDtailController($scope, $location, $state, $http, LoginService, CatalogDetailService, MainService ) {
    $scope.courseOverview = false;
    $scope.courseIntro = false;
    $scope.catalogCourses = false;

    $scope.catalogID = $location.path().split(/[\s/]+/).pop();
    console.log("courseID url", $scope.catalogID);

    $scope.initCatalogDetails = function() {
      $scope.promise = CatalogDetailService.getCatalogByID($scope.catalogID)
            .then(function (response) {
                    $scope.getCatalogByID=response.data;
                    $scope.getCourses = response.data.courses;
                    console.log("catalog details", $scope.getCatalogByID);
                    console.log("catalog details courses", $scope.getCourses);
                }, function (error) {
                    console.log("error in getting catalog details data..");
                });
     }


  }
})();
