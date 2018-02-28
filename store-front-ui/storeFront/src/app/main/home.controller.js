(function() {
  'use strict';

  var app = angular
    .module('storeFront')
    .controller('HomeController', HomeController);

  /** @ngInject */
  function HomeController($scope, $rootScope, $stateParams, $state, $location, $http, $localStorage, LoginService, MainService, LearnPDetailService) {
    //  $rootScope.title = "StoreFront Login";
    //   $scope.myList = false;
    $scope.searchResult = false;
    $rootScope.vissibleContent = true;
    $scope.selectedOption = "course";
    $scope.searchText = "";
    //$rootScope.radioValue = "learner";
    // $scope.logedInUser = $localStorage.userLoginId;
    // console.log("localStorage.userLoginId", $localStorage.userLoginId);

     $scope.go = function ( path ) {
        $location.path( path );
    };

    $scope.allList = function() {
      $scope.checkAuthentication();
      $scope.courseList();
      $scope.catalogList();
      $scope.courseType();
      $scope.courseStatus();
      $scope.myCourses();
      $scope.previewCourse();
    }

     $scope.courseList = function() {
		     $scope.promise=MainService.getAllCourses()
              	.then(function (response) {
                    $scope.getAllCourses=response.data;
                    console.log("courses", $scope.getAllCourses);
				 }, function (error) {
                   console.log("error in getting course list data..");
            });
		}

    $scope.previewCourse = function() {
       $scope.promise = MainService.getPreviewCourses()
             .then(function (response) {
                  $scope.getPreviewCoursesList = response.data;
                  console.log("preview courses list", response);
              }, function (error){
                  console.log("error in getting the preview course lists..");
            })
    }

      $scope.catalogList = function() {
		      $scope.promise=MainService.getAllCatalogs()
           	.then(function (response) {
                $scope.getAllCatalogs=response.data;
                console.log("catalogs", $scope.getAllCatalogs);
            }, function (error) {
               console.log("error in getting catalog list data..");
			});
 		}

      $scope.courseType = function() {
        $scope.promise=MainService.getAllCourseType()
            .then(function (response) {
                $scope.getAllCourseType=response.data;
                console.log("course type", $scope.getAllCourseType);
            }, function (error) {
               console.log("error in getting course type list data..");
            });
		}

		$scope.courseStatus = function() {
			$scope.promise=MainService.getAllCourseStatus()
               .then(function (response) {
					$scope.getAllCourseStatus=response.data;
                    console.log("course status", $scope.getAllCourseStatus);
			    }, function (error) {
				   console.log("error in getting course status list data..");
            });
		}

    $scope.myCourses = function() {
  		$scope.promise=MainService.getMyCourses()
              .then(function (response) {
                  $scope.getMyCourses=response.data;
                  console.log("list of my courses", $scope.getMyCourses);
  			        }, function (error) {
          				console.log("error in getting course status list data..");
                });
    		// return $http.get('/assets/static/my-courses.json').then(function(response) {
        //             return $scope.getMyCourses=response.data;
        //             console.log("list of my courses", $scope.getMyCourses);
        //           });
        }

      $('#mySelect').on("change", function(){
          $('option:selected', this).hide().siblings().show();
      }).trigger('change');

      $scope.changeOption = function() {
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
        //console.log("data", data);
        //console.log("selectedOption", selectedOption);
        if (selectedOption == 'catalog') {
          $state.go('catalogDetails',{id: data.catalogId});
        }
        if (selectedOption == 'course') {
          $state.go('details',{id: data.courseId});
        }
      }

      $scope.myCourseUrl = function(mycourse) {
          if (mycourse.type == 'LearningProgram') {
            $state.go('learningProgDetails',{id: mycourse.id});
          }
          if (mycourse.type == 'Course') {
            $state.go('details',{id: mycourse.id});
          }
      }

	   $scope.myCoursesByAuthor = function() {
        // return $http.get('/assets/static/dummy-getCoursesByAuthor.json').then(function(response) {
        //         return $scope.getMyCourses=response.data;
        //         console.log("list of my courses", $scope.getMyCourses);
        //       });
      $scope.promise=MainService.getCourseByAuthor()
            .then(function (response) {
                 $scope.getMyCourses=response.data;
                 console.log("list of my courses", $scope.getMyCourses);
           }, function (error) {
               console.log("error in getting course status list data..");
           });

      }

      $scope.learningProgList = function() {
        // $http.get('/assets/static/dummy-learningProg.json').then(function(response) {
        //         console.log("learningPrograms", $scope.getLPDetails);
        //         return $scope.getLPDetails = response.data;
        //     });
       $scope.promise=LearnPDetailService.getLPDetails()
             .then(function (response) {
                     $scope.getLPDetails=response.data;
                     console.log("learningPrograms", $scope.getLPDetails);
               }, function (error) {
                   console.log("error in getting learning program list data..");
               });
      }

      $scope.checkAuthentication = function() {
        $scope.promise = LoginService.getAuthenticationStatus()
              .then(function (response){
                  $scope.statusCode = response.status;
                  console.log("authentication status code", response);
                  if($scope.statusCode == -1){
                    console.log("login is false");
                  }
                  else {
                    console.log("login is true");
                  }
                }, function(error){
                    console.log("error in getting the code..");
              })
      }

      $scope.bookMarkCourses = function(courseId) {
        console.log("bookmarked course is", courseId);
        $scope.payload = {
          "courseId": courseId,
          "isBookMarked": true
        }
        $scope.promise = MainService.getBookMrkCourses($scope.payload)
              .then(function (response){
                $scope.bookMarked = response.status;
                console.log("bookMarked data", response);
              }, function (error) {
                  console.log("error in getting the bookmarked data");
              })
      }

  }
})();
