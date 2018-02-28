(function() {
  'use strict';

  angular.module('storeFront')
    .constant('storeFront_Constants', {
        //'REST_API_BASE': 'http://10.18.15.21:8080',
        'REST_API_BASE': 'http://localhost:8080',
        'UI_BASE': 'http://localhost:3000',
        //'REST_API_BASE': 'http://10.18.38.234:8080',
        // 'NUXEO_BASE_URL': 'http://ec2-50-16-164-241.compute-1.amazonaws.com:8080/nuxeo/api',
        'AUTH_KEY': '3a8000db72edf3460d6960a0de5b1bb4 ',
        // 'NUXEO_BATCHID_REFRESH_INTERVAL': 29 * 60 * 1000,
        // 'NUXEO_BATCH_ID': '',
        // 'SECURITY': {
        //     'LOGGED': false,
        //     'TOKEN': '',
        //     'ROLE': ''
        // },
        'API': {
            'GET_COURSES': {
                'URI': '/courses',
                'METHOD': 'GET'
            },
            'GET_CATALOGS': {
                'URI': '/catalogs',
                'METHOD': 'GET'
            },
            'GET_COURSE_DETAILS': {
              'URI': '/courses/{id}',
              'METHOD': 'GET'
            },
            'GET_CATALOG_DETAILS': {
              'URI': '/catalogs/{id}',
              'METHOD': 'GET'
            },
            'GET_COURSETYPE': {
              'URI': '/getAllCourseTypes',
              'METHOD': 'GET'
            },
            'GET_COURSESTATUS': {
              'URI': '/getAllCourseStatus',
              'METHOD': 'GET'
            },
            'GET_LEARNING_PROGRAM': {
              'URI': '/learningPrograms',
              'METHOD': 'GET'
            },
            'GET_LEARNING_PROGRAM_BYID': {
              'URI': '/learningPrograms/{id}',
              'METHOD': 'GET'
            },
            'GET_MYCOURSES': {
              'URI': '/my-courses',
              'METHOD': 'GET'
            },
            'GET_CATALOGSEARCH': {
              'URI': '/catalogSearch?catalogName={catalogName}',
              'METHOD': 'GET'
            },
            'GET_COURSESEARCH': {
              'URI': '/courseSearch?courseName={courseName}',
              'METHOD': 'GET'
            },
            'GET_USERS': {
              'URI': '/users',
              'METHOD': 'GET'
            },
            'GET_USERS_BYID': {
              'URI': '/users/{id}',
              'METHOD': 'GET'
            },
            'GET_COURSE_BYAUTHOR': {
              'URI': '/getAllCoursesByAuthor',
              'METHOD': 'GET'
            },
            'CONFIGURE_PUBLISHING': {
              'URI': '/publishCourse',
              'METHOD': 'POST'
            },
            'FLUIDIC_PLAYER_URL': {
              'URI': '/getFluidicPlayerUrl',
              'METHOD': 'GET'
            },
            'REGISTER_URL': {
              'URI': '/users/signUpNewUser',
              'METHOD': 'POST'
            },
            'PREVIEW_COURSES_BYID_URL': {
              'URI': '/courses/previewCourse',
              'METHOD': 'POST'
            },
            'GET_PREVIEW_COURSES_BYID': {
              'URI': '/courses/getPreviewCourses',
              'METHOD': 'GET'
            },
            'GET_ACCOUNT_LISTS': {
              'URI': '/accounts',
              'METHOD': 'GET'
            },
            'GET_LOGIN_AUTHENTICATION': {
              'URI': '/authenticate/isLoggedIn',
              'METHOD': 'GET'
            },
            'GET_BOOKMARKCOURSES': {
              'URI': '/courses/bookMarkCourse',
              'METHOD': 'GET'
            },
            'POST_ENROLLCOURSES': {
              'URI': '/courses/enrollCourse ',
              'METHOD': 'POST'
            }

        }
    })

})();
