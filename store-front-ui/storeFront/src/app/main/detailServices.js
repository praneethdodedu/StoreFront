(function(){
    'use strict';
    angular.module('storeFront')
        .factory('CourseDetailService', CourseDetailService);

    function CourseDetailService ($http, storeFront_Constants) {
      return {
            getCourseByID: function (id) {
              console.log("id inside services", id);
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_COURSE_DETAILS.URI.replace('{id}', id),
                     method: storeFront_Constants.API.GET_COURSE_DETAILS.METHOD
                });
            },
            getFluidicPlyrUrl: function (data) {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.FLUIDIC_PLAYER_URL.URI+'?courseId='+data.courseId+'&moduleId='+data.moduleId,
                     method: storeFront_Constants.API.FLUIDIC_PLAYER_URL.METHOD,
                     transformResponse: [function (data) {
                       return data;
                     }]
                })
            },
            postPreviewCourses: function (data) {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.PREVIEW_COURSES_BYID_URL.URI.replace('{userId}', userId),
                     method: storeFront_Constants.API.PREVIEW_COURSES_BYID_URL.METHOD,
                     data: data
                });
            },
            postEnrollCourses: function (data) {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.POST_ENROLLCOURSES.URI,
                     method: storeFront_Constants.API.POST_ENROLLCOURSES.METHOD,
                     data: data
                });
            }
      }
    }

})();
