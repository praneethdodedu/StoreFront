(function() {
  'use strict';

  angular
    .module('storeFront')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController($scope, $rootScope, $location, $http, $state, $localStorage, MainService, LearnPDetailService, LoginService) {
     $rootScope.title = "StoreFront";
      $scope.myList = false;
      $rootScope.vissibleContent = true;

      $scope.setToNormal = function () {
          $state.go('home');
          $rootScope.vissibleContent = true;
          $rootScope.selectedRadio = "learner";
      }

      $scope.checkAuthentication = function() {
        $scope.promise = LoginService.getAuthenticationStatus()
              .then(function (response){
                  $scope.statusCode = response.status;
                  console.log("authentication status code", response);
                  if($scope.statusCode == -1){
                    $scope.login = false;
                    console.log("login is false");
                  }
                  else {
                    $scope.login = true;
                    console.log("login is true");
                  }
                }, function(error){
                    console.log("error in getting the code..");
              })
      }

     $scope.go = function ( path ) {
        $location.path( path );
    };

    $scope.showList = function () {
      // $scope.initCatalogList();
       $scope.initCatalogListTemp();
       $scope.learningProgList();
       $scope.checkAuthentication();
       $scope.locationPath();
    }


  $scope.lpList = function () {
    $scope.myList = !$scope.myList;
    $scope.learningProgList();
  }

  $scope.hoverIn = function(catalogId) {
    //  $scope.childshow =!  $scope.childshow;
        $scope.catalogId = catalogId;
        console.log("catalogId", $scope.catalogId);
        //$('#'+$scope.catalogId).find('.libUlcourses').fadeIn();
        //$('#'+$scope.catalogId).siblings('.submenuCourses').find('.libUlcourses').fadeIn();
        //$('.progListing').find('a:active').css();
    }

      $scope.hoverOut = function(catalogId) {
        $scope.catalogId = catalogId;
        console.log("catalogId", $scope.catalogId);
        $('#'+$scope.catalogId).find('.libUlcourses').fadeOut();
      }

      $scope.initCatalogList = function() {
 				$scope.promise=CourseDataService.getAllCatalogs()
            	.then(function (response) {
                         $scope.getAllCatalogs=response.data;
                         console.log("catalogs", $scope.getAllCatalogs);
                   }, function (error) {
                       console.log("error in getting catalog list data..");
                   });
 			}

      $scope.initCatalogListTemp = function() {
        return $http.get('/assets/static/dummy-catalogs.json').then(function(response) {
                return $scope.getAllCatalogs=response.data;
                console.log("catalogs", $scope.getAllCatalogs);
              });
      }

      $scope.learningProgList = function() {
          $http.get('/assets/static/dummy-learningProg.json').then(function(response) {
                  return $scope.getLPDetails = response.data;
                  console.log("learningPrograms", $scope.getLPDetails);
              });
        // $scope.promise=LearnPDetailService.getLPDetails()
        //       	.then(function (response) {
        //                    $scope.getLPDetails=response.data;
        //                    console.log("learningPrograms", $scope.getLPDetails);
        //              }, function (error) {
        //                  console.log("error in getting learning program list data..");
        //              });
      }

      $scope.userList = function() {
          // $http.get('/assets/static/dummy-user.json').then(function(response) {
          //         $scope.getUserList = response.data;
          //         console.log("User list", $scope.getUserList);
          //         return $scope.getUserList;
          //     });
        $scope.promise=MainService.getUsers()
              	.then(function (response) {
                           $scope.getUserList=response.data;
                           console.log("User list", $scope.getUserList);
                     }, function (error) {
                         console.log("error in getting user list..");
                     });
      }

      $scope.userDetails = function(userId) {
          // $http.get('/assets/static/dummy-user.json').then(function(response) {
          //         return $scope.getUserDetails = response.data;
          //         console.log("User Details", $scope.getUserDetails);
          //     });
        $scope.promise=MainService.getUserDetails(userId)
              	.then(function (response) {
                           $scope.getUserDetails=response.data;
                           console.log("User Details", $scope.getUserDetails);
                     }, function (error) {
                         console.log("error in getting user list..");
                     });
      }

      $scope.switchRoles = function(selectedRadio) {
        console.log("$scope.selectedRadio swtichAccounts", selectedRadio);
        if (selectedRadio == 'learner') {
          $state.go('home');
        }
        if (selectedRadio == 'admin') {
          $state.go("admin");
        }
      }

      $scope.locationPath = function () {
        $location.path();
        console.log("path location", $location.path());
        if ($location.path() == '/home' || $location.path() == '/home/'){
              console.log("inside home page");
              $rootScope.selectedRadio = "learner";
          }
          if ($location.path() == '/adminHome' || $location.path() == '/adminHome/'){
                $rootScope.selectedRadio = "admin";
                console.log("inside admin page");
            }
       }

  }
})();
