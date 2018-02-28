(function() {
  'use strict';

  angular
    .module('storeFront')
    .controller('DetailController', DetailController);

  /** @ngInject */
  function DetailController($scope, $rootScope, $stateParams, $location, $state, $http, LoginService, CourseDetailService, MainService, $uibModal) {

    $scope.courseOverview = false;
    $scope.courseIntro = false;
    $scope.courseModule = false;

    $scope.courseID = $location.path().split(/[\s/]+/).pop();
    console.log("courseID url", $scope.courseID);
    console.log("  $scope.initCourseDetails()",$scope);

    $scope.initCourseDetails = function() {
		$scope.promise=CourseDetailService.getCourseByID($scope.courseID)
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

     $scope.initCourseDetails();

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

        // this.showModal = false;
        // //this.showView = false;
        // this.counter = 1;
        // this.toggleDialog = function () {
        //     this.showModal = !this.showModal;
        // }
        // // this.toggleView = function () {
        // //     this.showView = !this.showView;
        // // }
        // this.changeDisplay = function () {
        //     this.counter++;
        // }

       $scope.fluidicPlyrOpener = function (moduleId, courseId) {
         console.log("inside fluidic function", courseId, "moduleId", moduleId);
         $scope.payload = {
           "courseId": courseId,
           "moduleId": moduleId
         }
         $scope.promise=CourseDetailService.getFluidicPlyrUrl($scope.payload).then(function (response) {
                console.log("inside fludic player template")
                 console.log("response after service call", response)
                    $scope.getFluidicURL = response;
                    console.log("fluidic url", $scope.getFluidicURL);
                    console.log("fluidic url", $scope.getFluidicURL.data);
                    // window.addEventListener("message", function closePlayer(){
                    //   alert();
                    //   	if(event.data === "status:close"){
                    //   	   $state.go("details", {"id": courseId}) // replace <div_id> with div id.
                    //   	}
                    // });
                    // var uibInstance = $uibModal.open({
                    //    animation: 'true',
                    //    templateUrl: 'app/main/fluidicPlayer.html',
                    //    scope: $scope,
                    //    size: 'lg',
                    //    controller: function($scope) {
                    //      window.addEventListener("message", function closePlayer(){
                    //        	if(event.data === "status:close"){
                    //        	   $state.go("details", {"id": courseId}) // replace <div_id> with div id.
                    //        	}
                    //      });
                    //    }
                    //  })
              }, function (error) {
                  console.log("error in getting course status list data..");
              });
       }

       $scope.fludicUrl = function (url){
         console.log("fludic url function", url);
         var vedioPlayer = window.open(url);
         vedioPlayer.addEventListener("message", function closePlayer(){
           alert("bye adobe course video");
               if(event.data === "status:close"){
                  $state.go("details", {"id": courseId}) // replace <div_id> with div id.
               }
         });
       }

       $scope.previewCourse = function () {
         console.log("preview cousrse datas");
         $scope.payload = {
           // "courseId": courseId,
           // "isPreviewCourse ": isPreviewCourse
         }
         $scope.promise=CourseDetailService.postPreviewCourses($scope.payload)
                 .then(function (response) {
                     $scope.previewCoures=response.data;
                     console.log("$scope.previewCoures", $scope.previewCoures);
                 }, function (error) {
                     console.log("error in posting the preview course list data..");
             });
       }

       $scope.enrollCourse = function (courseId) {
         console.log("preview cousrse datas", courseId);
         $scope.payload = {
           "courseId": courseId
         }
         $scope.promise=CourseDetailService.postEnrollCourses($scope.payload)
                 .then(function (response) {
                     $scope.enrolledCoures=response;
                     $state.reload();
                     console.log("$scope.enrolledCoures", $scope.enrolledCoures);
                 }, function (error) {
                     console.log("error in posting the enrollment of courses..");
             });
       }

       $scope.initiatePayment = function(courseId, amount) {
         console.log("inside payment page");
       }


  }
})();
