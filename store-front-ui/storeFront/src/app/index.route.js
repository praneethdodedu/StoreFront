(function() {
  'use strict';

  angular
    .module('storeFront')
    .config(['$stateProvider', '$urlRouterProvider', '$httpProvider', routerConfig]);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider, $httpProvider) {
    $stateProvider
      .state('home', {
        url: '/home',
        templateUrl: 'app/main/main.html',
        controller: 'HomeController',
        controllerAs: 'home'
      })
      .state('storeFrontLogin', {
        url: '/',
        templateUrl: 'app/main/login.html',
        controller: 'LoginController',
        controllerAs: 'login'
      })
      .state('details', {
        url: '/details/:id',
        templateUrl: 'app/main/details.html',
        controller: 'DetailController',
        controllerAs: 'view'
      })
      .state('catalogDetails', {
        url: '/catalogDetails/:id',
        templateUrl: 'app/main/catalogDetails.html',
        controller: 'CatalogDtailController',
        controllerAs: 'catview'
      })
      .state('learningProg', {
        url: '/learningProg',
        templateUrl: 'app/main/learningProgView.html',
        controller: 'LearningProgController',
        controllerAs: 'lpview'
      })
      .state('learningProgDetails', {
        url: '/learningProg/:id',
        templateUrl: 'app/main/lpDetails.html',
        controller: 'LearningProgController',
        controllerAs: 'lpview'
      })
      .state('admin', {
        url: '/adminHome',
        templateUrl: 'app/main/admin.html',
        controller: 'AdminController',
        controllerAs: 'admview'
      })
      .state('adminDetails', {
        url: '/adminHome/details/:id',
        templateUrl: 'app/main/adminDetails.html',
        controller: 'AdminController',
        controllerAs: 'admdtlview'
      })
      .state('myCourses', {
       url: '/myCourses',
       templateUrl: 'app/main/myCoursesView.html',
       controller: 'HomeController',
       controllerAs: 'home'
     })
     .state('payment', {
      url: '/details/:id/payment',
      templateUrl: 'app/main/paymentPage.html',
      controller: 'DetailController',
      controllerAs: 'pymntview'
    });

      // .state('search', {
      //   url: '/search',
      //   templateUrl: 'app/main/search.html',
      //   controller: 'HomeController',
      //   controllerAs: 'home'
      // });

    $urlRouterProvider.otherwise('/home');
    $httpProvider.interceptors.push('StoreInterceptor');    // Enabling interceptors to handle common headers
    $httpProvider.defaults.withCredentials = true;
  }

})();
