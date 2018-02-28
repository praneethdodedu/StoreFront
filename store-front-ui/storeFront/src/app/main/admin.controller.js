(function() {
  'use strict';

  angular
    .module('storeFront')
    .controller('AdminController', AdminController);

  /** @ngInject */
  function AdminController($scope, $rootScope, $state, $location, $http, LoginService, MainService, $timeout, CourseDetailService) {
    $rootScope.title = "StoreFront Admin";
    $rootScope.vissibleContent = true;
    $scope.selectedOption = "course";
    // $scope.setToNormal = function () {
    //     $rootScope.vissibleContent = true;
    // }

    $scope.myCoursesByAuthor = function() {
        // $http.get('/assets/static/dummy-getCoursesByAuthor.json').then(function(response) {
        //       $scope.getMyCourses=response.data
        //       console.log("list of my courses", $scope.getMyCourses);
        //       return $scope.getMyCourses;
        // });
    $scope.promise=MainService.getCourseByAuthor()
          .then(function (response) {
               $scope.getMyCourses=response.data;
               console.log("list of my courses", $scope.getMyCourses);
         }, function (error) {
             console.log("error in getting course status list data..");
         });

    }

    $scope.courseDetails = function() {
        $scope.lpCourseID = $location.path().split(/[\s/]+/).pop();
        console.log("lpCourseID url", $scope.lpCourseID);
  		$scope.promise=CourseDetailService.getCourseByID($scope.lpCourseID)
              .then(function (response) {
                  $scope.getCourseByID=response.data;
                  $scope.getModule = response.data.module;
                  $scope.getAuthor = response.data.author;
                  console.log("courses details", $scope.getCourseByID);
                  console.log("courses details module", $scope.getModule);
                  console.log("courses details author", $scope.getAuthor);
                    console.log("$rootScope.userRoles", $rootScope.userRoles);

              }, function (error) {
                  console.log("error in getting course list data..");
          });
       }
     $scope.courseDetails();

    if ($location.path() == '/home'){
          //$rootScope.radioValue = "learner";
          $scope.default_option1 = true;
          $scope.default_option2 = false;
          console.log("inside home page");
    }
    if ($location.path() == '/adminHome'){
          //$rootScope.radioValue = "admin";
          $scope.default_option2 = true;
          $scope.default_option1 = false;
          console.log("inside admin page");
      }

      $scope.course = {};
      $scope.paymentSelection = function(course) {
        console.log('I am called', $scope.course);
          if($scope.course.selected == "paid") {
              $scope.course.selected = "paid";
              console.log('paid is called', $scope.course);
          }
          if($scope.course.selected == "trial") {
              $scope.course.selected = "trail";
              console.log('trial is called', $scope.course);
          }
          if($scope.course.selected == "free") {
              $scope.course.selected = "free";
              console.log('free is called', $scope.course);
          }
      }

      $scope.course.selected = "free";
      console.log($scope.course.selected);

      $scope.published = function(response) {
            console.log("published response",response);
            console.log("$scope.selectedType", $scope.selectedType);
            $scope.payload =
                {
                  "courseId": $scope.courseId,
                  "paymentType": $scope.selectedType,
                  "amount": $scope.enterValue,
                  "isPublished": true,
                  "trialPeriod": $scope.trialDays
                }
            $scope.promise=MainService.publishCourse($scope.payload)
                  .then(function (response) {
                       $scope.getMyCourses=response.data;
                       console.log("list of my courses", $scope.getMyCourses);
                       $state.reload();
                 }, function (error) {
                     console.log("error in getting course status list data..");
                 });
      }

      $scope.saveConfigurationTrial = function(response) {
        console.log("save config trail", response.selected, response.price, response.days, response.courseId );
        console.log("save config trail", response);
        alert("now u can publish");
        $scope.courseId = response.courseId;
        $scope.selectedType = response.selected;
        $scope.enterValue =  response.price;
        $scope.trialDays = response.days
      }

      $scope.saveConfigurationPaid = function(response) {
        // console.log("save config", response );
        $scope.courseId = response.courseId;
        $scope.selectedType = response.selected;
        $scope.enterValue =  response.price;
        console.log("save config paid", response.selected, response.price);
        alert("now u can publish");
      }

      $('#mySelect').on("change", function(){
          $('option:selected', this).hide().siblings().show();
      }).trigger('change');

      $scope.changeAOption = function() {
        console.log("searchtext", $scope.searchText);
        console.log("changeOption", $scope.selectedOption);
        $rootScope.vissibleContent = false;
        if ($scope.selectedOption == 'catalog') {
            $scope.promise=MainService.getCatalogSearch($scope.searchText)
                   .then(function (response) {
                        $scope.getSearchResult=response.data;
                        console.log("catalog search by name", $scope.getSearchResult);
                    }, function (error) {
                        console.log("error in getting catalog search by name..");
				});

        };
        if ($scope.selectedOption == 'course') {
           $scope.promise=MainService.getCourseSearch($scope.searchText)
                .then(function (response) {
                    $scope.getSearchResult=response.data;
                    console.log("course search by name", $scope.getSearchResult);
                }, function (error) {
                    console.log("error in getting course search by name...");
			    });
        }
      }

      $scope.pageUrl = function(data, selectedOption) {
        console.log("data", data);
        console.log("selectedOption", selectedOption);
        // if (selectedOption == 'catalog') {
        //   $state.go('adminDetails',{id: course.courseId});
        // }
        if (selectedOption == 'course') {
            $state.go('adminDetails',{id: data.courseId});
        }
      }



  }

})();
