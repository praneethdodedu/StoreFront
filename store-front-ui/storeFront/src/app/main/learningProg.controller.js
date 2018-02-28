(function() {
  'use strict';

  angular
    .module('storeFront')
    .controller('LearningProgController', LearningProgController);

    /** @ngInject */
    function LearningProgController($scope, $location, $http, LoginService, LearnPDetailService) {

      $scope.learningProgInIt = function() {
        $scope.learningProgList();
        $scope.learningProgDetailsList();
      }

      $scope.lpID = $location.path().split(/[\s/]+/).pop();
      console.log("lpID url", $scope.lpID);

      $scope.learningProgList = function() {
			$scope.promise=LearnPDetailService.getLPDetails()
				.then(function (response) {
                    $scope.getLPDetails=response.data;
                    console.log("learningPrograms", $scope.getLPDetails);
                }, function (error) {
					console.log("error in getting learning program list data..");
				});
 			}

      $scope.learningProgDetailsList = function() {
        console.log("lp id", $scope.lpID);
        $scope.promise=LearnPDetailService.getLPDetailsByID($scope.lpID)
              .then(function (response) {
                    $scope.getLPDetailsByID=response.data;
                    $scope.getlpCourses = response.data.courses;
                    console.log("learningPrograms by id", $scope.getLPDetailsByID);
                    console.log("learningPrograms by id", $scope.getlpCourses);
                }, function (error) {
                   console.log("error in getting learning program details list data..");
				});
      }

    }

})();
