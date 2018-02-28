(function(){
    'use strict';
    angular.module('storeFront')
        .factory('MainService', MainService);

    function MainService ($http, storeFront_Constants) {
      return {
            getAllCourses: function () {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_COURSES.URI,
                     method: storeFront_Constants.API.GET_COURSES.METHOD
                });
            },
            getAllCatalogs: function () {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_CATALOGS.URI,
                     method: storeFront_Constants.API.GET_CATALOGS.METHOD
                });
            },
            getCatalogByID: function (id) {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_CATALOG_DETAILS.URI.replace('{id}', id),
                     method: storeFront_Constants.API.GET_COURSES.METHOD
                });
            },
            getAllCourseType: function () {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_COURSETYPE.URI,
                     method: storeFront_Constants.API.GET_COURSETYPE.METHOD
                });
            },
            getAllCourseStatus: function () {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_COURSESTATUS.URI,
                     method: storeFront_Constants.API.GET_COURSESTATUS.METHOD
                });
            },
            getMyCourses: function () {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_MYCOURSES.URI,
                     method: storeFront_Constants.API.GET_MYCOURSES.METHOD
                });
            },
            getCatalogSearch: function (catalogName) {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_CATALOGSEARCH.URI.replace('{catalogName}', catalogName),
                     method: storeFront_Constants.API.GET_MYCOURSES.METHOD
                });
            },
            getCourseSearch: function (courseName) {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_COURSESEARCH.URI.replace('{courseName}', courseName),
                     method: storeFront_Constants.API.GET_MYCOURSES.METHOD
                });
            },
            getUsers: function () {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_USERS.URI,
                     method: storeFront_Constants.API.GET_USERS.METHOD
                });
            },
            getUserDetails: function (userId) {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_USERS_BYID.URI.replace('{userId}', userId),
                     method: storeFront_Constants.API.GET_USERS_BYID.METHOD
                });
            },
            getCourseByAuthor: function () {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_COURSE_BYAUTHOR.URI,
                     method: storeFront_Constants.API.GET_COURSE_BYAUTHOR.METHOD
                });
            },
            publishCourse: function (data) {
                return $http({
                     url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.CONFIGURE_PUBLISHING.URI,
                     method: storeFront_Constants.API.CONFIGURE_PUBLISHING.METHOD,
                     data: data
                });
            },
            getSignUpUserID: function(userId) {
              return userId;
            },
            getPreviewCourses: function () {
              return $http({
                   url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_PREVIEW_COURSES_BYID.URI,
                   method: storeFront_Constants.API.GET_PREVIEW_COURSES_BYID.METHOD
              });
            },
            getBookMrkCourses: function (data) {
              return $http({
                   url: storeFront_Constants.REST_API_BASE+storeFront_Constants.API.GET_BOOKMARKCOURSES.URI,
                   method: storeFront_Constants.API.GET_BOOKMARKCOURSES.METHOD,
                   data: data
              });
            }
      }
    }

})();
